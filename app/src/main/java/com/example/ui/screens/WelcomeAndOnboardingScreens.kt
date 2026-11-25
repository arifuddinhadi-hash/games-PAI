package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.StudentSessionEntity
import com.example.data.model.AvatarOption
import com.example.ui.components.BalighTopBar
import com.example.ui.components.FriendlyCharacterBubble
import com.example.ui.components.TrustedAdultHelpDialog
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryContainer
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1600)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        EmeraldPrimary,
                        Color(0xFF1F8F7B),
                        Color(0xFFFBF9F2)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + scaleIn(initialScale = 0.92f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.widthIn(max = 520.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_petualangan),
                        contentDescription = "Ilustrasi Petualangan Baligh",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                    )
                }
                Text(
                    text = "PETUALANGAN BALIGH",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Surface(
                    color = AmberSecondaryContainer,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Aku Siap Tumbuh Menjadi Muslim yang Bertanggung Jawab",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                Text(
                    text = "PAI & Budi Pekerti • Kelas IV SD (Fase B)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(180.dp)
                        .clip(RoundedCornerShape(50)),
                    color = EmeraldPrimary
                )
            }
        }
    }
}

@Composable
fun BerandaScreen(
    existingStudents: List<StudentSessionEntity>,
    activeStudent: StudentSessionEntity?,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onStartNewStudent: (nickname: String, className: String, groupName: String, avatarId: String) -> Unit,
    onResumeStudent: (StudentSessionEntity) -> Unit,
    onOpenTeacherDashboard: () -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("IV A") }
    var groupName by remember { mutableStateOf("Kelompok 1") }
    var selectedAvatar by remember { mutableStateOf(AvatarOption.NARA) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showRulesExpanded by remember { mutableStateOf(false) }

    if (showHelpDialog) {
        TrustedAdultHelpDialog(onDismiss = { showHelpDialog = false })
    }

    Scaffold(
        topBar = {
            BalighTopBar(
                title = "Petualangan Baligh",
                isMuted = isMuted,
                onToggleMute = onToggleMute,
                onCycleTextSize = onCycleTextSize,
                onShowHelp = { showHelpDialog = true }
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
                // Hero Card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_petualangan),
                            contentDescription = "Sahabat Petualangan Baligh",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                        )
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                color = EmeraldPrimaryContainer,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "Siap Belajar Offline • Tersimpan Aman di Perangkat",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "5 Pos Menuju Muslim yang Bertanggung Jawab",
                                style = MaterialTheme.typography.headlineMedium,
                                color = EmeraldPrimary
                            )
                            Text(
                                text = "Selamat datang penjelajah ilmu! Yuk belajar tentang masa baligh, kebersihan diri, menjaga privasi, dan akhlak mulia secara seru dan santun.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Resume active or saved student session
                if (activeStudent != null) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = EmeraldPrimaryContainer),
                        border = BorderStroke(1.5.dp, EmeraldPrimary)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val av = AvatarOption.entries.firstOrNull { it.id == activeStudent.avatarId } ?: AvatarOption.NARA
                            Image(
                                painter = painterResource(id = av.drawableRes),
                                contentDescription = av.displayName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, EmeraldPrimary, CircleShape)
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Lanjutkan Petualangan: ${activeStudent.nickname}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Kelas ${activeStudent.className} • ${activeStudent.completedPosSet().size}/5 Pos • ${activeStudent.totalXp} XP",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Button(
                                onClick = { onResumeStudent(activeStudent) },
                                modifier = Modifier.testTag("resume_adventure_button")
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Lanjut")
                            }
                        }
                    }
                }

                // Character Introduction & Rules
                FriendlyCharacterBubble(
                    character = selectedAvatar,
                    message = selectedAvatar.greeting,
                    accentColor = EmeraldPrimary
                )

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showRulesExpanded = !showRulesExpanded },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = EmeraldPrimary)
                                Spacer(Modifier.width(8.dp))
                                Text("Aturan Bermain yang Aman & Seru", style = MaterialTheme.typography.titleMedium)
                            }
                            Text(
                                text = if (showRulesExpanded) "Tutup" else "Lihat",
                                style = MaterialTheme.typography.labelLarge,
                                color = EmeraldPrimary
                            )
                        }
                        if (showRulesExpanded) {
                            Text("1. Jawaban benar mendapat +10 XP. Salah menjawab TIDAK mengurangi poin!", style = MaterialTheme.typography.bodyMedium)
                            Text("2. Selesaikan Pos 1 sampai Pos 5 secara berurutan untuk membuka Tantangan Final.", style = MaterialTheme.typography.bodyMedium)
                            Text("3. Tidak ada peringkat yang memalukan dan tidak ada pertanyaan pribadi sensitif.", style = MaterialTheme.typography.bodyMedium)
                            Text("4. Jika bingung, kamu boleh bertanya kepada orang tua atau guru PAI.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                // Student Registration (Minimal personal data: nickname/code only!)
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Daftar Penjelajah Baru",
                            style = MaterialTheme.typography.titleLarge,
                            color = EmeraldPrimary
                        )
                        Text(
                            text = "Cukup masukkan nama panggilan atau kode murid (tidak perlu data pribadi lengkap).",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = nickname,
                            onValueChange = { nickname = it },
                            label = { Text("Nama Panggilan / Kode Murid") },
                            placeholder = { Text("Contoh: Ahmad / M04") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("nickname_input")
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = className,
                                onValueChange = { className = it },
                                label = { Text("Kelas") },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("class_input")
                            )
                            OutlinedTextField(
                                value = groupName,
                                onValueChange = { groupName = it },
                                label = { Text("Kelompok") },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("group_input")
                            )
                        }

                        Text("Pilih Avatar Teman Petualangan:", style = MaterialTheme.typography.titleMedium)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AvatarOption.entries.forEach { avatar ->
                                val isSelected = selectedAvatar == avatar
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = if (isSelected) EmeraldPrimaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.5.dp else 1.dp,
                                        color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedAvatar = avatar }
                                        .testTag("avatar_${avatar.id}")
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box {
                                            Image(
                                                painter = painterResource(id = avatar.drawableRes),
                                                contentDescription = avatar.displayName,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                            )
                                            if (isSelected) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = "Dipilih",
                                                    tint = EmeraldPrimary,
                                                    modifier = Modifier
                                                        .size(22.dp)
                                                        .align(Alignment.BottomEnd)
                                                        .background(Color.White, CircleShape)
                                                )
                                            }
                                        }
                                        Text(avatar.displayName, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                val finalName = nickname.trim().ifBlank { "Penjelajah-${(10..99).random()}" }
                                onStartNewStudent(finalName, className, groupName, selectedAvatar.id)
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("start_adventure_button")
                        ) {
                            Icon(Icons.Default.Explore, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Mulai Petualangan Baligh!", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }

                // Switch between saved local students (optional)
                if (existingStudents.size > 1) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Ganti Profil Murid di Perangkat Ini", style = MaterialTheme.typography.titleMedium)
                            existingStudents.take(5).forEach { s ->
                                OutlinedButton(
                                    onClick = { onResumeStudent(s) },
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("${s.nickname} (${s.className})")
                                        Text("${s.completedPosSet().size}/5 Pos • ${s.totalXp} XP", color = AmberSecondary)
                                    }
                                }
                            }
                        }
                    }
                }

                // Teacher Dashboard Entry Card (Protected by PIN)
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = AmberSecondaryContainer.copy(alpha = 0.6f)),
                    border = BorderStroke(1.dp, AmberSecondary.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = AmberSecondary,
                            modifier = Modifier.size(34.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Area Khusus Guru PAI", style = MaterialTheme.typography.titleMedium)
                            Text(
                                "Dashboard hasil belajar, analisis soal, modul ajar 3 pertemuan, KKTP, & Google Sheets.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = onOpenTeacherDashboard,
                            colors = ButtonDefaults.buttonColors(containerColor = AmberSecondary),
                            modifier = Modifier.testTag("open_teacher_dashboard_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Guru")
                        }
                    }
                }
            }
        }
    }
}
