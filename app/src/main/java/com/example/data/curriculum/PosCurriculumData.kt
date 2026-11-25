package com.example.data.curriculum

import com.example.data.model.AvatarOption
import com.example.data.model.MatchPairItem
import com.example.data.model.MaterialCardItem
import com.example.data.model.MiniGameType
import com.example.data.model.PosModule
import com.example.data.model.ScenarioChoiceItem
import com.example.data.model.SequenceStepItem
import com.example.data.model.SevenAttitudeItem
import com.example.data.model.WordPuzzleItem
import com.example.ui.theme.Pos1Color
import com.example.ui.theme.Pos2Color
import com.example.ui.theme.Pos3Color
import com.example.ui.theme.Pos4Color
import com.example.ui.theme.Pos5Color

object PosCurriculumData {

    val posModules: List<PosModule> = listOf(
        PosModule(
            posNumber = 1,
            title = "Pos 1: Mengenal Baligh",
            subtitle = "Babak Baru Menuju Muslim Mandiri",
            openingStory = "Nara dan teman-teman berdiri di gerbang Taman Ilmu. Di depan mereka ada jalan bercahaya yang menggambarkan perjalanan tumbuh dari masa anak-anak menuju remaja. Yuk temukan arti baligh bersama Nara!",
            narrator = AvatarOption.NARA,
            missionGoal = "Memahami arti baligh dalam Islam, perjalanan pertumbuhan manusia, dan awal tanggung jawab beribadah dengan gembira.",
            badgeId = "badge_pos_1",
            badgeName = "Bintang Awal Baligh",
            badgeDescription = "Berhasil memahami makna baligh dan tanggung jawab awal seorang muslim.",
            accentColor = Pos1Color,
            gameType = MiniGameType.PILIH_PERNYATAAN_DAN_SUSUN_KATA,
            materialCards = listOf(
                MaterialCardItem(
                    title = "Apa Itu Baligh?",
                    summary = "Baligh berasal dari bahasa Arab yang artinya 'sampai'. Dalam Islam, baligh berarti seseorang telah sampai pada tahap kedewasaan awal sehingga mulai memiliki tanggung jawab menjalankan syariat agama.",
                    keyPoints = listOf(
                        "Baligh adalah tahap alami pertumbuhan manusia dari Allah Swt.",
                        "Setiap anak tumbuh dan berkembang menuju baligh sebagai karunia yang patut disyukuri.",
                        "Anak yang sudah baligh disebut mukalaf, yaitu muslim yang sudah wajib menjalankan perintah ibadah dan menjaga diri dari larangan Allah."
                    ),
                    gentleNote = "Tidak perlu terburu-buru! Setiap anak memiliki jadwal pertumbuhan masing-masing yang sudah diatur dengan indah oleh Allah Swt."
                ),
                MaterialCardItem(
                    title = "Dari Masa Anak-Anak ke Usia Baligh",
                    summary = "Saat masih kecil, ibadah seperti salat dan puasa adalah latihan penuh kasih sayang. Ketika memasuki usia baligh, latihan itu berubah menjadi kewajiban penuh dengan pahala besar.",
                    keyPoints = listOf(
                        "Masa anak-anak: belajar membaca Al-Qur'an, membiasakan salat, dan dibimbing orang tua.",
                        "Masa baligh: catatan amal kebaikan dan tanggung jawab pribadi mulai berlaku penuh.",
                        "Perubahan bukan untuk ditakuti, melainkan tanda bahwa kita dipercaya Allah untuk menjadi pribadi yang lebih mandiri."
                    )
                ),
                MaterialCardItem(
                    title = "Kewajiban Dasar Setelah Baligh",
                    summary = "Memasuki usia baligh berarti semakin semangat menjaga amanah ibadah harian dan akhlak baik.",
                    keyPoints = listOf(
                        "Menjalankan salat wajib lima waktu dengan tertib.",
                        "Melaksanakan puasa Ramadan (bagi yang mampu dan tidak berhalangan).",
                        "Menutup aurat, menjaga kebersihan diri, berkata jujur, dan berbakti kepada orang tua."
                    )
                )
            ),
            remedialSummary = "Ingat kata kuncinya: Baligh = Sampai pada tahap kedewasaan awal. Seseorang yang sudah baligh disebut Mukalaf dan mulai wajib melaksanakan salat 5 waktu serta menjaga akhlak.",
            remedialTip = "Baca ulang 3 kartu inti Pos 1, lalu coba susun kata kunci 'BALIGH' dan 'MUKALAF'."
        ),
        PosModule(
            posNumber = 2,
            title = "Pos 2: Tanda-Tanda Baligh",
            subtitle = "Mengenal Perubahan dengan Santun & Ilmiah",
            openingStory = "Di Pos 2, Aisyah dan Bimo melihat papan informasi ilmu fikih dan sains. Banyak teman kadang bingung soal tanda baligh. Yuk kita pelajari dengan bahasa yang santun, ilmiah, dan saling menghormati!",
            narrator = AvatarOption.AISYAH,
            missionGoal = "Mengenal tanda-tanda baligh menurut fikih dan biologi secara santun serta memahami bahwa waktu perkembangan tiap anak berbeda.",
            badgeId = "badge_pos_2",
            badgeName = "Detektif Ilmu Santun",
            badgeDescription = "Mampu membedakan fakta ilmiah & fikih tentang tanda baligh dengan sikap saling menghargai.",
            accentColor = Pos2Color,
            gameType = MiniGameType.BENAR_SALAH_DAN_RODA_DETEKTIF,
            materialCards = listOf(
                MaterialCardItem(
                    title = "Tanda Baligh Menurut Fikih Islam",
                    summary = "Para ulama fikih menjelaskan tiga tanda utama seseorang telah memasuki usia baligh:",
                    keyPoints = listOf(
                        "Mimpi basah (ihtilam) bagi anak laki-laki (atau perempuan jika mengalaminya).",
                        "Haid (menstruasi) bagi anak perempuan.",
                        "Mencapai usia 15 tahun hijriah apabila dua tanda sebelumnya belum dialami."
                    ),
                    gentleNote = "Jika salah satu tanda sudah muncul (misalnya haid atau mimpi basah pada usia 9–14 tahun), maka anak tersebut sudah masuk kategori baligh."
                ),
                MaterialCardItem(
                    title = "Mengenal Haid & Mimpi Basah Secara Santun",
                    summary = "Keduanya adalah proses alami tubuh yang sehat saat beranjak remaja, bukan penyakit dan bukan aib.",
                    keyPoints = listOf(
                        "Haid: keluarnya darah secara alami dan berkala pada anak perempuan yang sudah baligh sebagai tanda tubuh sehat.",
                        "Mimpi basah: keluarnya cairan alami saat tidur pada anak laki-laki yang menandakan sistem tubuh mulai matang.",
                        "Keduanya adalah rahasia privasi diri. Jika mengalaminya, ceritakan kepada orang tua atau guru terpercaya untuk dibimbing cara bersucinya."
                    ),
                    gentleNote = "Setiap anak tidak harus mengalami perubahan di waktu yang sama. Jangan pernah membandingkan diri atau menertawakan teman."
                ),
                MaterialCardItem(
                    title = "Perubahan Fisik & Emosi yang Alami",
                    summary = "Selain tanda utama fikih, tubuh dan perasaan juga ikut bertumbuh perlahan.",
                    keyPoints = listOf(
                        "Pada laki-laki: suara mulai berubah lebih berat, tinggi badan bertambah cepat, dan mulai tumbuh jakun.",
                        "Pada perempuan: tinggi badan bertambah dan bentuk tubuh mulai berkembang bertahap.",
                        "Perasaan lebih peka dan ingin lebih mandiri. Berdoa dan berbicara dengan orang tua membuat hati tetap tenang."
                    )
                )
            ),
            remedialSummary = "Tiga tanda baligh dalam fikih: (1) Mimpi basah, (2) Haid bagi perempuan, atau (3) Usia 15 tahun hijriah jika belum mengalami keduanya. Perkembangan setiap anak berbeda dan selalu normal.",
            remedialTip = "Jika masih bingung, tanyakan langsung kepada ayah, ibu, atau guru PAI di sekolah."
        ),
        PosModule(
            posNumber = 3,
            title = "Pos 3: Aku Menjaga Kebersihan",
            subtitle = "Bersih Sebagian dari Iman & Tata Cara Bersuci",
            openingStory = "Sampailah kita di Jembatan Mata Air Jernih! Bimo mengajak kita memastikan tubuh, pakaian, dan ibadah selalu suci sebelum menghadap Allah Swt.",
            narrator = AvatarOption.BIMO,
            missionGoal = "Memahami pentingnya kebersihan harian, pengertian bersuci (taharah), dan tata cara mandi wajib secara ringkas dan santun.",
            badgeId = "badge_pos_3",
            badgeName = "Penjaga Kesucian",
            badgeDescription = "Menguasai kebiasaan bersih harian dan urutan langkah mandi wajib yang benar.",
            accentColor = Pos3Color,
            gameType = MiniGameType.SUSUN_URUTAN_BERSUCI,
            materialCards = listOf(
                MaterialCardItem(
                    title = "Kebersihan Tubuh, Pakaian, dan Lingkungan",
                    summary = "Rasulullah saw. mengajarkan bahwa kebersihan adalah bagian dari iman. Memasuki usia baligh berarti semakin mandiri merawat diri.",
                    keyPoints = listOf(
                        "Mandi dua kali sehari dengan sabun, menggosok gigi, dan keramas teratur.",
                        "Mengganti pakaian bersih setelah berkeringat agar terhindar dari kuman dan bau badan.",
                        "Memastikan tempat tidur, kamar mandi, dan ruang belajar tetap rapi serta bebas najis."
                    )
                ),
                MaterialCardItem(
                    title = "Pengertian Bersuci & Hadas Besar",
                    summary = "Dalam fikih, agar sah melaksanakan salat dan membaca mushaf Al-Qur'an, seorang muslim harus suci dari hadas kecil dan hadas besar.",
                    keyPoints = listOf(
                        "Hadas kecil (seperti buang air atau buang angin) disucikan dengan berwudu.",
                        "Hadas besar terjadi setelah selesai haid bagi perempuan atau setelah mimpi basah bagi laki-laki.",
                        "Hadas besar disucikan dengan mandi wajib (mandi junub) menggunakan air suci yang menyucikan."
                    ),
                    gentleNote = "Selama masa haid, perempuan tidak wajib salat dan puasa; namun tetap boleh berzikir, berdoa, dan belajar. Setelah selesai haid, barulah mandi wajib lalu kembali salat."
                ),
                MaterialCardItem(
                    title = "Tata Cara Mandi Wajib Secara Ringkas",
                    summary = "Mandi wajib sangat mudah dilakukan apabila mengikuti urutannya dengan tenang (sesuaikan pula dengan panduan fikih sekolahmu):",
                    keyPoints = listOf(
                        "1. Membaca niat mandi wajib dalam hati karena Allah Ta'ala untuk menghilangkan hadas besar.",
                        "2. Mencuci kedua telapak tangan hingga bersih.",
                        "3. Membersihkan bagian tubuh yang kotor dengan air bersih.",
                        "4. Berwudu seperti wudu hendak salat.",
                        "5. Menyiram kepala tiga kali hingga air merata ke kulit kepala, lalu menyiram seluruh tubuh dari sisi kanan kemudian sisi kiri hingga rata tanpa terlewat."
                    ),
                    gentleNote = "Pastikan belajar langsung dari sumber terpercaya seperti buku PAI resmi, orang tua, atau guru PAI."
                )
            ),
            remedialSummary = "Mandi wajib dilakukan setelah selesai haid atau mengalami mimpi basah sebelum kembali salat. Urutan utamanya: Niat -> Cuci tangan -> Bersihkan kotoran -> Wudu -> Siram kepala & seluruh tubuh merata kanan ke kiri.",
            remedialTip = "Latih urutan langkah mandi wajib di permainan susun urutan sampai lancar!"
        ),
        PosModule(
            posNumber = 4,
            title = "Pos 4: Aku Menjaga Diri & Privasi",
            subtitle = "Batasan Aman, Aurat, dan Bijak di Dunia Digital",
            openingStory = "Di Pos 4, Aisyah menjaga Benteng Kehormatan. Saat beranjak dewasa, kita belajar melindungi aurat, menghormati batasan teman, dan menjaga privasi baik di dunia nyata maupun internet.",
            narrator = AvatarOption.AISYAH,
            missionGoal = "Menjaga aurat, menghargai privasi tubuh diri dan teman, mengenali orang dewasa tepercaya, serta berinternet dengan aman.",
            badgeId = "badge_pos_4",
            badgeName = "Perisai Privasi & Hormat",
            badgeDescription = "Siap melindungi batasan diri, menghormati teman, dan menjaga keamanan digital.",
            accentColor = Pos4Color,
            gameType = MiniGameType.SITUASI_PRIVASI_AMAN,
            materialCards = listOf(
                MaterialCardItem(
                    title = "Menjaga Aurat dan Privasi Tubuh",
                    summary = "Aurat adalah bagian tubuh yang harus dijaga dan ditutup dengan sopan sebagai bentuk rasa hormat kepada diri sendiri.",
                    keyPoints = listOf(
                        "Aurat laki-laki: bagian tubuh antara pusar sampai lutut.",
                        "Aurat perempuan: seluruh tubuh kecuali wajah dan kedua telapak tangan.",
                        "Biasakan berpakaian rapi, sopan, dan berganti pakaian di ruang tertutup."
                    )
                ),
                MaterialCardItem(
                    title = "Menghormati Batasan Diri & Teman",
                    summary = "Setiap anak berhak merasa aman dan dihargai saat tumbuh besar.",
                    keyPoints = listOf(
                        "Tidak mengejek, menertawakan, atau memaksa teman menceritakan perubahan tubuhnya.",
                        "Tidak ada orang lain yang boleh menyentuh area pribadi tubuh kita tanpa alasan medis yang didampingi orang tua.",
                        "Jika merasa tidak nyaman, bingung, atau takut, kamu BERHAK berkata 'Tidak!', menjauh, dan melapor kepada orang dewasa tepercaya (orang tua, guru, wali kelas)."
                    )
                ),
                MaterialCardItem(
                    title = "Jaga Privasi di Dunia Digital",
                    summary = "Saat memakai ponsel atau tablet, prinsip menjaga kehormatan tetap berlaku!",
                    keyPoints = listOf(
                        "Jangan membagikan alamat rumah, nomor telepon, kata sandi, atau foto pribadi kepada orang asing di internet.",
                        "Jangan pernah memotret atau menyebarkan foto/video teman tanpa izin.",
                        "Jika melihat pesan atau tayangan yang membuat tidak nyaman, segera tutup dan beri tahu orang tua."
                    )
                )
            ),
            remedialSummary = "Aurat laki-laki antara pusar dan lutut; aurat perempuan seluruh tubuh kecuali wajah & telapak tangan. Selalu hormati privasi teman, jaga data diri di internet, dan lapor kepada orang tua/guru jika merasa tidak aman.",
            remedialTip = "Ingat 3 langkah aman: Tolak dengan tegas, Menjauh ke tempat ramai, dan Cerita ke orang dewasa tepercaya."
        ),
        PosModule(
            posNumber = 5,
            title = "Pos 5: Muslim yang Bertanggung Jawab",
            subtitle = "Salat, Akhlak Mulia, dan Percaya Diri",
            openingStory = "Akhirnya kita tiba di Puncak Menara Cita-Cita! Nara, Bimo, dan Aisyah mengajakmu merangkum seluruh bekal untuk menjadi generasi muslim yang beriman, bersih, santun, dan bertanggung jawab.",
            narrator = AvatarOption.NARA,
            missionGoal = "Menumbuhkan tanggung jawab beribadah, menghormati orang tua dan guru, bersikap positif, dan siap melangkah menuju Tantangan Final.",
            badgeId = "badge_pos_5",
            badgeName = "Duta Akhlak Baligh",
            badgeDescription = "Siap mempraktikkan 7 sikap muslim bertanggung jawab dalam kehidupan sehari-hari.",
            accentColor = Pos5Color,
            gameType = MiniGameType.TANTANGAN_7_SIKAP,
            materialCards = listOf(
                MaterialCardItem(
                    title = "Tanggung Jawab Ibadah & Belajar",
                    summary = "Tanda muslim yang hebat bukan hanya bertambah usia, tetapi makin tekun beribadah dan rajin mencari ilmu.",
                    keyPoints = listOf(
                        "Menjaga salat 5 waktu tepat pada waktunya dengan kesadaran sendiri, bukan karena disuruh.",
                        "Rajin mengaji dan belajar ilmu agama dari guru maupun sumber tepercaya.",
                        "Berani bertanya dengan sopan jika ada hukum ibadah yang belum dipahami."
                    )
                ),
                MaterialCardItem(
                    title = "Akhlak Mulia kepada Sesama",
                    summary = "Kedewasaan tampak dari cara kita berbicara, bersikap, dan memperlakukan orang di sekitar kita.",
                    keyPoints = listOf(
                        "Hormat dan patuh kepada ayah, ibu, dan bapak/ibu guru.",
                        "Menghargai perbedaan fisik maupun tempo tumbuh kembang teman tanpa merundung.",
                        "Jujur dalam perkataan dan bertanggung jawab atas tugas rumah maupun sekolah."
                    )
                ),
                MaterialCardItem(
                    title = "Sikap Positif & Percaya Diri",
                    summary = "Tumbuh dewasa adalah proses bertahap yang indah.",
                    keyPoints = listOf(
                        "Syukuri diri apa adanya karena Allah menciptakan setiap anak unik.",
                        "Berdoa memohon perlindungan, kesehatan, dan bimbingan kepada Allah Swt.",
                        "Siap melangkah ke tantangan baru dengan semangat pantang menyerah!"
                    )
                )
            ),
            remedialSummary = "Muslim yang bertanggung jawab selalu menjaga salat 5 waktu, menjaga kebersihan, menghormati orang tua dan guru, menghargai teman, berani bertanya sopan, dan percaya diri menjalani proses bertumbuh.",
            remedialTip = "Coba aktifkan kembali ke-7 kartu sikap baik di game Pos 5!"
        )
    )

