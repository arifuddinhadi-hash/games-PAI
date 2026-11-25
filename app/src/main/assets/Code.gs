/**
 * ============================================================================
 * GOOGLE APPS SCRIPT BACKEND - PETUALANGAN BALIGH (PAI KELAS 4 SD - FASE B)
 * ============================================================================
 * Deskripsi:
 * Script ini menghubungkan aplikasi Android "Petualangan Baligh" dengan
 * Google Sheets sebagai basis data hasil belajar murid dan analitik guru.
 *
 * Cara Deploy:
 * 1. Buka Google Sheets baru -> beri nama "DB Petualangan Baligh Kelas 4".
 * 2. Klik menu Ekstensi (Extensions) > Apps Script.
 * 3. Hapus kode bawaan, tempel seluruh kode ini, lalu klik Simpan.
 * 4. Jalankan fungsi `initializeDatabase()` sekali dari editor Apps Script
 *    untuk membuat 8 sheet otomatis beserta header dan rumus rekap.
 * 5. Klik Deploy > New deployment > Type: Web app.
 *    - Execute as: Me
 *    - Who has access: Anyone (atau Anyone within organization)
 * 6. Salin Web App URL dan masukkan ke menu Dashboard Guru > Google Sheets
 *    di aplikasi Android Petualangan Baligh.
 * ============================================================================
 */

const SHEET_NAMES = {
  IDENTITAS: "IDENTITAS",
  BANK_SOAL: "BANK_SOAL",
  JAWABAN_MURID: "JAWABAN_MURID",
  HASIL_FORMATIF: "HASIL_FORMATIF",
  HASIL_SUMATIF: "HASIL_SUMATIF",
  REKAP_GURU: "REKAP_GURU",
  LOG_AKTIVITAS: "LOG_AKTIVITAS",
  PENGATURAN: "PENGATURAN"
};

/**
 * Inisialisasi seluruh 8 sheet, header, format, dan formula otomatis.
 * Jalankan fungsi ini 1x setelah menempel script.
 */
function initializeDatabase() {
  const ss = SpreadsheetApp.getActiveSpreadsheet();

  // 1. IDENTITAS (Minimalisasi data pribadi: hanya nama panggilan/kode)
  setupSheet_(ss, SHEET_NAMES.IDENTITAS, [
    "ID Murid", "Nama Panggilan", "Kelas", "Kelompok", "Tanggal", "Sesi"
  ]);

  // 2. BANK_SOAL
  setupSheet_(ss, SHEET_NAMES.BANK_SOAL, [
    "ID Soal", "Jenis Asesmen", "Pos", "Materi", "Indikator", "Pertanyaan",
    "Tipe Soal", "Opsi A", "Opsi B", "Opsi C", "Opsi D", "Kunci", "Skor",
    "Feedback Benar", "Feedback Salah", "Tingkat Kesulitan"
  ]);

  // 3. JAWABAN_MURID
  setupSheet_(ss, SHEET_NAMES.JAWABAN_MURID, [
    "Timestamp", "ID Murid", "Nama Panggilan", "Kelas", "ID Soal",
    "Jenis Asesmen", "Pos", "Jawaban Murid", "Kunci Jawaban",
    "Status Benar", "Skor Perolehan", "Skor Maksimum", "Durasi Pengerjaan (dtk)"
  ]);

  // 4. HASIL_FORMATIF
  setupSheet_(ss, SHEET_NAMES.HASIL_FORMATIF, [
    "Timestamp", "ID Murid", "Nama Panggilan", "Kelas", "Pos",
    "Jumlah Soal", "Jumlah Benar", "Jumlah Salah", "Nilai",
    "Status Penguasaan", "Rekomendasi"
  ]);

  // 5. HASIL_SUMATIF
  setupSheet_(ss, SHEET_NAMES.HASIL_SUMATIF, [
    "Timestamp", "ID Murid", "Nama Panggilan", "Kelas",
    "Jumlah Soal", "Skor Perolehan", "Skor Maksimum", "Nilai",
    "Status Ketuntasan", "Rekomendasi"
  ]);

  // 7. LOG_AKTIVITAS
  setupSheet_(ss, SHEET_NAMES.LOG_AKTIVITAS, [
    "Timestamp", "ID Murid", "Pos", "Aktivitas", "XP", "Badge", "Status Penyelesaian"
  ]);

  // 8. PENGATURAN (Default settings)
  const settingsSheet = setupSheet_(ss, SHEET_NAMES.PENGATURAN, [
    "Nama Pengaturan", "Nilai"
  ]);
  if (settingsSheet.getLastRow() <= 1) {
    settingsSheet.getRange(2, 1, 8, 2).setValues([
      ["Nama Sekolah", "SD Negeri Nusantara"],
      ["Kelas", "IV (Empat)"],
      ["KKTP", 75],
      ["Jumlah Soal Sumatif", 20],
      ["Skor Maksimum Sumatif", 100],
      ["Nama Guru", "Guru PAI"],
      ["Semester", "Ganjil"],
      ["Tahun Ajaran", "2026/2027"]
    ]);
  }

  // 6. REKAP_GURU (Menggunakan formula dinamis Google Sheets)
  setupRekapGuruSheet_(ss);

  return { status: "ok", message: "8 sheet Petualangan Baligh berhasil diinisialisasi." };
}

