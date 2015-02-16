=README

Kebetulan ada yang minta dijelasin tentang [spring-security](https://spring.io/projects/spring-security), sekalian aja saya buatin contoh, supaya saya juga ga lupa, hihihi. Kalau ada masalah, drop aja pesan ke [email saya](mailto:zakyalvan@gmail.com).

==Running Via Terminal

Prerequisite :
1. Java 7+
2. Install maven dan daftarkan MAVEN_HOME/bin directory sebagai environment variable.
3. Koneksi internet untuk resolve dan download library-lubrary yg jadi dependencies.

Untuk running contoh ini, dari project folder sebagai working directory, eksekusi perintah berikut

```
mvn clean
mvn jetty:run
```

Catatan : Bisa jadi perintah ini akan makan waktu jika library yang dibutuhkan belum ada di local maven repo.

==Runing via Eclipse
Saya sarankan menggunakan String Source Tool Suite, import project (Existing maven project), proses import mungkin akan makan waktu lama karena maven akan mengunduh library dependecies dari central repo maven. Lalu pilih 'Run As' -> 'Maven build...' dan ketik Goals 'clean jetty:run'

Arahkan browser anda ke alamat [localhost](http://localhost:8080).


Saran terakhir, sebaiknya liat project spring-boot kalau tertarik kerja dengan spring.