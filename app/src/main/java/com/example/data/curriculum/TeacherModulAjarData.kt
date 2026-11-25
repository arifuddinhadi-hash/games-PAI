package com.example.data.curriculum

import com.example.data.model.EnrichmentProject
import com.example.data.model.LessonStep
import com.example.data.model.ModulAjarMeeting
import com.example.data.model.ReflectionQuestion
import com.example.data.model.RubricCriterion

object TeacherModulAjarData {

    const val cpNote: String =
        "Rumusan Tujuan Pembelajaran berikut diselaraskan dengan kompetensi Pendidikan Agama Islam dan Budi Pekerti Fase B (Kelas IV SD) Kurikulum Merdeka pada elemen Fikih dan Akhlak. Guru disarankan menyesuaikan redaksi Capaian Pembelajaran (CP) resmi dengan dokumen kurikulum sekolah dan regulasi terbaru yang berlaku."

    val identitasModul: List<Pair<String, String>> = listOf(
        "Mata Pelajaran" to "Pendidikan Agama Islam dan Budi Pekerti (PAI & BP)",
        "Jenjang / Kelas / Fase" to "SD / Kelas IV (Usia 9–10 Tahun) / Fase B",
        "Materi Utama" to "Menyambut Usia Baligh – Aku Siap Tumbuh Menjadi Muslim yang Bertanggung Jawab",
        "Alokasi Waktu" to "3 Pertemuan × 2 JP (sesuaikan dengan jadwal sekolah)",
        "Model Pembelajaran" to "Pembelajaran Mendalam (Deep Learning), Experiential Learning, Gamifikasi & Refleksi",
        "Pendekatan" to "Berpusat pada Murid, Inklusif, Santun, dan Ramah Perlindungan Anak",
        "Media Utama" to "Aplikasi Interaktif 'Petualangan Baligh', Proyektor/Tablet, Papan Misi, Google Sheets Rekap Guru"
    )

    val kompetensiAwal: List<String> = listOf(
        "Murid mengenal tata cara berwudu dan salat fardu lima waktu.",
        "Murid memahami kebiasaan menjaga kebersihan badan dan pakaian sehari-hari.",
        "Murid terbiasa bekerja sama dan menghargai teman di dalam kelas."
    )

    val profilKarakter: List<String> = listOf(
        "Beriman, bertakwa kepada Tuhan YME, dan berakhlak mulia",
        "Mandiri dan bertanggung jawab atas ibadah serta kebersihan diri",
        "Bernalar kritis memilah informasi benar dan mitos",
        "Berkebinekaan & menghargai keunikan pertumbuhan teman"
    )

    val pemahamanBermakna: String =
        "Memasuki usia baligh adalah tanda kasih sayang Allah Swt. bahwa seorang anak siap tumbuh menjadi pribadi muslim yang suci, bertanggung jawab dalam beribadah, menjaga aurat serta privasi, dan berakhlak mulia kepada sesama."

    val pertanyaanPemantik: List<String> = listOf(
        "Apa perbedaan tugas dan kebiasaan kita saat masih kelas 1 SD dengan sekarang di kelas 4 SD?",
        "Mengapa Islam sangat memperhatikan kebersihan tubuh, pakaian, dan kesucian sebelum beribadah?",
        "Bagaimana cara menjaga diri, privasi, dan perasaan teman saat kita semua bertumbuh besar?"
    )

    val tujuanPembelajaran: List<String> = listOf(
        "1. Menjelaskan pengertian baligh dengan bahasa sederhana.",
        "2. Menjelaskan bahwa baligh merupakan salah satu tahap perkembangan manusia.",
        "3. Mengetahui tanda-tanda baligh menurut ajaran Islam secara santun dan ilmiah.",
        "4. Mengenal perbedaan perkembangan yang dapat terjadi pada anak laki-laki dan perempuan.",
        "5. Memahami bahwa waktu perkembangan setiap anak tidak selalu sama.",
        "6. Memahami kewajiban agama yang mulai berlaku ketika seseorang telah baligh sesuai usia.",
        "7. Mengetahui pentingnya menjaga kebersihan diri.",
        "8. Mengenal tata cara bersuci, termasuk mandi wajib secara edukatif dan tidak eksplisit.",
        "9. Mengenal pengertian haid bagi perempuan secara sederhana, tidak menakutkan, dan tidak diskriminatif.",
        "10. Mengenal pengertian mimpi basah bagi laki-laki secara sederhana dan santun.",
        "11. Mengetahui cara menjaga aurat, privasi, dan batasan tubuh.",
        "12. Memahami pentingnya berdoa, belajar, bertanya kepada orang dewasa tepercaya, dan menjaga diri.",
        "13. Menunjukkan sikap bertanggung jawab, menghargai diri sendiri, dan menghormati teman."
    )

