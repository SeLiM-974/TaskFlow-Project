package proje2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class GorevEklemeEkrani extends JFrame {
    private JTextField txtBaslik;
    private JTextArea txtAciklama;
    private JComboBox<String> comboOncelik;
    private JButton btnKaydet;
    private int userId;

    public GorevEklemeEkrani(int userId) {
        this.userId = userId;
        setTitle("TaskFlow | Yeni Görev Oluştur");
        setSize(500, 700); // Ekranı biraz daha büyüttük
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // ARKA PLAN
        JPanel anaPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 32, 67), 0, getHeight(), new Color(38, 103, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        anaPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        add(anaPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Başlık
        JLabel lblHeader = new JLabel("YENİ GÖREV DETAYLARI");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        anaPanel.add(lblHeader, gbc);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color accentColor = new Color(189, 195, 199);

        // Görev Adı Alanı
        anaPanel.add(createLabel("GÖREV ADI", labelFont, accentColor), gbc);
        txtBaslik = createModernField();
        anaPanel.add(txtBaslik, gbc);

        anaPanel.add(createLabel("AÇIKLAMA / DETAYLAR", labelFont, accentColor), gbc);
        txtAciklama = new JTextArea(8, 20); // Satır sayısını artırdık
        txtAciklama.setLineWrap(true);
        txtAciklama.setWrapStyleWord(true);
        txtAciklama.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(txtAciklama);
        scrollPane.setPreferredSize(new Dimension(0, 180)); // Yüksekliği burada sabitledik
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        anaPanel.add(scrollPane, gbc);

        // Öncelik Seviyesi
        anaPanel.add(createLabel("ÖNCELİK SEVİYESİ", labelFont, accentColor), gbc);
        String[] options = {"Düşük Öncelik", "Orta Öncelik", "Yüksek Öncelik"};
        comboOncelik = new JComboBox<>(options);
        comboOncelik.setPreferredSize(new Dimension(0, 40));
        anaPanel.add(comboOncelik, gbc);

        anaPanel.add(Box.createVerticalStrut(20), gbc);

        // Kaydet Butonu
        btnKaydet = new JButton("SİSTEME GÜVENLİ KAYDET");
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnKaydet.setBackground(new Color(0, 184, 148));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFocusPainted(false);
        btnKaydet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKaydet.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        btnKaydet.addActionListener(e -> kaydet());
        anaPanel.add(btnKaydet, gbc);
    }

    private void kaydet() {
        String baslik = txtBaslik.getText().trim();
        String detay = txtAciklama.getText().trim();
        String oncelik = (String) comboOncelik.getSelectedItem();

        if (baslik.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen bir görev başlığı girin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO gorevler (kullanici_id, gorev_basligi, detay, oncelik, durum) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, baslik);
            pstmt.setString(3, detay);
            pstmt.setString(4, oncelik);
            pstmt.setString(5, "Yapılacak");

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Görev Başarıyla Kaydedildi.");
            this.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
    // Yardımcı Metodlar
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField createModernField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(255, 255, 255, 240));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
    }
}