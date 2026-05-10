package proje2;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * TaskFlowAuth Sınıfı: Kullanıcı kayıt ve giriş işlemlerini yöneten ana GUI sınıfı.
 * CardLayout kullanarak tek bir pencerede iki farklı panel (Giriş/Kayıt) yönetir.
 */
public class TaskFlowAuth extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public TaskFlowAuth() {
        setTitle("TaskFlow | Kimlik Yönetimi");
        setSize(400, 550);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ekranların yönetimi için panellerin CardLayout'a eklenmesi
        mainPanel.add(createLoginPanel(), "Giriş");
        mainPanel.add(createRegisterPanel(), "Kayıt");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18, 32, 47));

        JLabel lblTitle = new JLabel("Giriş Yap", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setBounds(50, 40, 300, 30);
        panel.add(lblTitle);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(50, 150, 300, 40);
        panel.add(new JLabel("Kullanıcı Adı:") {{ setForeground(Color.WHITE); setBounds(50, 125, 100, 20); }});
        panel.add(txtUser);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(50, 230, 300, 40);
        panel.add(new JLabel("Şifre:") {{ setForeground(Color.WHITE); setBounds(50, 205, 100, 20); }});
        panel.add(txtPass);

        JButton btnLogin = new JButton("Giriş Yap");
        btnLogin.setBounds(50, 320, 300, 45);
        btnLogin.setBackground(new Color(0, 180, 216));

        // --- GİRİŞ MANTIĞI: Veritabanı Sorgulama ---
        btnLogin.addActionListener(e -> {
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());

            // Güvenlik: PreparedStatement kullanarak SQL Injection saldırılarını engelliyoruz.
            String sql = "SELECT id, ad_soyad FROM kullanicilar WHERE kullanici_adi = ? AND sifre = ?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("ad_soyad");
                    JOptionPane.showMessageDialog(this, "Hoş geldin " + name + "!");

                    // Başarılı Giriş: Kullanıcı ID'si görev paneline aktarılır (Session Management).
                    new AnaGorevPaneli(id).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Kullanıcı adı veya şifre hatalı!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Veritabanı bağlantı hatası!");
            }
        });
        panel.add(btnLogin);

        JButton btnGoRegister = new JButton("Hesabınız yok mu? Kayıt Olun");
        btnGoRegister.setBounds(50, 380, 300, 30);
        btnGoRegister.setContentAreaFilled(false);
        btnGoRegister.setForeground(Color.CYAN);
        btnGoRegister.setBorder(null);
        btnGoRegister.addActionListener(e -> cardLayout.show(mainPanel, "Kayıt"));
        panel.add(btnGoRegister);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18, 32, 47));

        JLabel lblTitle = new JLabel("Yeni Kayıt", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setBounds(50, 30, 300, 30);
        panel.add(lblTitle);

        JTextField txtName = new JTextField(); txtName.setBounds(50, 100, 300, 35);
        panel.add(new JLabel("Ad Soyad:") {{ setForeground(Color.WHITE); setBounds(50, 80, 100, 20); }});
        panel.add(txtName);

        JTextField txtEmail = new JTextField(); txtEmail.setBounds(50, 170, 300, 35);
        panel.add(new JLabel("E-Posta:") {{ setForeground(Color.WHITE); setBounds(50, 150, 100, 20); }});
        panel.add(txtEmail);

        JTextField txtUser = new JTextField(); txtUser.setBounds(50, 240, 300, 35);
        panel.add(new JLabel("Kullanıcı Adı:") {{ setForeground(Color.WHITE); setBounds(50, 220, 100, 20); }});
        panel.add(txtUser);

        JPasswordField txtPass = new JPasswordField(); txtPass.setBounds(50, 310, 300, 35);
        panel.add(new JLabel("Şifre:") {{ setForeground(Color.WHITE); setBounds(50, 290, 100, 20); }});
        panel.add(txtPass);

        JButton btnReg = new JButton("Kaydı Tamamla");
        btnReg.setBounds(50, 380, 300, 45);
        btnReg.setBackground(new Color(40, 167, 69));

        // --- KAYIT MANTIĞI: Veri Doğrulama (Validation) ve Kayıt ---
        btnReg.addActionListener(e -> {
            String name = txtName.getText();
            String email = txtEmail.getText();
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());

            // 1. Veri Doğrulama: Boş alan kontrolü
            if(name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()){
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları eksiksiz doldurun!");
                return;
            }

            // 2. Veri Doğrulama: E-posta formatı kontrolü (@ ve . kontrolü)
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Geçersiz e-posta! Lütfen geçerli bir e-posta adresi girin.");
                return;
            }

            String sql = "INSERT INTO kullanicilar (ad_soyad, email, kullanici_adi, sifre) VALUES (?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, username);
                pstmt.setString(4, password);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Kayıt Başarıyla Tamamlandı!");
                cardLayout.show(mainPanel, "Giriş");

            } catch (SQLException ex) {
                // Veritabanında tanımlı UNIQUE kısıtlaması (Unique Constraint) burada hata döndürebilir.
                JOptionPane.showMessageDialog(this, "Hata: Bu kullanıcı adı zaten kullanımda olabilir.");
                ex.printStackTrace();
            }
        });
        panel.add(btnReg);

        JButton btnBack = new JButton("Giriş Ekranına Dön");
        btnBack.setBounds(50, 440, 300, 30);
        btnBack.setContentAreaFilled(false);
        btnBack.setForeground(Color.LIGHT_GRAY);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Giriş"));
        panel.add(btnBack);

        return panel;
    }

    public static void main(String[] args) {
        // Swing arayüzünü güvenli bir şekilde başlatıyoruz.
        SwingUtilities.invokeLater(() -> new TaskFlowAuth());
    }
}
