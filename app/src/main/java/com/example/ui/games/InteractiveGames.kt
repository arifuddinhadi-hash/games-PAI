package com.example.ui.games

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.PosCurriculumData
import com.example.data.model.MatchPairItem
import com.example.data.model.QuestionItem
import com.example.data.model.ScenarioChoiceItem
import com.example.data.model.SequenceStepItem
import com.example.data.model.SevenAttitudeItem
import com.example.data.model.WordPuzzleItem
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ErrorCoral
import com.example.ui.theme.Pos2Color
import com.example.ui.theme.Pos3Color
import com.example.ui.theme.Pos4Color
import com.example.ui.theme.Pos5Color
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningContainer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 1. GAME COCOKKAN ISTILAH & PENGERTIAN (Pos 1)
 */
@Composable
fun CocokkanIstilahGame(
    pairs: List<MatchPairItem> = PosCurriculumData.matchPairsPos1,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onCompleted: (earnedXp: Int) -> Unit
) {
    val shuffledDefs = remember(pairs) { pairs.map { it.id to it.definition }.shuffled() }
    var selectedTermId by remember { mutableStateOf<String?>(null) }
    val matchedIds = remember { mutableStateListOf<String>() }
    var feedback by remember { mutableStateOf("Ketuk istilah di kiri, lalu ketuk pengertian yang cocok di kanan!") }
    var isErrorFeedback by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Game 1: Cocokkan Istilah Baligh", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                Text("${matchedIds.size}/${pairs.size}", style = MaterialTheme.typography.labelLarge, color = AmberSecondary)
            }
            Surface(
                color = if (isErrorFeedback) WarningContainer else SuccessContainer.copy(alpha = 0.7f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = feedback,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
            }

            pairs.forEach { item ->
                val isMatched = matchedIds.contains(item.id)
                val isSelected = selectedTermId == item.id
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = when {
                        isMatched -> SuccessContainer
                        isSelected -> EmeraldPrimary.copy(alpha = 0.16f)
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
                    },
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isMatched) SuccessGreen else if (isSelected) EmeraldPrimary else Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isMatched) {
                            selectedTermId = item.id
                            feedback = "Sekarang pilih arti yang cocok untuk '${item.term}'."
                            isErrorFeedback = false
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.term, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        if (isMatched) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Cocok", tint = SuccessGreen)
                        }
                    }
                }
            }

            Text("Pilih Pengertiannya:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)

            shuffledDefs.forEach { (defId, defText) ->
                val isMatched = matchedIds.contains(defId)
                OutlinedButton(
                    onClick = {
                        val termId = selectedTermId
                        if (termId == null) {
                            feedback = "Pilih dulu salah satu istilah di atas ya!"
                            isErrorFeedback = true
                        } else if (termId == defId) {
                            matchedIds.add(defId)
                            selectedTermId = null
                            isErrorFeedback = false
                            onPlayCorrect()
                            feedback = "Tepat sekali! Pasangan cocok (+10 XP)."
                        } else {
                            isErrorFeedback = true
                            onPlayWrong()
                            feedback = "Hampir tepat! Coba baca sekali lagi pengertiannya, kamu tidak kehilangan poin."
                        }
                    },
                    enabled = !isMatched,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isMatched) "✓ $defText" else defText,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }

            if (matchedIds.size == pairs.size) {
                Button(
                    onClick = { onCompleted(pairs.size * 10) },
                    modifier = Modifier.fillMaxWidth().testTag("match_game_finish"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Klaim +${pairs.size * 10} XP & Lanjut!")
                }
            }
        }
    }
}

