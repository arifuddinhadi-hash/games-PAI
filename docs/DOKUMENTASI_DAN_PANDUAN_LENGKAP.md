# DOKUMENTASI LENGKAP: PETUALANGAN BALIGH
**Aku Siap Tumbuh Menjadi Muslim yang Bertanggung Jawab**
Mata Pelajaran: Pendidikan Agama Islam dan Budi Pekerti (PAI & BP) • Kelas IV SD (Fase B)

---

## 1. Blueprint & Storyboard Aplikasi
1. **Splash Screen**: Menampilkan ilustrasi petualangan pastel, judul media, dan transisi otomatis menuju Beranda.
2. **Beranda & Registrasi Aman**: Perkenalan karakter ramah anak (Nara, Aisyah, Bimo), aturan permainan tanpa hukuman poin, input nama panggilan/kode murid (minimalisasi data pribadi), pemilihan kelas, kelompok, dan avatar, serta tombol khusus area Guru ber-PIN.
3. **Peta Misi Petualangan**: Jalur 5 Pos berurutan (atau bebas belajar bila diaktifkan guru), akses Asesmen Diagnostik (10 soal), Gerbang Akhir (Tantangan Final & Kuis Sumatif 20 soal), Galeri Lencana, Sertifikat Digital PDF, Refleksi Diri, dan Pojok Penguatan (Remedial & Pengayaan).
4. **Halaman Pos (1–5)**: Terdiri atas 3 tab terpandu: *(1) Materi & Cerita*, *(2) Mini-Game Interaktif*, *(3) Kuis Formatif (5 soal per Pos)* yang langsung memberi feedback edukatif, XP (+10 XP/benar), dan Lencana Pos.
5. **Dashboard Guru (PIN Default: `4024`)**: Ringkasan KPI & grafik penguasaan per pos, daftar murid & detail jawaban, analisis tingkat kesalahan per soal, Modul Ajar 3 Pertemuan Kurikulum Merdeka, serta Integrasi Google Sheets (`Code.gs`), Ekspor CSV, dan Cetak Laporan PDF.

## 2. Panduan Deploy Google Apps Script & Integrasi Google Sheets
1. Buka **Google Drive** -> buat **Google Sheets** baru, beri nama `DB Petualangan Baligh Kelas 4`.
2. Klik menu **Ekstensi (Extensions) > Apps Script**.
3. Salin seluruh isi file `google_apps_script/Code.gs` (atau salin langsung dari tombol *"Lihat & Salin Kode Code.gs"* di Dashboard Guru aplikasi Android).
4. Tempel ke editor Apps Script lalu klik **Simpan**.
5. Pilih fungsi `initializeDatabase` pada toolbar editor, lalu klik **Run (Jalankan)**. Berikan izin akses saat diminta. Ini otomatis membuat 8 sheet:
   - `IDENTITAS`, `BANK_SOAL`, `JAWABAN_MURID`, `HASIL_FORMATIF`, `HASIL_SUMATIF`, `REKAP_GURU` (berisi formula `COUNTA`, `AVERAGE`, `MAX`, `MIN`, `COUNTIF`, `AVERAGEIF`, `COUNTIFS`), `LOG_AKTIVITAS`, dan `PENGATURAN`.
6. Klik **Deploy > New deployment > Type: Web app**:
   - *Execute as*: **Me**
   - *Who has access*: **Anyone** (atau sesuai domain sekolah)
7. Salin **Web App URL** (`https://script.google.com/macros/s/.../exec`), buka aplikasi Android -> **Area Guru** -> tab **Sheets & Pengaturan** -> tempel URL -> klik **Tes Koneksi** lalu **Sinkronkan**.

## 3. Panduan Penggunaan & Pengujian
- **Untuk Murid**: Masukkan nama panggilan/kode -> pilih avatar -> kerjakan Cek Awal (Diagnostik) -> jelajahi Pos 1 s.d. Pos 5 -> selesaikan Tantangan Final & Kuis Akhir -> cetak Sertifikat Digital & isi Refleksi Diri.
- **Untuk Guru**: Masuk melalui tombol **Guru** (PIN awal `4024`, segera ubah di Pengaturan) -> atur **KKTP**, aktifkan/nonaktifkan **Mode Bebas Belajar**, pantau grafik kelas dan analisis kesalahan soal, unduh CSV atau cetak laporan PDF, serta sinkronkan ke Google Sheets.
- **Pengujian Fungsional**:
  1. Uji alur registrasi murid & cek data tersimpan di Room lokal.
  2. Uji kunci urutan Pos 1 -> Pos 2 dan toggle *Mode Bebas Belajar* dari Dashboard Guru.
  3. Uji skor sumatif otomatis `(Skor Perolehan / Skor Maksimum) × 100` dengan KKTP yang dapat diubah.
  4. Uji tombol *Tes Koneksi* & *Sinkronkan* ke Google Sheets dan verifikasi baris masuk di tab `IDENTITAS`, `JAWABAN_MURID`, `HASIL_FORMATIF`, `HASIL_SUMATIF`, dan `REKAP_GURU`.

## 4. Audit Perlindungan Anak & Keamanan Data
- Tidak ada gambar anatomi eksplisit atau pertanyaan pengalaman pribadi sensitif.
- Materi haid, mimpi basah, mandi wajib, dan aurat disampaikan secara ilmiah, santun, dan mendorong bertanya ke orang tua/guru tepercaya.
- Minimalisasi data pribadi: hanya nama panggilan/kode murid, kelas, dan kelompok.
- Sanitasi input formula injection di `Code.gs` dan akses Dashboard Guru terproteksi PIN.

## 5. Panduan Versi Offline (Tanpa Koneksi Internet)
Aplikasi tersedia dalam dua bentuk yang dapat dijalankan **100% offline**:
1. **Aplikasi Android Offline-First**:
   - Seluruh materi 5 Pos, mini-game, bank soal (diagnostik, formatif, sumatif, remedial, pengayaan), refleksi, sertifikat PDF, dan Dashboard Guru tersimpan penuh di perangkat melalui **Room Database** lokal.
   - Di **Area Guru > Tab Sheets & Pengaturan**, guru dapat mengaktifkan **Mode Offline Penuh** (menonaktifkan upaya sinkronisasi latar belakang ke Google Sheets), membuat **Backup Cadangan (.json)**, mengimpor ulang cadangan JSON dari perangkat lain, atau mengekspor rekap **CSV** & laporan **PDF** tanpa internet.
2. **Versi Web Offline Satu File (`petualangan_baligh_offline.html`)**:
   - Tersedia di folder proyek `offline_web/petualangan_baligh_offline.html` serta dapat langsung diekspor/dibagikan dari tombol **"Ekspor / Bagikan Versi Web Offline (.html)"** di **Area Guru** pada aplikasi Android.
   - File `.html` mandiri ini tidak membutuhkan server, CDN, maupun koneksi internet (menggunakan `localStorage` browser) dan dapat langsung dibuka di browser laptop, Chromebook, komputer lab sekolah, tablet, maupun HP.
