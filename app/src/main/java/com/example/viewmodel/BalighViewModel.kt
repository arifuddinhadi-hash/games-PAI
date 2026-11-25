package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppPreferences
import com.example.data.local.BalighDatabase
import com.example.data.local.FormativeResultEntity
import com.example.data.local.ReflectionEntity
import com.example.data.local.StudentAnswerEntity
import com.example.data.local.StudentSessionEntity
import com.example.data.local.SummativeResultEntity
import com.example.data.local.TeacherSettingsEntity
import com.example.data.repository.BalighRepository
import com.example.util.SoundAndFeedbackManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BalighViewModel(application: Application) : AndroidViewModel(application) {

    val repository: BalighRepository = BalighRepository(
        dao = BalighDatabase.getInstance(application).balighDao(),
        prefs = AppPreferences(application)
    )
    val soundManager = SoundAndFeedbackManager()

    val isMuted: StateFlow<Boolean> = repository.isMuted
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val textScale: StateFlow<Float> = repository.textScale
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1.0f)

    val settings: StateFlow<TeacherSettingsEntity> = repository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TeacherSettingsEntity())

    val activeStudentId: StateFlow<String?> = repository.activeStudentId
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val activeStudent: StateFlow<StudentSessionEntity?> = activeStudentId
        .flatMapLatest { id ->
            if (id.isNullOrBlank()) flowOf(null) else repository.observeStudent(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val activeFormativeResults: StateFlow<List<FormativeResultEntity>> = activeStudentId
        .flatMapLatest { id ->
            if (id.isNullOrBlank()) flowOf(emptyList()) else repository.observeFormativeForStudent(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeLatestSummative: StateFlow<SummativeResultEntity?> = activeStudentId
        .flatMapLatest { id ->
            if (id.isNullOrBlank()) flowOf(null) else repository.observeLatestSummative(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val activeLatestReflection: StateFlow<ReflectionEntity?> = activeStudentId
        .flatMapLatest { id ->
            if (id.isNullOrBlank()) flowOf(null) else repository.observeLatestReflection(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allStudents: StateFlow<List<StudentSessionEntity>> = repository.allStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureDefaultSettings()
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun toggleMute() {
        viewModelScope.launch {
            val next = !isMuted.value
            repository.setMuted(next)
            if (!next) soundManager.playTap(false)
        }
    }

    fun cycleTextScale() {
        viewModelScope.launch {
            val current = textScale.value
            val next = when {
                current < 1.08f -> 1.15f
                current < 1.24f -> 1.3f
                else -> 1.0f
            }
            repository.setTextScale(next)
            soundManager.playTap(isMuted.value)
        }
    }

    fun startStudentSession(
        nickname: String,
        className: String,
        groupName: String,
        avatarId: String,
        onReady: () -> Unit
    ) {
        viewModelScope.launch {
            soundManager.playBadgeEarned(isMuted.value)
            repository.createOrSelectStudent(nickname, className, groupName, avatarId)
            tryAutoSyncInBackground()
            onReady()
        }
    }

    fun switchStudent(studentId: String?, onReady: () -> Unit = {}) {
        viewModelScope.launch {
            repository.switchActiveStudent(studentId)
            onReady()
        }
    }

    fun awardGameXp(posNumber: Int, xp: Int, activityName: String) {
        val sid = activeStudentId.value ?: return
        viewModelScope.launch {
            repository.addXpAndBadge(
                studentId = sid,
                posNumber = posNumber,
                xpDelta = xp,
                badgeId = null,
                markPosCompleted = false,
                activityName = activityName
            )
            tryAutoSyncInBackground()
        }
    }

    fun submitDiagnostic(answers: List<StudentAnswerEntity>, onDone: () -> Unit) {
        val sid = activeStudentId.value ?: return
        viewModelScope.launch {
            repository.recordDiagnosticResult(sid, answers)
            soundManager.playBadgeEarned(isMuted.value)
            tryAutoSyncInBackground()
            onDone()
        }
    }

    fun submitFormative(
        posNumber: Int,
        answers: List<StudentAnswerEntity>,
        onResult: (FormativeResultEntity) -> Unit
    ) {
        val sid = activeStudentId.value ?: return
        viewModelScope.launch {
            val res = repository.recordFormativeResult(sid, posNumber, answers)
            soundManager.playBadgeEarned(isMuted.value)
            tryAutoSyncInBackground()
            onResult(res)
        }
    }

    fun submitFinalChallenge(bonusXp: Int, onDone: () -> Unit) {
        val sid = activeStudentId.value ?: return
        viewModelScope.launch {
            repository.recordFinalChallengeDone(sid, bonusXp)
            soundManager.playBadgeEarned(isMuted.value)
            tryAutoSyncInBackground()
            onDone()
        }
    }

    fun submitSummative(
        answers: List<StudentAnswerEntity>,
        onResult: (SummativeResultEntity) -> Unit
    ) {
        val sid = activeStudentId.value ?: return
        viewModelScope.launch {
            val res = repository.recordSummativeResult(sid, answers)
            soundManager.playBadgeEarned(isMuted.value)
            tryAutoSyncInBackground()
            onResult(res)
        }
    }

    fun submitReflection(moodEmoji: String, summary: String, onDone: () -> Unit) {
        val sid = activeStudentId.value ?: return
        viewModelScope.launch {
            repository.saveReflection(sid, moodEmoji, summary)
            soundManager.playBadgeEarned(isMuted.value)
            _toastMessage.value = "Terima kasih! Refleksimu tersimpan (+15 XP)."
            tryAutoSyncInBackground()
            onDone()
        }
    }

    private suspend fun tryAutoSyncInBackground() {
        val curSettings = settings.value
        if (curSettings.offlineOnlyMode) return
        val url = curSettings.appsScriptUrl.trim()
        if (url.startsWith("https://script.google.com/")) {
            repository.syncAllUnsyncedToSheets()
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
