package proje2;

import java.util.ArrayList;

public class GorevServisi {

    private ArrayList<KisiselGorev> tumGorevler = new ArrayList<>();

    public void gorevEkle(KisiselGorev yeniGorev) {
        tumGorevler.add(yeniGorev);
        System.out.println("Listeye Eklendi: " + yeniGorev.getBaslik());
    }

    public ArrayList<KisiselGorev> tumunuGetir() {
        return tumGorevler;
    }
}