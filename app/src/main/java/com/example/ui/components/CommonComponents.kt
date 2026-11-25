package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.model.AvatarOption
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.AmberSecondaryContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryContainer
import com.example.ui.theme.Pos1Color
import com.example.ui.theme.Pos2Color
import com.example.ui.theme.Pos3Color
import com.example.ui.theme.Pos5Color

@Composable
fun BalighTopBar(
    title: String,
    avatarId: String? = null,
    xp: Int? = null,
    badgeCount: Int? = null,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onCycleTextSize: () -> Unit,
    onShowHelp: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBack != null) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("top_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali"
                    )
                }
            } else if (avatarId != null) {
                val avatar = AvatarOption.entries.firstOrNull { it.id == avatarId } ?: AvatarOption.NARA
                Image(
                    painter = painterResource(id = avatar.drawableRes),
                    contentDescription = "Avatar ${avatar.displayName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, EmeraldPrimary, CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (xp != null && badgeCount != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        XpPill(xp = xp)
                        BadgeCountPill(count = badgeCount)
                    }
                }
            }

            IconButton(
                onClick = onCycleTextSize,
                modifier = Modifier.testTag("text_size_button")
            ) {
                Icon(
                    imageVector = Icons.Default.FormatSize,
                    contentDescription = "Ubah ukuran teks"
                )
            }

            IconButton(
                onClick = onToggleMute,
                modifier = Modifier.testTag("mute_toggle_button")
            ) {
                Icon(
                    imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = if (isMuted) "Aktifkan suara" else "Mode tanpa suara"
                )
            }

            if (onShowHelp != null) {
                IconButton(
                    onClick = onShowHelp,
                    modifier = Modifier.testTag("trusted_adult_help_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "Bantuan dan orang dewasa tepercaya",
                        tint = EmeraldPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun XpPill(xp: Int) {
    Surface(
        color = AmberSecondaryContainer,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, AmberSecondary.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = AmberSecondary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$xp XP",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun BadgeCountPill(count: Int) {
    Surface(
        color = EmeraldPrimaryContainer,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = EmeraldPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$count Lencana",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun FriendlyCharacterBubble(
    character: AvatarOption,
    message: String,
    accentColor: Color = EmeraldPrimary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.09f)),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = character.drawableRes),
                contentDescription = character.displayName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(2.dp, accentColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${character.displayName} • ${character.roleLabel}",
                    style = MaterialTheme.typography.labelLarge,
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun GrowthJourneyDiagram(modifier: Modifier = Modifier) {
    val stages = listOf(
        Triple("1. Masa Anak", "Belajar & berlatih ibadah dengan gembira", Pos2Color),
        Triple("2. Menuju Baligh", "Tubuh & pikiran bertumbuh alami sesuai jadwal Allah", Pos3Color),
        Triple("3. Usia Baligh", "Mulai mukalaf, menjaga kesucian & amanah", Pos1Color),
        Triple("4. Muslim Mandiri", "Salat tertib, santun, & bermanfaat bagi sesama", Pos5Color)
    )
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Peta Perjalanan Pertumbuhan Manusia",
                style = MaterialTheme.typography.titleMedium,
                color = EmeraldPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .align(Alignment.TopCenter)
                ) {
                    val y = size.height / 2f
                    drawLine(
                        color = Color(0xFFB7DCD1),
                        start = Offset(32f, y),
                        end = Offset(size.width - 32f, y),
                        strokeWidth = 6f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    stages.forEachIndexed { idx, (_, _, color) ->
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${idx + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            stages.forEach { (title, desc, color) ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$title: ",
                        style = MaterialTheme.typography.labelLarge,
                        color = color
                    )
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun TrustedAdultHelpDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = EmeraldPrimary,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            Text(
                text = "Kamu Tidak Sendirian!",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Jika kamu merasa bingung tentang tanda baligh, cara bersuci, atau ada hal yang membuatmu tidak nyaman:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text("• Bertanyalah dengan sopan kepada Ayah, Ibu, atau Wali di rumah.", style = MaterialTheme.typography.bodyMedium)
                Text("• Bertanyalah kepada Bapak/Ibu Guru PAI atau Wali Kelas.", style = MaterialTheme.typography.bodyMedium)
                Text("• Jangan pernah membandingkan diri dengan teman dan jangan membagikan data pribadimu di internet.", style = MaterialTheme.typography.bodyMedium)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Mengerti, Terima Kasih!")
            }
        }
    )
}
