#README

Kebetulan ada yang minta dijelasin tentang [spring-security](https://spring.io/projects/spring-security), sekalian aja saya buatin contoh, supaya saya juga ga lupa, hihihi. Kalau ada masalah atau pertanyaan, drop aja pesan ke [email saya](mailto:zakyalvan@gmail.com).

##Prerequisite
Berikut adalah kebutuhan untuk runing contoh project ini

1. Java 7+
2. Koneksi internet untuk resolve dan download library-lubrary yg jadi dependencies.
3. Git (Optional)

##Running
###Via Terminal

Persyaratan tambahan, install [apache maven](maven.apache.org) dan daftarkan `MAVEN_HOME/bin directory` sebagai environment variable, daripada manage dependencies dan build process secara manual. Untuk running contoh ini, dari project folder sebagai working directory, eksekusi perintah berikut

```
mvn clean
mvn jetty:run
```

Sebagai catatan, bisa jadi perintah ini akan makan waktu (tergantung kecepatan internet) jika library yang dibutuhkan belum ada di local maven repo Anda.

###Menggunakan Eclipse

Saya sarankan menggunakan String Source Tool Suite, import project (Existing maven project), proses import mungkin akan makan waktu lama karena maven akan mengunduh library dependecies dari central repo maven. Lalu pilih 'Run As' -> 'Maven build...' dan ketik Goals 'clean jetty:run'

Selain itu bisa juga dengan, Run on Server. Pada Tomcat 8 atau 'turunannya' seharusnya ga ada masalah. Jika menggunakan Tomcat 7 atau 'turunannya', ganti versi library servlet-api di pom.xml menjadi 3.0.1.

###Lihat Browser
Arahkan browser anda ke alamat [localhost](http://localhost:8080).


Saran terakhir, sebaiknya liat project spring-boot kalau tertarik kerja dengan spring.