function setupSheet_(ss, name, headers) {
  let sheet = ss.getSheetByName(name);
  if (!sheet) {
    sheet = ss.insertSheet(name);
  }
  if (sheet.getLastRow() === 0) {
    sheet.getRange(1, 1, 1, headers.length).setValues([headers]);
    const headerRange = sheet.getRange(1, 1, 1, headers.length);
    headerRange.setFontWeight("bold");
    headerRange.setBackground("#167766");
    headerRange.setFontColor("#FFFFFF");
    sheet.setFrozenRows(1);
  }
  return sheet;
}

function setupRekapGuruSheet_(ss) {
  let sheet = ss.getSheetByName(SHEET_NAMES.REKAP_GURU);
  if (!sheet) {
    sheet = ss.insertSheet(SHEET_NAMES.REKAP_GURU);
  }
  sheet.clear();

  const summaryRows = [
    ["REKAP OTOMATIS HASIL BELAJAR - PETUALANGAN BALIGH", "", ""],
    ["Metrik Kelas", "Nilai / Rumus", "Keterangan"],
    ["Jumlah Murid Terdaftar", '=COUNTA(IDENTITAS!A2:A)', "Total murid pada sheet IDENTITAS"],
    ["Jumlah Murid Mengikuti Sumatif", '=COUNTA(HASIL_SUMATIF!B2:B)', "Total pengerjaan asesmen sumatif"],
    ["Rata-rata Nilai Formatif", '=IFERROR(AVERAGE(HASIL_FORMATIF!I2:I), 0)', "Rata-rata seluruh pos formatif"],
    ["Rata-rata Nilai Sumatif", '=IFERROR(AVERAGE(HASIL_SUMATIF!H2:H), 0)', "Rata-rata skor sumatif akhir"],
    ["Nilai Sumatif Tertinggi", '=IFERROR(MAX(HASIL_SUMATIF!H2:H), 0)', "Nilai tertinggi di kelas"],
    ["Nilai Sumatif Terendah", '=IFERROR(MIN(HASIL_SUMATIF!H2:H), 0)', "Nilai terendah di kelas"],
    ["KKTP Aktif", '=IFERROR(VLOOKUP("KKTP", PENGATURAN!A2:B10, 2, FALSE), 75)', "Diambil dari sheet PENGATURAN"],
    ["Persentase Ketuntasan (%)", '=IFERROR(COUNTIF(HASIL_SUMATIF!H2:H, ">="&B9)/COUNTA(HASIL_SUMATIF!H2:H)*100, 0)', "Persentase murid >= KKTP"],
    ["Jumlah Murid Perlu Pendampingan/Remedial", '=IFERROR(COUNTIF(HASIL_SUMATIF!H2:H, "<"&B9), 0)', "Nilai sumatif < KKTP"],
    ["Jumlah Murid Membutuhkan Pengayaan", '=IFERROR(COUNTIF(HASIL_SUMATIF!H2:H, ">=90"), 0)', "Nilai sumatif >= 90"]
  ];
  sheet.getRange(1, 1, summaryRows.length, 3).setValues(summaryRows);
  sheet.getRange(1, 1, 1, 3).merge().setFontWeight("bold").setBackground("#167766").setFontColor("#FFFFFF");
  sheet.getRange(2, 1, 1, 3).setFontWeight("bold").setBackground("#C7F2E4");

  // Analisis Per Pos
  const posHeaderRow = 15;
  sheet.getRange(posHeaderRow, 1, 1, 4).setValues([
    ["ANALISIS PER POS", "Jumlah Pengerjaan", "Rata-rata Nilai", "Perlu Penguatan (<KKTP)"]
  ]).setFontWeight("bold").setBackground("#167766").setFontColor("#FFFFFF");

  for (let p = 1; p <= 5; p++) {
    const r = posHeaderRow + p;
    sheet.getRange(r, 1, 1, 4).setValues([[
      "Pos " + p,
      `=COUNTIF(HASIL_FORMATIF!E2:E, ${p})`,
      `=IFERROR(AVERAGEIF(HASIL_FORMATIF!E2:E, ${p}, HASIL_FORMATIF!I2:I), 0)`,
      `=COUNTIFS(HASIL_FORMATIF!E2:E, ${p}, HASIL_FORMATIF!I2:I, "<"&$B$9)`
    ]]);
  }
}