    // GAME 2: Cocokkan Istilah & Pengertian (Pos 1 & Pos 3)
    val matchPairsPos1: List<MatchPairItem> = listOf(
        MatchPairItem("m1", "Baligh", "Sampai pada tahap kedewasaan awal dalam Islam"),
        MatchPairItem("m2", "Mukalaf", "Muslim yang sudah wajib menjalankan syariat agama"),
        MatchPairItem("m3", "Salat 5 Waktu", "Kewajiban ibadah harian utama setelah baligh"),
        MatchPairItem("m4", "Aurat", "Bagian tubuh yang wajib dijaga dan ditutup sopan"),
        MatchPairItem("m5", "Mandiri", "Melaksanakan ibadah dan kebaikan atas kesadaran sendiri")
    )

    // GAME 7: Puzzle Kata Pengetahuan (Pos 1 & Pos 5)
    val wordPuzzlesPos1: List<WordPuzzleItem> = listOf(
        WordPuzzleItem("wp1", "Tahap kedewasaan awal seorang muslim menurut Islam", "BALIGH", "Baligh berarti 'sampai', yaitu sampainya seseorang pada tahap kewajiban beragama."),
        WordPuzzleItem("wp2", "Sebutan bagi orang Islam yang sudah wajib menjalankan perintah Allah", "MUKALAF", "Mukalaf adalah muslim yang berakal dan telah mencapai usia baligh."),
        WordPuzzleItem("wp3", "Ibadah tiang agama yang wajib dikerjakan 5 kali sehari", "SALAT", "Menjaga salat lima waktu adalah bukti tanggung jawab muslim yang telah baligh."),
        WordPuzzleItem("wp4", "Bagian tubuh yang wajib dijaga dan ditutup dengan pakaian sopan", "AURAT", "Menutup aurat menjaga kehormatan diri sesuai ajaran Islam."),
        WordPuzzleItem("wp5", "Sifat terpuji saat menjalankan tugas tanpa harus dipaksa", "AMANAH", "Amanah dan tanggung jawab mencerminkan kedewasaan akhlak anak saleh.")
    )

