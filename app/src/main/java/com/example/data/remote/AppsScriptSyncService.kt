package com.example.data.remote

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class SheetIdentityDto(
    val studentId: String,
    val nickname: String,
    val className: String,
    val groupName: String,
    val date: String,
    val sessionCode: String
)

@JsonClass(generateAdapter = true)
data class SheetAnswerDto(
    val timestamp: String,
    val studentId: String,
    val nickname: String,
    val className: String,
    val questionId: String,
    val assessmentType: String,
    val posNumber: Int,
    val studentAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val earnedScore: Int,
    val maxScore: Int,
    val durationSeconds: Int
)

@JsonClass(generateAdapter = true)
data class SheetFormativeDto(
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
    val recommendation: String
)

@JsonClass(generateAdapter = true)
data class SheetSummativeDto(
    val timestamp: String,
    val studentId: String,
    val nickname: String,
    val className: String,
    val totalQuestions: Int,
    val earnedScore: Int,
    val maxScore: Int,
    val finalScore: Int,
    val passStatus: String,
    val recommendation: String
)

@JsonClass(generateAdapter = true)
data class SheetActivityLogDto(
    val timestamp: String,
    val studentId: String,
    val posNumber: Int,
    val activityName: String,
    val xpEarned: Int,
    val badgeEarned: String,
    val completionStatus: String
)

@JsonClass(generateAdapter = true)
data class SheetSettingsDto(
    val schoolName: String,
    val className: String,
    val kktp: Int,
    val questionCount: Int,
    val maxScore: Int,
    val teacherName: String,
    val semester: String,
    val academicYear: String
)

@JsonClass(generateAdapter = true)
data class SheetBankSoalDto(
    val id: String,
    val assessmentType: String,
    val posNumber: Int,
    val material: String,
    val indicator: String,
    val question: String,
    val questionType: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String,
    val score: Int,
    val feedbackCorrect: String,
    val feedbackWrong: String,
    val difficulty: String
)

@JsonClass(generateAdapter = true)
data class SheetSyncPayload(
    val identitas: List<SheetIdentityDto>,
    val answers: List<SheetAnswerDto>,
    val formativeResults: List<SheetFormativeDto>,
    val summativeResults: List<SheetSummativeDto>,
    val activityLogs: List<SheetActivityLogDto>,
    val settings: SheetSettingsDto?,
    val bankSoal: List<SheetBankSoalDto>
)

@JsonClass(generateAdapter = true)
data class SheetSyncResponse(
    val status: String? = null,
    val message: String? = null,
    val syncedRows: Int? = null,
    val spreadsheetName: String? = null,
    val timestamp: String? = null
)

class AppsScriptSyncService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .followRedirects(true)
        .followSslRedirects(true)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val payloadAdapter = moshi.adapter(SheetSyncPayload::class.java)
    private val responseAdapter = moshi.adapter(SheetSyncResponse::class.java)

    suspend fun pingWebApp(webAppUrl: String): Result<SheetSyncResponse> = withContext(Dispatchers.IO) {
        runCatching {
            val cleanUrl = webAppUrl.trim()
            require(cleanUrl.startsWith("https://script.google.com/")) {
                "URL harus diawali https://script.google.com/"
            }
            val sep = if (cleanUrl.contains("?")) "&" else "?"
            val request = Request.Builder()
                .url("${cleanUrl}${sep}action=ping")
                .get()
                .build()
            client.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string().orEmpty()
                if (!response.isSuccessful) {
                    error("HTTP ${response.code}: Gagal menghubungi Apps Script.")
                }
                val parsed = runCatching { responseAdapter.fromJson(bodyStr) }.getOrNull()
                if (parsed?.status == "ok") {
                    parsed
                } else {
                    error(parsed?.message ?: "Respons tidak valid dari Apps Script.")
                }
            }
        }
    }

    suspend fun syncBatch(webAppUrl: String, payload: SheetSyncPayload): Result<SheetSyncResponse> =
        withContext(Dispatchers.IO) {
            runCatching {
                val cleanUrl = webAppUrl.trim()
                require(cleanUrl.startsWith("https://script.google.com/")) {
                    "URL Apps Script belum valid."
                }
                val json = payloadAdapter.toJson(payload)
                val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
                val request = Request.Builder()
                    .url(cleanUrl)
                    .post(body)
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (!response.isSuccessful) {
                        error("HTTP ${response.code}: Gagal mengirim data ke Google Sheets.")
                    }
                    val parsed = runCatching { responseAdapter.fromJson(bodyStr) }.getOrNull()
                    if (parsed?.status == "ok") {
                        parsed
                    } else {
                        error(parsed?.message ?: "Sinkronisasi ditolak oleh Apps Script.")
                    }
                }
            }
        }
}
