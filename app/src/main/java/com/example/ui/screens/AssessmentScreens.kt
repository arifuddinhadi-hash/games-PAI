package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.AssessmentBankData
import com.example.data.local.StudentAnswerEntity
import com.example.data.local.StudentSessionEntity
import com.example.data.local.SummativeResultEntity
import com.example.data.local.TeacherSettingsEntity
import com.example.data.model.AssessmentType
import com.example.data.model.QuestionItem
import com.example.data.model.QuestionType
import com.example.ui.components.BalighTopBar
import com.example.ui.components.TrustedAdultHelpDialog
import com.example.ui.games.QuizCepatGame
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningContainer
import com.example.util.ExportAndCertificateHelper

@Composable
fun DiagnosticAssessmentScreen(
    student: StudentSessionEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onSubmitDiagnostic: (List<StudentAnswerEntity>, onDone: () -> Unit) -> Unit,
    onBackToMap: () -> Unit
) {
    val questions = AssessmentBankData.diagnosticQuestions
    var completedCount by remember { mutableStateOf<Int?>(null) }
    var showHelp by remember { mutableStateOf(false) }

    if (showHelp) TrustedAdultHelpDialog(onDismiss = { showHelp = false })

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Asesmen Diagnostik (10 Soal)",
                avatarId = student.avatarId,
                xp = student.totalXp,
                badgeCount = student.badgesList().size,
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onShowHelp = { showHelp = true },
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
                val correct = completedCount
                if (correct == null) {
                    Surface(color = AmberSecondaryContainer, shape = RoundedCornerShape(16.dp)) {
                        Text(
                            text = "Cek Pengetahuan Awal: Jawablah sesuai pengetahuanmu sekarang. Tidak ada nilai jelek atau hukuman di sini!",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(12.dp)
                        )
                    }
                    GenericQuestionRunner(
                        questions = questions,
                        student = student,
                        accentColor = AmberSecondary,
                        onPlayCorrect = onPlayCorrect,
                        onPlayWrong = onPlayWrong,
                        onFinish = { answers ->
                            val okCount = answers.count { it.isCorrect }
                            onSubmitDiagnostic(answers) {
                                completedCount = okCount
                            }
                        }
                    )
                } else {
                    val interpretation = when {
                        correct >= 8 -> "Bekal awalmu sangat kuat! Kamu siap menjelajahi 5 Pos dan tantangan pengayaan."
                        correct >= 5 -> "Bekal awalmu cukup baik! Mari lengkapi pengetahuanmu dengan seru di Pos 1 sampai Pos 5."
                        else -> "Selamat datang penjelajah baru! Petualangan Pos 1 sampai Pos 5 akan membimbingmu langkah demi langkah."
                    }
                    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(54.dp))
                            Text("Cek Awal Selesai!", style = MaterialTheme.typography.headlineMedium, color = EmeraldPrimary)
                            Text("Kamu menjawab tepat $correct dari ${questions.size} pertanyaan awal.", style = MaterialTheme.typography.titleMedium)
                            Surface(color = SuccessContainer, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                                Text(interpretation, modifier = Modifier.padding(14.dp), style = MaterialTheme.typography.bodyMedium)
                            }
                            Button(onClick = onBackToMap, modifier = Modifier.fillMaxWidth()) {
                                Text("Lanjut ke Peta Petualangan")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FinalChallengeScreen(
    student: StudentSessionEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onFinishChallenge: (bonusXp: Int, onDone: () -> Unit) -> Unit,
    onBackToMap: () -> Unit
) {
    val mixedQuestions = remember {
        listOf(
            AssessmentBankData.formativeQuestions[0],
            AssessmentBankData.formativeQuestions[5],
            AssessmentBankData.formativeQuestions[11],
            AssessmentBankData.formativeQuestions[17],
            AssessmentBankData.formativeQuestions[20]
        )
    }
    var summaryText by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Tantangan Final",
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
                val msg = summaryText
                if (msg == null) {
                    QuizCepatGame(
                        questions = mixedQuestions,
                        title = "Tantangan Final Pos 1–5",
                        onPlayCorrect = onPlayCorrect,
                        onPlayWrong = onPlayWrong,
                        onCompleted = { correct, xp ->
                            onFinishChallenge(xp) {
                                summaryText = "Kamu menjawab tepat $correct dari ${mixedQuestions.size} misi final dan meraih Lencana Penjelajah Final (+$xp XP)!"
                            }
                        }
                    )
                } else {
                    Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AmberSecondary, modifier = Modifier.size(56.dp))
                            Text("Tantangan Final Ditaklukkan!", style = MaterialTheme.typography.headlineMedium, color = AmberSecondary)
                            Text(msg, style = MaterialTheme.typography.bodyLarge)
                            Button(onClick = onBackToMap, modifier = Modifier.fillMaxWidth()) {
                                Text("Lanjut ke Kuis Akhir (Sumatif)")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummativeAssessmentScreen(
    student: StudentSessionEntity,
    settings: TeacherSettingsEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onSubmitSummative: (List<StudentAnswerEntity>, onResult: (SummativeResultEntity) -> Unit) -> Unit,
    onOpenCertificate: () -> Unit,
    onOpenRemedial: () -> Unit,
    onBackToMap: () -> Unit
) {
    val questions = AssessmentBankData.summativeQuestions
    var result by remember { mutableStateOf<SummativeResultEntity?>(null) }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Kuis Akhir (Sumatif 20 Soal)",
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
                val res = result
                if (res == null) {
                    Surface(color = EmeraldPrimary.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)) {
                        Text(
                            text = "Asesmen Sumatif terdiri dari 20 soal (15 Pilihan Ganda, 3 Benar/Salah, dan 2 Studi Situasi/Jawaban Singkat). Kerjakan dengan tenang dan jujur ya!",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(12.dp)
                        )
                    }
                    GenericQuestionRunner(
                        questions = questions,
                        student = student,
                        accentColor = EmeraldPrimary,
                        onPlayCorrect = onPlayCorrect,
                        onPlayWrong = onPlayWrong,
                        onFinish = { answers ->
                            onSubmitSummative(answers) { saved ->
                                result = saved
                            }
                        }
                    )
                } else {
                    val passed = res.finalScore >= settings.kktp
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(2.dp, if (passed) SuccessGreen else AmberSecondary)
                    ) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = if (passed) AmberSecondary else EmeraldPrimary, modifier = Modifier.size(56.dp))
                            Text("Hasil Kuis Akhir", style = MaterialTheme.typography.headlineMedium, color = EmeraldPrimary)
                            Text("${res.finalScore}", style = MaterialTheme.typography.displayLarge, color = if (passed) SuccessGreen else AmberSecondary)
                            Surface(color = if (passed) SuccessContainer else WarningContainer, shape = RoundedCornerShape(50)) {
                                Text(res.passStatus, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
                            }
                            Text("Skor Perolehan: ${res.earnedScore} / ${res.maxScore} (KKTP: ${settings.kktp})", style = MaterialTheme.typography.bodyMedium)
                            Surface(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                                Text("Rekomendasi: ${res.recommendation}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(14.dp))
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                                if (!passed) {
                                    OutlinedButton(onClick = onOpenRemedial, modifier = Modifier.weight(1f)) {
                                        Text("Pojok Penguatan")
                                    }
                                }
                                Button(onClick = onOpenCertificate, modifier = Modifier.weight(1f).testTag("summative_to_certificate_button")) {
                                    Text("Lihat Sertifikat")
                                }
                            }
                            OutlinedButton(onClick = onBackToMap, modifier = Modifier.fillMaxWidth()) {
                                Text("Kembali ke Peta")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenericQuestionRunner(
    questions: List<QuestionItem>,
    student: StudentSessionEntity,
    accentColor: androidx.compose.ui.graphics.Color,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onFinish: (List<StudentAnswerEntity>) -> Unit
) {
    var index by remember { mutableIntStateOf(0) }
    var picked by remember(index) { mutableStateOf<String?>(null) }
    var shortInput by remember(index) { mutableStateOf("") }
    var qStartMillis by remember(index) { mutableStateOf(System.currentTimeMillis()) }
    val answers = remember { mutableStateListOf<StudentAnswerEntity>() }

    val q = questions[index]

    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("${q.assessmentType.label} • ${q.questionType.label}", style = MaterialTheme.typography.labelLarge, color = accentColor)
                Text("${index + 1}/${questions.size}", style = MaterialTheme.typography.titleMedium)
            }
            LinearProgressIndicator(
                progress = { (index + 1).toFloat() / questions.size },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)),
                color = accentColor
            )
            Text(q.question, style = MaterialTheme.typography.titleMedium)

            if (q.questionType == QuestionType.JAWABAN_SINGKAT || q.options.isEmpty()) {
                OutlinedTextField(
                    value = shortInput,
                    onValueChange = { if (picked == null) shortInput = it },
                    label = { Text("Tulis jawaban singkatmu") },
                    singleLine = true,
                    enabled = picked == null,
                    modifier = Modifier.fillMaxWidth().testTag("short_answer_input")
                )
                if (picked == null) {
                    Button(
                        onClick = {
                            val clean = shortInput.trim()
                            if (clean.isNotEmpty()) {
                                picked = clean
                                val elapsed = ((System.currentTimeMillis() - qStartMillis) / 1000).toInt().coerceAtLeast(1)
                                val ok = clean.equals(q.correctAnswer, ignoreCase = true)
                                if (ok) onPlayCorrect() else onPlayWrong()
                                answers.add(
                                    StudentAnswerEntity(
                                        timestamp = ExportAndCertificateHelper.nowTimestamp(),
                                        studentId = student.studentId,
                                        nickname = student.nickname,
                                        className = student.className,
                                        questionId = q.id,
                                        assessmentType = q.assessmentType.label,
                                        posNumber = q.posNumber,
                                        studentAnswer = clean,
                                        correctAnswer = q.correctAnswer,
                                        isCorrect = ok,
                                        earnedScore = if (ok) q.maxScore else 0,
                                        maxScore = q.maxScore,
                                        durationSeconds = elapsed
                                    )
                                )
                            }
                        },
                        enabled = shortInput.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Kirim Jawaban") }
                }
            } else {
                q.options.forEach { opt ->
                    val isSelected = picked == opt
                    val isCorrect = opt == q.correctAnswer
                    OutlinedButton(
                        onClick = {
                            if (picked == null) {
                                picked = opt
                                val elapsed = ((System.currentTimeMillis() - qStartMillis) / 1000).toInt().coerceAtLeast(1)
                                val ok = opt.equals(q.correctAnswer, ignoreCase = true)
                                if (ok) onPlayCorrect() else onPlayWrong()
                                answers.add(
                                    StudentAnswerEntity(
                                        timestamp = ExportAndCertificateHelper.nowTimestamp(),
                                        studentId = student.studentId,
                                        nickname = student.nickname,
                                        className = student.className,
                                        questionId = q.id,
                                        assessmentType = q.assessmentType.label,
                                        posNumber = q.posNumber,
                                        studentAnswer = opt,
                                        correctAnswer = q.correctAnswer,
                                        isCorrect = ok,
                                        earnedScore = if (ok) q.maxScore else 0,
                                        maxScore = q.maxScore,
                                        durationSeconds = elapsed
                                    )
                                )
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = if (isSelected || (picked != null && isCorrect)) 2.dp else 1.dp,
                            color = when {
                                picked == null -> MaterialTheme.colorScheme.outline
                                isCorrect -> SuccessGreen
                                isSelected -> WarningAmber
                                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(opt, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            if (picked != null) {
                val ok = picked.equals(q.correctAnswer, ignoreCase = true)
                Surface(color = if (ok) SuccessContainer else WarningContainer, shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(if (ok) q.feedbackCorrect else q.feedbackWrong, fontWeight = FontWeight.Bold)
                        Text(q.explanation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Button(
                    onClick = {
                        if (index < questions.lastIndex) {
                            index++
                        } else {
                            onFinish(answers.toList())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    modifier = Modifier.fillMaxWidth().testTag("assessment_next_button")
                ) {
                    Text(if (index < questions.lastIndex) "Soal Selanjutnya" else "Lihat Hasil Akhir")
                }
            }
        }
    }
}
