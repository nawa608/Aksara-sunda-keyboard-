PAPAN KETIK AKSARA SUNDA — PROYEK ANDROID

Ini adalah proyek sumber aplikasi keyboard Android (IME), bukan APK yang sudah terpasang.
Keyboard dirancang untuk mengetik langsung ke kolom teks di aplikasi seperti WhatsApp,
Instagram, Facebook, SMS, dan aplikasi lain yang menerima input keyboard.

ISI KEYBOARD
- Ngalagena
- Swara dan rarangken
- Halaman angka/tanda Aksara Sunda
- Tombol spasi, hapus, enter, dan beralih halaman

CARA MEMBANGUN APK
1. Salin ZIP ke komputer dan ekstrak.
2. Pasang Android Studio versi terbaru serta Android SDK.
3. Buka folder AksaraSundaKeyboard melalui Android Studio.
4. Tunggu Gradle selesai sinkronisasi. Jika diminta, izinkan unduhan komponen Gradle/SDK.
5. Pilih Build > Build APK(s).
6. APK biasanya tersedia di app/build/outputs/apk/debug/app-debug.apk.

CARA MENGAKTIFKAN DI HP ANDROID
1. Pasang APK hasil build.
2. Buka Pengaturan HP > Sistem/Setelan tambahan > Bahasa & masukan > Keyboard di layar
   (nama menu bisa berbeda tergantung merek HP).
3. Aktifkan “Aksara Sunda”.
4. Buka WhatsApp atau aplikasi lain, ketuk kolom pesan, lalu pilih ikon pemilih keyboard
   di bilah navigasi/notifikasi atau tahan ikon globe pada keyboard untuk memilih Aksara Sunda.

CATATAN
- Proyek ini membutuhkan Android Studio/Android SDK untuk membangun APK. ZIP ini belum berisi APK siap pasang.
- Tampilan glyph bergantung pada dukungan font Aksara Sunda di perangkat. Jika kotak-kotak muncul,
  perangkat mungkin memerlukan font yang mendukung blok Unicode Sundanese.
- Tombol “ABC” mencoba kembali ke keyboard sistem. Jika tidak berhasil pada perangkat tertentu,
  pilih keyboard lain melalui pemilih keyboard Android.
- Halaman ketiga merupakan halaman tanda/angka Unicode awal; susunan ini adalah versi prototipe
  dan dapat disempurnakan sesuai kebutuhan pengguna.


MEMBANGUN APK HANYA DENGAN HP (MELALUI GITHUB ACTIONS)
1. Buat akun / masuk ke https://github.com lewat Chrome.
2. Buat repository baru, misalnya "aksara-sunda-keyboard".
3. Ekstrak ZIP proyek. Unggah semua isi folder ke repository GitHub, termasuk folder tersembunyi .github/workflows.
   Jika unggah folder tersembunyi sulit lewat HP, gunakan GitHub web editor untuk membuat file
   .github/workflows/build-apk.yml sesuai file yang disediakan dalam ZIP.
4. Setelah file masuk ke branch main, buka tab Actions pada repository.
5. Pilih "Build APK Aksara Sunda", lalu tekan Run workflow bila tombol tersedia.
6. Tunggu workflow selesai. Buka hasil run yang hijau, cari bagian Artifacts, lalu unduh
   aksara-sunda-keyboard-apk.zip.
7. Ekstrak file artifact tersebut untuk mendapatkan app-debug.apk, lalu pasang di HP.
   Jika Android memblokir pemasangan, izinkan instalasi aplikasi dari sumber tersebut untuk
   aplikasi yang digunakan membuka APK, hanya jika Anda mempercayai file yang dibuat.

Catatan keamanan: APK debug belum ditandatangani untuk distribusi publik. Jangan unggah rahasia
atau kata sandi ke repository. Alur ini membangun kode proyek Anda melalui GitHub Actions.