    val meetings: List<ModulAjarMeeting> = listOf(
        ModulAjarMeeting(
            meetingNumber = 1,
            title = "Pertemuan 1: Mengenal Baligh & Tanda-Tandanya Secara Santun",
            duration = "2 JP (2 × 35 menit)",
            focusPos = "Asesmen Diagnostik, Pos 1 (Mengenal Baligh) & Pos 2 (Tanda Baligh)",
            learningObjective = "Murid menjelaskan arti baligh, kewajiban dasar mukalaf, tanda baligh fikih/biologi secara santun, serta menghargai perbedaan tempo pertumbuhan tiap anak.",
            steps = listOf(
                LessonStep(
                    phase = "Pendahuluan",
                    durationMinutes = "10 menit",
                    teacherActivity = "Membuka kelas dengan salam, doa belajar, mengecek kehadiran, apersepsi foto perkembangan anak dari bayi hingga SD, serta menyampaikan pertanyaan pemantik.",
                    studentActivity = "Menjawab salam, berdoa khusyuk, mengamati alur pertumbuhan, dan menjawab pertanyaan pemantik secara santun.",
                    media = "Aplikasi Petualangan Baligh (Halaman Beranda)",
                    assessment = "Observasi kesiapan belajar"
                ),
                LessonStep(
                    phase = "Asesmen Awal",
                    durationMinutes = "10 menit",
                    teacherActivity = "Memandu murid masuk dengan nama panggilan/kode murid dan menyelesaikan 10 soal Asesmen Diagnostik tanpa tekanan nilai.",
                    studentActivity = "Memilih avatar (Nara/Aisyah/Bimo) dan menjawab 10 pertanyaan diagnostik secara mandiri.",
                    media = "Fitur Asesmen Diagnostik",
                    assessment = "Asesmen Diagnostik (10 soal)"
                ),
                LessonStep(
                    phase = "Inti: Pos 1 & Pos 2",
                    durationMinutes = "40 menit",
                    teacherActivity = "Memfasilitasi eksplorasi Pos 1 & Pos 2, menekankan bahwa waktu pertumbuhan berbeda dan melarang saling membandingkan/mengejek.",
                    studentActivity = "Membaca kartu materi, memainkan game Susun Kata & Roda Detektif, berdiskusi kelompok kecil, serta mengerjakan formatif Pos 1 & 2.",
                    media = "Pos 1 & Pos 2 + Mini-Game Interaktif",
                    assessment = "Asesmen Formatif Pos 1 & 2 (10 soal)"
                ),
                LessonStep(
                    phase = "Penutup",
                    durationMinutes = "10 menit",
                    teacherActivity = "Memberikan penguatan positif, mengingatkan agar bertanya kepada orang tua/guru jika bingung, dan menutup dengan doa.",
                    studentActivity = "Menyimpulkan materi hari ini dengan satu kalimat positif dan membaca doa penutup.",
                    media = "Papan refleksi singkat",
                    assessment = "Refleksi lisan"
                )
            )
        ),
        ModulAjarMeeting(
            meetingNumber = 2,
            title = "Pertemuan 2: Kebersihan, Bersuci, dan Menjaga Diri & Privasi",
            duration = "2 JP (2 × 35 menit)",
            focusPos = "Pos 3 (Kebersihan & Bersuci) & Pos 4 (Menjaga Diri & Privasi)",
            learningObjective = "Murid memahami pentingnya kebersihan, mengurutkan langkah mandi wajib secara benar, menjaga aurat, serta mengambil keputusan aman di dunia nyata dan digital.",
            steps = listOf(
                LessonStep(
                    phase = "Pendahuluan",
                    durationMinutes = "10 menit",
                    teacherActivity = "Salam, doa, review singkat materi Pos 1–2 lewat tanya jawab ceria.",
                    studentActivity = "Mengulang poin kunci tanda baligh dan saling menyemangati.",
                    media = "Peta Petualangan",
                    assessment = "Tanya jawab apersepsi"
                ),
                LessonStep(
                    phase = "Inti: Pos 3 & Pos 4",
                    durationMinutes = "45 menit",
                    teacherActivity = "Membimbing materi kebersihan, pengertian mandi wajib, diskusi studi kasus batasan aurat, privasi teman, orang dewasa tepercaya, dan keamanan internet.",
                    studentActivity = "Memainkan game Susun Urutan Mandi Wajib (Pos 3) dan Simulasi Keputusan Aman & Privasi Digital (Pos 4), lalu menyelesaikan formatif Pos 3 & 4.",
                    media = "Pos 3 & Pos 4 + Game Susun Urutan & Situasi Aman",
                    assessment = "Asesmen Formatif Pos 3 & 4 (10 soal) + Observasi diskusi"
                ),
                LessonStep(
                    phase = "Penutup",
                    durationMinutes = "15 menit",
                    teacherActivity = "Menguatkan aturan emas perlindungan anak ('Tolak, Menjauh, Cerita ke Orang Tepercaya') dan menutup pembelajaran.",
                    studentActivity = "Menuliskan komitmen menjaga kebersihan dan menghormati privasi teman.",
                    media = "Kartu komitmen",
                    assessment = "Cek pemahaman"
                )
            )
        ),
        ModulAjarMeeting(
            meetingNumber = 3,
            title = "Pertemuan 3: Tanggung Jawab, Tantangan Final & Asesmen Sumatif",
            duration = "2 JP (2 × 35 menit)",
            focusPos = "Pos 5, Tantangan Final, Asesmen Sumatif, Refleksi & Tindak Lanjut",
            learningObjective = "Murid menunjukkan kesiapan sikap muslim bertanggung jawab, menuntaskan asesmen sumatif, merefleksikan proses belajar, dan mengikuti remedial/pengayaan sesuai kebutuhan.",
            steps = listOf(
                LessonStep(
                    phase = "Pendahuluan",
                    durationMinutes = "10 menit",
                    teacherActivity = "Salam, doa, review cepat Pos 3–4 dan pengecekan progres kelas melalui Dashboard Guru.",
                    studentActivity = "Membuka Peta Misi dan bersiap menuju Pos 5.",
                    media = "Dashboard Guru & Peta Misi",
                    assessment = "Cek progres pra-sumatif"
                ),
                LessonStep(
                    phase = "Inti: Pos 5 & Sumatif",
                    durationMinutes = "45 menit",
                    teacherActivity = "Memandu eksplorasi Pos 5 (7 Sikap Muslim Bertanggung Jawab), Tantangan Final, dan Asesmen Sumatif (20 soal) secara kondusif dan jujur.",
                    studentActivity = "Menyelesaikan Pos 5, Tantangan Final, Asesmen Sumatif (20 soal), serta melihat Sertifikat Digital.",
                    media = "Pos 5, Tantangan Final, Sumatif & Sertifikat",
                    assessment = "Asesmen Formatif Pos 5 (5 soal) & Sumatif (20 soal)"
                ),
                LessonStep(
                    phase = "Refleksi & Tindak Lanjut",
                    durationMinutes = "15 menit",
                    teacherActivity = "Membimbing pengisian Refleksi Murid, memetakan murid untuk Remedial pendampingan atau Pengayaan proyek poster/peta konsep, lalu sinkronisasi ke Google Sheets.",
                    studentActivity = "Mengisi 6 pertanyaan refleksi murid dan memulai aktivitas remedial atau pengayaan sesuai arahan guru.",
                    media = "Refleksi Murid & Google Sheets Sync",
                    assessment = "Refleksi & Rencana Tindak Lanjut"
                )
            )
        )
    )