/**
 * 2. GAME PUZZLE KATA PENGETAHUAN (Pos 1 & Pos 5)
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PuzzleKataGame(
    puzzles: List<WordPuzzleItem> = PosCurriculumData.wordPuzzlesPos1,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onCompleted: (earnedXp: Int) -> Unit
) {
    var index by remember { mutableIntStateOf(0) }
    val current = puzzles[index]
    val scrambled = remember(index) {
        current.targetWord.toList().mapIndexed { i, c -> i to c }.shuffled()
    }
    val pickedIndices = remember(index) { mutableStateListOf<Int>() }
    var statusText by remember(index) { mutableStateOf<String?>(null) }
    var solvedCurrent by remember(index) { mutableStateOf(false) }
    var totalXp by remember { mutableIntStateOf(0) }

    val builtWord = pickedIndices.map { scrambled[it].second }.joinToString("")

    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Puzzle Pengetahuan (${index + 1}/${puzzles.size})", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                Text("+$totalXp XP", style = MaterialTheme.typography.labelLarge, color = AmberSecondary)
            }
            Text("Petunjuk: ${current.clue}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (builtWord.isEmpty()) "_ ".repeat(current.targetWord.length) else builtWord,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = EmeraldPrimary,
                    modifier = Modifier.padding(14.dp)
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                scrambled.forEachIndexed { idx, (_, ch) ->
                    val used = pickedIndices.contains(idx)
                    FilterChip(
                        selected = used,
                        onClick = {
                            if (!solvedCurrent) {
                                if (used) pickedIndices.remove(idx) else pickedIndices.add(idx)
                            }
                        },
                        label = { Text(ch.toString(), style = MaterialTheme.typography.titleMedium) }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        pickedIndices.clear()
                        statusText = null
                    },
                    enabled = !solvedCurrent
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Ulang Susun")
                }
                Button(
                    onClick = {
                        if (builtWord.equals(current.targetWord, ignoreCase = true)) {
                            onPlayCorrect()
                            totalXp += 10
                            solvedCurrent = true
                            statusText = "Benar! ${current.explanation} (+10 XP)"
                        } else {
                            onPlayWrong()
                            statusText = "Belum pas nih, yuk coba susun lagi hurufnya!"
                        }
                    },
                    enabled = !solvedCurrent && builtWord.length == current.targetWord.length,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cek Kata")
                }
            }

            statusText?.let { msg ->
                Surface(
                    color = if (solvedCurrent) SuccessContainer else WarningContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(msg, modifier = Modifier.fillMaxWidth().padding(10.dp), style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (solvedCurrent) {
                Button(
                    onClick = {
                        if (index < puzzles.lastIndex) {
                            index++
                        } else {
                            onCompleted(totalXp)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (index < puzzles.lastIndex) "Kata Berikutnya" else "Selesaikan Puzzle (+$totalXp XP)")
                }
            }
        }
    }
}

/**
 * 3 & 5. RODA PERTANYAAN & BENAR ATAU SALAH DETEKTIF (Pos 2)
 */
