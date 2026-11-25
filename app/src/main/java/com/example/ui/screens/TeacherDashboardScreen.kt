package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.posAccentColor
import com.example.ui.components.BalighTopBar
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorCoral
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningContainer
import com.example.viewmodel.PosMasteryRow
import com.example.viewmodel.TeacherDashboardViewModel

@Composable
fun TeacherDashboardScreen(
    viewModel: TeacherDashboardViewModel,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onBack: () -> Unit
) {
    val isUnlocked by viewModel.isUnlocked.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val statusMsg by viewModel.statusMessage.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }
    var activeTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var expandedStudentId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Dashboard Guru PAI",
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onBack = {
                    viewModel.lockDashboard()
                    onBack()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!isUnlocked) {
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .widthIn(max = 420.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(44.dp))
                        Text("Verifikasi Akses Guru", style = MaterialTheme.typography.headlineMedium, color = EmeraldPrimary)
                        Text(
                            "Area ini dilindungi agar data hasil belajar hanya diakses oleh guru. (PIN awal bawaan: 4024, dapat diubah di Pengaturan).",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        OutlinedTextField(
                            value = pinInput,
                            onValueChange = {
                                pinInput = it
                                pinError = false
                            },
                            label = { Text("Masukkan PIN Guru") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            isError = pinError,
                            modifier = Modifier.fillMaxWidth().testTag("teacher_pin_input")
                        )
                        if (pinError) {
                            Text("PIN tidak sesuai. Silakan coba lagi.", color = ErrorCoral, style = MaterialTheme.typography.labelMedium)
                        }
                        Button(
                            onClick = {
                                if (!viewModel.verifyPin(pinInput)) {
                                    pinError = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("teacher_pin_submit")
                        ) {
                            Text("Buka Dashboard Guru")
                        }
                    }
                }
            } else {
                Column(modifier = Modifier.widthIn(max = 860.dp).fillMaxSize()) {
                    statusMsg?.let { msg ->
                        Surface(color = SuccessContainer, modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(msg, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                                Text(
                                    "Tutup",
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable { viewModel.clearStatusMessage() }
                                )
                            }
                        }
                    }

                    ScrollableTabRow(
                        selectedTabIndex = activeTab,
                        containerColor = MaterialTheme.colorScheme.surface,
                        edgePadding = 12.dp
                    ) {
                        Tab(selected = activeTab == 0, onClick = { activeTab = 0 }, text = { Text("Ringkasan & Grafik") })
                        Tab(selected = activeTab == 1, onClick = { activeTab = 1 }, text = { Text("Daftar Murid (${state.students.size})") })
                        Tab(selected = activeTab == 2, onClick = { activeTab = 2 }, text = { Text("Analisis Soal & TP") })
                        Tab(selected = activeTab == 3, onClick = { activeTab = 3 }, text = { Text("Modul Ajar (3 JP×2)") })
                        Tab(selected = activeTab == 4, onClick = { activeTab = 4 }, text = { Text("Sheets & Pengaturan") })
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                            .navigationBarsPadding(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        when (activeTab) {
                            0 -> {
                                // KPI Summary Grid
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                                    KpiCard("Murid", "${state.students.size}", "Terdaftar lokal", EmeraldPrimary, Modifier.weight(1f))
                                    KpiCard("Rata Formatif", "${state.averageFormative}", "Dari seluruh pos", AmberSecondary, Modifier.weight(1f))
                                    KpiCard("Rata Sumatif", "${state.averageSummative}", "KKTP: ${state.settings.kktp}", EmeraldPrimary, Modifier.weight(1f))
                                    KpiCard("Ketuntasan", "${state.passRatePercent}%", "Max ${state.highestSummative} / Min ${state.lowestSummative}", SuccessGreen, Modifier.weight(1f))
                                }

                                // Canvas Bar Chart: Penguasaan per Pos
                                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Text("Grafik Penguasaan Materi Per Pos (1–5)", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                                        PosMasteryBarChart(state.posMastery)
                                        state.posMastery.forEach { p ->
                                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text(p.title, style = MaterialTheme.typography.bodyMedium)
                                                Text("${p.averageScore}% (${p.attemptsCount}x)", fontWeight = FontWeight.Bold, color = posAccentColor(p.posNumber))
                                            }
                                        }
                                    }
                                }

                                // Actionable lists
                                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("Pemetaan Tindak Lanjut Kelas", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                                        Text("• Perlu Pendampingan / Remedial (< KKTP ${state.settings.kktp}): ${state.remedialStudents.size} murid", fontWeight = FontWeight.SemiBold)
                                        state.remedialStudents.forEach { (s, sc) ->
                                            Text("   - ${s.nickname} (${s.className}): Nilai ${sc ?: "-"}")
                                        }
                                        Text("• Siap Pengayaan (>= ${state.settings.categoryVeryGoodMin}): ${state.enrichmentStudents.size} murid", fontWeight = FontWeight.SemiBold, color = SuccessGreen)
                                        state.enrichmentStudents.forEach { (s, sc) ->
                                            Text("   - ${s.nickname} (${s.className}): Nilai $sc")
                                        }
                                        Text("• Belum Menyelesaikan 5 Pos & Sumatif: ${state.incompleteStudents.size} murid", fontWeight = FontWeight.SemiBold, color = AmberSecondary)
                                    }
                                }

                                // Recent Activity Logs
                                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text("Riwayat Aktivitas Terbaru", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                                        if (state.logs.isEmpty()) {
                                            Text("Belum ada aktivitas tercatat.", style = MaterialTheme.typography.bodyMedium)
                                        } else {
                                            state.logs.take(10).forEach { l ->
                                                Text("${l.timestamp} • ${l.studentId}: ${l.activityName} (+${l.xpEarned} XP)", style = MaterialTheme.typography.bodyMedium)
                                            }
                                        }
                                    }
                                }
                            }

                            1 -> {
                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                                    label = { Text("Cari nama panggilan atau kelas murid") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                val filtered = state.students.filter {
                                    searchQuery.isBlank() ||
                                        it.nickname.contains(searchQuery, ignoreCase = true) ||
                                        it.className.contains(searchQuery, ignoreCase = true)
                                }

                                if (filtered.isEmpty()) {
                                    Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                        Text(
                                            "Belum ada data murid. Saat murid memulai petualangan dan mengerjakan kuis, hasilnya otomatis muncul di sini.",
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                } else {
                                    filtered.forEach { student ->
                                        val sum = state.summativeResults.firstOrNull { it.studentId == student.studentId }
                                        val formList = state.formativeResults.filter { it.studentId == student.studentId }
                                        val studentAnswers = state.allAnswers.filter { it.studentId == student.studentId }
                                        val isExpanded = expandedStudentId == student.studentId

                                        Card(
                                            shape = RoundedCornerShape(18.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                            border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.25f)),
                                            modifier = Modifier.fillMaxWidth().clickable {
                                                expandedStudentId = if (isExpanded) null else student.studentId
                                            }
                                        ) {
                                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text("${student.nickname} (${student.className})", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                                                    Text("${student.totalXp} XP • ${student.badgesList().size} Lencana", fontWeight = FontWeight.Bold, color = AmberSecondary)
                                                }
                                                Text(
                                                    "Pos Selesai: ${student.completedPosSet().size}/5 | Sumatif: ${sum?.finalScore ?: "-"} (${sum?.passStatus ?: "Belum"})",
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                if (isExpanded) {
                                                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                                                    Text("Detail Formatif:", fontWeight = FontWeight.Bold)
                                                    if (formList.isEmpty()) Text("- Belum mengerjakan formatif", style = MaterialTheme.typography.bodyMedium)
                                                    formList.forEach { f ->
                                                        Text("• Pos ${f.posNumber}: Nilai ${f.scorePercent} (Benar ${f.correctCount}, Salah ${f.wrongCount})", style = MaterialTheme.typography.bodyMedium)
                                                    }
                                                    Text("Jawaban Terakhir (${studentAnswers.size} total):", fontWeight = FontWeight.Bold)
                                                    studentAnswers.take(8).forEach { a ->
                                                        Text("• ${a.questionId} (${a.assessmentType}): '${a.studentAnswer}' -> ${if (a.isCorrect) "BENAR" else "SALAH"}", style = MaterialTheme.typography.labelMedium)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            2 -> {
                                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("Analisis Per Soal (Urutan Kesalahan Tertinggi)", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                                        if (state.questionAnalytics.isEmpty()) {
                                            Text("Belum ada data jawaban untuk dianalisis.", style = MaterialTheme.typography.bodyMedium)
                                        } else {
                                            state.questionAnalytics.take(15).forEach { q ->
                                                Surface(
                                                    color = if (q.errorRatePercent >= 50) ErrorContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                                    shape = RoundedCornerShape(12.dp)
                                                ) {
                                                    Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                            Text("${q.questionId} • ${q.materialTopic}", fontWeight = FontWeight.Bold)
                                                            Text("Salah ${q.errorRatePercent}% (${q.wrongCount}/${q.attempts})", color = if (q.errorRatePercent >= 50) ErrorCoral else SuccessGreen, fontWeight = FontWeight.Bold)
                                                        }
                                                        Text("Indikator: ${q.indicator}", style = MaterialTheme.typography.labelMedium)
                                                        LinearProgressIndicator(
                                                            progress = { (100 - q.errorRatePercent) / 100f },
                                                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50)),
                                                            color = if (q.errorRatePercent >= 50) ErrorCoral else EmeraldPrimary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            3 -> TeacherModulAjarTab()

                            4 -> TeacherSheetsAndSettingsTab(
                                state = state,
                                isSyncing = isSyncing,
                                onSaveSettings = { viewModel.saveSettings(it) },
                                onTestConnection = { viewModel.testSheetsConnection(it) },
                                onSyncNow = { viewModel.syncNow() },
                                onExportBackupJson = { cb -> viewModel.exportOfflineBackupJson(cb) },
                                onImportBackupJson = { json -> viewModel.importOfflineBackupJson(json) },
                                onResetSessions = { viewModel.resetAllSessions() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun KpiCard(
    label: String,
    value: String,
    subtitle: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, accent.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.headlineMedium, color = accent, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun PosMasteryBarChart(rows: List<PosMasteryRow>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(vertical = 8.dp)
    ) {
        val barWidth = (size.width / (rows.size * 2f)).coerceAtLeast(24f)
        val spacing = (size.width - (barWidth * rows.size)) / (rows.size + 1)
        val maxHeight = size.height - 12f

        rows.forEachIndexed { idx, row ->
            val left = spacing + idx * (barWidth + spacing)
            val ratio = (row.averageScore / 100f).coerceIn(0.05f, 1f)
            val barH = maxHeight * ratio
            val top = maxHeight - barH

            drawRoundRect(
                color = posAccentColor(row.posNumber).copy(alpha = 0.18f),
                topLeft = Offset(left, 0f),
                size = Size(barWidth, maxHeight),
                cornerRadius = CornerRadius(14f, 14f)
            )
            drawRoundRect(
                color = posAccentColor(row.posNumber),
                topLeft = Offset(left, top),
                size = Size(barWidth, barH),
                cornerRadius = CornerRadius(14f, 14f)
            )
        }
    }
}
