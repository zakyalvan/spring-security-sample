#README

Kebetulan ada yang minta dijelasin tentang [spring-security](https://spring.io/projects/spring-security), sekalian aja saya buatin contoh, supaya saya juga ga lupa, hihihi. Kalau ada masalah atau pertanyaan, drop aja pesan ke [email saya](mailto:zakyalvan@gmail.com).

##Prerequisite
Berikut adalah kebutuhan untuk runing contoh project ini

1. Java 7+
2. Koneksi internet untuk resolve dan download library-lubrary yg jadi dependencies.
3. Git (Optional)

##Running
###Terminal

Persyaratan tambahan, install [apache maven](maven.apache.org) dan daftarkan `MAVEN_HOME/bin` directory sebagai environment variable, daripada manage dependencies dan build process secara manual. Untuk running contoh ini via terminal, dari project folder sebagai working directory, eksekusi perintah berikut

```
mvn clean
mvn jetty:run
```

Sebagai catatan, bisa jadi perintah ini akan makan waktu (tergantung kecepatan internet) jika library yang dibutuhkan belum ada di local maven repo Anda.

###Eclipse

Saya sarankan menggunakan String Source Tool Suite, import project (Existing maven project), proses import mungkin akan makan waktu lama karena maven akan mengunduh library dependecies dari central repo maven. Lalu pilih 'Run As' -> 'Maven build...' dan ketik Goals 'clean jetty:run'

Selain itu bisa juga dengan, Run on Server. Pada Tomcat 8 atau 'turunannya' seharusnya ga ada masalah. Jika menggunakan Tomcat 7 atau 'turunannya', ganti versi library servlet-api di pom.xml menjadi 3.0.1.

###Lihat Browser
Arahkan browser anda ke alamat [localhost](http://localhost:8080).

##Mengamankan Aplikasi Web Java dengan Spring Security
###Cuap Cuap Pengantar
Dalam konteks aplikasi web, spring security adalah security framework berbasis `javax.servlet.Filter`. Waluapun sebenarnya cakupan feature-nya bukan cuma security untuk web layer, namun kita batasi saja dulu sampe di situ. Dalam menggunakan library ini, developer akan 'dipakasa' bekerja dalam paradigma *contract base*, maksudnya jika tugas developer adalah mengimplimentasi interface-interface yang telah disediakan untuk untuk memenuhi kebutuhan atau 'kastemais'. Mungkin yang sebelumnya terbiasa dengan handle login manual dengan controller action, ini akan membingungkan. Ingat, login cuman cuma salah satu aspek, masih ada authorization dan perintilan lain macam CSRF, mau handle manual semuanya dari web layer sampe business layer? Saya sih ga mau!

###Membuat Aplikasi Web Spring Web MVC
Sebenarnya spring security bisa digunakan untuk semua aplikasi web java berbasis servlet, namun karena saya cuma bersahabat dengan Spring Web MVC, maafkan kalau saya terpaksa make itu. Langkahnya sederhana, buat project maven dengan packaging `war` lalu edit file `pom.xml` untuk mendeklarasikan *dependency* ke library-library serta build plugin yang dibutuhkan seperti berikut ini

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	
	<!-- Anda bebas memilih groupId dan artifactId dari project maven -->
	<groupId>com.innovez.sample</groupId>
	<artifactId>spring-security-sample</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<dependencies>
		<!-- Dependecy untuk logging -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.7.10</version>
		</dependency>

		<!-- Ini dibutuhkan untuk (salah satunya) transaction management dengan annotation @Transactional -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aspects</artifactId>
			<version>4.1.4.RELEASE</version>
		</dependency>

		<!-- Library utama spring-webmvc -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>4.1.4.RELEASE</version>
		</dependency>
		<!-- Silahkan ganti versi servlet-api sesuai kontainer yang di gunakan -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.1.0</version>
		</dependency>
		<!-- Library yang akan digunakan oleh komponen spring web untuk serialize object java ke json -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.5.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>4.1.4.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
			<version>4.1.4.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-orm</artifactId>
			<version>4.1.4.RELEASE</version>
		</dependency>
		<!-- Hibernate JPA Persistence Provider -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>4.3.8.Final</version>
		</dependency>
		<!-- Agar praktis, project ini menggunakan embedded database -->
		<dependency>
			<groupId>org.hsqldb</groupId>
			<artifactId>hsqldb</artifactId>
			<version>2.3.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aop</artifactId>
			<version>4.1.4.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>5.1.3.Final</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.2</version>
				<configuration>
					<source>1.7</source>
					<target>1.7</target>
					<encoding>UTF-8</encoding>
				</configuration>
			</plugin>
			<!-- Embedded jetty servlet container, web project bisa dijalankan dengan perintah mvn jetty:run -->
			<plugin>
				<groupId>org.eclipse.jetty</groupId>
				<artifactId>jetty-maven-plugin</artifactId>
				<version>9.2.7.v20150116</version>
			</plugin>
		</plugins>
	</build>
</project>
```

###Declare Dependency Spring Security
Langkah pertama untuk mengamankan aplikasi web java adalah deklarasi library dependency `spring-security` di `pom.xml`

```
		...
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-web</artifactId>
			<version>3.2.5.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-config</artifactId>
			<version>3.2.5.RELEASE</version>
		</dependency>
		...
```

###Configure Security Filter (web.xml)
Bagian yang paling penting adalah konfigurasi security filter di `web.xml`. Untuk menyederhanakan, berikut ini adalah cuplikan kode untuk itu

```
...
<filter>
	<filter-name>springSecurityFilterChain</filter-name>
	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
	<filter-name>springSecurityFilterChain</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
...
```

Ini berarti kita akan mem-filter seluruh request yang diterima aplikasi web kita, dan 'menerapkan' mekanisme keamanan untuk seluruh request tersebut. Sebenarnya filter ini tidak melakukannya sendiri akan tetapi di delegasikan ke (rangkain) spring bean dengan nama `springSecurityFilterChain`, jangan takut, setelah ini saya bahas tentang ini, tapi ga detail ya.

###Konfigurasi Komponen Spring Security
Banyak sekali komponen yang terlibat dalam 'mengamankan' aplikasi web dengan spring-security, syukurnya ada beberapa kemudahan. Langkah pertama, buat file konfigurasi spring standard dengan tambahan security namespace seperti berikut ini, lihat `src/main/resources/META-INF/spring/core/security-context.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:security="http://www.springframework.org/schema/security"
	xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
		
	<!-- Di sini akan diisi konfigurasi spring security -->
		
</beans>
```

Seperti saya sebut sebelumunya, filter sekuriti akan mendelegasikan pekerjaan ke (rangkaian) spring bean dengan nama `springSecurityFilterChain`. Seperti sudah saya sebut juga, banyak komponen yang terlibat dan konfigur mereka satu satu satu secara manual sangat **welehweleh**! Syukurnya spring-security menyediakan mekanisme konfigurasi yang lumayan 'sederhana' sebagai berikut.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:security="http://www.springframework.org/schema/security"
	xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<security:http use-expressions="true">
		<security:form-login login-page="/login" login-processing-url="/authenticate" authentication-failure-url="/login?failed=true" username-parameter="username" password-parameter="password" />
		<security:logout logout-url="/logout" logout-success-url="/login?from=logout" invalidate-session="true" />
		
		<security:intercept-url pattern="/login" access="permitAll()"/>
		<security:intercept-url pattern="/**" access="isAuthenticated()"/>
	</security:http>
	<security:authentication-manager alias="authManager">
		<security:authentication-provider user-service-ref="userDetailsService" />
	</security:authentication-manager>
	
	<bean name="userDetailsService" class="com.innovez.sample.core.security.CustomUserDetailsService" />
</beans>
```

Beberapa baris konfigurasi di atas akan membuat dan menginisiasi banyak komponen *security*, namun yang paling penting, elemen konfigurasi `<security:http></security:http>` secara implisit membuat bean `springSecurityFilterChain`, ke mana mekanisme *security* akan didelegasikan oleh servlet filter yang telah dijelaskan sebelumnya. Abaikan dulu deklarasi elemen konfigurasi yang lain, akan dibahas di sesi selanjutnya.

###Komponen Pendukung Authentication Provider
Spring menyediakan banyak komponen yang dapat melakukan mekanisme authentikasi, misal LDAP, database, JDBC, CAS dan termasuk fitur kastemisasi jika yang disediakan tidak sesuai kebutuhan. Kali ini kita akan meng-custom mekanisme autentikasi, namun data user di baca dari record database.b
FIXME

###Inisiasi Komponen Security
TODO

Saran terakhir, sebaiknya liat project spring-boot kalau tertarik kerja dengan spring.