package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.curriculum.AssessmentBankData
import com.example.data.local.ActivityLogEntity
import com.example.data.local.FormativeResultEntity
import com.example.data.local.ReflectionEntity
import com.example.data.local.StudentAnswerEntity
import com.example.data.local.StudentSessionEntity
import com.example.data.local.SummativeResultEntity
import com.example.data.local.TeacherSettingsEntity
import com.example.data.repository.BalighRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class QuestionAnalyticsRow(
    val questionId: String,
    val posNumber: Int,
    val materialTopic: String,
    val indicator: String,
    val attempts: Int,
    val wrongCount: Int,
    val errorRatePercent: Int
)

data class PosMasteryRow(
    val posNumber: Int,
    val title: String,
    val averageScore: Int,
    val attemptsCount: Int
)

data class TeacherDashboardState(
    val settings: TeacherSettingsEntity = TeacherSettingsEntity(),
    val students: List<StudentSessionEntity> = emptyList(),
    val formativeResults: List<FormativeResultEntity> = emptyList(),
    val summativeResults: List<SummativeResultEntity> = emptyList(),
    val allAnswers: List<StudentAnswerEntity> = emptyList(),
    val logs: List<ActivityLogEntity> = emptyList(),
    val reflections: List<ReflectionEntity> = emptyList(),
    val averageFormative: Int = 0,
    val averageSummative: Int = 0,
    val highestSummative: Int = 0,
    val lowestSummative: Int = 0,
    val passRatePercent: Int = 0,
    val posMastery: List<PosMasteryRow> = emptyList(),
    val questionAnalytics: List<QuestionAnalyticsRow> = emptyList(),
    val remedialStudents: List<Pair<StudentSessionEntity, Int?>> = emptyList(),
    val enrichmentStudents: List<Pair<StudentSessionEntity, Int>> = emptyList(),
    val incompleteStudents: List<StudentSessionEntity> = emptyList(),
    val unsyncedCount: Int = 0
)

