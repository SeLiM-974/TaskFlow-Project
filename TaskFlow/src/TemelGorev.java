package proje2;

public abstract class TemelGorev implements GorevIslemleri {
    protected int id;
    protected String baslik;
    protected String aciklama;

    public TemelGorev(String baslik, String aciklama) {
        this.baslik = baslik;
        this.aciklama = aciklama;
    }

    public int getId() {
        return id;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getBaslik() { return baslik; }
}
