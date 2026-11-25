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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.AssessmentBankData
import com.example.data.curriculum.PosCurriculumData
import com.example.data.local.FormativeResultEntity
import com.example.data.local.StudentAnswerEntity
import com.example.data.local.StudentSessionEntity
import com.example.ui.components.BalighTopBar
import com.example.ui.components.FriendlyCharacterBubble
import com.example.ui.components.GrowthJourneyDiagram
import com.example.ui.components.TrustedAdultHelpDialog
import com.example.ui.games.CocokkanIstilahGame
import com.example.ui.games.PuzzleKataGame
import com.example.ui.games.RodaDanBenarSalahGame
import com.example.ui.games.SituasiKeputusanGame
import com.example.ui.games.SusunUrutanGame
import com.example.ui.games.Tantangan7SikapGame
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningContainer
import com.example.util.ExportAndCertificateHelper

@Composable
fun PosDetailScreen(
    posNumber: Int,
    student: StudentSessionEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onAwardGameXp: (Int, String) -> Unit,
    onSubmitFormative: (List<StudentAnswerEntity>, onResult: (FormativeResultEntity) -> Unit) -> Unit,
    onBackToMap: () -> Unit
) {
    val pos = PosCurriculumData.posModules.first { it.posNumber == posNumber }
    val formativeQuestions = remember(posNumber) {
        AssessmentBankData.formativeQuestions.filter { it.posNumber == posNumber }
    }

    var activeTab by remember { mutableIntStateOf(0) } // 0: Materi, 1: Mini-Game, 2: Kuis Formatif
    var showHelpDialog by remember { mutableStateOf(false) }
    var pos1GameSubMode by remember { mutableIntStateOf(0) } // 0: Cocokkan, 1: Puzzle Kata
    var gameFinishedBanner by remember { mutableStateOf<String?>(null) }
    var completedFormativeResult by remember { mutableStateOf<FormativeResultEntity?>(null) }

    if (showHelpDialog) {
        TrustedAdultHelpDialog(onDismiss = { showHelpDialog = false })
    }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = pos.title,
                avatarId = student.avatarId,
                xp = student.totalXp,
                badgeCount = student.badgesList().size,
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onShowHelp = { showHelpDialog = true },
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
                    .fillMaxSize()
            ) {
                ScrollableTabRow(
                    selectedTabIndex = activeTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = pos.accentColor,
                    edgePadding = 12.dp
                ) {
                    Tab(
                        selected = activeTab == 0,
                        onClick = { activeTab = 0 },
                        text = { Text("1. Materi & Cerita") }
                    )
                    Tab(
                        selected = activeTab == 1,
                        onClick = { activeTab = 1 },
                        text = { Text("2. Mini-Game") }
                    )
                    Tab(
                        selected = activeTab == 2,
                        onClick = { activeTab = 2 },
                        text = { Text("3. Kuis Formatif (5)") }
                    )
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
                            // Story & Mission Goal
                            FriendlyCharacterBubble(
                                character = pos.narrator,
                                message = pos.openingStory,
                                accentColor = pos.accentColor
                            )

                            Card(
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = pos.accentColor.copy(alpha = 0.1f)),
                                border = BorderStroke(1.dp, pos.accentColor.copy(alpha = 0.35f))
                            ) {
                                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = pos.accentColor)
                                    Spacer(Modifier.width(10.dp))
                                    Column {
                                        Text("Tujuan Misi", style = MaterialTheme.typography.titleMedium, color = pos.accentColor)
                                        Text(pos.missionGoal, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }

                            if (posNumber == 1) {
                                GrowthJourneyDiagram()
                            }

                            // Material Cards
                            pos.materialCards.forEachIndexed { idx, card ->
                                Card(
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier.size(28.dp).clip(CircleShape).background(pos.accentColor),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("${idx + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                                            }
                                            Spacer(Modifier.width(10.dp))
                                            Text(card.title, style = MaterialTheme.typography.titleLarge, color = pos.accentColor)
                                        }
                                        Text(card.summary, style = MaterialTheme.typography.bodyLarge)
                                        card.keyPoints.forEach { pt ->
                                            Row(verticalAlignment = Alignment.Top) {
                                                Text("• ", fontWeight = FontWeight.Bold, color = pos.accentColor)
                                                Text(pt, style = MaterialTheme.typography.bodyMedium)
                                            }
                                        }
                                        card.gentleNote?.let { note ->
                                            Surface(
                                                color = AmberSecondaryContainer.copy(alpha = 0.6f),
                                                shape = RoundedCornerShape(12.dp)
                                            ) {
                                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                                                    Icon(Icons.Default.Info, contentDescription = null, tint = AmberSecondary, modifier = Modifier.size(18.dp))
                                                    Spacer(Modifier.width(8.dp))
                                                    Text(note, style = MaterialTheme.typography.bodyMedium)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Button(
                                onClick = { activeTab = 1 },
                                colors = ButtonDefaults.buttonColors(containerColor = pos.accentColor),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth().height(50.dp).testTag("pos_to_game_button")
                            ) {
                                Icon(Icons.Default.SportsEsports, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Lanjut ke Mini-Game Seru!")
                            }
                        }

                        1 -> {
                            gameFinishedBanner?.let { msg ->
                                Surface(color = SuccessContainer, shape = RoundedCornerShape(16.dp)) {
                                    Column(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text(msg, style = MaterialTheme.typography.titleMedium, color = SuccessGreen)
                                        Button(
                                            onClick = { activeTab = 2 },
                                            colors = ButtonDefaults.buttonColors(containerColor = pos.accentColor),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("Lanjut ke Kuis Formatif Pos $posNumber")
                                        }
                                    }
                                }
                            }

                            when (posNumber) {
                                1 -> {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                        OutlinedButton(
                                            onClick = { pos1GameSubMode = 0 },
                                            modifier = Modifier.weight(1f)
                                        ) { Text("Cocokkan Istilah") }
                                        OutlinedButton(
                                            onClick = { pos1GameSubMode = 1 },
                                            modifier = Modifier.weight(1f)
                                        ) { Text("Puzzle Kata") }
                                    }
                                    if (pos1GameSubMode == 0) {
                                        CocokkanIstilahGame(
                                            onPlayCorrect = onPlayCorrect,
                                            onPlayWrong = onPlayWrong,
                                            onCompleted = { xp ->
                                                onAwardGameXp(xp, "Game Cocokkan Istilah Pos 1")
                                                gameFinishedBanner = "Hebat! Kamu mendapatkan +$xp XP dari Cocokkan Istilah!"
                                            }
                                        )
                                    } else {
                                        PuzzleKataGame(
                                            onPlayCorrect = onPlayCorrect,
                                            onPlayWrong = onPlayWrong,
                                            onCompleted = { xp ->
                                                onAwardGameXp(xp, "Game Puzzle Kata Pos 1")
                                                gameFinishedBanner = "Keren! Kamu menyelesaikan Puzzle Kata (+$xp XP)!"
                                            }
                                        )
                                    }
                                }
                                2 -> {
                                    RodaDanBenarSalahGame(
                                        onPlayCorrect = onPlayCorrect,
                                        onPlayWrong = onPlayWrong,
                                        onCompleted = { xp ->
                                            onAwardGameXp(xp, "Roda Detektif Tanda Baligh Pos 2")
                                            gameFinishedBanner = "Mantap! Roda Detektif selesai (+$xp XP)!"
                                        }
                                    )
                                }
                                3 -> {
                                    SusunUrutanGame(
                                        onPlayCorrect = onPlayCorrect,
                                        onPlayWrong = onPlayWrong,
                                        onCompleted = { xp ->
                                            onAwardGameXp(xp, "Susun Langkah Mandi Wajib Pos 3")
                                            gameFinishedBanner = "Alhamdulillah! Langkah bersuci sudah runtut (+$xp XP)!"
                                        }
                                    )
                                }
                                4 -> {
                                    SituasiKeputusanGame(
                                        onPlayCorrect = onPlayCorrect,
                                        onPlayWrong = onPlayWrong,
                                        onCompleted = { xp ->
                                            onAwardGameXp(xp, "Situasi & Privasi Digital Pos 4")
                                            gameFinishedBanner = "Hebat! Kamu pintar menjaga privasi diri & teman (+$xp XP)!"
                                        }
                                    )
                                }
                                5 -> {
                                    Tantangan7SikapGame(
                                        onPlayCorrect = onPlayCorrect,
                                        onCompleted = { xp ->
                                            onAwardGameXp(xp, "7 Sikap Muslim Bertanggung Jawab Pos 5")
                                            gameFinishedBanner = "Masya Allah! Ke-7 sikap mulia telah aktif (+$xp XP)!"
                                        }
                                    )
                                }
                            }
                        }

                        2 -> {
                            val result = completedFormativeResult
                            if (result == null) {
                                PosFormativeQuizRunner(
                                    posNumber = posNumber,
                                    accentColor = pos.accentColor,
                                    student = student,
                                    questions = formativeQuestions,
                                    onPlayCorrect = onPlayCorrect,
                                    onPlayWrong = onPlayWrong,
                                    onFinish = { answers ->
                                        onSubmitFormative(answers) { saved ->
                                            completedFormativeResult = saved
                                        }
                                    }
                                )
                            } else {
                                Card(
                                    shape = RoundedCornerShape(24.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    border = BorderStroke(2.dp, pos.accentColor)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = AmberSecondary, modifier = Modifier.size(56.dp))
                                        Text("Lencana Baru Diraih!", style = MaterialTheme.typography.headlineMedium, color = pos.accentColor)
                                        Surface(color = AmberSecondaryContainer, shape = RoundedCornerShape(50)) {
                                            Text(
                                                pos.badgeName,
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                            )
                                        }
                                        Text(pos.badgeDescription, style = MaterialTheme.typography.bodyMedium)
                                        Surface(color = SuccessContainer, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                Text("Nilai Formatif Pos $posNumber: ${result.scorePercent}", style = MaterialTheme.typography.titleLarge, color = SuccessGreen)
                                                Text("Benar ${result.correctCount} dari ${result.totalQuestions} soal • Status: ${result.masteryStatus}", style = MaterialTheme.typography.bodyMedium)
                                                Text("Saran: ${result.recommendation}", style = MaterialTheme.typography.bodyMedium)
                                            }
                                        }
                                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                                            OutlinedButton(
                                                onClick = { completedFormativeResult = null },
                                                modifier = Modifier.weight(1f)
                                            ) { Text("Ulangi Kuis") }
                                            Button(
                                                onClick = onBackToMap,
                                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                                modifier = Modifier.weight(1f).testTag("pos_finish_back_to_map")
                                            ) { Text("Kembali ke Peta") }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PosFormativeQuizRunner(
    posNumber: Int,
    accentColor: Color,
    student: StudentSessionEntity,
    questions: List<com.example.data.model.QuestionItem>,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onFinish: (List<StudentAnswerEntity>) -> Unit
) {
    var index by remember { mutableIntStateOf(0) }
    var picked by remember(index) { mutableStateOf<String?>(null) }
    var qStartMillis by remember(index) { mutableStateOf(System.currentTimeMillis()) }
    val collectedAnswers = remember { mutableStateListOf<StudentAnswerEntity>() }

    val q = questions[index]
    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Kuis Formatif Pos $posNumber", style = MaterialTheme.typography.titleLarge, color = accentColor)
                Text("Soal ${index + 1}/${questions.size}", style = MaterialTheme.typography.titleMedium)
            }
            Text(q.question, style = MaterialTheme.typography.titleMedium)

            q.options.forEach { option ->
                val isSelected = picked == option
                val isCorrect = option == q.correctAnswer
                OutlinedButton(
                    onClick = {
                        if (picked == null) {
                            picked = option
                            val elapsed = ((System.currentTimeMillis() - qStartMillis) / 1000).toInt().coerceAtLeast(1)
                            val ok = option.equals(q.correctAnswer, ignoreCase = true)
                            if (ok) onPlayCorrect() else onPlayWrong()
                            collectedAnswers.add(
                                StudentAnswerEntity(
                                    timestamp = ExportAndCertificateHelper.nowTimestamp(),
                                    studentId = student.studentId,
                                    nickname = student.nickname,
                                    className = student.className,
                                    questionId = q.id,
                                    assessmentType = q.assessmentType.label,
                                    posNumber = posNumber,
                                    studentAnswer = option,
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
                    Text(option, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth())
                }
            }

            if (picked != null) {
                val ok = picked.equals(q.correctAnswer, ignoreCase = true)
                Surface(color = if (ok) SuccessContainer else WarningContainer, shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (ok) q.feedbackCorrect else q.feedbackWrong,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(q.explanation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Button(
                    onClick = {
                        if (index < questions.lastIndex) {
                            index++
                        } else {
                            onFinish(collectedAnswers.toList())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (index < questions.lastIndex) "Soal Selanjutnya" else "Lihat Hasil & Raih Lencana!")
                }
            }
        }
    }
}
