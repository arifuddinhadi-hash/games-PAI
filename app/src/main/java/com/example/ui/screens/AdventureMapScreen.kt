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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.PosCurriculumData
import com.example.data.local.StudentSessionEntity
import com.example.data.local.TeacherSettingsEntity
import com.example.data.model.AvatarOption
import com.example.ui.components.BalighTopBar
import com.example.ui.components.FriendlyCharacterBubble
import com.example.ui.components.TrustedAdultHelpDialog
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryContainer
import com.example.ui.theme.Pos4Color
import com.example.ui.theme.SuccessContainer
import com.example.ui.theme.SuccessGreen

@Composable
fun AdventureMapScreen(
    student: StudentSessionEntity,
    settings: TeacherSettingsEntity,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onBackToHome: () -> Unit,
    onOpenDiagnostic: () -> Unit,
    onOpenPos: (Int) -> Unit,
    onOpenFinalChallenge: () -> Unit,
    onOpenSummative: () -> Unit,
    onOpenResultsAndCertificate: () -> Unit,
    onOpenReflection: () -> Unit,
    onOpenRemedialEnrichment: () -> Unit
) {
    var showHelpDialog by remember { mutableStateOf(false) }
    val completedPos = student.completedPosSet()
    val allPosDone = completedPos.containsAll(listOf(1, 2, 3, 4, 5))
    val unlockedFinal = allPosDone || settings.freeLearningMode
    val avatar = AvatarOption.entries.firstOrNull { it.id == student.avatarId } ?: AvatarOption.NARA

    if (showHelpDialog) {
        TrustedAdultHelpDialog(onDismiss = { showHelpDialog = false })
    }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Peta Misi • ${student.nickname}",
                avatarId = student.avatarId,
                xp = student.totalXp,
                badgeCount = student.badgesList().size,
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onShowHelp = { showHelpDialog = true },
                onBack = onBackToHome
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
                // Progress Summary Banner
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Perjalanan Petualanganmu", style = MaterialTheme.typography.titleLarge, color = EmeraldPrimary)
                            Text("${completedPos.size}/5 Pos", style = MaterialTheme.typography.titleMedium, color = AmberSecondary)
                        }
                        LinearProgressIndicator(
                            progress = { completedPos.size / 5f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(50)),
                            color = EmeraldPrimary
                        )
                        if (settings.freeLearningMode) {
                            Surface(color = AmberSecondaryContainer, shape = RoundedCornerShape(10.dp)) {
                                Text(
                                    "Mode Bebas Belajar aktif oleh Guru: seluruh Pos & Tantangan Final dapat langsung dibuka.",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // Diagnostic Pre-check
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (student.diagnosticDone) SuccessContainer else AmberSecondaryContainer.copy(alpha = 0.7f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (student.diagnosticDone) Icons.Default.CheckCircle else Icons.Default.Quiz,
                            contentDescription = null,
                            tint = if (student.diagnosticDone) SuccessGreen else AmberSecondary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Cek Pengetahuan Awal (Diagnostik)", style = MaterialTheme.typography.titleMedium)
                            Text(
                                if (student.diagnosticDone) "Sudah selesai! Boleh diulang kapan saja." else "10 pertanyaan ringan sebelum memulai Pos 1 (tanpa nilai hukuman).",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = onOpenDiagnostic,
                            modifier = Modifier.testTag("open_diagnostic_button")
                        ) {
                            Text(if (student.diagnosticDone) "Ulang" else "Mulai")
                        }
                    }
                }

                FriendlyCharacterBubble(
                    character = avatar,
                    message = if (!allPosDone) {
                        "Ayo selesaikan Pos 1 sampai Pos 5 satu per satu untuk mengumpulkan 5 Lencana dan membuka Tantangan Final!"
                    } else {
                        "Masya Allah, hebat! Semua 5 Pos sudah kamu taklukkan. Sekarang tantang dirimu di Tantangan Final & Kuis Akhir!"
                    }
                )

                // 5 Pos Adventure Path
                PosCurriculumData.posModules.forEachIndexed { idx, pos ->
                    val isDone = completedPos.contains(pos.posNumber)
                    val isUnlocked = pos.posNumber == 1 ||
                        completedPos.contains(pos.posNumber - 1) ||
                        isDone ||
                        settings.freeLearningMode

                    if (idx > 0) {
                        // Subtle dotted connector line
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(width = 4.dp, height = 16.dp)) {
                                drawLine(
                                    color = pos.accentColor.copy(alpha = 0.45f),
                                    start = Offset(size.width / 2, 0f),
                                    end = Offset(size.width / 2, size.height),
                                    strokeWidth = 6f,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                                )
                            }
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f)
                        ),
                        border = BorderStroke(
                            width = if (isDone) 2.dp else 1.dp,
                            color = if (isDone) SuccessGreen else if (isUnlocked) pos.accentColor.copy(alpha = 0.45f) else Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = isUnlocked) { onOpenPos(pos.posNumber) }
                            .testTag("pos_card_${pos.posNumber}")
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(if (isUnlocked) pos.accentColor else Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Selesai", tint = Color.White, modifier = Modifier.size(28.dp))
                                } else if (!isUnlocked) {
                                    Icon(Icons.Default.Lock, contentDescription = "Terkunci", tint = Color.White, modifier = Modifier.size(24.dp))
                                } else {
                                    Text("${pos.posNumber}", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                                }
                            }
                            Spacer(Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(pos.title, style = MaterialTheme.typography.titleLarge, color = if (isUnlocked) pos.accentColor else MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(pos.subtitle, style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = if (isDone) "Lencana diraih: ${pos.badgeName}" else if (isUnlocked) "Siap dijelajahi • 1 Game + 5 Soal Formatif" else "Selesaikan Pos ${pos.posNumber - 1} dahulu",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isDone) SuccessGreen else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (isUnlocked) {
                                Button(
                                    onClick = { onOpenPos(pos.posNumber) },
                                    colors = ButtonDefaults.buttonColors(containerColor = pos.accentColor),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Final Challenge & Summative Cards
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (unlockedFinal) AmberSecondaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                    border = BorderStroke(1.5.dp, if (unlockedFinal) AmberSecondary else Color.Transparent)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (unlockedFinal) Icons.Default.AutoAwesome else Icons.Default.Lock,
                                contentDescription = null,
                                tint = AmberSecondary
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Gerbang Akhir Petualangan", style = MaterialTheme.typography.titleLarge)
                        }
                        Text(
                            text = if (unlockedFinal) {
                                "Hebat! Kamu sudah siap menaklukkan Tantangan Final dan Kuis Akhir (Asesmen Sumatif 20 Soal)."
                            } else {
                                "Selesaikan seluruh Pos 1 sampai Pos 5 untuk membuka Tantangan Final dan Kuis Akhir."
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = onOpenFinalChallenge,
                                enabled = unlockedFinal,
                                colors = ButtonDefaults.buttonColors(containerColor = AmberSecondary),
                                modifier = Modifier.weight(1f).testTag("open_final_challenge_button")
                            ) {
                                Text(if (student.finalChallengeDone) "Ulang Tantangan" else "Tantangan Final")
                            }
                            Button(
                                onClick = onOpenSummative,
                                enabled = unlockedFinal,
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                modifier = Modifier.weight(1f).testTag("open_summative_button")
                            ) {
                                Text(if (student.summativeDone) "Ulang Kuis Akhir" else "Kuis Akhir (20)")
                            }
                        }
                    }
                }

                // Quick Navigation Tiles (Hasil, Sertifikat, Refleksi, Remedial/Pengayaan)
                Text("Pojok Penjelajah", style = MaterialTheme.typography.titleLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    ExplorerTile(
                        title = "Hasil & Sertifikat",
                        subtitle = "XP, Lencana & PDF",
                        icon = Icons.Default.EmojiEvents,
                        color = EmeraldPrimary,
                        onClick = onOpenResultsAndCertificate,
                        modifier = Modifier.weight(1f).testTag("open_results_tile")
                    )
                    ExplorerTile(
                        title = "Refleksi Diri",
                        subtitle = if (student.reflectionDone) "Sudah diisi ✓" else "6 Pertanyaan",
                        icon = Icons.Default.Favorite,
                        color = Pos4Color,
                        onClick = onOpenReflection,
                        modifier = Modifier.weight(1f).testTag("open_reflection_tile")
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    ExplorerTile(
                        title = "Pojok Penguatan & Bintang",
                        subtitle = "Materi Ringkas, Remedial & Proyek Pengayaan",
                        icon = Icons.Default.School,
                        color = AmberSecondary,
                        onClick = onOpenRemedialEnrichment,
                        modifier = Modifier.fillMaxWidth().testTag("open_remedial_tile")
                    )
                }
            }
        }
    }
}

@Composable
private fun ExplorerTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, color.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color)
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, color = color)
                Text(subtitle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