    // GAME 5: Roda Pertanyaan Detektif Ilmu (Pos 2)
    val wheelStatementsPos2: List<Pair<String, Boolean>> = listOf(
        "Mimpi basah merupakan tanda baligh yang alami pada anak laki-laki." to true,
        "Haid pada anak perempuan adalah kondisi sehat dan alami yang diatur Allah Swt." to true,
        "Semua anak kelas 4 SD pasti mengalami tanda baligh di tanggal yang sama." to false,
        "Jika belum mengalami tanda fisik, batas usia baligh menurut fikih adalah 15 tahun hijriah." to true,
        "Kita boleh menertawakan teman yang suaranya mulai berubah saat beranjak remaja." to false,
        "Jika bingung tentang perubahan tubuh, kita boleh bertanya pada orang tua atau guru PAI." to true
    )

    // GAME 4: Susun Urutan Langkah Mandi Wajib (Pos 3)
    val sequenceStepsMandiWajib: List<SequenceStepItem> = listOf(
        SequenceStepItem("s1", 1, "Membaca niat mandi wajib di dalam hati karena Allah Ta'ala."),
        SequenceStepItem("s2", 2, "Mencuci kedua telapak tangan hingga bersih."),
        SequenceStepItem("s3", 3, "Membersihkan bagian tubuh yang kotor dengan air bersih."),
        SequenceStepItem("s4", 4, "Berwudu dengan sempurna seperti wudu sebelum salat."),
        SequenceStepItem("s5", 5, "Menyiram kepala 3 kali hingga air merata ke kulit kepala."),
        SequenceStepItem("s6", 6, "Menyiram seluruh tubuh dari sisi kanan lalu sisi kiri hingga rata.")
    )