/**
 * Endpoint GET untuk cek koneksi & membaca pengaturan/rekap oleh Guru.
 */
function doGet(e) {
  try {
    const action = (e && e.parameter && e.parameter.action) ? e.parameter.action : "ping";
    const ss = SpreadsheetApp.getActiveSpreadsheet();

    if (action === "ping") {
      return jsonResponse_({
        status: "ok",
        app: "Petualangan Baligh",
        spreadsheetName: ss.getName(),
        timestamp: new Date().toISOString()
      });
    }

    if (action === "getSettings") {
      const sheet = ss.getSheetByName(SHEET_NAMES.PENGATURAN);
      const settings = {};
      if (sheet && sheet.getLastRow() > 1) {
        const values = sheet.getRange(2, 1, sheet.getLastRow() - 1, 2).getValues();
        values.forEach(row => {
          if (row[0]) settings[String(row[0])] = row[1];
        });
      }
      return jsonResponse_({ status: "ok", settings: settings });
    }

    return jsonResponse_({ status: "error", message: "Action GET tidak dikenal." });
  } catch (err) {
    return jsonResponse_({ status: "error", message: String(err) });
  }
}

/**
 * Endpoint POST untuk menerima sinkronisasi batch data belajar dari aplikasi Android.
 * Melakukan validasi & sanitasi input sebelum menyimpan ke Google Sheets.
 */
function doPost(e) {
  try {
    if (!e || !e.postData || !e.postData.contents) {
      return jsonResponse_({ status: "error", message: "Payload kosong." });
    }

    const payload = JSON.parse(e.postData.contents);
    const ss = SpreadsheetApp.getActiveSpreadsheet();

    // Pastikan 8 sheet sudah ada
    if (!ss.getSheetByName(SHEET_NAMES.IDENTITAS)) {
      initializeDatabase();
    }

    let insertedCount = 0;

    // 1. IDENTITAS
    if (Array.isArray(payload.identitas) && payload.identitas.length > 0) {
      const rows = payload.identitas.map(item => [
        sanitize_(item.studentId, 40),
        sanitize_(item.nickname, 40),
        sanitize_(item.className, 20),
        sanitize_(item.groupName, 30),
        sanitize_(item.date, 30),
        sanitize_(item.sessionCode, 30)
      ]);
      appendUniqueRows_(ss.getSheetByName(SHEET_NAMES.IDENTITAS), rows, 0);
      insertedCount += rows.length;
    }

    // 2. JAWABAN_MURID
    if (Array.isArray(payload.answers) && payload.answers.length > 0) {
      const rows = payload.answers.map(a => [
        sanitize_(a.timestamp, 35),
        sanitize_(a.studentId, 40),
        sanitize_(a.nickname, 40),
        sanitize_(a.className, 20),
        sanitize_(a.questionId, 30),
        sanitize_(a.assessmentType, 25),
        Number(a.posNumber) || 0,
        sanitize_(a.studentAnswer, 120),
        sanitize_(a.correctAnswer, 120),
        a.isCorrect ? "BENAR" : "SALAH",
        Number(a.earnedScore) || 0,
        Number(a.maxScore) || 0,
        Number(a.durationSeconds) || 0
      ]);
      appendRows_(ss.getSheetByName(SHEET_NAMES.JAWABAN_MURID), rows);
      insertedCount += rows.length;
    }

    // 3. HASIL_FORMATIF
    if (Array.isArray(payload.formativeResults) && payload.formativeResults.length > 0) {
      const rows = payload.formativeResults.map(f => [
        sanitize_(f.timestamp, 35),
        sanitize_(f.studentId, 40),
        sanitize_(f.nickname, 40),
        sanitize_(f.className, 20),
        Number(f.posNumber) || 0,
        Number(f.totalQuestions) || 0,
        Number(f.correctCount) || 0,
        Number(f.wrongCount) || 0,
        Number(f.scorePercent) || 0,
        sanitize_(f.masteryStatus, 40),
        sanitize_(f.recommendation, 200)
      ]);
      appendRows_(ss.getSheetByName(SHEET_NAMES.HASIL_FORMATIF), rows);
      insertedCount += rows.length;
    }

    // 4. HASIL_SUMATIF
    if (Array.isArray(payload.summativeResults) && payload.summativeResults.length > 0) {
      const rows = payload.summativeResults.map(s => [
        sanitize_(s.timestamp, 35),
        sanitize_(s.studentId, 40),
        sanitize_(s.nickname, 40),
        sanitize_(s.className, 20),
        Number(s.totalQuestions) || 0,
        Number(s.earnedScore) || 0,
        Number(s.maxScore) || 0,
        Number(s.finalScore) || 0,
        sanitize_(s.passStatus, 40),
        sanitize_(s.recommendation, 200)
      ]);
      appendRows_(ss.getSheetByName(SHEET_NAMES.HASIL_SUMATIF), rows);
      insertedCount += rows.length;
    }

    // 5. LOG_AKTIVITAS
    if (Array.isArray(payload.activityLogs) && payload.activityLogs.length > 0) {
      const rows = payload.activityLogs.map(l => [
        sanitize_(l.timestamp, 35),
        sanitize_(l.studentId, 40),
        Number(l.posNumber) || 0,
        sanitize_(l.activityName, 80),
        Number(l.xpEarned) || 0,
        sanitize_(l.badgeEarned, 60),
        sanitize_(l.completionStatus, 30)
      ]);
      appendRows_(ss.getSheetByName(SHEET_NAMES.LOG_AKTIVITAS), rows);
      insertedCount += rows.length;
    }

    // 6. PENGATURAN (Opsional update dari Guru)
    if (payload.settings) {
      updateSettingsSheet_(ss.getSheetByName(SHEET_NAMES.PENGATURAN), payload.settings);
    }

    // 7. BANK_SOAL (Opsional sinkronisasi bank soal dari aplikasi)
    if (Array.isArray(payload.bankSoal) && payload.bankSoal.length > 0) {
      const qSheet = ss.getSheetByName(SHEET_NAMES.BANK_SOAL);
      if (qSheet.getLastRow() <= 1) {
        const qRows = payload.bankSoal.map(q => [
          sanitize_(q.id, 30),
          sanitize_(q.assessmentType, 25),
          Number(q.posNumber) || 0,
          sanitize_(q.material, 80),
          sanitize_(q.indicator, 150),
          sanitize_(q.question, 300),
          sanitize_(q.questionType, 30),
          sanitize_(q.optionA, 150),
          sanitize_(q.optionB, 150),
          sanitize_(q.optionC, 150),
          sanitize_(q.optionD, 150),
          sanitize_(q.correctAnswer, 100),
          Number(q.score) || 0,
          sanitize_(q.feedbackCorrect, 200),
          sanitize_(q.feedbackWrong, 200),
          sanitize_(q.difficulty, 20)
        ]);
        appendRows_(qSheet, qRows);
      }
    }

    return jsonResponse_({
      status: "ok",
      syncedRows: insertedCount,
      timestamp: new Date().toISOString()
    });
  } catch (err) {
    return jsonResponse_({ status: "error", message: String(err) });
  }
}