@Composable
fun RodaDanBenarSalahGame(
    statements: List<Pair<String, Boolean>> = PosCurriculumData.wheelStatementsPos2,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onCompleted: (earnedXp: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(0f) }
    var activeIndex by remember { mutableIntStateOf(0) }
    val answered = remember { mutableStateMapOf<Int, Boolean>() }
    var feedback by remember { mutableStateOf<String?>(null) }
    var isSpinning by remember { mutableStateOf(false) }

    val wheelColors = listOf(EmeraldPrimary, Pos2Color, Pos3Color, Pos4Color, Pos5Color, AmberSecondary)

    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Roda Detektif: Benar atau Salah?", style = MaterialTheme.typography.titleLarge, color = Pos2Color)
            Text(
                "Putar roda untuk memilih kartu pernyataan (${answered.size}/${statements.size} selesai).",
                style = MaterialTheme.typography.bodyMedium
            )

            Box(contentAlignment = Alignment.TopCenter) {
                Canvas(modifier = Modifier.size(160.dp).padding(top = 12.dp)) {
                    val sweep = 360f / statements.size
                    rotate(rotation.value) {
                        statements.indices.forEach { i ->
                            drawArc(
                                color = wheelColors[i % wheelColors.size].copy(alpha = if (answered.containsKey(i)) 0.35f else 0.92f),
                                startAngle = i * sweep,
                                sweepAngle = sweep,
                                useCenter = true,
                                topLeft = Offset.Zero,
                                size = Size(size.width, size.height)
                            )
                        }
                    }
                    drawCircle(color = Color.White, radius = 22f)
                }
                // Pointer triangle
                Canvas(modifier = Modifier.size(20.dp)) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width / 2f, size.height)
                        close()
                    }
                    drawPath(path, color = Color(0xFF1D2623))
                }
            }

            Button(
                onClick = {
                    if (!isSpinning && answered.size < statements.size) {
                        isSpinning = true
                        feedback = null
                        val remaining = statements.indices.filter { !answered.containsKey(it) }
                        val next = remaining.random()
                        scope.launch {
                            rotation.animateTo(
                                targetValue = rotation.value + 540f + (next * 60f),
                                animationSpec = tween(750, easing = FastOutSlowInEasing)
                            )
                            activeIndex = next
                            isSpinning = false
                        }
                    }
                },
                enabled = !isSpinning && answered.size < statements.size
            ) {
                Text(if (isSpinning) "Memutar..." else "Putar Roda Pertanyaan!")
            }

            val (stmtText, correctBool) = statements[activeIndex]
            Surface(
                color = Pos2Color.copy(alpha = 0.08f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Pos2Color.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Pernyataan #${activeIndex + 1}:", style = MaterialTheme.typography.labelLarge, color = Pos2Color)
                    Text(stmtText, style = MaterialTheme.typography.titleMedium)

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                val ok = correctBool
                                answered[activeIndex] = ok
                                if (ok) {
                                    onPlayCorrect()
                                    feedback = "Benar sekali! Fakta ini santun dan ilmiah (+10 XP)."
                                } else {
                                    onPlayWrong()
                                    feedback = "Kurang tepat, pernyataan itu sebenarnya Salah. Yuk ingat faktanya!"
                                }
                            },
                            enabled = !answered.containsKey(activeIndex),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            modifier = Modifier.weight(1f)
                        ) { Text("BENAR") }

                        Button(
                            onClick = {
                                val ok = !correctBool
                                answered[activeIndex] = ok
                                if (ok) {
                                    onPlayCorrect()
                                    feedback = "Tepat! Kamu berhasil mendeteksi mitos yang salah (+10 XP)."
                                } else {
                                    onPlayWrong()
                                    feedback = "Pernyataan itu sebenarnya Benar ya! Tidak apa-apa, terus belajar."
                                }
                            },
                            enabled = !answered.containsKey(activeIndex),
                            colors = ButtonDefaults.buttonColors(containerColor = ErrorCoral),
                            modifier = Modifier.weight(1f)
                        ) { Text("SALAH") }
                    }
                }
            }

            feedback?.let {
                Surface(color = WarningContainer, shape = RoundedCornerShape(12.dp)) {
                    Text(it, modifier = Modifier.fillMaxWidth().padding(10.dp), style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (answered.size == statements.size) {
                val xp = answered.values.count { it } * 10 + 10
                Button(onClick = { onCompleted(xp) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Selesai! Klaim +$xp XP")
                }
            }
        }
    }
}

/**
 * 4. SUSUN URUTAN LANGKAH BERSUCI / MANDI WAJIB (Pos 3)
 */
@Composable
fun SusunUrutanGame(
    steps: List<SequenceStepItem> = PosCurriculumData.sequenceStepsMandiWajib,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onCompleted: (earnedXp: Int) -> Unit
) {
    val currentOrder = remember(steps) { mutableStateListOf<SequenceStepItem>().apply { addAll(steps.shuffled()) } }
    var checkedCorrect by remember { mutableStateOf(false) }
    var feedback by remember { mutableStateOf("Gunakan tombol panah atas/bawah untuk mengurutkan 6 langkah mandi wajib dari awal hingga akhir!") }

    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Game: Susun Urutan Mandi Wajib", style = MaterialTheme.typography.titleLarge, color = Pos3Color)
            Surface(
                color = if (checkedCorrect) SuccessContainer else Pos3Color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(feedback, modifier = Modifier.fillMaxWidth().padding(10.dp), style = MaterialTheme.typography.bodyMedium)
            }

            currentOrder.forEachIndexed { idx, step ->
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, if (checkedCorrect) SuccessGreen else Pos3Color.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(28.dp).clip(CircleShape).background(Pos3Color),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${idx + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(step.text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                        if (!checkedCorrect) {
                            IconButton(
                                onClick = {
                                    if (idx > 0) {
                                        val tmp = currentOrder[idx - 1]
                                        currentOrder[idx - 1] = currentOrder[idx]
                                        currentOrder[idx] = tmp
                                    }
                                },
                                enabled = idx > 0
                            ) {
                                Icon(Icons.Default.ArrowUpward, contentDescription = "Naikkan urutan")
                            }
                            IconButton(
                                onClick = {
                                    if (idx < currentOrder.lastIndex) {
                                        val tmp = currentOrder[idx + 1]
                                        currentOrder[idx + 1] = currentOrder[idx]
                                        currentOrder[idx] = tmp
                                    }
                                },
                                enabled = idx < currentOrder.lastIndex
                            ) {
                                Icon(Icons.Default.ArrowDownward, contentDescription = "Turunkan urutan")
                            }
                        }
                    }
                }
            }

            if (!checkedCorrect) {
                Button(
                    onClick = {
                        val isAllOrdered = currentOrder.mapIndexed { idx, s -> s.orderIndex == idx + 1 }.all { it }
                        if (isAllOrdered) {
                            checkedCorrect = true
                            onPlayCorrect()
                            feedback = "Alhamdulillah! Urutan langkah mandi wajib sudah tepat semua (+50 XP)!"
                        } else {
                            onPlayWrong()
                            val rightCount = currentOrder.mapIndexed { idx, s -> s.orderIndex == idx + 1 }.count { it }
                            feedback = "Sudah ada $rightCount langkah di posisi yang benar. Ingat: mulai dari Niat -> Cuci tangan -> Bersih -> Wudu -> Kepala -> Tubuh kanan ke kiri!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Periksa Urutan")
                }
            } else {
                Button(
                    onClick = { onCompleted(50) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) {
                    Text("Hebat! Klaim +50 XP & Lanjut")
                }
            }
        }
    }
}

/**
 * 6. PILIH KEPUTUSAN DARI SITUASI & PRIVASI DIGITAL (Pos 4)
 */
@Composable
fun SituasiKeputusanGame(
    scenarios: List<ScenarioChoiceItem> = PosCurriculumData.scenarioChoicesPos4,
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onCompleted: (earnedXp: Int) -> Unit
) {
    var index by remember { mutableIntStateOf(0) }
    var selectedIdx by remember(index) { mutableStateOf<Int?>(null) }
    var totalXp by remember { mutableIntStateOf(0) }
    val item = scenarios[index]

    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Situasi & Keputusan Aman (${index + 1}/${scenarios.size})", style = MaterialTheme.typography.titleLarge, color = Pos4Color)
                Text("+$totalXp XP", style = MaterialTheme.typography.labelLarge, color = AmberSecondary)
            }

            Surface(color = Pos4Color.copy(alpha = 0.09f), shape = RoundedCornerShape(14.dp)) {
                Text(
                    text = item.situation,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().padding(14.dp)
                )
            }

            item.choices.forEachIndexed { cIdx, choice ->
                val isPicked = selectedIdx == cIdx
                val isBest = cIdx == item.bestChoiceIndex
                OutlinedButton(
                    onClick = {
                        if (selectedIdx == null) {
                            selectedIdx = cIdx
                            if (isBest) {
                                totalXp += 10
                                onPlayCorrect()
                            } else {
                                onPlayWrong()
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = if (isPicked || (selectedIdx != null && isBest)) 2.dp else 1.dp,
                        color = when {
                            selectedIdx == null -> MaterialTheme.colorScheme.outline
                            isBest -> SuccessGreen
                            isPicked -> WarningAmber
                            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(choice, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth())
                }
            }

            if (selectedIdx != null) {
                val ok = selectedIdx == item.bestChoiceIndex
                Surface(
                    color = if (ok) SuccessContainer else WarningContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = (if (ok) "Keputusan Tepat (+10 XP): " else "Yuk Pilih Langkah Lebih Aman: ") + item.safeExplanation,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    )
                }
                Button(
                    onClick = {
                        if (index < scenarios.lastIndex) index++ else onCompleted(totalXp.coerceAtLeast(20))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (index < scenarios.lastIndex) "Situasi Berikutnya" else "Selesaikan Misi Pos 4")
                }
            }
        }
    }
}

/**
 * 7. TANTANGAN 7 SIKAP MUSLIM BERTANGGUNG JAWAB (Pos 5)
 */
@Composable
fun Tantangan7SikapGame(
    attitudes: List<SevenAttitudeItem> = PosCurriculumData.sevenAttitudes,
    onPlayCorrect: () -> Unit,
    onCompleted: (earnedXp: Int) -> Unit
) {
    val activatedIds = remember { mutableStateListOf<String>() }

    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Tantangan: 7 Sikap Muslim Hebat", style = MaterialTheme.typography.titleLarge, color = Pos5Color)
                Text("${activatedIds.size}/7", style = MaterialTheme.typography.titleMedium, color = EmeraldPrimary)
            }
            Text("Ketuk setiap kartu sikap untuk membaca komitmen nyata dan mengaktifkannya (+5 XP per kartu):", style = MaterialTheme.typography.bodyMedium)

            attitudes.forEach { item ->
                val active = activatedIds.contains(item.id)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (active) SuccessContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, if (active) SuccessGreen else Pos5Color.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (!active) {
                                activatedIds.add(item.id)
                                onPlayCorrect()
                            }
                        }
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(item.title, style = MaterialTheme.typography.titleMedium, color = if (active) SuccessGreen else Pos5Color)
                            if (active) Icon(Icons.Default.CheckCircle, contentDescription = "Aktif", tint = SuccessGreen)
                        }
                        Text(item.description, style = MaterialTheme.typography.bodyMedium)
                        if (active) {
                            Text("Aksi Nyata: ${item.exampleAction}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            if (activatedIds.size == attitudes.size) {
                Button(
                    onClick = { onCompleted(35) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Pos5Color)
                ) {
                    Text("Aktif Semua! Klaim +35 XP")
                }
            }
        }
    }
}