    // GAME 6: Situasi & Keputusan Aman + Privasi Digital (Pos 4)
    val scenarioChoicesPos4: List<ScenarioChoiceItem> = listOf(
        ScenarioChoiceItem(
            id = "sc1",
            characterName = "Aisyah",
            situation = "Saat istirahat, ada teman bercanda menanyakan hal pribadi tentang perubahan tubuh temannya di depan kelas.",
            choices = listOf(
                "Ikut menertawakan supaya dianggap seru",
                "Mengingatkan dengan sopan bahwa hal itu privasi dan tidak pantas dijadikan candaan",
                "Merekam pembicaraan itu dengan ponsel"
            ),
            bestChoiceIndex = 1,
            safeExplanation = "Hebat! Menghormati privasi dan perasaan teman adalah ciri akhlak mulia."
        ),
        ScenarioChoiceItem(
            id = "sc2",
            characterName = "Nara",
            situation = "Nara menerima pesan di gim daring dari akun asing yang menanyakan alamat rumah dan nama sekolahnya.",
            choices = listOf(
                "Memberitahu alamat agar dapat hadiah",
                "Menolak membagikan data pribadi, memblokir akun, dan melapor ke orang tua",
                "Mengirim foto seragam sekolah saja"
            ),
            bestChoiceIndex = 1,
            safeExplanation = "Tepat sekali! Alamat, sekolah, dan foto pribadi tidak boleh dibagikan ke orang asing."
        ),
        ScenarioChoiceItem(
            id = "sc3",
            characterName = "Bimo",
            situation = "Seorang teman merasa bingung setelah membaca info tidak jelas di media sosial tentang usia baligh.",
            choices = listOf(
                "Menyarankan bertanya kepada orang tua atau guru PAI agar mendapat ilmu yang benar",
                "Menyuruhnya menebak-nebak sendiri",
                "Mempercayai komentar acak di video pendek"
            ),
            bestChoiceIndex = 0,
            safeExplanation = "Benar! Sumber informasi terbaik adalah orang tua, guru PAI, dan buku pelajaran resmi."
        ),
        ScenarioChoiceItem(
            id = "sc4",
            characterName = "Aisyah",
            situation = "Ada orang yang tidak dikenal mencoba memegang bahu atau area pribadi secara memaksa dan membuat tidak nyaman.",
            choices = listOf(
                "Diam saja karena takut",
                "Berkata 'TIDAK!' dengan tegas, menjauh ke tempat aman, dan segera melapor ke orang dewasa tepercaya",
                "Menerimanya agar tidak dianggap tidak sopan"
            ),
            bestChoiceIndex = 1,
            safeExplanation = "Keren dan berani! Tubuhmu adalah milikmu yang dijaga Allah. Kamu berhak menolak dan melapor."
        ),
        ScenarioChoiceItem(
            id = "sc5",
            characterName = "Nara",
            situation = "Kamu memiliki foto kegiatan kelas yang lucu, tetapi ada teman yang tampak kurang rapi di foto tersebut.",
            choices = listOf(
                "Langsung unggah ke status media sosial",
                "Meminta izin terlebih dahulu dan tidak menyebarkan foto yang membuat teman malu",
                "Mengirimkan ke grup agar ditertawakan"
            ),
            bestChoiceIndex = 1,
            safeExplanation = "Masya Allah, bijak sekali! Tidak menyebarkan foto teman tanpa izin menjaga kehormatan bersama."
        )
    )