function appendRows_(sheet, rows) {
  if (!sheet || !rows || rows.length === 0) return;
  const startRow = sheet.getLastRow() + 1;
  sheet.getRange(startRow, 1, rows.length, rows[0].length).setValues(rows);
}

function appendUniqueRows_(sheet, rows, keyIndex) {
  if (!sheet || !rows || rows.length === 0) return;
  const existingKeys = new Set();
  const lastRow = sheet.getLastRow();
  if (lastRow > 1) {
    const colValues = sheet.getRange(2, keyIndex + 1, lastRow - 1, 1).getValues();
    colValues.forEach(r => existingKeys.add(String(r[0])));
  }
  const newRows = rows.filter(r => !existingKeys.has(String(r[keyIndex])));
  if (newRows.length > 0) {
    appendRows_(sheet, newRows);
  }
}

function updateSettingsSheet_(sheet, settings) {
  if (!sheet || !settings) return;
  const rows = [
    ["Nama Sekolah", sanitize_(settings.schoolName, 80)],
    ["Kelas", sanitize_(settings.className, 30)],
    ["KKTP", Number(settings.kktp) || 75],
    ["Jumlah Soal Sumatif", Number(settings.questionCount) || 20],
    ["Skor Maksimum Sumatif", Number(settings.maxScore) || 100],
    ["Nama Guru", sanitize_(settings.teacherName, 60)],
    ["Semester", sanitize_(settings.semester, 20)],
    ["Tahun Ajaran", sanitize_(settings.academicYear, 20)]
  ];
  sheet.getRange(2, 1, rows.length, 2).setValues(rows);
}

/**
 * Sanitasi input untuk mencegah Formula Injection (=, +, -, @) di Google Sheets.
 */
function sanitize_(val, maxLen) {
  if (val === null || val === undefined) return "";
  let s = String(val).trim();
  if (s.length > maxLen) {
    s = s.substring(0, maxLen);
  }
  if (/^[=+\-@]/.test(s)) {
    s = "'" + s;
  }
  return s;
}

function jsonResponse_(obj) {
  return ContentService
    .createTextOutput(JSON.stringify(obj))
    .setMimeType(ContentService.MimeType.JSON);
}