/**
 * 8. KUIS CEPAT DENGAN TIMER OPSIONAL NON-HUKUMAN (Tantangan Final)
 */
@Composable
fun QuizCepatGame(
    questions: List<QuestionItem>,
    title: String = "Kuis Cepat Tantangan Final",
    onPlayCorrect: () -> Unit,
    onPlayWrong: () -> Unit,
    onCompleted: (correctCount: Int, bonusXp: Int) -> Unit
) {
    var index by remember { mutableIntStateOf(0) }
    var useOptionalTimer by remember { mutableStateOf(false) }
    var secondsLeft by remember(index, useOptionalTimer) { mutableIntStateOf(20) }
    var pickedAnswer by remember(index) { mutableStateOf<String?>(null) }
    var correctCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(index, useOptionalTimer, pickedAnswer) {
        if (useOptionalTimer && pickedAnswer == null) {
            while (secondsLeft > 0) {
                delay(1000)
                secondsLeft--
            }
        }
    }

    val q = questions[index]
    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("$title (${index + 1}/${questions.size})", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(if (useOptionalTimer) "${secondsLeft}d" else "Santai", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.width(4.dp))
                    Switch(checked = useOptionalTimer, onCheckedChange = { useOptionalTimer = it })
                }
            }
            Text("Timer opsional hanya untuk latihan fokus dan tidak mengurangi nilaimu.", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Text(q.question, style = MaterialTheme.typography.titleMedium)

            q.options.forEach { opt ->
                val isSelected = pickedAnswer == opt
                val isCorrect = opt == q.correctAnswer
                OutlinedButton(
                    onClick = {
                        if (pickedAnswer == null) {
                            pickedAnswer = opt
                            if (isCorrect) {
                                correctCount++
                                onPlayCorrect()
                            } else {
                                onPlayWrong()
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = if (isSelected || (pickedAnswer != null && isCorrect)) 2.dp else 1.dp,
                        color = when {
                            pickedAnswer == null -> MaterialTheme.colorScheme.outline
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

            if (pickedAnswer != null) {
                val ok = pickedAnswer == q.correctAnswer
                Surface(color = if (ok) SuccessContainer else WarningContainer, shape = RoundedCornerShape(12.dp)) {
                    Text(
                        text = if (ok) "${q.feedbackCorrect} (+15 XP)" else "${q.feedbackWrong} ${q.explanation}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    )
                }
                Button(
                    onClick = {
                        if (index < questions.lastIndex) {
                            index++
                        } else {
                            onCompleted(correctCount, correctCount * 15 + 20)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (index < questions.lastIndex) "Soal Selanjutnya" else "Selesaikan Tantangan Final!")
                }
            }
        }
    }
}
