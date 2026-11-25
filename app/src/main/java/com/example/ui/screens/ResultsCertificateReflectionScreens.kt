package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.AssessmentBankData
import com.example.data.curriculum.PosCurriculumData
import com.example.data.curriculum.TeacherModulAjarData
import com.example.data.local.FormativeResultEntity
import com.example.data.local.ReflectionEntity
import com.example.data.local.StudentSessionEntity
import com.example.data.local.SummativeResultEntity
import com.example.data.local.TeacherSettingsEntity
import com.example.ui.components.BalighTopBar
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryContainer
import com.example.ui.theme.Pos4Color
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen
import com.example.util.ExportAndCertificateHelper

@Composable
fun ResultsAndCertificateScreen(
    student: StudentSessionEntity,
    formativeResults: List<FormativeResultEntity>,
    latestSummative: SummativeResultEntity?,
    settings: TeacherSettingsEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onBackToMap: () -> Unit
) {
    val context = LocalContext.current
    val allBadgesMeta = remember {
        PosCurriculumData.posModules.map { it.badgeId to it.badgeName } +
            listOf(
                "badge_final_explorer" to "Penjelajah Final Baligh",
                "badge_lulus_baligh" to "Bintang Muslim Bertanggung Jawab"
            )
    }
    val earnedBadges = student.badgesList()
    val earnedBadgeNames = allBadgesMeta.filter { earnedBadges.contains(it.first) }.map { it.second }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Hasil Belajar & Sertifikat",
                avatarId = student.avatarId,
                xp = student.totalXp,
                badgeCount = earnedBadges.size,
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onBack = onBackToMap
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
            Column(
                modifier = Modifier
                    .widthIn(max = 680.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Digital Certificate Card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(3.dp, AmberSecondary)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = AmberSecondary, modifier = Modifier.size(48.dp))
                        Text("SERTIFIKAT PENGHARGAAN DIGITAL", style = MaterialTheme.typography.labelLarge, color = AmberSecondary)
                        Text("PETUALANGAN BALIGH", style = MaterialTheme.typography.headlineMedium, color = EmeraldPrimary)
                        Text("Diberikan kepada:", style = MaterialTheme.typography.bodyMedium)
                        Text(student.nickname, style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
                        Text("Kelas ${student.className} • ${settings.schoolName}", style = MaterialTheme.typography.bodyMedium)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                        Text(
                            "Telah mengikuti pembelajaran PAI Fase B: 'Aku Siap Tumbuh Menjadi Muslim yang Bertanggung Jawab'.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Surface(color = EmeraldPrimaryContainer, shape = RoundedCornerShape(50)) {
                                Text("${student.completedPosSet().size}/5 Pos Selesai", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontWeight = FontWeight.Bold)
                            }
                            Surface(color = AmberSecondaryContainer, shape = RoundedCornerShape(50)) {
                                Text("${student.totalXp} XP", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(
                            "Tanggal: ${student.sessionDate} • Pembimbing: ${settings.teacherName}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(4.dp))
                        Button(
                            onClick = {
                                ExportAndCertificateHelper.printStudentCertificate(
                                    context = context,
                                    student = student,
                                    summativeScore = latestSummative?.finalScore,
                                    badgeNames = earnedBadgeNames,
                                    settings = settings
                                )
                            },
                            modifier = Modifier.fillMaxWidth().testTag("print_certificate_button")
                        ) {
                            Icon(Icons.Default.Print, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Cetak / Simpan PDF Sertifikat")
                        }
                    }
                }

                // Badge Gallery
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Galeri Lencana (${earnedBadges.size}/${allBadgesMeta.size})", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                        allBadgesMeta.forEach { (id, name) ->
                            val has = earnedBadges.contains(id)
                            Surface(
                                color = if (has) SuccessContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (has) Icons.Default.Star else Icons.Default.EmojiEvents,
                                            contentDescription = null,
                                            tint = if (has) AmberSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(Modifier.width(10.dp))
                                        Text(name, fontWeight = if (has) FontWeight.Bold else FontWeight.Normal)
                                    }
                                    Text(if (has) "Diraih ✓" else "Belum", style = MaterialTheme.typography.labelMedium, color = if (has) SuccessGreen else MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }

                // Formative & Summative Breakdown
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Rekap Nilai Belajar", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                        if (formativeResults.isEmpty()) {
                            Text("Belum ada hasil kuis formatif pos.", style = MaterialTheme.typography.bodyMedium)
                        } else {
                            formativeResults.forEach { f ->
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Formatif Pos ${f.posNumber}", fontWeight = FontWeight.SemiBold)
                                    Text("Nilai ${f.scorePercent} (${f.correctCount}/${f.totalQuestions} benar)", color = EmeraldPrimary)
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        if (latestSummative == null) {
                            Text("Kuis Akhir (Sumatif) belum dikerjakan.", style = MaterialTheme.typography.bodyMedium)
                        } else {
                            Text("Nilai Sumatif Akhir: ${latestSummative.finalScore}", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                            Text("Status: ${latestSummative.passStatus}", style = MaterialTheme.typography.bodyMedium)
                            Text("Catatan: ${latestSummative.recommendation}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReflectionScreen(
    student: StudentSessionEntity,
    latestReflection: ReflectionEntity?,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onSaveReflection: (moodEmoji: String, summary: String, onDone: () -> Unit) -> Unit,
    onBackToMap: () -> Unit
) {
    val moods = listOf("😊 Senang", "🤩 Semangat", "😌 Tenang", "🤔 Masih Ingin Tahu")
    var selectedMood by remember { mutableStateOf(moods.first()) }
    val selectedAnswers = remember { mutableStateMapOf<String, String>() }
    val questions = TeacherModulAjarData.reflectionQuestions

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Refleksi Diri Murid",
                avatarId = student.avatarId,
                xp = student.totalXp,
                badgeCount = student.badgesList().size,
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onBack = onBackToMap
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
            Column(
                modifier = Modifier
                    .widthIn(max = 680.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(color = Pos4Color.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)) {
                    Text(
                        "Refleksi ini bersifat aman dan tidak meminta cerita pengalaman pribadi. Yuk pilih perasaan dan komitmen baikmu!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    )
                }

                latestReflection?.let {
                    Surface(color = SuccessContainer, shape = RoundedCornerShape(14.dp)) {
                        Text(
                            "Kamu sudah mengisi refleksi sebelumnya (${it.moodEmoji}). Kamu boleh memperbaruinya kapan saja.",
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Bagaimana perasaanmu belajar hari ini?", style = MaterialTheme.typography.titleMedium)
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            moods.forEach { mood ->
                                OutlinedButton(
                                    onClick = { selectedMood = mood },
                                    border = BorderStroke(
                                        if (selectedMood == mood) 2.dp else 1.dp,
                                        if (selectedMood == mood) EmeraldPrimary else MaterialTheme.colorScheme.outline
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(mood, modifier = Modifier.fillMaxWidth())
                                }
                            }
                        }
                    }
                }

                questions.forEach { rq ->
                    Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(rq.prompt, style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                            rq.options.forEach { opt ->
                                val picked = selectedAnswers[rq.id] == opt
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (picked) EmeraldPrimaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                    border = BorderStroke(1.dp, if (picked) EmeraldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                    modifier = Modifier.fillMaxWidth().clickable { selectedAnswers[rq.id] = opt }
                                ) {
                                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                        if (picked) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                                            Spacer(Modifier.width(8.dp))
                                        }
                                        Text(opt, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        val summary = questions.joinToString(" | ") { "${it.id}: ${selectedAnswers[it.id] ?: it.options.first()}" }
                        onSaveReflection(selectedMood, summary, onBackToMap)
                    },
                    enabled = selectedAnswers.size >= 3,
                    modifier = Modifier.fillMaxWidth().height(52.dp).testTag("save_reflection_button")
                ) {
                    Text(if (selectedAnswers.size >= 3) "Simpan Refleksiku (+15 XP)" else "Pilih minimal 3 jawaban refleksi")
                }
            }
        }
    }
}

@Composable
fun RemedialPengayaanScreen(
    student: StudentSessionEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onAwardXp: (Int, String) -> Unit,
    onBackToMap: () -> Unit
) {
    var tab by remember { mutableIntStateOf(0) } // 0: Remedial (Penguatan), 1: Pengayaan (Bintang)
    var showRemedialQuiz by remember { mutableStateOf(false) }
    var showEnrichmentQuiz by remember { mutableStateOf(false) }
    var feedbackBanner by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Pojok Penguatan & Bintang",
                avatarId = student.avatarId,
                xp = student.totalXp,
                badgeCount = student.badgesList().size,
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onBack = onBackToMap
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
            Column(modifier = Modifier.widthIn(max = 680.dp).fillMaxSize()) {
                TabRow(selectedTabIndex = tab) {
                    Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("Penguatan (Remedial)") })
                    Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Tantangan Pengayaan") })
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    feedbackBanner?.let {
                        Surface(color = SuccessContainer, shape = RoundedCornerShape(14.dp)) {
                            Text(it, modifier = Modifier.fillMaxWidth().padding(12.dp), color = SuccessGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (tab == 0) {
                        Surface(color = AmberSecondaryContainer, shape = RoundedCornerShape(16.dp)) {
                            Text(
                                "Setiap anak belajar dengan kecepatannya sendiri. Pojok Penguatan hadir untuk membantumu makin mantap tanpa rasa takut!",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth().padding(12.dp)
                            )
                        }

                        if (!showRemedialQuiz) {
                            PosCurriculumData.posModules.forEach { pos ->
                                Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text("Ringkasan ${pos.title}", style = MaterialTheme.typography.titleMedium, color = pos.accentColor)
                                        Text(pos.remedialSummary, style = MaterialTheme.typography.bodyMedium)
                                        Text("Tips: ${pos.remedialTip}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                            Button(onClick = { showRemedialQuiz = true }, modifier = Modifier.fillMaxWidth()) {
                                Icon(Icons.Default.School, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Kerjakan 5 Latihan Penguatan")
                            }
                        } else {
                            GenericQuestionRunner(
                                questions = AssessmentBankData.remedialQuestions,
                                student = student,
                                accentColor = AmberSecondary,
                                onPlayCorrect = onPlayCorrect,
                                onPlayWrong = onPlayWrong,
                                onFinish = { answers ->
                                    val ok = answers.count { it.isCorrect }
                                    val xp = ok * 10 + 10
                                    onAwardXp(xp, "Latihan Penguatan Remedial ($ok/5)")
                                    feedbackBanner = "Hebat! Kamu menyelesaikan latihan penguatan ($ok/5 benar, +$xp XP)!"
                                    showRemedialQuiz = false
                                }
                            )
                        }
                    } else {
                        if (!showEnrichmentQuiz) {
                            Button(
                                onClick = { showEnrichmentQuiz = true },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Mulai Kuis Lanjutan Pengayaan (5 Soal)")
                            }

                            Text("Proyek Kreatif Pengayaan", style = MaterialTheme.typography.titleLarge)
                            TeacherModulAjarData.enrichmentProjects.forEach { project ->
                                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text(project.title, style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
                                        Text(project.description, style = MaterialTheme.typography.bodyMedium)
                                        project.steps.forEachIndexed { idx, s ->
                                            Text("${idx + 1}. $s", style = MaterialTheme.typography.bodyMedium)
                                        }
                                    }
                                }
                            }
                        } else {
                            GenericQuestionRunner(
                                questions = AssessmentBankData.enrichmentQuestions,
                                student = student,
                                accentColor = EmeraldPrimary,
                                onPlayCorrect = onPlayCorrect,
                                onPlayWrong = onPlayWrong,
                                onFinish = { answers ->
                                    val ok = answers.count { it.isCorrect }
                                    val xp = ok * 15 + 15
                                    onAwardXp(xp, "Kuis Pengayaan ($ok/5)")
                                    feedbackBanner = "Luar biasa! Tantangan pengayaan selesai ($ok/5 benar, +$xp XP)!"
                                    showEnrichmentQuiz = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
