package com.example.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.ui.theme.Pos1Color
import com.example.ui.theme.Pos2Color
import com.example.ui.theme.Pos3Color
import com.example.ui.theme.Pos4Color
import com.example.ui.theme.Pos5Color

enum class AvatarOption(
    val id: String,
    val displayName: String,
    val roleLabel: String,
    val greeting: String,
    @DrawableRes val drawableRes: Int
) {
    NARA(
        id = "nara",
        displayName = "Nara",
        roleLabel = "Penjelajah Ilmu",
        greeting = "Halo! Aku Nara. Yuk kita jelajahi 5 pos seru tentang tumbuh dewasa dengan hati yang siap dan gembira!",
        drawableRes = R.drawable.img_avatar_nara
    ),
    AISYAH(
        id = "aisyah",
        displayName = "Aisyah",
        roleLabel = "Sahabat Petualangan",
        greeting = "Assalamu'alaikum! Aku Aisyah. Di petualangan ini kita belajar menjaga kebersihan, privasi, dan akhlak mulia.",
        drawableRes = R.drawable.img_avatar_aisyah
    ),
    BIMO(
        id = "bimo",
        displayName = "Bimo",
        roleLabel = "Sahabat Petualangan",
        greeting = "Hai teman! Aku Bimo. Ingat ya, setiap anak punya waktu tumbuh yang berbeda-beda dan itu hal yang normal!",
        drawableRes = R.drawable.img_avatar_bimo
    )
}

enum class AssessmentType(val label: String) {
    DIAGNOSTIK("Diagnostik"),
    FORMATIF("Formatif"),
    SUMATIF("Sumatif"),
    REMEDIAL("Remedial"),
    PENGAYAAN("Pengayaan")
}

enum class QuestionType(val label: String) {
    PILIHAN_GANDA("Pilihan Ganda"),
    BENAR_SALAH("Benar / Salah"),
    SITUASI("Studi Situasi"),
    JAWABAN_SINGKAT("Jawaban Singkat")
}

enum class MiniGameType(val title: String, val subtitle: String) {
    PILIH_PERNYATAAN_DAN_SUSUN_KATA(
        title = "Pilih Pernyataan & Susun Kata Baligh",
        subtitle = "Cocokkan istilah penting dan rangkai kata kunci tentang masa baligh"
    ),
    BENAR_SALAH_DAN_RODA_DETEKTIF(
        title = "Benar/Salah & Roda Detektif Tanda Baligh",
        subtitle = "Putar roda fakta santun dan bedakan mitos vs fakta ilmiah"
    ),
    SUSUN_URUTAN_BERSUCI(
        title = "Susun Langkah Bersuci & Detektif Kebersihan",
        subtitle = "Urutkan langkah mandi wajib secara benar dan pilih perilaku bersih"
    ),
    SITUASI_PRIVASI_AMAN(
        title = "Situasi & Keputusan Aman + Jaga Privasi Digital",
        subtitle = "Ambil keputusan tepat untuk menjaga aurat, batasan diri, dan keamanan digital"
    ),
    TANTANGAN_7_SIKAP(
        title = "Puzzle Pengetahuan & 7 Sikap Muslim Bertanggung Jawab",
        subtitle = "Lengkapi puzzle akhlak dan aktifkan 7 sikap utama muslim beranjak dewasa"
    ),
    TANTANGAN_FINAL(
        title = "Tantangan Final Penjelajah Baligh",
        subtitle = "Misi terpadu melintasi seluruh kompetensi Pos 1 sampai Pos 5"
    )
}

data class MaterialCardItem(
    val title: String,
    val summary: String,
    val keyPoints: List<String>,
    val gentleNote: String? = null
)

data class QuestionItem(
    val id: String,
    val assessmentType: AssessmentType,
    val posNumber: Int, // 0 for global/final, 1..5 for Pos
    val materialTopic: String,
    val indicator: String,
    val question: String,
    val questionType: QuestionType,
    val options: List<String>,
    val correctAnswer: String,
    val maxScore: Int = 5,
    val feedbackCorrect: String,
    val feedbackWrong: String,
    val explanation: String,
    val difficulty: String = "Sedang"
)

data class MatchPairItem(
    val id: String,
    val term: String,
    val definition: String
)

data class SequenceStepItem(
    val id: String,
    val orderIndex: Int,
    val text: String
)

data class WordPuzzleItem(
    val id: String,
    val clue: String,
    val targetWord: String,
    val explanation: String
)

data class ScenarioChoiceItem(
    val id: String,
    val situation: String,
    val characterName: String,
    val choices: List<String>,
    val bestChoiceIndex: Int,
    val safeExplanation: String
)

data class SevenAttitudeItem(
    val id: String,
    val title: String,
    val description: String,
    val exampleAction: String
)

data class PosModule(
    val posNumber: Int,
    val title: String,
    val subtitle: String,
    val openingStory: String,
    val narrator: AvatarOption,
    val missionGoal: String,
    val badgeId: String,
    val badgeName: String,
    val badgeDescription: String,
    val accentColor: Color,
    val materialCards: List<MaterialCardItem>,
    val gameType: MiniGameType,
    val remedialSummary: String,
    val remedialTip: String
)

data class ModulAjarMeeting(
    val meetingNumber: Int,
    val title: String,
    val duration: String,
    val focusPos: String,
    val learningObjective: String,
    val steps: List<LessonStep>
)

data class LessonStep(
    val phase: String,
    val durationMinutes: String,
    val teacherActivity: String,
    val studentActivity: String,
    val media: String,
    val assessment: String
)

data class RubricCriterion(
    val aspect: String,
    val veryGood: String,
    val good: String,
    val fair: String,
    val needsGuidance: String
)

data class ReflectionQuestion(
    val id: String,
    val prompt: String,
    val options: List<String>
)

data class EnrichmentProject(
    val id: String,
    val title: String,
    val description: String,
    val steps: List<String>
)

fun posAccentColor(posNumber: Int): Color = when (posNumber) {
    1 -> Pos1Color
    2 -> Pos2Color
    3 -> Pos3Color
    4 -> Pos4Color
    5 -> Pos5Color
    else -> Pos1Color
}
