package com.example.data.repository

import com.example.data.curriculum.AssessmentBankData
import com.example.data.curriculum.PosCurriculumData
import com.example.data.local.ActivityLogEntity
import com.example.data.local.AppPreferences
import com.example.data.local.BalighDao
import com.example.data.local.FormativeResultEntity
import com.example.data.local.ReflectionEntity
import com.example.data.local.StudentAnswerEntity
import com.example.data.local.StudentSessionEntity
import com.example.data.local.SummativeResultEntity
import com.example.data.local.TeacherSettingsEntity
import com.example.data.remote.AppsScriptSyncService
import com.example.data.remote.SheetActivityLogDto
import com.example.data.remote.SheetAnswerDto
import com.example.data.remote.SheetBankSoalDto
import com.example.data.remote.SheetFormativeDto
import com.example.data.remote.SheetIdentityDto
import com.example.data.remote.SheetSettingsDto
import com.example.data.remote.SheetSummativeDto
import com.example.data.remote.SheetSyncPayload
import com.example.data.remote.SheetSyncResponse
import com.example.util.ExportAndCertificateHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class BalighRepository(
    private val dao: BalighDao,
    private val prefs: AppPreferences,
    private val syncService: AppsScriptSyncService = AppsScriptSyncService()
) {
    val activeStudentId: Flow<String?> = prefs.activeStudentId
    val isMuted: Flow<Boolean> = prefs.isMuted
    val textScale: Flow<Float> = prefs.textScale

    val allStudents: Flow<List<StudentSessionEntity>> = dao.observeAllStudents()
    val allAnswers: Flow<List<StudentAnswerEntity>> = dao.observeAllAnswers()
    val allFormative: Flow<List<FormativeResultEntity>> = dao.observeAllFormative()
    val allSummative: Flow<List<SummativeResultEntity>> = dao.observeAllSummative()
    val allLogs: Flow<List<ActivityLogEntity>> = dao.observeAllLogs()
    val allReflections: Flow<List<ReflectionEntity>> = dao.observeAllReflections()

    val settings: Flow<TeacherSettingsEntity> = dao.observeSettings().map {
        it ?: TeacherSettingsEntity()
    }

    fun observeStudent(studentId: String): Flow<StudentSessionEntity?> = dao.observeStudent(studentId)
    fun observeFormativeForStudent(studentId: String): Flow<List<FormativeResultEntity>> =
        dao.observeFormativeForStudent(studentId)
    fun observeLatestSummative(studentId: String): Flow<SummativeResultEntity?> =
        dao.observeLatestSummativeForStudent(studentId)
    fun observeAnswersForStudent(studentId: String): Flow<List<StudentAnswerEntity>> =
        dao.observeAnswersForStudent(studentId)
    fun observeLatestReflection(studentId: String): Flow<ReflectionEntity?> =
        dao.observeLatestReflection(studentId)

    suspend fun ensureDefaultSettings() {
        if (dao.getSettings() == null) {
            dao.upsertSettings(TeacherSettingsEntity())
        }
    }

    suspend fun setMuted(muted: Boolean) = prefs.setMuted(muted)
    suspend fun setTextScale(scale: Float) = prefs.setTextScale(scale)

    suspend fun createOrSelectStudent(
        nickname: String,
        className: String,
        groupName: String,
        avatarId: String
    ): StudentSessionEntity {
        val cleanNickname = nickname.trim().take(32)
        val cleanClass = className.trim().ifBlank { "IV A" }.take(20)
        val cleanGroup = groupName.trim().ifBlank { "Kelompok 1" }.take(25)
        val studentId = "MRD-${UUID.randomUUID().toString().take(8).uppercase()}"
        val sessionCode = "SESI-${System.currentTimeMillis().toString().takeLast(4)}"
        val student = StudentSessionEntity(
            studentId = studentId,
            nickname = cleanNickname,
            className = cleanClass,
            groupName = cleanGroup,
            avatarId = avatarId,
            sessionDate = ExportAndCertificateHelper.todayDate(),
            sessionCode = sessionCode
        )
        dao.upsertStudent(student)
        prefs.setActiveStudentId(student.studentId)
        logActivity(student.studentId, 0, "Memulai Petualangan Baligh", 10, "", "Mulai")
        return student
    }

    suspend fun switchActiveStudent(studentId: String?) {
        prefs.setActiveStudentId(studentId)
    }

    suspend fun addXpAndBadge(
        studentId: String,
        posNumber: Int,
        xpDelta: Int,
        badgeId: String? = null,
        markPosCompleted: Boolean = false,
        activityName: String
    ) {
        val current = dao.getStudentById(studentId) ?: return
        val updatedPos = current.completedPosSet().toMutableSet()
        if (markPosCompleted && posNumber in 1..5) {
            updatedPos.add(posNumber)
        }
        val updatedBadges = current.badgesList().toMutableList()
        if (!badgeId.isNullOrBlank() && !updatedBadges.contains(badgeId)) {
            updatedBadges.add(badgeId)
        }
        val updated = current.copy(
            totalXp = current.totalXp + xpDelta.coerceAtLeast(0),
            completedPosCsv = updatedPos.sorted().joinToString(","),
            badgesCsv = updatedBadges.joinToString(","),
            syncedToSheets = false,
            lastUpdatedMillis = System.currentTimeMillis()
        )
        dao.upsertStudent(updated)
        logActivity(
            studentId = studentId,
            posNumber = posNumber,
            activityName = activityName,
            xpEarned = xpDelta.coerceAtLeast(0),
            badgeEarned = badgeId.orEmpty(),
            status = if (markPosCompleted) "Selesai" else "Progres"
        )
    }

    suspend fun recordDiagnosticResult(
        studentId: String,
        answers: List<StudentAnswerEntity>
    ) {
        val current = dao.getStudentById(studentId) ?: return
        dao.insertAnswers(answers)
        val xpBonus = answers.count { it.isCorrect } * 5 + 15
        dao.upsertStudent(
            current.copy(
                diagnosticDone = true,
                totalXp = current.totalXp + xpBonus,
                syncedToSheets = false,
                lastUpdatedMillis = System.currentTimeMillis()
            )
        )
        logActivity(studentId, 0, "Asesmen Diagnostik (10 Soal)", xpBonus, "", "Selesai")
    }

    suspend fun recordFormativeResult(
        studentId: String,
        posNumber: Int,
        answers: List<StudentAnswerEntity>
    ): FormativeResultEntity {
        val current = dao.getStudentById(studentId)
            ?: throw IllegalStateException("Sesi murid tidak ditemukan.")
        val currentSettings = dao.getSettings() ?: TeacherSettingsEntity()
        dao.insertAnswers(answers)

        val total = answers.size.coerceAtLeast(1)
        val correct = answers.count { it.isCorrect }
        val wrong = total - correct
        val earned = answers.sumOf { it.earnedScore }
        val max = answers.sumOf { it.maxScore }.coerceAtLeast(1)
        val percent = (earned * 100) / max

        val mastery = when {
            percent >= currentSettings.categoryVeryGoodMin -> "Sangat Baik"
            percent >= currentSettings.categoryGoodMin -> "Baik"
            percent >= currentSettings.kktp -> "Tuntas / Cukup"
            else -> "Perlu Pendampingan"
        }

        val rec = if (percent < currentSettings.kktp) {
            when (posNumber) {
                1 -> "Berikan ulang materi pengertian baligh, istilah mukalaf, dan latihan pemahaman Pos 1."
                2 -> "Berikan kartu fakta tanda baligh santun dan diskusi pendampingan Pos 2."
                3 -> "Latih ulang urutan langkah bersuci & mandi wajib serta materi kebersihan Pos 3."
                4 -> "Berikan latihan situasi menjaga aurat, batasan diri, dan privasi digital Pos 4."
                else -> "Penguatan kebiasaan salat, hormat orang tua/guru, dan 7 sikap tanggung jawab Pos 5."
            }
        } else {
            "Menguasai materi Pos $posNumber dengan baik. Siap melanjutkan atau mengikuti pengayaan."
        }

        val result = FormativeResultEntity(
            timestamp = ExportAndCertificateHelper.nowTimestamp(),
            studentId = current.studentId,
            nickname = current.nickname,
            className = current.className,
            posNumber = posNumber,
            totalQuestions = total,
            correctCount = correct,
            wrongCount = wrong,
            scorePercent = percent,
            masteryStatus = mastery,
            recommendation = rec
        )
        dao.insertFormativeResult(result)

        val posBadge = PosCurriculumData.posModules.firstOrNull { it.posNumber == posNumber }?.badgeId
        val xpGain = correct * 10 + 10
        addXpAndBadge(
            studentId = studentId,
            posNumber = posNumber,
            xpDelta = xpGain,
            badgeId = posBadge,
            markPosCompleted = true,
            activityName = "Formatif Pos $posNumber (Skor $percent)"
        )
        return result
    }

    suspend fun recordFinalChallengeDone(studentId: String, bonusXp: Int) {
        val current = dao.getStudentById(studentId) ?: return
        val updatedBadges = current.badgesList().toMutableList()
        if (!updatedBadges.contains("badge_final_explorer")) {
            updatedBadges.add("badge_final_explorer")
        }
        dao.upsertStudent(
            current.copy(
                finalChallengeDone = true,
                totalXp = current.totalXp + bonusXp.coerceAtLeast(0),
                badgesCsv = updatedBadges.joinToString(","),
                syncedToSheets = false,
                lastUpdatedMillis = System.currentTimeMillis()
            )
        )
        logActivity(studentId, 5, "Tantangan Final", bonusXp, "badge_final_explorer", "Selesai")
    }

    suspend fun recordSummativeResult(
        studentId: String,
        answers: List<StudentAnswerEntity>
    ): SummativeResultEntity {
        val current = dao.getStudentById(studentId)
            ?: throw IllegalStateException("Sesi murid tidak ditemukan.")
        val currentSettings = dao.getSettings() ?: TeacherSettingsEntity()
        dao.insertAnswers(answers)

        val earned = answers.sumOf { it.earnedScore }
        val max = answers.sumOf { it.maxScore }.coerceAtLeast(1)
        val finalScore = (earned * 100) / max

        val category = when {
            finalScore >= currentSettings.categoryVeryGoodMin -> "Sangat Baik"
            finalScore >= currentSettings.categoryGoodMin -> "Baik"
            finalScore >= currentSettings.categoryFairMin -> "Cukup"
            else -> "Perlu Pendampingan"
        }
        val passStatus = if (finalScore >= currentSettings.kktp) {
            "Tuntas ($category)"
        } else {
            "Perlu Pendampingan ($category)"
        }

        // Compute weakest Pos for personalized recommendation
        val wrongByPos = answers.filter { !it.isCorrect && it.posNumber in 1..5 }
            .groupingBy { it.posNumber }
            .eachCount()
        val weakestPos = wrongByPos.maxByOrNull { it.value }?.key

        val rec = when {
            finalScore >= currentSettings.categoryVeryGoodMin ->
                "Luar biasa! Disarankan mengikuti tantangan pengayaan poster/peta konsep."
            finalScore >= currentSettings.kktp ->
                "Tuntas melampaui KKTP (${currentSettings.kktp}). Terus jaga ibadah dan kebersihan."
            weakestPos == 1 -> "Perlu penguatan Pos 1: pengertian baligh dan tanggung jawab mukalaf."
            weakestPos == 2 -> "Perlu penguatan Pos 2: kartu fakta tanda-tanda baligh secara santun."
            weakestPos == 3 -> "Perlu penguatan Pos 3: materi kebersihan dan urutan mandi wajib."
            weakestPos == 4 -> "Perlu penguatan Pos 4: latihan situasi menjaga aurat dan privasi digital."
            weakestPos == 5 -> "Perlu penguatan Pos 5: tanggung jawab salat dan akhlak baik."
            else -> "Perlu pendampingan belajar ulang dan latihan remedial ramah anak."
        }

        val result = SummativeResultEntity(
            timestamp = ExportAndCertificateHelper.nowTimestamp(),
            studentId = current.studentId,
            nickname = current.nickname,
            className = current.className,
            totalQuestions = answers.size,
            earnedScore = earned,
            maxScore = max,
            finalScore = finalScore,
            passStatus = passStatus,
            recommendation = rec
        )
        dao.insertSummativeResult(result)

        val xpGain = answers.count { it.isCorrect } * 10 + 25
        val updatedBadges = current.badgesList().toMutableList()
        if (!updatedBadges.contains("badge_lulus_baligh")) {
            updatedBadges.add("badge_lulus_baligh")
        }
        dao.upsertStudent(
            current.copy(
                summativeDone = true,
                totalXp = current.totalXp + xpGain,
                badgesCsv = updatedBadges.joinToString(","),
                syncedToSheets = false,
                lastUpdatedMillis = System.currentTimeMillis()
            )
        )
        logActivity(studentId, 0, "Asesmen Sumatif (Nilai $finalScore)", xpGain, "badge_lulus_baligh", "Selesai")
        return result
    }

    suspend fun saveReflection(
        studentId: String,
        moodEmoji: String,
        answersSummary: String
    ) {
        val current = dao.getStudentById(studentId) ?: return
        dao.insertReflection(
            ReflectionEntity(
                timestamp = ExportAndCertificateHelper.nowTimestamp(),
                studentId = current.studentId,
                nickname = current.nickname,
                moodEmoji = moodEmoji,
                answersSummary = answersSummary
            )
        )
        dao.upsertStudent(
            current.copy(
                reflectionDone = true,
                totalXp = current.totalXp + 15,
                lastUpdatedMillis = System.currentTimeMillis()
            )
        )
        logActivity(studentId, 0, "Mengisi Refleksi Diri", 15, "", "Selesai")
    }

    suspend fun logActivity(
        studentId: String,
        posNumber: Int,
        activityName: String,
        xpEarned: Int,
        badgeEarned: String,
        status: String
    ) {
        dao.insertActivityLog(
            ActivityLogEntity(
                timestamp = ExportAndCertificateHelper.nowTimestamp(),
                studentId = studentId,
                posNumber = posNumber,
                activityName = activityName,
                xpEarned = xpEarned,
                badgeEarned = badgeEarned,
                completionStatus = status
            )
        )
    }

    suspend fun updateSettings(settings: TeacherSettingsEntity) {
        dao.upsertSettings(settings)
    }

    suspend fun resetAllSessions() {
        dao.clearAllAnswers()
        dao.clearAllFormative()
        dao.clearAllSummative()
        dao.clearAllLogs()
        dao.clearAllReflections()
        dao.clearAllStudents()
        prefs.setActiveStudentId(null)
    }

    suspend fun testSheetsConnection(url: String): Result<SheetSyncResponse> =
        syncService.pingWebApp(url)

    suspend fun syncAllUnsyncedToSheets(): Result<SheetSyncResponse> {
        val currentSettings = dao.getSettings() ?: TeacherSettingsEntity()
        if (currentSettings.offlineOnlyMode) {
            return Result.failure(IllegalStateException("Mode Offline Penuh sedang aktif. Nonaktifkan mode offline jika ingin mengirim ke Google Sheets."))
        }
        val url = currentSettings.appsScriptUrl.trim()
        if (url.isBlank()) {
            return Result.failure(IllegalStateException("URL Web App Google Apps Script belum diisi di Pengaturan Guru."))
        }

        val unsyncedStudents = dao.getUnsyncedStudents()
        val unsyncedAnswers = dao.getUnsyncedAnswers()
        val unsyncedFormative = dao.getUnsyncedFormative()
        val unsyncedSummative = dao.getUnsyncedSummative()
        val unsyncedLogs = dao.getUnsyncedLogs()

        val payload = SheetSyncPayload(
            identitas = unsyncedStudents.map {
                SheetIdentityDto(
                    studentId = it.studentId,
                    nickname = it.nickname,
                    className = it.className,
                    groupName = it.groupName,
                    date = it.sessionDate,
                    sessionCode = it.sessionCode
                )
            },
            answers = unsyncedAnswers.map {
                SheetAnswerDto(
                    timestamp = it.timestamp,
                    studentId = it.studentId,
                    nickname = it.nickname,
                    className = it.className,
                    questionId = it.questionId,
                    assessmentType = it.assessmentType,
                    posNumber = it.posNumber,
                    studentAnswer = it.studentAnswer,
                    correctAnswer = it.correctAnswer,
                    isCorrect = it.isCorrect,
                    earnedScore = it.earnedScore,
                    maxScore = it.maxScore,
                    durationSeconds = it.durationSeconds
                )
            },
            formativeResults = unsyncedFormative.map {
                SheetFormativeDto(
                    timestamp = it.timestamp,
                    studentId = it.studentId,
                    nickname = it.nickname,
                    className = it.className,
                    posNumber = it.posNumber,
                    totalQuestions = it.totalQuestions,
                    correctCount = it.correctCount,
                    wrongCount = it.wrongCount,
                    scorePercent = it.scorePercent,
                    masteryStatus = it.masteryStatus,
                    recommendation = it.recommendation
                )
            },
            summativeResults = unsyncedSummative.map {
                SheetSummativeDto(
                    timestamp = it.timestamp,
                    studentId = it.studentId,
                    nickname = it.nickname,
                    className = it.className,
                    totalQuestions = it.totalQuestions,
                    earnedScore = it.earnedScore,
                    maxScore = it.maxScore,
                    finalScore = it.finalScore,
                    passStatus = it.passStatus,
                    recommendation = it.recommendation
                )
            },
            activityLogs = unsyncedLogs.map {
                SheetActivityLogDto(
                    timestamp = it.timestamp,
                    studentId = it.studentId,
                    posNumber = it.posNumber,
                    activityName = it.activityName,
                    xpEarned = it.xpEarned,
                    badgeEarned = it.badgeEarned,
                    completionStatus = it.completionStatus
                )
            },
            settings = SheetSettingsDto(
                schoolName = currentSettings.schoolName,
                className = currentSettings.className,
                kktp = currentSettings.kktp,
                questionCount = currentSettings.questionCount,
                maxScore = currentSettings.maxScore,
                teacherName = currentSettings.teacherName,
                semester = currentSettings.semester,
                academicYear = currentSettings.academicYear
            ),
            bankSoal = AssessmentBankData.allQuestionsForBank.map { q ->
                SheetBankSoalDto(
                    id = q.id,
                    assessmentType = q.assessmentType.label,
                    posNumber = q.posNumber,
                    material = q.materialTopic,
                    indicator = q.indicator,
                    question = q.question,
                    questionType = q.questionType.label,
                    optionA = q.options.getOrElse(0) { "" },
                    optionB = q.options.getOrElse(1) { "" },
                    optionC = q.options.getOrElse(2) { "" },
                    optionD = q.options.getOrElse(3) { "" },
                    correctAnswer = q.correctAnswer,
                    score = q.maxScore,
                    feedbackCorrect = q.feedbackCorrect,
                    feedbackWrong = q.feedbackWrong,
                    difficulty = q.difficulty
                )
            }
        )

        val result = syncService.syncBatch(url, payload)
        if (result.isSuccess) {
            if (unsyncedStudents.isNotEmpty()) dao.markStudentsSynced(unsyncedStudents.map { it.studentId })
            if (unsyncedAnswers.isNotEmpty()) dao.markAnswersSynced(unsyncedAnswers.map { it.id })
            if (unsyncedFormative.isNotEmpty()) dao.markFormativeSynced(unsyncedFormative.map { it.id })
            if (unsyncedSummative.isNotEmpty()) dao.markSummativeSynced(unsyncedSummative.map { it.id })
            if (unsyncedLogs.isNotEmpty()) dao.markLogsSynced(unsyncedLogs.map { it.id })
        }
        return result
    }

    suspend fun getUnsyncedCount(): Int {
        return dao.getUnsyncedStudents().size +
            dao.getUnsyncedAnswers().size +
            dao.getUnsyncedFormative().size +
            dao.getUnsyncedSummative().size +
            dao.getUnsyncedLogs().size
    }

    suspend fun exportOfflineBackupJson(): String {
        val students = dao.observeAllStudents().first()
        val answers = dao.observeAllAnswers().first()
        val formatives = dao.observeAllFormative().first()
        val summatives = dao.observeAllSummative().first()

        val root = JSONObject()
        root.put("app", "Petualangan Baligh")
        root.put("exportedAt", ExportAndCertificateHelper.nowTimestamp())

        val stuArr = JSONArray()
        students.forEach { s ->
            stuArr.put(JSONObject().apply {
                put("studentId", s.studentId)
                put("nickname", s.nickname)
                put("className", s.className)
                put("groupName", s.groupName)
                put("avatarId", s.avatarId)
                put("sessionDate", s.sessionDate)
                put("sessionCode", s.sessionCode)
                put("totalXp", s.totalXp)
                put("completedPosCsv", s.completedPosCsv)
                put("badgesCsv", s.badgesCsv)
                put("diagnosticDone", s.diagnosticDone)
                put("finalChallengeDone", s.finalChallengeDone)
                put("summativeDone", s.summativeDone)
                put("reflectionDone", s.reflectionDone)
            })
        }
        root.put("students", stuArr)

        val formArr = JSONArray()
        formatives.forEach { f ->
            formArr.put(JSONObject().apply {
                put("timestamp", f.timestamp)
                put("studentId", f.studentId)
                put("nickname", f.nickname)
                put("className", f.className)
                put("posNumber", f.posNumber)
                put("totalQuestions", f.totalQuestions)
                put("correctCount", f.correctCount)
                put("wrongCount", f.wrongCount)
                put("scorePercent", f.scorePercent)
                put("masteryStatus", f.masteryStatus)
                put("recommendation", f.recommendation)
            })
        }
        root.put("formatives", formArr)

        val sumArr = JSONArray()
        summatives.forEach { sm ->
            sumArr.put(JSONObject().apply {
                put("timestamp", sm.timestamp)
                put("studentId", sm.studentId)
                put("nickname", sm.nickname)
                put("className", sm.className)
                put("totalQuestions", sm.totalQuestions)
                put("earnedScore", sm.earnedScore)
                put("maxScore", sm.maxScore)
                put("finalScore", sm.finalScore)
                put("passStatus", sm.passStatus)
                put("recommendation", sm.recommendation)
            })
        }
        root.put("summatives", sumArr)

        val ansArr = JSONArray()
        answers.forEach { a ->
            ansArr.put(JSONObject().apply {
                put("timestamp", a.timestamp)
                put("studentId", a.studentId)
                put("nickname", a.nickname)
                put("className", a.className)
                put("questionId", a.questionId)
                put("assessmentType", a.assessmentType)
                put("posNumber", a.posNumber)
                put("studentAnswer", a.studentAnswer)
                put("correctAnswer", a.correctAnswer)
                put("isCorrect", a.isCorrect)
                put("earnedScore", a.earnedScore)
                put("maxScore", a.maxScore)
                put("durationSeconds", a.durationSeconds)
            })
        }
        root.put("answers", ansArr)

        return root.toString(2)
    }

    suspend fun importOfflineBackupJson(json: String): Result<Int> = runCatching {
        val root = JSONObject(json)
        var importedStudents = 0
        val stuArr = root.optJSONArray("students") ?: JSONArray()
        for (i in 0 until stuArr.length()) {
            val o = stuArr.getJSONObject(i)
            val sid = o.getString("studentId")
            if (dao.getStudentById(sid) == null) {
                dao.upsertStudent(
                    StudentSessionEntity(
                        studentId = sid,
                        nickname = o.getString("nickname"),
                        className = o.optString("className", "IV A"),
                        groupName = o.optString("groupName", "Kelompok 1"),
                        avatarId = o.optString("avatarId", "nara"),
                        sessionDate = o.optString("sessionDate", ExportAndCertificateHelper.todayDate()),
                        sessionCode = o.optString("sessionCode", "OFFLINE"),
                        totalXp = o.optInt("totalXp", 0),
                        completedPosCsv = o.optString("completedPosCsv", ""),
                        badgesCsv = o.optString("badgesCsv", ""),
                        diagnosticDone = o.optBoolean("diagnosticDone", false),
                        finalChallengeDone = o.optBoolean("finalChallengeDone", false),
                        summativeDone = o.optBoolean("summativeDone", false),
                        reflectionDone = o.optBoolean("reflectionDone", false)
                    )
                )
                importedStudents++
            }
        }

        val formArr = root.optJSONArray("formatives") ?: JSONArray()
        for (i in 0 until formArr.length()) {
            val o = formArr.getJSONObject(i)
            dao.insertFormativeResult(
                FormativeResultEntity(
                    timestamp = o.getString("timestamp"),
                    studentId = o.getString("studentId"),
                    nickname = o.getString("nickname"),
                    className = o.getString("className"),
                    posNumber = o.getInt("posNumber"),
                    totalQuestions = o.getInt("totalQuestions"),
                    correctCount = o.getInt("correctCount"),
                    wrongCount = o.getInt("wrongCount"),
                    scorePercent = o.getInt("scorePercent"),
                    masteryStatus = o.getString("masteryStatus"),
                    recommendation = o.getString("recommendation")
                )
            )
        }

        val sumArr = root.optJSONArray("summatives") ?: JSONArray()
        for (i in 0 until sumArr.length()) {
            val o = sumArr.getJSONObject(i)
            dao.insertSummativeResult(
                SummativeResultEntity(
                    timestamp = o.getString("timestamp"),
                    studentId = o.getString("studentId"),
                    nickname = o.getString("nickname"),
                    className = o.getString("className"),
                    totalQuestions = o.getInt("totalQuestions"),
                    earnedScore = o.getInt("earnedScore"),
                    maxScore = o.getInt("maxScore"),
                    finalScore = o.getInt("finalScore"),
                    passStatus = o.getString("passStatus"),
                    recommendation = o.getString("recommendation")
                )
            )
        }
        importedStudents
    }
}