    val differentiationNotes: List<Pair<String, String>> = listOf(
        "Diferensiasi Konten" to "Menyediakan kartu ringkas, infografis langkah bersuci, dan mode ukuran teks besar (Aa) untuk kenyamanan membaca.",
        "Diferensiasi Proses" to "Murid belajar mandiri melalui permainan interaktif atau berpasangan; tersedia mode bebas belajar yang dapat diaktifkan guru.",
        "Diferensiasi Produk" to "Murid remedial fokus menguatkan konsep inti via latihan 5 soal ramah anak; murid pengayaan membuat poster, peta konsep, atau kampanye kebersihan."
    )

    val rubricCriteria: List<RubricCriterion> = listOf(
        RubricCriterion(
            aspect = "Pemahaman Konsep Baligh & Tanda-Tandanya",
            veryGood = "Menjelaskan arti baligh, mukalaf, dan 3 tanda baligh dengan sangat santun serta menghargai perbedaan tempo tumbuh.",
            good = "Menjelaskan arti baligh dan tanda-tandanya dengan benar secara umum.",
            fair = "Mengenal arti baligh tetapi masih tertukar pada sebagian tanda fikih.",
            needsGuidance = "Memerlukan pendampingan guru untuk memahami konsep dasar baligh dan tanda-tandanya."
        ),
        RubricCriterion(
            aspect = "Kebersihan Diri & Tata Cara Bersuci",
            veryGood = "Menjelaskan fungsi mandi wajib dan mengurutkan seluruh langkahnya secara tepat dan runtut.",
            good = "Menjelaskan pentingnya kebersihan dan sebagian besar urutan mandi wajib dengan benar.",
            fair = "Mengetahui kapan mandi wajib tetapi urutan langkahnya masih perlu dibimbing.",
            needsGuidance = "Memerlukan bimbingan bertahap mengenai kebersihan diri dan tata cara bersuci."
        ),
        RubricCriterion(
            aspect = "Menjaga Aurat, Privasi & Akhlak Tanggung Jawab",
            veryGood = "Konsisten memilih keputusan aman, menjaga privasi digital, dan menghormati teman tanpa mengejek.",
            good = "Menunjukkan sikap menjaga aurat dan memilih tindakan aman pada sebagian besar situasi.",
            fair = "Mulai memahami batasan privasi namun perlu pengingat dalam konteks digital.",
            needsGuidance = "Memerlukan pendampingan intensif mengenai batasan privasi dan kepada siapa melapor."
        )
    )

