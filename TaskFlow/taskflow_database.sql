CREATE TABLE IF NOT EXISTS kullanicilar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ad_soyad VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    kullanici_adi VARCHAR(50) NOT NULL,
    sifre VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS gorevler (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kullanici_id INT,
    gorev_basligi VARCHAR(255) NOT NULL,
    detay TEXT,
    oncelik VARCHAR(50),
    durum VARCHAR(50) DEFAULT 'Yapılacak',
    olusturma_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (kullanici_id) REFERENCES kullanicilar(id) ON DELETE CASCADE
);
