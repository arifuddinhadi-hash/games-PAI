package com.example.util

import android.content.Context
import android.content.Intent
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.FileProvider
import com.example.data.local.FormativeResultEntity
import com.example.data.local.StudentAnswerEntity
import com.example.data.local.StudentSessionEntity
import com.example.data.local.SummativeResultEntity
import com.example.data.local.TeacherSettingsEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ExportAndCertificateHelper {

    fun nowTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun todayDate(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"))
        return sdf.format(Date())
    }

    fun readBundledCodeGs(context: Context): String {
        return runCatching {
            context.assets.open("Code.gs").bufferedReader().use { it.readText() }
        }.getOrElse {
            "// File Code.gs tidak ditemukan di assets."
        }
    }

    fun isDeviceOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? android.net.ConnectivityManager ?: return false
        val net = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(net) ?: return false
        return caps.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun shareOfflineHtmlApp(context: Context): Result<Unit> = runCatching {
        val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
        val file = File(exportDir, "petualangan_baligh_offline.html")
        context.assets.open("petualangan_baligh_offline.html").use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/html"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Petualangan Baligh (Versi Web Offline)")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Bagikan / Simpan Versi Web Offline (.html)"))
    }

    fun shareBackupJson(context: Context, jsonContent: String): Result<Unit> = runCatching {
        val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
        val file = File(exportDir, "backup_petualangan_baligh.json")
        file.writeText(jsonContent, Charsets.UTF_8)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Backup Data Offline Petualangan Baligh")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Simpan / Bagikan Backup JSON Offline"))
    }

    fun shareCsvReport(
        context: Context,
        students: List<StudentSessionEntity>,
        formativeResults: List<FormativeResultEntity>,
        summativeResults: List<SummativeResultEntity>,
        answers: List<StudentAnswerEntity>,
        settings: TeacherSettingsEntity
    ): Result<Unit> = runCatching {
        val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
        val file = File(exportDir, "rekap_petualangan_baligh.csv")

        val sb = StringBuilder()
        sb.appendLine("REKAP HASIL BELAJAR - PETUALANGAN BALIGH (${csvEsc(settings.schoolName)})")
        sb.appendLine("Guru,${csvEsc(settings.teacherName)},Kelas,${csvEsc(settings.className)},KKTP,${settings.kktp},Semester,${csvEsc(settings.semester)} ${csvEsc(settings.academicYear)}")
        sb.appendLine()
        sb.appendLine("IDENTITAS & PROGRES MURID")
        sb.appendLine("ID Murid,Nama Panggilan,Kelas,Kelompok,Tanggal,Total XP,Pos Selesai,Jumlah Badge,Diagnostik,Tantangan Final,Sumatif,Refleksi")
        students.forEach { s ->
            sb.appendLine(
                listOf(
                    csvEsc(s.studentId),
                    csvEsc(s.nickname),
                    csvEsc(s.className),
                    csvEsc(s.groupName),
                    csvEsc(s.sessionDate),
                    s.totalXp.toString(),
                    csvEsc(s.completedPosCsv),
                    s.badgesList().size.toString(),
                    if (s.diagnosticDone) "Selesai" else "Belum",
                    if (s.finalChallengeDone) "Selesai" else "Belum",
                    if (s.summativeDone) "Selesai" else "Belum",
                    if (s.reflectionDone) "Selesai" else "Belum"
                ).joinToString(",")
            )
        }

        sb.appendLine()
        sb.appendLine("HASIL FORMATIF PER POS")
        sb.appendLine("Timestamp,ID Murid,Nama Panggilan,Kelas,Pos,Soal,Benar,Salah,Nilai,Penguasaan,Rekomendasi")
        formativeResults.forEach { f ->
            sb.appendLine(
                listOf(
                    csvEsc(f.timestamp),
                    csvEsc(f.studentId),
                    csvEsc(f.nickname),
                    csvEsc(f.className),
                    f.posNumber.toString(),
                    f.totalQuestions.toString(),
                    f.correctCount.toString(),
                    f.wrongCount.toString(),
                    f.scorePercent.toString(),
                    csvEsc(f.masteryStatus),
                    csvEsc(f.recommendation)
                ).joinToString(",")
            )
        }

        sb.appendLine()
        sb.appendLine("HASIL SUMATIF AKHIR")
        sb.appendLine("Timestamp,ID Murid,Nama Panggilan,Kelas,Jumlah Soal,Skor Perolehan,Skor Maksimum,Nilai Akhir,Ketuntasan,Rekomendasi")
        summativeResults.forEach { sum ->
            sb.appendLine(
                listOf(
                    csvEsc(sum.timestamp),
                    csvEsc(sum.studentId),
                    csvEsc(sum.nickname),
                    csvEsc(sum.className),
                    sum.totalQuestions.toString(),
                    sum.earnedScore.toString(),
                    sum.maxScore.toString(),
                    sum.finalScore.toString(),
                    csvEsc(sum.passStatus),
                    csvEsc(sum.recommendation)
                ).joinToString(",")
            )
        }

        sb.appendLine()
        sb.appendLine("DETAIL JAWABAN MURID")
        sb.appendLine("Timestamp,ID Murid,Nama Panggilan,ID Soal,Jenis,Pos,Jawaban,Kunci,Status,Skor,Durasi(dtk)")
        answers.forEach { a ->
            sb.appendLine(
                listOf(
                    csvEsc(a.timestamp),
                    csvEsc(a.studentId),
                    csvEsc(a.nickname),
                    csvEsc(a.questionId),
                    csvEsc(a.assessmentType),
                    a.posNumber.toString(),
                    csvEsc(a.studentAnswer),
                    csvEsc(a.correctAnswer),
                    if (a.isCorrect) "BENAR" else "SALAH",
                    "${a.earnedScore}/${a.maxScore}",
                    a.durationSeconds.toString()
                ).joinToString(",")
            )
        }

        file.writeText(sb.toString(), Charsets.UTF_8)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Rekap Nilai Petualangan Baligh")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Bagikan / Simpan Rekap CSV"))
    }

    fun printTeacherReport(
        context: Context,
        students: List<StudentSessionEntity>,
        summativeResults: List<SummativeResultEntity>,
        settings: TeacherSettingsEntity
    ) {
        val avg = if (summativeResults.isEmpty()) 0 else summativeResults.map { it.finalScore }.average().toInt()
        val passed = summativeResults.count { it.finalScore >= settings.kktp }
        val passRate = if (summativeResults.isEmpty()) 0 else (passed * 100) / summativeResults.size

        val rowsHtml = students.joinToString("") { s ->
            val sum = summativeResults.firstOrNull { it.studentId == s.studentId }
            val nilaiStr = sum?.finalScore?.toString() ?: "-"
            val statusStr = sum?.passStatus ?: "Belum Sumatif"
            """
            <tr>
                <td>${htmlEsc(s.nickname)}</td>
                <td>${htmlEsc(s.className)}</td>
                <td>${s.completedPosSet().size}/5 Pos</td>
                <td>${s.totalXp} XP</td>
                <td>${nilaiStr}</td>
                <td>${htmlEsc(statusStr)}</td>
            </tr>
            """.trimIndent()
        }

        val html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <style>
                body { font-family: sans-serif; color: #1D2623; padding: 24px; }
                h1 { color: #167766; margin-bottom: 4px; }
                .meta { color: #4C5954; margin-bottom: 16px; font-size: 13px; }
                .kpi { display: flex; gap: 12px; margin-bottom: 18px; }
                .box { border: 1px solid #C7F2E4; background: #F4FBF8; padding: 10px 14px; border-radius: 8px; }
                table { width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 13px; }
                th { background: #167766; color: #fff; text-align: left; padding: 8px; }
                td { border-bottom: 1px solid #E2E8E5; padding: 8px; }
            </style>
        </head>
        <body>
            <h1>Laporan Hasil Belajar - Petualangan Baligh</h1>
            <div class="meta">
                Sekolah: ${htmlEsc(settings.schoolName)} | Guru: ${htmlEsc(settings.teacherName)} |
                Kelas: ${htmlEsc(settings.className)} | Semester: ${htmlEsc(settings.semester)} ${htmlEsc(settings.academicYear)} |
                KKTP: ${settings.kktp}
            </div>
            <div class="kpi">
                <div class="box"><b>Total Murid:</b> ${students.size}</div>
                <div class="box"><b>Rata-rata Sumatif:</b> $avg</div>
                <div class="box"><b>Ketuntasan:</b> $passRate% ($passed/${summativeResults.size})</div>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Nama Panggilan</th><th>Kelas</th><th>Progres Pos</th><th>XP</th><th>Nilai Sumatif</th><th>Status</th>
                    </tr>
                </thead>
                <tbody>$rowsHtml</tbody>
            </table>
        </body>
        </html>
        """.trimIndent()

        printHtmlDocument(context, "Laporan_Petualangan_Baligh", html)
    }

    fun printStudentCertificate(
        context: Context,
        student: StudentSessionEntity,
        summativeScore: Int?,
        badgeNames: List<String>,
        settings: TeacherSettingsEntity
    ) {
        val badgeHtml = if (badgeNames.isEmpty()) {
            "Penjelajah Ilmu Hebat"
        } else {
            badgeNames.joinToString(" • ") { htmlEsc(it) }
        }
        val html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <style>
                body { font-family: sans-serif; background: #FBF9F2; padding: 24px; text-align: center; }
                .cert { border: 8px double #167766; background: #FFFFFF; padding: 32px; border-radius: 16px; }
                h1 { color: #167766; font-size: 28px; margin-bottom: 6px; }
                h2 { color: #D98218; font-size: 20px; margin-top: 0; }
                .name { font-size: 32px; font-weight: bold; color: #1D2623; margin: 18px 0 8px; border-bottom: 2px solid #D98218; display: inline-block; padding: 0 24px 6px; }
                .badges { background: #C7F2E4; color: #063B30; padding: 10px 16px; border-radius: 12px; display: inline-block; margin: 14px 0; font-size: 13px; }
                .footer { margin-top: 28px; font-size: 13px; color: #4C5954; }
            </style>
        </head>
        <body>
            <div class="cert">
                <h2>SERTIFIKAT PENGHARGAAN DIGITAL</h2>
                <h1>PETUALANGAN BALIGH</h1>
                <p>Diberikan dengan penuh rasa bangga kepada:</p>
                <div class="name">${htmlEsc(student.nickname)}</div>
                <p>Kelas ${htmlEsc(student.className)} • ${htmlEsc(settings.schoolName)}</p>
                <p>Telah menyelesaikan perjalanan pembelajaran PAI Kelas IV Fase B:<br/>
                <b>"Aku Siap Tumbuh Menjadi Muslim yang Bertanggung Jawab"</b></p>
                <p>Total Perolehan: <b>${student.totalXp} XP</b> ${summativeScore?.let { "• Nilai Sumatif: <b>$it</b>" } ?: ""}</p>
                <div class="badges">Lencana: $badgeHtml</div>
                <p><i>"Teruslah menjaga kebersihan, salat lima waktu, privasi diri, dan akhlak mulia!"</i></p>
                <div class="footer">
                    Tanggal: ${htmlEsc(todayDate())} • Pembimbing: ${htmlEsc(settings.teacherName)}
                </div>
            </div>
        </body>
        </html>
        """.trimIndent()

        printHtmlDocument(context, "Sertifikat_${student.nickname}", html)
    }

    private fun printHtmlDocument(context: Context, jobName: String, html: String) {
        val webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager ?: return
                val adapter = webView.createPrintDocumentAdapter(jobName)
                printManager.print(jobName, adapter, PrintAttributes.Builder().build())
            }
        }
        webView.loadDataWithBaseURL(null, html, "text/HTML", "UTF-8", null)
    }

    private fun csvEsc(text: String): String {
        val sanitized = if (text.startsWith("=") || text.startsWith("+") || text.startsWith("-") || text.startsWith("@")) {
            "'$text"
        } else text
        return "\"${sanitized.replace("\"", "\"\"")}\""
    }

    private fun htmlEsc(text: String): String = text
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
}