    val reflectionQuestions: List<ReflectionQuestion> = listOf(
        ReflectionQuestion(
            id = "REF-01",
            prompt = "1. Apa hal paling penting yang saya pelajari hari ini?",
            options = listOf(
                "Baligh adalah tahap alami menuju muslim yang mandiri",
                "Pentingnya menjaga kebersihan dan tata cara bersuci",
                "Cara menjaga aurat, privasi diri, dan menghargai teman",
                "Semua hal baik tentang tanggung jawab seorang muslim"
            )
        ),
        ReflectionQuestion(
            id = "REF-02",
            prompt = "2. Materi pos mana yang paling mudah saya pahami?",
            options = listOf(
                "Pos 1: Mengenal Baligh",
                "Pos 2: Tanda-Tanda Baligh",
                "Pos 3: Menjaga Kebersihan & Bersuci",
                "Pos 4: Menjaga Diri & Privasi",
                "Pos 5: Muslim yang Bertanggung Jawab"
            )
        ),
        ReflectionQuestion(
            id = "REF-03",
            prompt = "3. Materi apa yang masih ingin saya pelajari lebih dalam bersama guru atau orang tua?",
            options = listOf(
                "Arti baligh & tanggung jawab ibadah",
                "Tanda-tanda baligh yang santun",
                "Langkah mandi wajib yang benar",
                "Menjaga privasi di dunia nyata & internet",
                "Saya sudah cukup paham semuanya"
            )
        ),
        ReflectionQuestion(
            id = "REF-04",
            prompt = "4. Kebiasaan baik apa yang akan saya lakukan mulai hari ini untuk menjaga kebersihan?",
            options = listOf(
                "Mandi teratur memakai sabun & ganti baju bersih",
                "Menjaga kebersihan tempat ibadah & kamar",
                "Berwudu dengan rapi sebelum salat",
                "Melakukan semua kebiasaan bersih di atas"
            )
        ),
        ReflectionQuestion(
            id = "REF-05",
            prompt = "5. Bagaimana cara saya menghargai diri sendiri dan teman di sekolah?",
            options = listOf(
                "Menutup aurat dengan sopan & tidak mengejek teman",
                "Tidak membandingkan pertumbuhan tubuh teman",
                "Menjaga privasi foto & cerita teman",
                "Mempraktikkan semuanya setiap hari"
            )
        ),
        ReflectionQuestion(
            id = "REF-06",
            prompt = "6. Siapa orang dewasa tepercaya yang akan saya ajak bertanya jika saya bingung atau butuh bantuan?",
            options = listOf(
                "Ayah atau Ibu / Wali di rumah",
                "Bapak / Ibu Guru PAI",
                "Wali Kelas di sekolah",
                "Orang tua dan guru tepercaya"
            )
        )
    )