    // GAME 8 (Pos 5): 7 Sikap Muslim Bertanggung Jawab
    val sevenAttitudes: List<SevenAttitudeItem> = listOf(
        SevenAttitudeItem("a1", "1. Menjaga Salat 5 Waktu", "Melaksanakan salat tepat waktu dengan kesadaran hati.", "Segera berwudu saat mendengar azan tanpa menunggu dimarahi."),
        SevenAttitudeItem("a2", "2. Menjaga Kebersihan Diri", "Tubuh, pakaian, dan lingkungan selalu bersih dan suci.", "Mandi teratur, memakai baju bersih, dan merapikan kamar."),
        SevenAttitudeItem("a3", "3. Menutup Aurat & Santun", "Berpakaian sopan sesuai ajaran Islam.", "Memilih pakaian yang menutup aurat saat keluar rumah maupun olahraga."),
        SevenAttitudeItem("a4", "4. Hormat kepada Orang Tua & Guru", "Berbicara lembut, mendengar nasihat, dan membantu di rumah.", "Mengucapkan salam, mencium tangan, dan berkata santun."),
        SevenAttitudeItem("a5", "5. Menghargai Perbedaan Teman", "Tidak membandingkan fisik dan tidak mengejek teman.", "Mendukung teman belajar bersama dengan ramah."),
        SevenAttitudeItem("a6", "6. Berani Bertanya kepada Orang Tepercaya", "Mencari jawaban fikih dari guru PAI atau orang tua.", "Bertanya dengan sopan saat ada pelajaran bersuci yang belum jelas."),
        SevenAttitudeItem("a7", "7. Jujur, Amanah & Percaya Diri", "Mensyukuri karunia Allah dan jujur dalam tugas.", "Mengerjakan kuis sendiri tanpa menyontek dan gemar berdoa.")
    )
}
