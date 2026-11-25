package com.example.data.curriculum

import com.example.data.model.AssessmentType
import com.example.data.model.QuestionItem
import com.example.data.model.QuestionType

object AssessmentBankData {

    // =========================================================================
    // 1. ASESMEN DIAGNOSTIK (10 SOAL) - Pengetahuan Awal Tanpa Hukuman
    // =========================================================================
    val diagnosticQuestions: List<QuestionItem> = listOf(
        QuestionItem(
            id = "DIAG-01",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 1,
            materialTopic = "Pengertian Baligh",
            indicator = "Mengenal istilah baligh secara sederhana",
            question = "Dalam pelajaran PAI, kata 'baligh' artinya anak mulai memasuki tahap...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Bayi baru lahir", "Kedewasaan awal", "Masa pensiun", "Permainan bebas"),
            correctAnswer = "Kedewasaan awal",
            maxScore = 10,
            feedbackCorrect = "Bagus! Kamu sudah punya gambaran awal tentang arti baligh.",
            feedbackWrong = "Tidak apa-apa, baligh artinya tahap kedewasaan awal. Yuk kita pelajari lebih seru di Pos 1!",
            explanation = "Baligh secara bahasa berarti 'sampai', yakni sampai pada tahap kedewasaan awal dalam Islam.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-02",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 1,
            materialTopic = "Kewajiban Ibadah",
            indicator = "Mengetahui kewajiban ibadah dasar",
            question = "Ibadah wajib yang harus dikerjakan lima kali sehari oleh seorang muslim yang baligh adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Salat fardu", "Piknik", "Lomba lari", "Sedekah tahunan"),
            correctAnswer = "Salat fardu",
            maxScore = 10,
            feedbackCorrect = "Benar! Salat fardu 5 waktu adalah kewajiban utama.",
            feedbackWrong = "Jawabannya adalah salat fardu 5 waktu. Nanti kita bahas lengkap di Pos 1 dan Pos 5.",
            explanation = "Salat lima waktu merupakan tiang agama yang menjadi kewajiban penuh sejak memasuki usia baligh.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-03",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 2,
            materialTopic = "Keberagaman Perkembangan",
            indicator = "Memahami tempo tumbuh anak berbeda",
            question = "Benar atau Salah: Setiap anak mengalami pertumbuhan menuju baligh di waktu yang bisa berbeda-beda.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 10,
            feedbackCorrect = "Tepat sekali! Allah mengatur waktu tumbuh setiap anak secara unik.",
            feedbackWrong = "Pernyataan itu Benar. Setiap anak punya jadwal tumbuh yang unik dan semuanya normal.",
            explanation = "Tidak semua anak mengalami perubahan pada usia yang sama persis; rentang waktunya berbeda-beda.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-04",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 2,
            materialTopic = "Tanda Baligh",
            indicator = "Mengenal tanda baligh secara umum",
            question = "Salah satu tanda baligh yang dialami anak perempuan menurut ilmu fikih adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Haid", "Tumbuh gigi susu", "Pandai bersepeda", "Mengantuk"),
            correctAnswer = "Haid",
            maxScore = 10,
            feedbackCorrect = "Betul! Di Pos 2 kita akan mempelajarinya dengan santun dan jelas.",
            feedbackWrong = "Tanda pada anak perempuan adalah haid. Tenang, di Pos 2 dijelaskan secara sederhana dan tidak menakutkan.",
            explanation = "Haid adalah tanda alami bahwa tubuh anak perempuan bertumbuh sehat memasuki usia baligh.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-05",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 3,
            materialTopic = "Kebersihan Diri",
            indicator = "Mengetahui pentingnya kebersihan dalam Islam",
            question = "Dalam ajaran Islam, menjaga kebersihan tubuh dan pakaian merupakan bagian dari...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Iman", "Kemalasan", "Kesombongan", "Hukuman"),
            correctAnswer = "Iman",
            maxScore = 10,
            feedbackCorrect = "Tepat! Kebersihan adalah sebagian dari iman.",
            feedbackWrong = "Dalam hadis, kebersihan disebut sebagian dari iman. Kita akan mendalaminya di Pos 3.",
            explanation = "Islam sangat mencintai kebersihan dan kesucian diri sebelum beribadah.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-06",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 3,
            materialTopic = "Bersuci",
            indicator = "Mengenal konsep mandi wajib",
            question = "Benar atau Salah: Untuk bersuci dari hadas besar, umat Islam melakukan mandi wajib dengan air bersih.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 10,
            feedbackCorrect = "Betul! Mandi wajib menyucikan tubuh dari hadas besar.",
            feedbackWrong = "Jawabannya Benar! Nanti di Pos 3 kita akan bermain menyusun langkah mandi wajib.",
            explanation = "Mandi wajib dengan air suci menyucikan hadas besar agar sah beribadah.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-07",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 4,
            materialTopic = "Menjaga Aurat",
            indicator = "Mengetahui kewajiban menutup aurat",
            question = "Ketika keluar rumah atau berolahraga bersama teman, pakaian yang kita kenakan sebaiknya...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Sopan dan menutup aurat", "Asal mahal", "Sangat tipis", "Tidak perlu rapi"),
            correctAnswer = "Sopan dan menutup aurat",
            maxScore = 10,
            feedbackCorrect = "Hebat! Berpakaian sopan dan menutup aurat menjaga kehormatan diri.",
            feedbackWrong = "Pilihan terbaik adalah pakaian yang sopan dan menutup aurat.",
            explanation = "Menutup aurat melindungi kehormatan dan melatih rasa malu yang baik.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-08",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 4,
            materialTopic = "Orang Dewasa Tepercaya",
            indicator = "Mengetahui kepada siapa bertanya jika bingung",
            question = "Situasi: Jika Nara bingung tentang pelajaran tanda-tanda baligh, sebaiknya Nara bertanya kepada...",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Orang tua atau guru PAI",
                "Orang asing di internet",
                "Menyimpan rasa takut sendirian"
            ),
            correctAnswer = "Orang tua atau guru PAI",
            maxScore = 10,
            feedbackCorrect = "Sangat bijak! Orang tua dan guru PAI siap membimbing dengan benar.",
            feedbackWrong = "Sebaiknya bertanya pada orang tua atau guru PAI yang tepercaya.",
            explanation = "Orang dewasa tepercaya memberikan penjelasan yang aman, benar, dan sesuai syariat.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-09",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 4,
            materialTopic = "Privasi Digital",
            indicator = "Mengetahui batasan privasi di internet",
            question = "Benar atau Salah: Alamat rumah dan kata sandi boleh dibagikan ke teman baru di gim daring.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Salah",
            maxScore = 10,
            feedbackCorrect = "Tepat! Data pribadi harus dijaga rapat demi keamananmu.",
            feedbackWrong = "Pernyataan itu Salah. Jangan pernah membagikan alamat rumah atau kata sandi di internet.",
            explanation = "Menjaga informasi pribadi di dunia digital melindungi diri dari bahaya.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "DIAG-10",
            assessmentType = AssessmentType.DIAGNOSTIK,
            posNumber = 5,
            materialTopic = "Menghormati Teman",
            indicator = "Menunjukkan sikap santun terhadap teman",
            question = "Situasi: Bimo melihat ada teman yang tubuhnya lebih tinggi dari teman lain. Sikap terbaik Bimo adalah...",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Menghargai dan tetap berteman akrab",
                "Memberinya julukan yang aneh",
                "Membanding-bandingkannya di depan kelas"
            ),
            correctAnswer = "Menghargai dan tetap berteman akrab",
            maxScore = 10,
            feedbackCorrect = "Masya Allah! Menghargai teman adalah akhlak anak saleh.",
            feedbackWrong = "Sikap terbaik adalah menghargai dan berteman akrab tanpa mengejek.",
            explanation = "Tidak membandingkan fisik teman menjaga persaudaraan dan rasa percaya diri bersama.",
            difficulty = "Mudah"
        )
    )

    // =========================================================================
    // 2. ASESMEN FORMATIF (25 SOAL = 5 SOAL PER POS)
    // =========================================================================
    val formativeQuestions: List<QuestionItem> = listOf(
        // --- POS 1 (5 soal) ---
        QuestionItem(
            id = "FORM-P1-01",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 1,
            materialTopic = "Pengertian Baligh",
            indicator = "Menjelaskan arti kata baligh secara bahasa dan istilah",
            question = "Secara bahasa Arab, kata 'baligh' memiliki arti...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Sampai", "Cepat", "Diam", "Kembali"),
            correctAnswer = "Sampai",
            maxScore = 20,
            feedbackCorrect = "Hebat! Baligh berarti 'sampai' pada tahap kedewasaan awal.",
            feedbackWrong = "Belum tepat. Baligh berasal dari bahasa Arab yang artinya 'sampai'.",
            explanation = "Baligh berarti sampainya seorang muslim pada usia di mana hukum syariat mulai berlaku penuh.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P1-02",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 1,
            materialTopic = "Mukalaf",
            indicator = "Mengenal sebutan mukalaf bagi muslim yang baligh",
            question = "Sebutan bagi seorang muslim yang berakal dan sudah mencapai usia baligh sehingga wajib menjalankan perintah agama adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Mukalaf", "Musafir", "Mualaf", "Mubazir"),
            correctAnswer = "Mukalaf",
            maxScore = 20,
            feedbackCorrect = "Tepat sekali! Mukalaf artinya dibebani tanggung jawab syariat dengan penuh kemuliaan.",
            feedbackWrong = "Istilah yang benar adalah Mukalaf.",
            explanation = "Seorang mukalaf mulai bertanggung jawab langsung atas salat, puasa, dan akhlaknya kepada Allah Swt.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "FORM-P1-03",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 1,
            materialTopic = "Perbedaan Masa Anak dan Baligh",
            indicator = "Membedakan kewajiban sebelum dan sesudah baligh",
            question = "Benar atau Salah: Ketika sudah memasuki usia baligh, ibadah salat 5 waktu menjadi tanggung jawab pribadi kepada Allah Swt.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 20,
            feedbackCorrect = "Benar! Salat 5 waktu adalah tanggung jawab utama saat baligh.",
            feedbackWrong = "Pernyataan tersebut Benar. Saat kecil kita berlatih, dan saat baligh salat menjadi kewajiban penuh.",
            explanation = "Setiap amalan baik setelah baligh dicatat langsung sebagai pahala tanggung jawab pribadi.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P1-04",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 1,
            materialTopic = "Makna Pertumbuhan",
            indicator = "Memahami baligh sebagai tahap alami menuju remaja",
            question = "Situasi: Nara merasa agak cemas mendengar kata 'baligh'. Nasihat terbaik untuk Nara adalah...",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Baligh adalah tahap alami yang baik agar kita makin mandiri dan disayang Allah",
                "Baligh adalah hukuman bagi anak kelas 4",
                "Kita harus bersembunyi jika mulai tumbuh tinggi"
            ),
            correctAnswer = "Baligh adalah tahap alami yang baik agar kita makin mandiri dan disayang Allah",
            maxScore = 20,
            feedbackCorrect = "Luar biasa! Nasihatmu sangat menenangkan dan sesuai ajaran Islam.",
            feedbackWrong = "Baligh bukan hal menakutkan, melainkan tahap alami menuju kedewasaan yang patut disyukuri.",
            explanation = "Dengan ilmu yang benar, anak siap menyambut masa baligh dengan percaya diri.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P1-05",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 1,
            materialTopic = "Tanggung Jawab Baligh",
            indicator = "Menyebutkan contoh kewajiban saat baligh",
            question = "Selain salat lima waktu, kewajiban agama yang mulai berlaku penuh saat sudah baligh adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Puasa Ramadan dan menutup aurat", "Bermain sepanjang malam", "Menunda tugas sekolah", "Mengoleksi mainan"),
            correctAnswer = "Puasa Ramadan dan menutup aurat",
            maxScore = 20,
            feedbackCorrect = "Cerdas! Puasa Ramadan dan menutup aurat termasuk kewajiban mukalaf.",
            feedbackWrong = "Jawabannya adalah Puasa Ramadan dan menutup aurat.",
            explanation = "Salat, puasa Ramadan, menutup aurat, dan berakhlak mulia menjadi pegangan utama sejak baligh.",
            difficulty = "Sedang"
        ),

        // --- POS 2 (5 soal) ---
        QuestionItem(
            id = "FORM-P2-01",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 2,
            materialTopic = "Tanda Baligh Laki-laki",
            indicator = "Mengenal tanda baligh khusus anak laki-laki",
            question = "Tanda baligh menurut fikih yang umumnya dialami anak laki-laki saat tidur adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Mimpi basah (ihtilam)", "Mimpi terbang", "Demam tinggi", "Gigi goyang"),
            correctAnswer = "Mimpi basah (ihtilam)",
            maxScore = 20,
            feedbackCorrect = "Tepat! Mimpi basah atau ihtilam adalah tanda alami kedewasaan laki-laki.",
            feedbackWrong = "Tanda tersebut adalah mimpi basah (ihtilam).",
            explanation = "Mimpi basah menandakan tubuh anak laki-laki mulai memasuki tahap kedewasaan fisik.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P2-02",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 2,
            materialTopic = "Tanda Baligh Perempuan",
            indicator = "Mengenal tanda baligh khusus anak perempuan",
            question = "Tanda baligh khusus bagi anak perempuan yang menandakan tubuhnya sehat dan matang adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Haid (menstruasi)", "Suara menjadi serak", "Rambut memendek", "Mengantuk di kelas"),
            correctAnswer = "Haid (menstruasi)",
            maxScore = 20,
            feedbackCorrect = "Betul! Haid adalah tanda alami dan sehat bagi perempuan.",
            feedbackWrong = "Jawabannya adalah haid (menstruasi).",
            explanation = "Haid adalah keluarnya darah alami secara berkala pada perempuan sebagai sunnatullah.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P2-03",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 2,
            materialTopic = "Batas Usia Fikih",
            indicator = "Mengetahui batas usia baligh menurut fikih",
            question = "Apabila seseorang belum mengalami haid ataupun mimpi basah, menurut fikih ia tetap dianggap baligh ketika mencapai usia...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("15 tahun hijriah", "7 tahun masehi", "25 tahun hijriah", "5 tahun hijriah"),
            correctAnswer = "15 tahun hijriah",
            maxScore = 20,
            feedbackCorrect = "Benar sekali! Usia 15 tahun hijriah adalah batas usia baligh dalam fikih.",
            feedbackWrong = "Batas usia menurut fikih adalah 15 tahun hijriah.",
            explanation = "Mayoritas ulama fikih menetapkan usia 15 tahun hijriah sebagai penanda masuknya usia baligh bila tanda biologis belum tampak.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "FORM-P2-04",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 2,
            materialTopic = "Perbedaan Waktu Tumbuh",
            indicator = "Memahami waktu perkembangan tidak selalu sama",
            question = "Benar atau Salah: Jika teman sebaya sudah mengalami perubahan suara sedangkan kita belum, berarti kita mengalami gangguan.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Salah",
            maxScore = 20,
            feedbackCorrect = "Tepat! Pernyataan itu salah karena waktu pertumbuhan setiap anak memang berbeda.",
            feedbackWrong = "Jawabannya Salah. Setiap anak bertumbuh dengan kecepatan berbeda dan semua itu normal.",
            explanation = "Tidak perlu membandingkan diri dengan teman karena Allah mengatur pertumbuhan tiap anak dengan sempurna.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P2-05",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 2,
            materialTopic = "Sumber Bertanya",
            indicator = "Mengetahui sikap tepat ketika bingung tentang tanda baligh",
            question = "Situasi: Jika Aisyah ingin tahu cara mempersiapkan diri menghadapi masa haid nanti, kepada siapa ia paling tepat bertanya?",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Ibu, kakak perempuan, atau guru PAI perempuan",
                "Teman yang sama-sama menebak",
                "Komentar orang asing di media sosial"
            ),
            correctAnswer = "Ibu, kakak perempuan, atau guru PAI perempuan",
            maxScore = 20,
            feedbackCorrect = "Pilihan yang sangat bijak dan aman!",
            feedbackWrong = "Sebaiknya bertanya kepada ibu, orang tua, atau guru PAI yang terpercaya.",
            explanation = "Orang dewasa tepercaya akan menjelaskan dengan ramah, benar, dan menjaga privasi anak.",
            difficulty = "Mudah"
        ),

        // --- POS 3 (5 soal) ---
        QuestionItem(
            id = "FORM-P3-01",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 3,
            materialTopic = "Kebersihan Harian",
            indicator = "Menerapkan kebiasaan menjaga kebersihan tubuh & pakaian",
            question = "Kebiasaan bersih berikut yang paling baik dilakukan setelah berolahraga dan berkeringat adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Mandi dengan sabun dan mengganti pakaian bersih",
                "Langsung tidur dengan baju olahraga yang basah",
                "Menyemprot parfum tanpa mandi",
                "Membiarkan baju dipakai lagi besok"
            ),
            correctAnswer = "Mandi dengan sabun dan mengganti pakaian bersih",
            maxScore = 20,
            feedbackCorrect = "Keren! Mandi dan ganti baju membuat tubuh segar, sehat, dan siap beribadah.",
            feedbackWrong = "Yang benar adalah mandi dengan sabun dan mengganti pakaian bersih.",
            explanation = "Kebersihan tubuh dan pakaian mencegah penyakit kulit dan membuat ibadah lebih nyaman.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P3-02",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 3,
            materialTopic = "Kapan Mandi Wajib",
            indicator = "Mengetahui waktu diwajibkannya mandi wajib",
            question = "Seseorang yang beragama Islam wajib melaksanakan mandi wajib apabila...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Selesai haid atau mengalami mimpi basah",
                "Setelah makan siang",
                "Sebelum belajar matematika",
                "Setelah berwudu"
            ),
            correctAnswer = "Selesai haid atau mengalami mimpi basah",
            maxScore = 20,
            feedbackCorrect = "Tepat! Mandi wajib dilakukan untuk menyucikan diri dari hadas besar.",
            feedbackWrong = "Mandi wajib dilakukan setelah selesai haid atau mengalami mimpi basah.",
            explanation = "Selesai haid dan mimpi basah termasuk hadas besar yang hanya suci dengan mandi wajib.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "FORM-P3-03",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 3,
            materialTopic = "Urutan Mandi Wajib",
            indicator = "Mengurutkan langkah awal mandi wajib",
            question = "Langkah paling pertama yang dilakukan saat memulai mandi wajib adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Membaca niat mandi wajib dalam hati karena Allah",
                "Langsung mengeringkan badan dengan handuk",
                "Berlari mengelilingi kamar",
                "Memakai sepatu"
            ),
            correctAnswer = "Membaca niat mandi wajib dalam hati karena Allah",
            maxScore = 20,
            feedbackCorrect = "Benar! Segala ibadah dimulai dengan niat karena Allah Swt.",
            feedbackWrong = "Langkah pertama mandi wajib adalah membaca niat di dalam hati.",
            explanation = "Niat membedakan mandi biasa dengan mandi wajib untuk bersuci dari hadas besar.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P3-04",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 3,
            materialTopic = "Ibadah Saat Haid",
            indicator = "Memahami ketentuan ibadah ringan saat haid dengan santun",
            question = "Benar atau Salah: Ketika sedang haid, anak perempuan tetap boleh berdoa dan berzikir sambil menjaga kebersihan diri.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 20,
            feedbackCorrect = "Betul sekali! Haid tidak menghalangi kita berzikir dan berdoa kepada Allah.",
            feedbackWrong = "Jawabannya Benar! Meski sedang tidak salat, doa dan zikir tetap mendatangkan pahala.",
            explanation = "Selama haid, perempuan tidak salat dan puasa, namun tetap dekat dengan Allah melalui doa dan zikir.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "FORM-P3-05",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 3,
            materialTopic = "Sumber Informasi Bersuci",
            indicator = "Memilih sumber fikih yang kredibel",
            question = "Agar tata cara bersuci kita sesuai tuntunan, sebaiknya kita mengikuti panduan dari...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Guru PAI, orang tua, dan buku fikih resmi sekolah",
                "Rumor yang belum jelas kebenarannya",
                "Tebakan teman sebaya",
                "Iklan di internet"
            ),
            correctAnswer = "Guru PAI, orang tua, dan buku fikih resmi sekolah",
            maxScore = 20,
            feedbackCorrect = "Tepat! Ilmu agama harus diambil dari sumber yang tepercaya.",
            feedbackWrong = "Selalu rujuk kepada guru PAI, orang tua, atau buku pelajaran resmi.",
            explanation = "Belajar dari guru dan sumber sahih memastikan ibadah kita sah dan menenteramkan hati.",
            difficulty = "Mudah"
        ),

        // --- POS 4 (5 soal) ---
        QuestionItem(
            id = "FORM-P4-01",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 4,
            materialTopic = "Batas Aurat",
            indicator = "Mengetahui batas aurat laki-laki dan perempuan",
            question = "Dalam Islam, batas aurat anak laki-laki yang wajib ditutup dengan sopan adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Antara pusar sampai lutut", "Hanya rambut", "Telapak tangan saja", "Dari bahu sampai siku"),
            correctAnswer = "Antara pusar sampai lutut",
            maxScore = 20,
            feedbackCorrect = "Benar! Aurat laki-laki adalah antara pusar hingga lutut.",
            feedbackWrong = "Batas aurat laki-laki dalam fikih adalah antara pusar sampai lutut.",
            explanation = "Baik laki-laki maupun perempuan diajarkan berpakaian rapi menutup aurat.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "FORM-P4-02",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 4,
            materialTopic = "Menghormati Batasan Teman",
            indicator = "Tidak memaksa teman membicarakan hal pribadi",
            question = "Benar atau Salah: Memaksa teman menceritakan pengalaman pribadinya tentang perubahan tubuh termasuk sikap yang melanggar privasi.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 20,
            feedbackCorrect = "Tepat! Setiap orang punya hak atas privasinya.",
            feedbackWrong = "Pernyataan itu Benar. Kita wajib menghormati batasan dan privasi teman.",
            explanation = "Islam melarang membuka aib atau memaksa orang lain menceritakan hal yang bersifat pribadi.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P4-03",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 4,
            materialTopic = "Perlindungan Diri",
            indicator = "Mengetahui hak meminta bantuan saat tidak aman",
            question = "Situasi: Jika ada seseorang yang membuat kamu merasa tidak nyaman atau takut dengan sentuhan yang tidak pantas, apa yang harus dilakukan?",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Berkata 'Tidak!' dengan berani, segera menjauh, dan melapor ke orang tua atau guru",
                "Diam saja dan tidak bilang siapa-siapa",
                "Menuruti keinginannya karena malu"
            ),
            correctAnswer = "Berkata 'Tidak!' dengan berani, segera menjauh, dan melapor ke orang tua atau guru",
            maxScore = 20,
            feedbackCorrect = "Hebat dan berani! Kamu berhak selalu merasa aman.",
            feedbackWrong = "Jangan takut! Katakan Tidak, menjauh, dan segera cerita ke orang tua atau guru.",
            explanation = "Orang dewasa tepercaya selalu siap melindungi anak dari perlakuan yang tidak aman.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P4-04",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 4,
            materialTopic = "Privasi Digital",
            indicator = "Menggunakan internet secara bijak",
            question = "Manakah informasi yang TIDAK boleh diunggah secara terbuka di media sosial atau gim daring?",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Alamat rumah, nomor HP, dan kata sandi akun",
                "Gambar pemandangan gunung",
                "Quotes semangat belajar",
                "Foto buku pelajaran"
            ),
            correctAnswer = "Alamat rumah, nomor HP, dan kata sandi akun",
            maxScore = 20,
            feedbackCorrect = "Cerdas! Menjaga data pribadi membuatmu aman di dunia digital.",
            feedbackWrong = "Alamat rumah, nomor HP, dan kata sandi wajib dirahasiakan.",
            explanation = "Literasi digital dimulai dari melindungi identitas pribadi kita dan keluarga.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P4-05",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 4,
            materialTopic = "Menjaga Kehormatan Teman",
            indicator = "Tidak menyebarkan foto teman tanpa izin",
            question = "Sebelum membagikan foto bersama teman kelas ke internet, hal sopan yang harus dilakukan adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Meminta izin teman serta orang tua terlebih dahulu",
                "Langsung unggah tanpa bertanya",
                "Mengubah foto menjadi bahan ejekan",
                "Menambahkan alamat rumah teman"
            ),
            correctAnswer = "Meminta izin teman serta orang tua terlebih dahulu",
            maxScore = 20,
            feedbackCorrect = "Masya Allah! Kamu sangat menghargai privasi teman.",
            feedbackWrong = "Kita harus meminta izin terlebih dahulu dan menjaga kehormatan teman.",
            explanation = "Tidak menyebarkan foto teman sembarangan menjaga keamanan dan persahabatan.",
            difficulty = "Mudah"
        ),

        // --- POS 5 (5 soal) ---
        QuestionItem(
            id = "FORM-P5-01",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 5,
            materialTopic = "Tanggung Jawab Salat",
            indicator = "Menunjukkan kesadaran melaksanakan salat",
            question = "Contoh sikap muslim yang mulai bertanggung jawab saat mendengar suara azan adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Menghentikan bermain sejenak lalu bersiap berwudu dan salat",
                "Menunggu sampai waktu salat hampir habis",
                "Salat hanya jika diberi hadiah",
                "Pura-pura tidak mendengar"
            ),
            correctAnswer = "Menghentikan bermain sejenak lalu bersiap berwudu dan salat",
            maxScore = 20,
            feedbackCorrect = "Alhamdulillah! Segera salat saat azan menunjukkan ketaatan yang matang.",
            feedbackWrong = "Sikap bertanggung jawab adalah bergegas wudu dan salat saat azan tiba.",
            explanation = "Kedewasaan iman terlihat dari kemauan sendiri melaksanakan salat tanpa paksaan.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P5-02",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 5,
            materialTopic = "Menghormati Orang Tua & Guru",
            indicator = "Menunjukkan bakti kepada orang tua dan guru",
            question = "Benar atau Salah: Bertanya dengan bahasa yang sopan kepada orang tua atau guru PAI saat kesulitan belajar adalah perilaku terpuji.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 20,
            feedbackCorrect = "Tepat! Bertanya dengan sopan adalah kunci bertambahnya ilmu.",
            feedbackWrong = "Pernyataan tersebut Benar. Malu bertanya sesat di jalan; bertanya sopan tanda anak cerdas.",
            explanation = "Menghormati guru dan orang tua diwujudkan dengan tutur kata santun dan mendengar penjelasan.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P5-03",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 5,
            materialTopic = "Percaya Diri & Positif",
            indicator = "Memiliki sikap positif terhadap pertumbuhan diri",
            question = "Bagaimana perasaan yang sebaiknya kita miliki saat menyadari bahwa tubuh dan pemikiran kita sedang bertumbuh?",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Bersyukur, percaya diri, dan terus belajar hal baik",
                "Takut dan enggan bertemu teman",
                "Sombong merasa paling hebat",
                "Malas merawat kebersihan"
            ),
            correctAnswer = "Bersyukur, percaya diri, dan terus belajar hal baik",
            maxScore = 20,
            feedbackCorrect = "Luar biasa! Rasa syukur dan percaya diri membuat hari-harimu penuh berkah.",
            feedbackWrong = "Sikap terbaik adalah bersyukur, percaya diri, dan bersemangat belajar.",
            explanation = "Pertumbuhan adalah karunia Allah Swt. yang disyukuri dengan berakhlak baik.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P5-04",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 5,
            materialTopic = "Menghargai Teman",
            indicator = "Membangun persahabatan tanpa mengejek",
            question = "Situasi: Ada teman baru yang pemalu di kelas karena merasa belum terbiasa dengan lingkungan baru. Sikap Bimo yang paling baik adalah...",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Menyapa dengan ramah dan mengajaknya belajar kelompok bersama",
                "Membiarkannya sendirian",
                "Menjadikannya bahan candaan"
            ),
            correctAnswer = "Menyapa dengan ramah dan mengajaknya belajar kelompok bersama",
            maxScore = 20,
            feedbackCorrect = "Akhlak yang sangat mulia! Kamu sahabat terbaik.",
            feedbackWrong = "Menyapa ramah dan mengajaknya bergabung mencerminkan kasih sayang sesama muslim.",
            explanation = "Muslim yang bertanggung jawab menjaga perasaan teman dan menciptakan kelas yang aman.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "FORM-P5-05",
            assessmentType = AssessmentType.FORMATIF,
            posNumber = 5,
            materialTopic = "Akhlak Mulia & Doa",
            indicator = "Membiasakan berdoa dalam proses bertumbuh",
            question = "Kebiasaan rohani yang membantu hati kita tetap tenang, kuat, dan terlindungi selama masa pertumbuhan adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Berdoa kepada Allah Swt. dan membaca Al-Qur'an",
                "Menghabiskan waktu dengan marah-marah",
                "Mengabaikan nasihat orang tua",
                "Meniru perbuatan buruk"
            ),
            correctAnswer = "Berdoa kepada Allah Swt. dan membaca Al-Qur'an",
            maxScore = 20,
            feedbackCorrect = "Masya Allah, tepat sekali! Doa adalah kekuatan terbaik seorang mukmin.",
            feedbackWrong = "Jawabannya adalah berdoa kepada Allah Swt. dan membaca Al-Qur'an.",
            explanation = "Berdoa menghubungkan hati kita dengan Allah agar selalu diberi petunjuk dan perlindungan.",
            difficulty = "Mudah"
        )
    )

    // =========================================================================
    // 3. ASESMEN SUMATIF (20 SOAL = 15 PG + 3 BENAR/SALAH + 2 SITUASI/SINGKAT)
    // Skor Maksimum = 20 x 5 = 100
    // =========================================================================
    val summativeQuestions: List<QuestionItem> = listOf(
        // 15 Pilihan Ganda
        QuestionItem(
            id = "SUM-01",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 1,
            materialTopic = "Pengertian Baligh",
            indicator = "Menjelaskan arti kata baligh",
            question = "Arti kata 'baligh' menurut bahasa Arab adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Sampai", "Permulaan", "Kecil", "Istirahat"),
            correctAnswer = "Sampai",
            maxScore = 5,
            feedbackCorrect = "Tepat! Baligh berarti 'sampai'.",
            feedbackWrong = "Baligh berarti 'sampai' pada tahap kedewasaan awal.",
            explanation = "Baligh berasal dari kata balagha yang berarti sampai.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-02",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 1,
            materialTopic = "Mukalaf",
            indicator = "Mengidentifikasi istilah mukalaf",
            question = "Orang Islam yang berakal dan sudah baligh sehingga mulai wajib melaksanakan perintah agama disebut...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Mukalaf", "Muadzin", "Makmum", "Munafik"),
            correctAnswer = "Mukalaf",
            maxScore = 5,
            feedbackCorrect = "Benar! Mukalaf memikul kewajiban ibadah.",
            feedbackWrong = "Istilah yang tepat adalah Mukalaf.",
            explanation = "Seorang mukalaf bertanggung jawab atas ibadah dan perbuatannya di hadapan Allah Swt.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-03",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 1,
            materialTopic = "Kewajiban Baligh",
            indicator = "Menyebutkan kewajiban yang berlaku sejak baligh",
            question = "Berikut ini adalah kewajiban yang mulai berlaku penuh ketika seseorang memasuki usia baligh, KECUALI...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Membayar biaya sekolah sendiri",
                "Melaksanakan salat lima waktu",
                "Berpuasa di bulan Ramadan",
                "Menutup aurat dengan sopan"
            ),
            correctAnswer = "Membayar biaya sekolah sendiri",
            maxScore = 5,
            feedbackCorrect = "Cermat sekali! Anak kelas 4 belum wajib mencari nafkah atau membayar biaya sekolah sendiri.",
            feedbackWrong = "Yang bukan kewajiban anak baligh adalah membayar biaya sekolah sendiri.",
            explanation = "Kewajiban baligh berkaitan dengan ibadah (salat, puasa), menutup aurat, dan akhlak.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-04",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 2,
            materialTopic = "Tanda Baligh Laki-laki",
            indicator = "Mengenal tanda baligh anak laki-laki",
            question = "Tanda baligh menurut fikih yang dialami anak laki-laki berupa keluarnya cairan alami saat tidur disebut...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Mimpi basah", "Berkeringat", "Mengigau", "Hidung tersumbat"),
            correctAnswer = "Mimpi basah",
            maxScore = 5,
            feedbackCorrect = "Benar! Mimpi basah atau ihtilam menandai kedewasaan awal.",
            feedbackWrong = "Jawabannya adalah mimpi basah (ihtilam).",
            explanation = "Mimpi basah adalah proses biologis alami yang menandakan laki-laki telah baligh.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-05",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 2,
            materialTopic = "Tanda Baligh Perempuan",
            indicator = "Mengenal pengertian haid secara santun",
            question = "Pengertian haid yang benar dan sesuai ilmu kesehatan serta fikih adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Keluarnya darah alami secara berkala pada perempuan yang menandakan tubuh sehat",
                "Penyakit menular yang berbahaya",
                "Luka akibat terjatuh saat bermain",
                "Hal yang harus ditakuti oleh anak perempuan"
            ),
            correctAnswer = "Keluarnya darah alami secara berkala pada perempuan yang menandakan tubuh sehat",
            maxScore = 5,
            feedbackCorrect = "Tepat! Haid adalah tanda kesehatan dan karunia Allah Swt.",
            feedbackWrong = "Haid adalah keluarnya darah alami yang menandakan tubuh sehat, bukan penyakit.",
            explanation = "Memahami haid secara ilmiah dan santun menghilangkan rasa takut dan salah paham.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-06",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 2,
            materialTopic = "Batas Usia Fikih",
            indicator = "Menentukan batas usia baligh menurut fikih",
            question = "Jika seorang anak belum mengalami mimpi basah atau haid, maka menurut fikih ia dianggap telah baligh pada usia...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("15 tahun hijriah", "10 tahun hijriah", "20 tahun hijriah", "8 tahun hijriah"),
            correctAnswer = "15 tahun hijriah",
            maxScore = 5,
            feedbackCorrect = "Benar! Usia 15 tahun hijriah merupakan ketetapan fikih.",
            feedbackWrong = "Usia batas akhir menurut fikih adalah 15 tahun hijriah.",
            explanation = "Apabila tanda biologis muncul lebih cepat (usia 9–14 tahun), maka sejak itulah ia sudah baligh.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-07",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 2,
            materialTopic = "Keberagaman Perkembangan",
            indicator = "Menghargai perbedaan waktu perkembangan teman",
            question = "Mengapa kita tidak boleh membandingkan perkembangan tubuh kita dengan teman sekelas?",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Karena waktu perkembangan setiap anak berbeda dan semua tetap normal",
                "Karena teman sekelas bukan saudara",
                "Karena pertumbuhan hanya terjadi saat liburan",
                "Karena tinggi badan tidak bisa berubah"
            ),
            correctAnswer = "Karena waktu perkembangan setiap anak berbeda dan semua tetap normal",
            maxScore = 5,
            feedbackCorrect = "Hebat! Sikap saling menghargai membuat semua anak nyaman bertumbuh.",
            feedbackWrong = "Alasannya karena waktu perkembangan setiap anak berbeda-beda dan semuanya normal.",
            explanation = "Setiap anak tumbuh sesuai ketetapan Allah Swt. sehingga tidak pantas dibandingkan.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-08",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 3,
            materialTopic = "Kebersihan Diri",
            indicator = "Menjaga kebersihan diri dan pakaian",
            question = "Salah satu cara menjaga kebersihan diri yang penting dilakukan setiap hari menjelang remaja adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Mandi teratur memakai sabun dan mengganti pakaian bersih",
                "Memakai baju yang sama selama seminggu",
                "Menghindari mencuci tangan",
                "Menumpuk sampah di bawah meja"
            ),
            correctAnswer = "Mandi teratur memakai sabun dan mengganti pakaian bersih",
            maxScore = 5,
            feedbackCorrect = "Benar! Mandi dan berganti baju bersih menjaga kesehatan tubuh.",
            feedbackWrong = "Caranya adalah mandi teratur memakai sabun dan mengganti pakaian bersih.",
            explanation = "Kebersihan diri mendukung kekhusyukan ibadah dan kebugaran tubuh.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-09",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 3,
            materialTopic = "Hadas Besar & Mandi Wajib",
            indicator = "Menentukan cara bersuci dari hadas besar",
            question = "Setelah mengalami mimpi basah atau selesai masa haid, cara bersuci agar dapat salat kembali adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Melakukan mandi wajib", "Cukup mencuci muka", "Hanya menyisir rambut", "Tepuk tangan tiga kali"),
            correctAnswer = "Melakukan mandi wajib",
            maxScore = 5,
            feedbackCorrect = "Tepat! Hadas besar disucikan dengan mandi wajib.",
            feedbackWrong = "Hadas besar harus disucikan dengan mandi wajib menggunakan air bersih.",
            explanation = "Mandi wajib meratakan air suci ke seluruh tubuh disertai niat karena Allah Swt.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-10",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 3,
            materialTopic = "Tata Cara Mandi Wajib",
            indicator = "Mengetahui tata cara mandi wajib secara berurutan",
            question = "Saat melakukan mandi wajib, air harus disiramkan hingga merata ke...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Kulit kepala dan seluruh anggota tubuh dari ujung rambut hingga ujung kaki",
                "Kedua tangan dan wajah saja",
                "Kaki bagian bawah saja",
                "Punggung saja"
            ),
            correctAnswer = "Kulit kepala dan seluruh anggota tubuh dari ujung rambut hingga ujung kaki",
            maxScore = 5,
            feedbackCorrect = "Tepat sekali! Seluruh tubuh dan kulit kepala harus terkena air suci secara merata.",
            feedbackWrong = "Air harus merata ke kulit kepala dan seluruh anggota tubuh.",
            explanation = "Syarat sah mandi wajib adalah niat dan meratakan air ke seluruh badan.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-11",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 4,
            materialTopic = "Menjaga Aurat",
            indicator = "Mengetahui batas aurat perempuan",
            question = "Aurat anak perempuan muslimah ketika berada di luar rumah atau di hadapan bukan mahram adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Seluruh tubuh kecuali wajah dan kedua telapak tangan",
                "Dari lutut sampai kaki saja",
                "Bagian wajah dan tangan saja",
                "Bagian leher saja"
            ),
            correctAnswer = "Seluruh tubuh kecuali wajah dan kedua telapak tangan",
            maxScore = 5,
            feedbackCorrect = "Benar! Menutup aurat menjaga kehormatan perempuan muslimah.",
            feedbackWrong = "Aurat perempuan adalah seluruh tubuh kecuali wajah dan kedua telapak tangan.",
            explanation = "Batasan aurat diajarkan dalam Islam sebagai bentuk penjagaan kehormatan.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-12",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 4,
            materialTopic = "Menghormati Privasi",
            indicator = "Menjaga batasan diri dan orang lain",
            question = "Apabila ada teman yang belum mau bercerita tentang hal pribadi dirinya, sikap yang tepat adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Menghormati keputusannya dan tidak memaksa",
                "Terus mendesak sampai ia menangis",
                "Menyebarkan gosip di grup kelas",
                "Menertawakannya bersama teman"
            ),
            correctAnswer = "Menghormati keputusannya dan tidak memaksa",
            maxScore = 5,
            feedbackCorrect = "Sangat santun! Menghormati privasi teman adalah akhlak terpuji.",
            feedbackWrong = "Kita harus menghormati keputusannya dan tidak memaksa.",
            explanation = "Setiap anak berhak menjaga privasi tubuh dan pengalamannya.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-13",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 4,
            materialTopic = "Privasi Digital",
            indicator = "Menggunakan internet secara aman",
            question = "Tindakan berikut ini yang mencerminkan kecakapan menjaga privasi digital adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Tidak membagikan foto teman tanpa izin dan merahasiakan kata sandi",
                "Memasang alamat rumah lengkap di profil media sosial",
                "Menerima ajakan bertemu orang asing dari gim daring",
                "Menuliskan nomor HP orang tua di kolom komentar publik"
            ),
            correctAnswer = "Tidak membagikan foto teman tanpa izin dan merahasiakan kata sandi",
            maxScore = 5,
            feedbackCorrect = "Hebat! Kamu anak muslim yang cerdas dan aman di dunia digital.",
            feedbackWrong = "Yang tepat adalah tidak membagikan foto teman tanpa izin dan merahasiakan kata sandi.",
            explanation = "Privasi digital melindungi diri sendiri dan teman-teman dari risiko kejahatan siber.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-14",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 5,
            materialTopic = "Tanggung Jawab Ibadah",
            indicator = "Menunjukkan kesadaran beribadah",
            question = "Perilaku berikut yang paling tepat menggambarkan anak yang siap bertanggung jawab di usia baligh adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Melaksanakan salat 5 waktu atas kesadaran sendiri dan berkata jujur",
                "Hanya rajin belajar kalau diawasi guru",
                "Sering menunda waktu salat hingga habis",
                "Membantah nasihat ayah dan ibu"
            ),
            correctAnswer = "Melaksanakan salat 5 waktu atas kesadaran sendiri dan berkata jujur",
            maxScore = 5,
            feedbackCorrect = "Masya Allah, tepat sekali!",
            feedbackWrong = "Jawabannya adalah salat 5 waktu atas kesadaran sendiri dan berkata jujur.",
            explanation = "Kesadaran beribadah dan kejujuran merupakan inti tanggung jawab muslim.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "SUM-15",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 5,
            materialTopic = "Bertanya dengan Sopan",
            indicator = "Mengetahui orang dewasa tepercaya untuk bertanya",
            question = "Ketika menemui keraguan dalam tata cara ibadah saat memasuki usia baligh, langkah paling tepat adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Bertanya dengan sopan kepada orang tua atau guru PAI",
                "Berhenti melaksanakan ibadah selamanya",
                "Mengikuti informasi acak tanpa mengecek kebenarannya",
                "Menertawakan masalah tersebut"
            ),
            correctAnswer = "Bertanya dengan sopan kepada orang tua atau guru PAI",
            maxScore = 5,
            feedbackCorrect = "Tepat! Bertanya kepada guru PAI atau orang tua menjamin keilmuan kita benar.",
            feedbackWrong = "Bertanyalah dengan sopan kepada orang tua atau guru PAI.",
            explanation = "Menuntut ilmu agama kepada ahlinya menjaga ibadah tetap sah.",
            difficulty = "Mudah"
        ),

        // 3 Benar atau Salah
        QuestionItem(
            id = "SUM-16",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 2,
            materialTopic = "Mitos vs Fakta Baligh",
            indicator = "Membedakan fakta benar tentang pertumbuhan",
            question = "Benar atau Salah: Anak perempuan yang sedang haid tetap boleh berdoa dan berzikir kepada Allah Swt.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 5,
            feedbackCorrect = "Benar! Berdoa dan berzikir tetap sangat dianjurkan.",
            feedbackWrong = "Pernyataan itu Benar. Doa dan zikir selalu terbuka bagi siapa pun.",
            explanation = "Selama haid, larangan hanya pada salat, puasa, dan tawaf; zikir serta doa tetap berpahala.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-17",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 3,
            materialTopic = "Tata Cara Mandi Wajib",
            indicator = "Memahami pentingnya niat dalam mandi wajib",
            question = "Benar atau Salah: Mandi wajib menjadi sah walaupun seseorang lupa berniat menyucikan hadas besar.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Salah",
            maxScore = 5,
            feedbackCorrect = "Tepat! Niat adalah rukun penting agar mandi menjadi mandi wajib yang sah.",
            feedbackWrong = "Pernyataan itu Salah, karena niat merupakan rukun wajib dalam mandi wajib.",
            explanation = "Niat di dalam hati membedakan ibadah bersuci dengan mandi biasa.",
            difficulty = "Sulit"
        ),
        QuestionItem(
            id = "SUM-18",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 4,
            materialTopic = "Menghargai Teman",
            indicator = "Menolak perundungan terkait perubahan fisik",
            question = "Benar atau Salah: Mengejek teman yang memiliki jerawat atau suara berubah adalah perbuatan yang dilarang agama.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 5,
            feedbackCorrect = "Benar sekali! Mengolok-olok teman dilarang oleh Allah Swt.",
            feedbackWrong = "Pernyataan itu Benar. Islam melarang keras mengolok-olok atau merundung sesama.",
            explanation = "Perubahan fisik adalah ciptaan Allah; saling menghormati menjaga kehormatan bersama.",
            difficulty = "Mudah"
        ),

        // 2 Soal Situasi / Jawaban Singkat
        QuestionItem(
            id = "SUM-19",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 4,
            materialTopic = "Keputusan Aman",
            indicator = "Mengambil tindakan aman saat melihat pelanggaran privasi",
            question = "Situasi: Kamu menerima pesan dari orang tak dikenal yang meminta foto dirimu dan alamat rumahmu dengan iming-iming hadiah koin gim. Apa langkah paling aman yang kamu pilih?",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Menolak permintaan itu, tidak membalas, memblokir akun, dan melapor ke orang tua",
                "Mengirimkan alamat sekolah saja agar aman",
                "Memberikan foto tapi tanpa nama"
            ),
            correctAnswer = "Menolak permintaan itu, tidak membalas, memblokir akun, dan melapor ke orang tua",
            maxScore = 5,
            feedbackCorrect = "Brilian! Kamu sangat cerdas menjaga keselamatan dan privasi digital.",
            feedbackWrong = "Langkah aman adalah menolak, memblokir akun asing, dan segera melapor kepada orang tua.",
            explanation = "Iming-iming hadiah dari orang asing di internet sering menjadi jebakan; segera minta bantuan orang tua.",
            difficulty = "Sedang"
        ),
        QuestionItem(
            id = "SUM-20",
            assessmentType = AssessmentType.SUMATIF,
            posNumber = 5,
            materialTopic = "Inti Tanggung Jawab Baligh",
            indicator = "Menuliskan kata kunci tanggung jawab muslim baligh",
            question = "Jawaban Singkat: Tuliskan satu kata bahasa Arab yang berarti 'muslim yang berakal dan sudah baligh sehingga wajib menjalankan syariat agama' (petunjuk: berawalan huruf M).",
            questionType = QuestionType.JAWABAN_SINGKAT,
            options = emptyList(),
            correctAnswer = "Mukalaf",
            maxScore = 5,
            feedbackCorrect = "Masya Allah! Jawabanmu 'Mukalaf' tepat sekali!",
            feedbackWrong = "Kata yang dimaksud adalah 'Mukalaf'.",
            explanation = "Mukalaf adalah orang Islam yang telah baligh dan berakal sehingga berkewajiban menjalankan ibadah dengan penuh tanggung jawab.",
            difficulty = "Sulit"
        )
    )

    // =========================================================================
    // 4. LATIHAN REMEDIAL (5 SOAL) - Ramah & Menguatkan
    // =========================================================================
    val remedialQuestions: List<QuestionItem> = listOf(
        QuestionItem(
            id = "REM-01",
            assessmentType = AssessmentType.REMEDIAL,
            posNumber = 1,
            materialTopic = "Arti Baligh",
            indicator = "Menguatkan pemahaman arti baligh",
            question = "Kata 'baligh' artinya sampai pada tahap...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Kedewasaan awal", "Masa bayi", "Bermain bebas"),
            correctAnswer = "Kedewasaan awal",
            maxScore = 20,
            feedbackCorrect = "Bagus sekali! Kamu makin paham artinya.",
            feedbackWrong = "Yuk ingat lagi: Baligh artinya sampai pada tahap kedewasaan awal.",
            explanation = "Pada tahap ini, seorang muslim siap melatih tanggung jawab ibadahnya.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "REM-02",
            assessmentType = AssessmentType.REMEDIAL,
            posNumber = 2,
            materialTopic = "Tanda Baligh Fikih",
            indicator = "Mengingat 3 tanda baligh",
            question = "Jika belum haid atau mimpi basah, usia baligh menurut fikih adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("15 tahun hijriah", "5 tahun hijriah", "30 tahun hijriah"),
            correctAnswer = "15 tahun hijriah",
            maxScore = 20,
            feedbackCorrect = "Tepat! Angka yang benar adalah 15 tahun hijriah.",
            feedbackWrong = "Ingat angka 15 ya: 15 tahun hijriah.",
            explanation = "Tiga tanda baligh: mimpi basah, haid, atau usia 15 tahun hijriah.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "REM-03",
            assessmentType = AssessmentType.REMEDIAL,
            posNumber = 3,
            materialTopic = "Mandi Wajib",
            indicator = "Mengingat fungsi mandi wajib",
            question = "Cara bersuci dari hadas besar seperti setelah haid atau mimpi basah adalah dengan...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Mandi wajib", "Mencuci sepatu", "Tidur siang"),
            correctAnswer = "Mandi wajib",
            maxScore = 20,
            feedbackCorrect = "Betul! Mandi wajib menyucikan tubuh secara menyeluruh.",
            feedbackWrong = "Jawabannya adalah mandi wajib.",
            explanation = "Mandi wajib dimulai dengan niat lalu meratakan air bersih ke seluruh tubuh.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "REM-04",
            assessmentType = AssessmentType.REMEDIAL,
            posNumber = 4,
            materialTopic = "Menjaga Privasi",
            indicator = "Menguatkan perilaku aman",
            question = "Kepada siapa kita boleh bertanya jika bingung tentang perubahan saat memasuki usia baligh?",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf("Orang tua atau guru PAI", "Orang asing di internet", "Tidak perlu bertanya selamanya"),
            correctAnswer = "Orang tua atau guru PAI",
            maxScore = 20,
            feedbackCorrect = "Keren! Orang tua dan guru PAI adalah sumber aman.",
            feedbackWrong = "Sebaiknya bertanya kepada orang tua atau guru PAI.",
            explanation = "Bertanya kepada orang dewasa tepercaya membuat hati tenang.",
            difficulty = "Mudah"
        ),
        QuestionItem(
            id = "REM-05",
            assessmentType = AssessmentType.REMEDIAL,
            posNumber = 5,
            materialTopic = "Tanggung Jawab",
            indicator = "Menghormati teman",
            question = "Benar atau Salah: Menghargai teman tanpa membandingkan bentuk tubuhnya adalah akhlak mulia.",
            questionType = QuestionType.BENAR_SALAH,
            options = listOf("Benar", "Salah"),
            correctAnswer = "Benar",
            maxScore = 20,
            feedbackCorrect = "Masya Allah, jawabanmu benar!",
            feedbackWrong = "Pernyataan itu Benar. Saling menghargai membuat semua teman nyaman.",
            explanation = "Setiap anak tumbuh secara unik atas kehendak Allah Swt.",
            difficulty = "Mudah"
        )
    )

    // =========================================================================
    // 5. KUIS LANJUTAN PENGAYAAN (5 SOAL)
    // =========================================================================
    val enrichmentQuestions: List<QuestionItem> = listOf(
        QuestionItem(
            id = "PENG-01",
            assessmentType = AssessmentType.PENGAYAAN,
            posNumber = 1,
            materialTopic = "Analisis Mukalaf",
            indicator = "Menganalisis tanggung jawab mukalaf",
            question = "Mengapa Allah Swt. baru mewajibkan salat dan puasa secara penuh setelah seseorang mencapai akil baligh?",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Karena pada saat itulah akal dan fisik seseorang siap memikul amanah dengan penuh kesadaran",
                "Karena anak kecil dilarang masuk masjid",
                "Karena ibadah hanya boleh dilakukan orang lanjut usia"
            ),
            correctAnswer = "Karena pada saat itulah akal dan fisik seseorang siap memikul amanah dengan penuh kesadaran",
            maxScore = 20,
            feedbackCorrect = "Analisis yang dalam dan tepat! Islam agama yang penuh kasih sayang.",
            feedbackWrong = "Islam menetapkan syariat sesuai kesiapan akal dan fisik manusia.",
            explanation = "Allah Maha Adil; kewajiban diberikan sejalan dengan kesiapan diri hamba-Nya.",
            difficulty = "Sulit"
        ),
        QuestionItem(
            id = "PENG-02",
            assessmentType = AssessmentType.PENGAYAAN,
            posNumber = 3,
            materialTopic = "Rukun Mandi Wajib",
            indicator = "Membedakan rukun dan sunah mandi wajib",
            question = "Dalam fikih, dua rukun utama mandi wajib yang tidak boleh ditinggalkan agar mandi sah adalah...",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "Niat dan meratakan air suci ke seluruh tubuh",
                "Memakai sabun wangi dan handuk baru",
                "Mandi di sore hari dan menyisir rambut"
            ),
            correctAnswer = "Niat dan meratakan air suci ke seluruh tubuh",
            maxScore = 20,
            feedbackCorrect = "Luar biasa! Pemahaman fikihmu sangat kuat.",
            feedbackWrong = "Rukun wajibnya adalah niat dan meratakan air suci ke seluruh tubuh.",
            explanation = "Wudu sebelum mandi, mencuci tangan, dan mendahulukan kanan adalah sunah yang menyempurnakan mandi wajib.",
            difficulty = "Sulit"
        ),
        QuestionItem(
            id = "PENG-03",
            assessmentType = AssessmentType.PENGAYAAN,
            posNumber = 4,
            materialTopic = "Studi Kasus Privasi Kelas",
            indicator = "Memecahkan masalah perundungan di kelas",
            question = "Jika di grup chat kelas ada teman yang mengirim stiker mengejek penampilan fisik teman lain, tindakan pemimpin kecil yang paling bijak adalah...",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Menegur dengan santun agar berhenti mengejek dan mengingatkan untuk menghormati teman, atau melapor ke wali kelas jika berlanjut",
                "Ikut tertawa agar dianggap ramah",
                "Menyimpan gambar itu untuk disebar lagi"
            ),
            correctAnswer = "Menegur dengan santun agar berhenti mengejek dan mengingatkan untuk menghormati teman, atau melapor ke wali kelas jika berlanjut",
            maxScore = 20,
            feedbackCorrect = "Keren! Kamu menunjukkan kepemimpinan yang santun dan melindungi teman.",
            feedbackWrong = "Pemimpin yang baik membela kebaikan secara santun atau melapor ke wali kelas.",
            explanation = "Menjaga adab di grup digital menjaga persaudaraan dan keamanan semua murid.",
            difficulty = "Sulit"
        ),
        QuestionItem(
            id = "PENG-04",
            assessmentType = AssessmentType.PENGAYAAN,
            posNumber = 2,
            materialTopic = "Literasi Informasi Kesehatan & Fikih",
            indicator = "Memverifikasi kebenaran informasi",
            question = "Kamu membaca artikel internet yang mengatakan 'orang yang sedang haid tidak boleh mencuci rambut selama seminggu'. Sikap kritis seorang pelajar PAI adalah...",
            questionType = QuestionType.SITUASI,
            options = listOf(
                "Mengecek kebenarannya ke guru PAI atau orang tua, karena menjaga kebersihan rambut tetap dianjurkan dalam Islam",
                "Langsung percaya tanpa bertanya",
                "Menyebarkan pesan berantai tersebut ke semua teman"
            ),
            correctAnswer = "Mengecek kebenarannya ke guru PAI atau orang tua, karena menjaga kebersihan rambut tetap dianjurkan dalam Islam",
            maxScore = 20,
            feedbackCorrect = "Tepat sekali! Itu adalah mitos; keramas dan menjaga kebersihan justru dianjurkan.",
            feedbackWrong = "Selalu tabayyun (cek kebenaran) ke guru atau orang tua. Menjaga kebersihan rambut tetap baik.",
            explanation = "Tabayyun (memeriksa kebenaran informasi) melindungi kita dari mitos yang merugikan kesehatan.",
            difficulty = "Sulit"
        ),
        QuestionItem(
            id = "PENG-05",
            assessmentType = AssessmentType.PENGAYAAN,
            posNumber = 5,
            materialTopic = "Kampanye Kebaikan",
            indicator = "Merancang ajakan kebaikan yang santun",
            question = "Manakah slogan kampanye paling tepat dan ramah anak untuk dipasang di mading kelas 4?",
            questionType = QuestionType.PILIHAN_GANDA,
            options = listOf(
                "\"Tumbuh Sehat, Bersih Suci, Saling Menghargai: Aku Siap Jadi Muslim Bertanggung Jawab!\"",
                "\"Siapa Belum Tinggi Berarti Kalah!\"",
                "\"Dilarang Bertanya di Kelas!\""
            ),
            correctAnswer = "\"Tumbuh Sehat, Bersih Suci, Saling Menghargai: Aku Siap Jadi Muslim Bertanggung Jawab!\"",
            maxScore = 20,
            feedbackCorrect = "Inspiratif sekali! Slogan ini positif, inklusif, dan penuh semangat.",
            feedbackWrong = "Slogan terbaik adalah yang positif, mengajak kebersihan, dan saling menghargai.",
            explanation = "Kampanye positif membangun iklim kelas yang nyaman untuk semua murid.",
            difficulty = "Sedang"
        )
    )

    val allQuestionsForBank: List<QuestionItem> =
        diagnosticQuestions + formativeQuestions + summativeQuestions + remedialQuestions + enrichmentQuestions
}