    val enrichmentProjects: List<EnrichmentProject> = listOf(
        EnrichmentProject(
            id = "PRJ-01",
            title = "Proyek 1: Poster '7 Kebiasaan Muslim Bersih & Bertanggung Jawab'",
            description = "Buatlah poster ceria di kertas gambar atau aplikasi desain sederhana yang mengajak teman-teman menjaga kebersihan, salat tepat waktu, dan saling menghargai.",
            steps = listOf(
                "Pilih 3–5 pesan positif dari Pos 3 dan Pos 5.",
                "Gunakan gambar simbol bersih seperti air, sabun, sajadah, atau senyuman teman.",
                "Presentasikan secara singkat di depan kelas dengan bahasa yang santun."
            )
        ),
        EnrichmentProject(
            id = "PRJ-02",
            title = "Proyek 2: Peta Konsep 'Bekalku Memasuki Usia Baligh'",
            description = "Rangkailah peta konsep bercabang berisi: Arti Baligh, 3 Tanda Fikih, Cara Bersuci, Menjaga Privasi, dan Kewajiban Ibadah.",
            steps = listOf(
                "Tulis tema utama di tengah kertas.",
                "Buat 5 cabang sesuai Pos 1 sampai Pos 5 dengan warna berbeda.",
                "Tambahkan satu doa kebaikan yang sering kamu baca."
            )
        ),
        EnrichmentProject(
            id = "PRJ-03",
            title = "Proyek 3: Kampanye Kelas Aman & Ramah Pertumbuhan",
            description = "Bersama kelompokmu, rancanglah 3 aturan kelas ramah anak untuk mencegah ejekan dan menjaga privasi digital teman.",
            steps = listOf(
                "Diskusikan contoh perkataan yang menyemangati teman.",
                "Tulis kesepakatan kelas: 'Stop Mengejek, Yuk Saling Menghargai!'.",
                "Tempelkan hasil karya di papan mading kelas dengan izin guru."
            )
        )
    )

    val sheetSchemaSummary: List<Pair<String, String>> = listOf(
        "1. IDENTITAS" to "ID Murid, Nama Panggilan, Kelas, Kelompok, Tanggal, Sesi (tanpa data sensitif).",
        "2. BANK_SOAL" to "ID Soal, Jenis Asesmen, Pos, Materi, Indikator, Pertanyaan, Tipe, Opsi A–D, Kunci, Skor, Feedback, Kesulitan.",
        "3. JAWABAN_MURID" to "Timestamp, ID Murid, Nama Panggilan, Kelas, ID Soal, Jenis, Pos, Jawaban, Kunci, Status, Skor, Durasi.",
        "4. HASIL_FORMATIF" to "Timestamp, ID Murid, Nama Panggilan, Kelas, Pos, Soal, Benar, Salah, Nilai, Penguasaan, Rekomendasi.",
        "5. HASIL_SUMATIF" to "Timestamp, ID Murid, Nama Panggilan, Kelas, Soal, Skor Perolehan, Skor Maks, Nilai, Ketuntasan, Rekomendasi.",
        "6. REKAP_GURU" to "Formula otomatis Google Sheets: COUNTA, AVERAGE, MAX, MIN, COUNTIF ketuntasan vs KKTP, & rata-rata per Pos.",
        "7. LOG_AKTIVITAS" to "Timestamp, ID Murid, Pos, Aktivitas, XP, Badge, Status Penyelesaian.",
        "8. PENGATURAN" to "Nama Sekolah, Kelas, KKTP, Jumlah Soal, Skor Maksimum, Nama Guru, Semester, Tahun Ajaran."
    )
}
