package proje2;

public abstract class OncelikliGorev extends TemelGorev {
    protected String oncelik;

    public OncelikliGorev(String baslik, String aciklama, String oncelik) {
        super(baslik, aciklama);
        this.oncelik = oncelik;
    }
}