class TeacherDashboardViewModel(
    private val repository: BalighRepository
) : ViewModel() {

    private val _isUnlocked = MutableStateFlow(false)
    val isUnlocked: StateFlow<Boolean> = _isUnlocked.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private val _unsyncedCount = MutableStateFlow(0)

    val uiState: StateFlow<TeacherDashboardState> = combine(
        repository.settings,
        repository.allStudents,
        repository.allFormative,
        repository.allSummative,
        repository.allAnswers,
        repository.allLogs,
        repository.allReflections,
        _unsyncedCount
    ) { values ->
        @Suppress("UNCHECKED_CAST")
        val settings = values[0] as TeacherSettingsEntity
        @Suppress("UNCHECKED_CAST")
        val students = values[1] as List<StudentSessionEntity>
        @Suppress("UNCHECKED_CAST")
        val formative = values[2] as List<FormativeResultEntity>
        @Suppress("UNCHECKED_CAST")
        val summative = values[3] as List<SummativeResultEntity>
        @Suppress("UNCHECKED_CAST")
        val answers = values[4] as List<StudentAnswerEntity>
        @Suppress("UNCHECKED_CAST")
        val logs = values[5] as List<ActivityLogEntity>
        @Suppress("UNCHECKED_CAST")
        val reflections = values[6] as List<ReflectionEntity>
        val unsynced = values[7] as Int

        val avgFormative = if (formative.isEmpty()) 0 else formative.map { it.scorePercent }.average().toInt()
        val latestSumByStudent = summative.groupBy { it.studentId }.mapValues { it.value.first() }
        val sumScores = latestSumByStudent.values.map { it.finalScore }
        val avgSummative = if (sumScores.isEmpty()) 0 else sumScores.average().toInt()
        val highSum = sumScores.maxOrNull() ?: 0
        val lowSum = sumScores.minOrNull() ?: 0
        val passedCount = sumScores.count { it >= settings.kktp }
        val passRate = if (sumScores.isEmpty()) 0 else (passedCount * 100) / sumScores.size

        val posTitles = mapOf(
            1 to "Pos 1: Mengenal Baligh",
            2 to "Pos 2: Tanda Baligh",
            3 to "Pos 3: Kebersihan",
            4 to "Pos 4: Diri & Privasi",
            5 to "Pos 5: Tanggung Jawab"
        )
        val posMastery = (1..5).map { p ->
            val items = formative.filter { it.posNumber == p }
            PosMasteryRow(
                posNumber = p,
                title = posTitles[p] ?: "Pos $p",
                averageScore = if (items.isEmpty()) 0 else items.map { it.scorePercent }.average().toInt(),
                attemptsCount = items.size
            )
        }

        val bankMap = AssessmentBankData.allQuestionsForBank.associateBy { it.id }
        val qAnalytics = answers
            .groupBy { it.questionId }
            .map { (qId, list) ->
                val meta = bankMap[qId]
                val wrong = list.count { !it.isCorrect }
                QuestionAnalyticsRow(
                    questionId = qId,
                    posNumber = meta?.posNumber ?: list.first().posNumber,
                    materialTopic = meta?.materialTopic ?: "Materi PAI",
                    indicator = meta?.indicator ?: "-",
                    attempts = list.size,
                    wrongCount = wrong,
                    errorRatePercent = if (list.isEmpty()) 0 else (wrong * 100) / list.size
                )
            }
            .sortedByDescending { it.errorRatePercent }

        val remedial = students.mapNotNull { s ->
            val sum = latestSumByStudent[s.studentId]?.finalScore
            val formAvg = formative.filter { it.studentId == s.studentId }.map { it.scorePercent }.takeIf { it.isNotEmpty() }?.average()?.toInt()
            if ((sum != null && sum < settings.kktp) || (sum == null && formAvg != null && formAvg < settings.kktp)) {
                s to (sum ?: formAvg)
            } else null
        }

        val enrichment = students.mapNotNull { s ->
            val sum = latestSumByStudent[s.studentId]?.finalScore
            if (sum != null && sum >= settings.categoryVeryGoodMin) {
                s to sum
            } else null
        }

        val incomplete = students.filter { it.completedPosSet().size < 5 || !it.summativeDone }

        TeacherDashboardState(
            settings = settings,
            students = students,
            formativeResults = formative,
            summativeResults = summative,
            allAnswers = answers,
            logs = logs,
            reflections = reflections,
            averageFormative = avgFormative,
            averageSummative = avgSummative,
            highestSummative = highSum,
            lowestSummative = lowSum,
            passRatePercent = passRate,
            posMastery = posMastery,
            questionAnalytics = qAnalytics,
            remedialStudents = remedial,
            enrichmentStudents = enrichment,
            incompleteStudents = incomplete,
            unsyncedCount = unsynced
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TeacherDashboardState())

    init {
        refreshUnsyncedCount()
    }

    fun refreshUnsyncedCount() {
        viewModelScope.launch {
            _unsyncedCount.value = repository.getUnsyncedCount()
        }
    }

    fun verifyPin(inputPin: String): Boolean {
        val valid = inputPin.trim() == uiState.value.settings.teacherPin
        if (valid) {
            _isUnlocked.value = true
            refreshUnsyncedCount()
        }
        return valid
    }

    fun lockDashboard() {
        _isUnlocked.value = false
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    fun saveSettings(updated: TeacherSettingsEntity) {
        viewModelScope.launch {
            repository.updateSettings(updated)
            _statusMessage.value = "Pengaturan sekolah & KKTP berhasil disimpan."
        }
    }

    fun testSheetsConnection(url: String) {
        viewModelScope.launch {
            _isSyncing.value = true
            val res = repository.testSheetsConnection(url)
            _isSyncing.value = false
            _statusMessage.value = res.fold(
                onSuccess = { "Koneksi berhasil ke spreadsheet: ${it.spreadsheetName ?: "Google Sheets"}!" },
                onFailure = { "Koneksi gagal: ${it.localizedMessage}" }
            )
        }
    }

    fun syncNow() {
        viewModelScope.launch {
            _isSyncing.value = true
            val res = repository.syncAllUnsyncedToSheets()
            _isSyncing.value = false
            refreshUnsyncedCount()
            _statusMessage.value = res.fold(
                onSuccess = { "Sinkronisasi selesai (${it.syncedRows ?: 0} baris terkirim ke Google Sheets)." },
                onFailure = { "Sinkronisasi tertunda: ${it.localizedMessage}" }
            )
        }
    }

    fun resetAllSessions() {
        viewModelScope.launch {
            repository.resetAllSessions()
            refreshUnsyncedCount()
            _statusMessage.value = "Seluruh sesi murid lokal berhasil direset."
        }
    }

    fun exportOfflineBackupJson(onJsonReady: (String) -> Unit) {
        viewModelScope.launch {
            val json = repository.exportOfflineBackupJson()
            onJsonReady(json)
        }
    }

    fun importOfflineBackupJson(json: String) {
        viewModelScope.launch {
            val res = repository.importOfflineBackupJson(json)
            refreshUnsyncedCount()
            _statusMessage.value = res.fold(
                onSuccess = { count -> "Impor backup offline berhasil ($count profil murid baru ditambahkan)." },
                onFailure = { err -> "Gagal mengimpor backup: ${err.localizedMessage}" }
            )
        }
    }

    class Factory(private val repository: BalighRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TeacherDashboardViewModel(repository) as T
        }
    }
}
