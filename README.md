TaskFlow | Akıllı Görev Yönetim Sistemi 🚀

TaskFlow, Java Swing ve MySQL kullanılarak geliştirilmiş, nesne yönelimli programlama (OOP) prensiplerini temel alan profesyonel bir görev takip otomasyonudur. Proje; sürdürülebilir bir mimari ile görsel zenginliği (GUI) birleştiren, kullanıcı odaklı bir masaüstü uygulamasıdır.

🌟 Öne Çıkan Özellikler
•	Özgün GUI Tasarımı: Standart Swing görünümünden uzak, Graphics2D ve GradientPaint kütüphaneleri kullanılarak kodlanmış, modern ve kurumsal arayüzler.
•	Kullanıcıya Özel Dashboard: Her kullanıcı sadece kendi oluşturduğu görevleri görür; veritabanı "Kişi-Görev" ilişkisi üzerine kurulmuştur.
•	Dinamik Görev Yönetimi: Görevlerin başlık, öncelik (Düşük, Orta, Yüksek) ve durum bilgilerinin profesyonel bir JTable üzerinde anlık listelenmesi.
•	Güvenli JDBC Mimarisi: Tüm sorgularda PreparedStatement kullanılarak SQL Injection riskine karşı tam koruma sağlanmıştır.
•	Kendi Kendine Yeten Sistem: Uygulama içerisindeki formlar aracılığıyla yeni görevler eklenebilir ve yayınlanabilir; veritabanına (Workbench) manuel müdahale gerektirmez.

🛠️ Gereksinimler
•	Java JDK: 8 veya üzeri.
•	Veritabanı: MySQL Server (Yönetim için MySQL Workbench).
•	JDBC Driver: MySQL Connector (Proje ana dizininde mevcuttur).
•	IDE: Eclipse veya IntelliJ IDEA.

⚙️ Kurulum ve Çalıştırma
1. Projeyi İndirme
GitHub sayfasındaki "Code" butonuna tıklayıp "Download ZIP" seçeneği ile dosyaları indirin.
2. MySQL Workbench Yapılandırması
•	MySQL Workbench'i açın.
•	"Server" -> "Data Import" menüsünden projedeki taskflow_database.sql dosyasını seçin.
•	taskflow_db adında bir şema oluşturup "Start Import" butonuna basın.
3. Bağlantı Ayarları
src/DBConnection.java dosyasını açıp kendi Workbench bilgilerinizi girin:
String url = "jdbc:mysql://localhost:3306/taskflow_db";
String username = "root"; 
String password = "sifreniz";
4. Projeyi Başlatma
Eclipse üzerinden Login_Panel.java (veya başlangıç dosyanız) dosyasına sağ tıklayıp "Run As -> Java Application" seçeneğine tıklayın.

🏗️ Teknik Mimari (OOP)
Proje; Kalıtım (Inheritance), Soyutlama (Abstraction) ve Kapsülleme (Encapsulation) prensiplerini uçtan uca uygular. Veritabanı seviyesinde kurulan Foreign Key ve On Delete Cascade kuralları ile veri bütünlüğü en üst düzeyde tutulmuştur.

✅ Önemli Notlar
Giriş yapmak için önce "Kayıt Ol" panelini kullanınız. Dashboard üzerindeki "Yeni Görev Oluştur" butonu sayesinde sistemi tamamen uygulama içerisinden yönetebilirsiniz.
