package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BalighDao {
    // Student Sessions
    @Query("SELECT * FROM student_sessions ORDER BY lastUpdatedMillis DESC")
    fun observeAllStudents(): Flow<List<StudentSessionEntity>>

    @Query("SELECT * FROM student_sessions WHERE studentId = :studentId LIMIT 1")
    fun observeStudent(studentId: String): Flow<StudentSessionEntity?>

    @Query("SELECT * FROM student_sessions WHERE studentId = :studentId LIMIT 1")
    suspend fun getStudentById(studentId: String): StudentSessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStudent(student: StudentSessionEntity)

    @Query("UPDATE student_sessions SET syncedToSheets = 1 WHERE studentId IN (:ids)")
    suspend fun markStudentsSynced(ids: List<String>)

    @Query("SELECT * FROM student_sessions WHERE syncedToSheets = 0")
    suspend fun getUnsyncedStudents(): List<StudentSessionEntity>

    // Answers
    @Query("SELECT * FROM student_answers ORDER BY id DESC")
    fun observeAllAnswers(): Flow<List<StudentAnswerEntity>>

    @Query("SELECT * FROM student_answers WHERE studentId = :studentId ORDER BY id DESC")
    fun observeAnswersForStudent(studentId: String): Flow<List<StudentAnswerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<StudentAnswerEntity>)

    @Query("SELECT * FROM student_answers WHERE syncedToSheets = 0")
    suspend fun getUnsyncedAnswers(): List<StudentAnswerEntity>

    @Query("UPDATE student_answers SET syncedToSheets = 1 WHERE id IN (:ids)")
    suspend fun markAnswersSynced(ids: List<Long>)

    // Formative Results
    @Query("SELECT * FROM formative_results ORDER BY id DESC")
    fun observeAllFormative(): Flow<List<FormativeResultEntity>>

    @Query("SELECT * FROM formative_results WHERE studentId = :studentId ORDER BY posNumber ASC")
    fun observeFormativeForStudent(studentId: String): Flow<List<FormativeResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormativeResult(result: FormativeResultEntity)

    @Query("SELECT * FROM formative_results WHERE syncedToSheets = 0")
    suspend fun getUnsyncedFormative(): List<FormativeResultEntity>

    @Query("UPDATE formative_results SET syncedToSheets = 1 WHERE id IN (:ids)")
    suspend fun markFormativeSynced(ids: List<Long>)

    // Summative Results
    @Query("SELECT * FROM summative_results ORDER BY id DESC")
    fun observeAllSummative(): Flow<List<SummativeResultEntity>>

    @Query("SELECT * FROM summative_results WHERE studentId = :studentId ORDER BY id DESC LIMIT 1")
    fun observeLatestSummativeForStudent(studentId: String): Flow<SummativeResultEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummativeResult(result: SummativeResultEntity)

    @Query("SELECT * FROM summative_results WHERE syncedToSheets = 0")
    suspend fun getUnsyncedSummative(): List<SummativeResultEntity>

    @Query("UPDATE summative_results SET syncedToSheets = 1 WHERE id IN (:ids)")
    suspend fun markSummativeSynced(ids: List<Long>)

    // Activity Logs
    @Query("SELECT * FROM activity_logs ORDER BY id DESC")
    fun observeAllLogs(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: ActivityLogEntity)

    @Query("SELECT * FROM activity_logs WHERE syncedToSheets = 0")
    suspend fun getUnsyncedLogs(): List<ActivityLogEntity>

    @Query("UPDATE activity_logs SET syncedToSheets = 1 WHERE id IN (:ids)")
    suspend fun markLogsSynced(ids: List<Long>)

    // Reflections
    @Query("SELECT * FROM reflections ORDER BY id DESC")
    fun observeAllReflections(): Flow<List<ReflectionEntity>>

    @Query("SELECT * FROM reflections WHERE studentId = :studentId ORDER BY id DESC LIMIT 1")
    fun observeLatestReflection(studentId: String): Flow<ReflectionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReflection(reflection: ReflectionEntity)

    // Teacher Settings
    @Query("SELECT * FROM teacher_settings WHERE id = 1 LIMIT 1")
    fun observeSettings(): Flow<TeacherSettingsEntity?>

    @Query("SELECT * FROM teacher_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettings(): TeacherSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSettings(settings: TeacherSettingsEntity)

    // Session Reset (Teacher controlled)
    @Query("DELETE FROM student_sessions")
    suspend fun clearAllStudents()

    @Query("DELETE FROM student_answers")
    suspend fun clearAllAnswers()

    @Query("DELETE FROM formative_results")
    suspend fun clearAllFormative()

    @Query("DELETE FROM summative_results")
    suspend fun clearAllSummative()

    @Query("DELETE FROM activity_logs")
    suspend fun clearAllLogs()

    @Query("DELETE FROM reflections")
    suspend fun clearAllReflections()
}
