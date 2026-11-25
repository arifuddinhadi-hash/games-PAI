package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_sessions")
data class StudentSessionEntity(
    @PrimaryKey val studentId: String,
    val nickname: String,
    val className: String,
    val groupName: String,
    val avatarId: String,
    val sessionDate: String,
    val sessionCode: String,
    val totalXp: Int = 0,
    val completedPosCsv: String = "", // e.g. "1,2,3"
    val badgesCsv: String = "",       // e.g. "badge_pos_1,badge_pos_2"
    val diagnosticDone: Boolean = false,
    val finalChallengeDone: Boolean = false,
    val summativeDone: Boolean = false,
    val reflectionDone: Boolean = false,
    val syncedToSheets: Boolean = false,
    val lastUpdatedMillis: Long = System.currentTimeMillis()
) {
    fun completedPosSet(): Set<Int> =
        completedPosCsv.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()

    fun badgesList(): List<String> =
        badgesCsv.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}

@Entity(tableName = "student_answers")
data class StudentAnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: String,
    val studentId: String,
    val nickname: String,
    val className: String,
    val questionId: String,
    val assessmentType: String, // DIAGNOSTIK, FORMATIF, SUMATIF, REMEDIAL, PENGAYAAN
    val posNumber: Int,
    val studentAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val earnedScore: Int,
    val maxScore: Int,
    val durationSeconds: Int,
    val syncedToSheets: Boolean = false
)

@Entity(tableName = "formative_results")
data class FormativeResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: String,
    val studentId: String,
    val nickname: String,
    val className: String,
    val posNumber: Int,
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val scorePercent: Int,
    val masteryStatus: String,
    val recommendation: String,
    val syncedToSheets: Boolean = false
)

@Entity(tableName = "summative_results")
data class SummativeResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: String,
    val studentId: String,
    val nickname: String,
    val className: String,
    val totalQuestions: Int,
    val earnedScore: Int,
    val maxScore: Int,
    val finalScore: Int,
    val passStatus: String,
    val recommendation: String,
    val syncedToSheets: Boolean = false
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: String,
    val studentId: String,
    val posNumber: Int,
    val activityName: String,
    val xpEarned: Int,
    val badgeEarned: String,
    val completionStatus: String,
    val syncedToSheets: Boolean = false
)

@Entity(tableName = "reflections")
data class ReflectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: String,
    val studentId: String,
    val nickname: String,
    val moodEmoji: String,
    val answersSummary: String
)

@Entity(tableName = "teacher_settings")
data class TeacherSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val schoolName: String = "SD Negeri Nusantara",
    val className: String = "IV (Empat)",
    val kktp: Int = 75,
    val questionCount: Int = 20,
    val maxScore: Int = 100,
    val teacherName: String = "Guru PAI",
    val semester: String = "Ganjil",
    val academicYear: String = "2026/2027",
    val appsScriptUrl: String = "",
    val freeLearningMode: Boolean = false,
    val teacherPin: String = "4024",
    val categoryVeryGoodMin: Int = 90,
    val categoryGoodMin: Int = 80,
    val categoryFairMin: Int = 70,
    val offlineOnlyMode: Boolean = false
)
