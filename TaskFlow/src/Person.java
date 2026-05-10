package proje2;

// Kullanıcıların ortak özelliklerini (ID, Ad, Email) tutar.
public class Person {
    protected int ID; // Protected: Sadece miras alan sınıfların erişebilmesi için (Encapsulation).
    protected String name;
    protected String email;

    public Person() {}
    public Person(int ID, String name, String email){
        this.ID=ID;
        this.name=name;
        this.email=email;
    }

    // Getter ve Setter Metotları: Veri kapsülleme (Encapsulation) prensibi.
    public int getID() {return ID;}
    public void setID(int ID) {this.ID = ID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
