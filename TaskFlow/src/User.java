package proje2;

// Kalıtım (Inheritance): Person sınıfından türer ve IUserOperations arayüzünü uygular.
public class User extends Person implements IUserOperations {
    private String username;
    private String password;

    public User() {
        super();
    }

    // super(): Üst sınıfın (Person) constructor'ını çağırarak temel verileri başlatır.
    public User(int ID, String name, String email, String username, String password) {
        super(ID, name, email);
        this.username = username;
        this.password = password;
    }

    // Polymorphism: Interface'den gelen metotların sınıf içinde özelleştirilmesi (Overriding).
    @Override
    public void login() {
        System.out.println(username + " (ID: " + getID() + ") sisteme giriş yapıyor...");
    }

    @Override
    public void register() {
        System.out.println(name + " için " + email + " adresiyle kayıt yapılıyor...");
    }

    public String getUsername() {return username;}
    public void SetUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void SetPassword(String password) {this.password = password;}
}
