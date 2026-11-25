package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Html
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.TeacherModulAjarData
import com.example.data.local.TeacherSettingsEntity
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ErrorCoral
import com.example.ui.theme.SuccessContainer
import com.example.util.ExportAndCertificateHelper
import com.example.viewmodel.TeacherDashboardState

@Composable
fun TeacherModulAjarTab() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Modul Ajar Kurikulum Merdeka (3 Pertemuan)", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                TeacherModulAjarData.identitasModul.forEach { (k, v) ->
                    Text("$k: $v", style = MaterialTheme.typography.bodyMedium)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                Text("Catatan Capaian Pembelajaran (CP) Fase B:", style = MaterialTheme.typography.titleMedium)
                Text(TeacherModulAjarData.cpNote, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("13 Tujuan Pembelajaran (TP)", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                TeacherModulAjarData.tujuanPembelajaran.forEach { tp ->
                    Text(tp, style = MaterialTheme.typography.bodyMedium)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                Text("Pemahaman Bermakna:", style = MaterialTheme.typography.titleMedium)
                Text(TeacherModulAjarData.pemahamanBermakna, style = MaterialTheme.typography.bodyMedium)
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                Text("Pertanyaan Pemantik:", style = MaterialTheme.typography.titleMedium)
                TeacherModulAjarData.pertanyaanPemantik.forEach { q ->
                    Text("• $q", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        TeacherModulAjarData.meetings.forEach { meeting ->
            Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(meeting.title, style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                    Text("${meeting.duration} • Fokus: ${meeting.focusPos}", style = MaterialTheme.typography.labelLarge, color = AmberSecondary)
                    Text("Tujuan: ${meeting.learningObjective}", style = MaterialTheme.typography.bodyMedium)
                    meeting.steps.forEach { step ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("${step.phase} (${step.durationMinutes})", fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                                Text("Guru: ${step.teacherActivity}", style = MaterialTheme.typography.bodyMedium)
                                Text("Murid: ${step.studentActivity}", style = MaterialTheme.typography.bodyMedium)
                                Text("Media: ${step.media} | Asesmen: ${step.assessment}", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }

        Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Diferensiasi & Rubrik Penilaian", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                TeacherModulAjarData.differentiationNotes.forEach { (k, v) ->
                    Text("• $k: $v", style = MaterialTheme.typography.bodyMedium)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                TeacherModulAjarData.rubricCriteria.forEach { r ->
                    Surface(color = AmberSecondaryContainer.copy(alpha = 0.45f), shape = RoundedCornerShape(12.dp)) {
                        Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(r.aspect, fontWeight = FontWeight.Bold)
                            Text("Sangat Baik: ${r.veryGood}", style = MaterialTheme.typography.bodyMedium)
                            Text("Baik: ${r.good}", style = MaterialTheme.typography.bodyMedium)
                            Text("Cukup: ${r.fair}", style = MaterialTheme.typography.bodyMedium)
                            Text("Perlu Pendampingan: ${r.needsGuidance}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherSheetsAndSettingsTab(
    state: TeacherDashboardState,
    isSyncing: Boolean,
    onSaveSettings: (TeacherSettingsEntity) -> Unit,
    onTestConnection: (String) -> Unit,
    onSyncNow: () -> Unit,
    onExportBackupJson: ((String) -> Unit) -> Unit,
    onImportBackupJson: (String) -> Unit,
    onResetSessions: () -> Unit
) {
    val context = LocalContext.current
    val s = state.settings
    val isOnline = remember { ExportAndCertificateHelper.isDeviceOnline(context) }

    var schoolName by remember(s) { mutableStateOf(s.schoolName) }
    var teacherName by remember(s) { mutableStateOf(s.teacherName) }
    var className by remember(s) { mutableStateOf(s.className) }
    var semester by remember(s) { mutableStateOf(s.semester) }
    var academicYear by remember(s) { mutableStateOf(s.academicYear) }
    var kktpText by remember(s) { mutableStateOf(s.kktp.toString()) }
    var appsScriptUrl by remember(s) { mutableStateOf(s.appsScriptUrl) }
    var freeMode by remember(s) { mutableStateOf(s.freeLearningMode) }
    var offlineOnlyMode by remember(s) { mutableStateOf(s.offlineOnlyMode) }
    var newPin by remember(s) { mutableStateOf(s.teacherPin) }

    var showCodeModal by remember { mutableStateOf(false) }
    var showResetConfirm by remember { mutableStateOf(false) }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
            }.onSuccess { content ->
                if (!content.isNullOrBlank()) {
                    onImportBackupJson(content)
                }
            }.onFailure {
                Toast.makeText(context, "Gagal membaca file backup JSON.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (showCodeModal) {
        val codeGs = remember { ExportAndCertificateHelper.readBundledCodeGs(context) }
        AlertDialog(
            onDismissRequest = { showCodeModal = false },
            title = { Text("Kode Backend Google Apps Script (Code.gs)") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Langkah Deploy:\n1. Buat Google Sheets baru.\n2. Buka Ekstensi > Apps Script.\n3. Tempel kode di bawah, Simpan, lalu jalankan initializeDatabase() 1x.\n4. Deploy > Web app (Execute as: Me, Access: Anyone).\n5. Tempel URL Web App ke kolom Integrasi Google Sheets.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(10.dp)) {
                        Text(
                            text = codeGs.take(2600) + "\n... (Salin seluruh kode dengan tombol Salin)",
                            style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("Code.gs", codeGs))
                    Toast.makeText(context, "Seluruh Code.gs berhasil disalin ke clipboard!", Toast.LENGTH_LONG).show()
                }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Salin Kode Lengkap")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCodeModal = false }) { Text("Tutup") }
            }
        )
    }

    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text("Reset Semua Sesi Lokal?") },
            text = { Text("Tindakan ini akan menghapus seluruh data murid, jawaban, dan hasil belajar di perangkat ini. Pastikan sudah ekspor backup JSON/CSV atau sinkron ke Google Sheets terlebih dahulu.") },
            confirmButton = {
                Button(
                    onClick = {
                        showResetConfirm = false
                        onResetSessions()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorCoral)
                ) { Text("Ya, Reset Sekarang") }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirm = false }) { Text("Batal") }
            }
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Offline-First Mode & Standalone HTML Export Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SuccessContainer.copy(alpha = 0.55f)),
            border = BorderStroke(1.5.dp, EmeraldPrimary)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudOff, contentDescription = null, tint = EmeraldPrimary)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (offlineOnlyMode || !isOnline) "Mode Offline Penuh Aktif (100% Lokal)" else "Mode Hybrid (Offline Lokal + Online Siap)",
                        style = MaterialTheme.typography.titleMedium,
                        color = EmeraldPrimary
                    )
                }
                Text(
                    "Seluruh materi, game, kuis, grafik guru, sertifikat PDF, dan database Room tersimpan di perangkat sehingga berjalan lancar tanpa internet.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Kunci Mode Offline Penuh", style = MaterialTheme.typography.titleMedium)
                        Text("Nonaktifkan semua pengiriman internet otomatis.", style = MaterialTheme.typography.bodyMedium)
                    }
                    Switch(
                        checked = offlineOnlyMode,
                        onCheckedChange = {
                            offlineOnlyMode = it
                            onSaveSettings(s.copy(offlineOnlyMode = it))
                        },
                        modifier = Modifier.testTag("offline_mode_switch")
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            onExportBackupJson { json ->
                                ExportAndCertificateHelper.shareBackupJson(context, json)
                                    .onFailure {
                                        Toast.makeText(context, "Gagal ekspor JSON: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        },
                        modifier = Modifier.weight(1f).testTag("export_json_backup_button")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Backup JSON")
                    }
                    OutlinedButton(
                        onClick = {
                            importLauncher.launch(arrayOf("application/json", "*/*"))
                        },
                        modifier = Modifier.weight(1f).testTag("import_json_backup_button")
                    ) {
                        Icon(Icons.Default.UploadFile, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Impor JSON")
                    }
                }

                Button(
                    onClick = {
                        ExportAndCertificateHelper.shareOfflineHtmlApp(context)
                            .onFailure {
                                Toast.makeText(context, "Gagal membagikan versi web offline: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                    },
                    modifier = Modifier.fillMaxWidth().testTag("export_offline_html_button")
                ) {
                    Icon(Icons.Default.Html, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Ekspor / Bagikan Versi Web Offline (.html)")
                }
            }
        }

        // Google Sheets Integration Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.35f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Integrasi Google Sheets (Opsional Saat Online)", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                Text(
                    "Baris lokal menunggu sinkronisasi: ${state.unsyncedCount} • Struktur: IDENTITAS, BANK_SOAL, JAWABAN_MURID, HASIL_FORMATIF, HASIL_SUMATIF, REKAP_GURU, LOG_AKTIVITAS, PENGATURAN.",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = appsScriptUrl,
                    onValueChange = { appsScriptUrl = it },
                    label = { Text("URL Web App Google Apps Script") },
                    placeholder = { Text("https://script.google.com/macros/s/.../exec") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("apps_script_url_input")
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { onTestConnection(appsScriptUrl) },
                        enabled = !isSyncing && appsScriptUrl.isNotBlank() && !offlineOnlyMode,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.WifiTethering, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Tes Koneksi")
                    }
                    Button(
                        onClick = {
                            onSaveSettings(s.copy(appsScriptUrl = appsScriptUrl.trim(), offlineOnlyMode = offlineOnlyMode))
                            onSyncNow()
                        },
                        enabled = !isSyncing && appsScriptUrl.isNotBlank() && !offlineOnlyMode,
                        modifier = Modifier.weight(1f).testTag("sync_sheets_button")
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text(if (isSyncing) "Mengirim..." else "Sinkronkan")
                    }
                }
                OutlinedButton(
                    onClick = { showCodeModal = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Code, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Lihat & Salin Kode Code.gs + Panduan Deploy")
                }

                TeacherModulAjarData.sheetSchemaSummary.forEach { (sheetName, cols) ->
                    Surface(color = SuccessContainer.copy(alpha = 0.5f), shape = RoundedCornerShape(10.dp)) {
                        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                            Text(sheetName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                            Text(cols, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
        }

        // Export CSV & Print Report
        Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Ekspor & Cetak Laporan Offline", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            ExportAndCertificateHelper.shareCsvReport(
                                context = context,
                                students = state.students,
                                formativeResults = state.formativeResults,
                                summativeResults = state.summativeResults,
                                answers = state.allAnswers,
                                settings = state.settings
                            ).onFailure {
                                Toast.makeText(context, "Gagal mengekspor CSV: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f).testTag("export_csv_button")
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                        Text("Unduh CSV")
                    }
                    OutlinedButton(
                        onClick = {
                            ExportAndCertificateHelper.printTeacherReport(
                                context = context,
                                students = state.students,
                                summativeResults = state.summativeResults,
                                settings = state.settings
                            )
                        },
                        modifier = Modifier.weight(1f).testTag("print_report_button")
                    ) {
                        Icon(Icons.Default.Print, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                        Text("Cetak PDF")
                    }
                }
            }
        }

        // Settings & KKTP
        Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Pengaturan Kelas, KKTP & Mode Belajar", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                OutlinedTextField(value = schoolName, onValueChange = { schoolName = it }, label = { Text("Nama Sekolah") }, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = teacherName, onValueChange = { teacherName = it }, label = { Text("Nama Guru") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = className, onValueChange = { className = it }, label = { Text("Kelas") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = semester, onValueChange = { semester = it }, label = { Text("Semester") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = academicYear, onValueChange = { academicYear = it }, label = { Text("Tahun Ajaran") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = kktpText,
                        onValueChange = { kktpText = it.filter { ch -> ch.isDigit() } },
                        label = { Text("KKTP (0-100)") },
                        modifier = Modifier.weight(1f).testTag("kktp_input")
                    )
                    OutlinedTextField(
                        value = newPin,
                        onValueChange = { newPin = it.filter { ch -> ch.isDigit() }.take(8) },
                        label = { Text("PIN Guru") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Mode Bebas Belajar", style = MaterialTheme.typography.titleMedium)
                        Text("Izinkan murid membuka seluruh Pos & Tantangan Final tanpa urutan.", style = MaterialTheme.typography.bodyMedium)
                    }
                    Switch(checked = freeMode, onCheckedChange = { freeMode = it })
                }

                Button(
                    onClick = {
                        val parsedKktp = (kktpText.toIntOrNull() ?: 75).coerceIn(40, 100)
                        val validPin = newPin.ifBlank { "4024" }
                        onSaveSettings(
                            s.copy(
                                schoolName = schoolName.trim(),
                                teacherName = teacherName.trim(),
                                className = className.trim(),
                                semester = semester.trim(),
                                academicYear = academicYear.trim(),
                                kktp = parsedKktp,
                                appsScriptUrl = appsScriptUrl.trim(),
                                freeLearningMode = freeMode,
                                offlineOnlyMode = offlineOnlyMode,
                                teacherPin = validPin
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth().testTag("save_teacher_settings_button")
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Simpan Pengaturan")
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                OutlinedButton(
                    onClick = { showResetConfirm = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorCoral),
                    border = BorderStroke(1.dp, ErrorCoral),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.DeleteForever, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Reset Sesi Murid Lokal")
                }
            }
        }
    }
}
