package proje2;

public class KisiselGorev extends OncelikliGorev {

    private Object sahibi;

    public KisiselGorev(String baslik, String aciklama, String oncelik) {
        super(baslik, aciklama, oncelik);
    }

    @Override
    public void veritabaninaKaydet() {
        System.out.println(baslik + " görev kaydedildi.");
    }

    @Override
    public void veritabanindanSil() {
        System.out.println("Görev silindi.");
    }

    @Override
    public void durumuGuncelle(String yeniDurum) {
        System.out.println("Yeni durum: " + yeniDurum);
    }
}
