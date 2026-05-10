package proje2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;

public class AnaGorevPaneli extends JFrame {
    private int userId;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JLabel lblWelcome, lblStats;
    private JTextField txtSearch;

    public AnaGorevPaneli(int userId) {
        this.userId = userId;

        setTitle("TaskFlow | Görev Yönetim Sistemi");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 39, 46));
        setLayout(new BorderLayout(15, 15));

        // --- ÜST PANEL ---
        JPanel topContainer = new JPanel(new GridLayout(2, 1));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 52, 54));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblWelcome = new JLabel("Merhaba, " + "Yükleniyor...");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);
        headerPanel.add(lblWelcome, BorderLayout.WEST);

        JButton btnYeniGorev = new JButton("YENİ GÖREV EKLE");
        btnYeniGorev.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnYeniGorev.setBackground(new Color(38, 103, 255));
        btnYeniGorev.setForeground(Color.WHITE);
        btnYeniGorev.setFocusPainted(false);
        btnYeniGorev.addActionListener(e -> openAddScreen());
        headerPanel.add(btnYeniGorev, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        searchPanel.setBackground(new Color(30, 39, 46));

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) { gorevleriYukle(); }
        });

        lblStats = new JLabel("Veriler güncelleniyor...");
        lblStats.setForeground(new Color(189, 195, 199));
        lblStats.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JLabel lblSearchTag = new JLabel("Görevlerde Ara:");
        lblSearchTag.setForeground(Color.WHITE);
        searchPanel.add(lblSearchTag);
        searchPanel.add(txtSearch);
        searchPanel.add(lblStats);

        topContainer.add(headerPanel);
        topContainer.add(searchPanel);
        add(topContainer, BorderLayout.NORTH);

        // --- ORTA PANEL (Tablo Bölümü) ---
        // Sütunlara "AÇIKLAMA" eklendi
        tableModel = new DefaultTableModel(new String[]{"#", "GÖREV", "AÇIKLAMA", "ÖNCELİK", "DURUM", "TARİH", "ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0 || column == 6) return Integer.class;
                return String.class;
            }
        };

        taskTable = new JTable(tableModel);
        // burası
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        taskTable.setRowSorter(sorter);

        // burası
        sorter.setComparator(3, (s1, s2) -> getPriorityLevel(s1.toString()) - getPriorityLevel(s2.toString()));

        styleTable();

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.getViewport().setBackground(new Color(30, 39, 46));
        add(scrollPane, BorderLayout.CENTER);

        // --- ALT PANEL ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        bottomPanel.setOpaque(false);

        JButton btnTamamla = new JButton("Görevi Tamamla");
        btnTamamla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTamamla.setBackground(new Color(0, 184, 148));
        btnTamamla.setForeground(Color.WHITE);
        btnTamamla.setFocusPainted(false);

        JButton btnSil = new JButton("Görevi Kaldır");
        btnSil.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSil.setBackground(new Color(214, 48, 49));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFocusPainted(false);

        btnTamamla.addActionListener(e -> durumGuncelle());
        btnSil.addActionListener(e -> gorevSil());
        bottomPanel.add(btnTamamla); bottomPanel.add(btnSil);
        add(bottomPanel, BorderLayout.SOUTH);

        kullaniciBilgisiGetir();
        gorevleriYukle();
    }
    // sıralama
    private int getPriorityLevel(String p) {
        if (p.contains("Yüksek")) return 3;
        if (p.contains("Orta")) return 2;
        if (p.contains("Düşük")) return 1;
        return 0;
    }

    private void styleTable() {
        taskTable.setRowHeight(45); // Biraz daha yüksek yaptık detaylar rahat görünsün
        taskTable.setBackground(new Color(45, 52, 54));
        taskTable.setForeground(Color.WHITE);
        taskTable.setGridColor(new Color(30, 39, 46));
        taskTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskTable.getTableHeader().setBackground(new Color(38, 103, 255));
        taskTable.getTableHeader().setForeground(Color.WHITE);
        taskTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Sütun Ayarları
        taskTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(40); // Sıra no dar
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Başlık
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(350); // Açıklama GENİŞ

        // ID sütununu gizle (Sütun 6 oldu artık)
        taskTable.getColumnModel().getColumn(6).setMinWidth(0);
        taskTable.getColumnModel().getColumn(6).setMaxWidth(0);

        // Öncelik Renklendirme (Sütun 3)
        taskTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                String val = String.valueOf(v);
                if (val.contains("Yüksek")) l.setForeground(new Color(255, 107, 107));
                else if (val.contains("Orta")) l.setForeground(new Color(255, 217, 61));
                else if (val.contains("Düşük")) l.setForeground(new Color(85, 239, 196));
                return l;
            }
        });

        taskTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    }

    private void openAddScreen() {
        GorevEklemeEkrani g = new GorevEklemeEkrani(userId);
        g.setVisible(true);
        g.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) { gorevleriYukle(); }
        });
    }
    // burası arama
    private void gorevleriYukle() {
        tableModel.setRowCount(0);
        String searchWord = txtSearch.getText().trim();
        String sql = "SELECT * FROM gorevler WHERE kullanici_id = ? AND (gorev_basligi LIKE ? OR detay LIKE ?) ORDER BY id ASC";
        int total = 0, pending = 0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, "%" + searchWord + "%");
            pstmt.setString(3, "%" + searchWord + "%");
            ResultSet rs = pstmt.executeQuery();

            int siraNo = 1;
            while (rs.next()) {
                String durum = rs.getString("durum");
                tableModel.addRow(new Object[]{
                        siraNo++,
                        rs.getString("gorev_basligi"),
                        rs.getString("detay"), // Veritabanındaki detaylar buraya
                        rs.getString("oncelik"),
                        durum,
                        rs.getTimestamp("olusturma_tarihi"),
                        rs.getInt("id")
                });
                total++;
                if (!durum.contains("TAMAMLANDI")) pending++;
            }
            lblStats.setText("Toplam: " + total + " | Bekleyen: " + pending);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void durumGuncelle() {
        int row = taskTable.getSelectedRow();
        if (row == -1) return;
        int modelRow = taskTable.convertRowIndexToModel(row);
        int gercekId = (int) tableModel.getValueAt(modelRow, 6);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE gorevler SET durum = 'TAMAMLANDI' WHERE id = ?")) {
            pstmt.setInt(1, gercekId);
            pstmt.executeUpdate();
            gorevleriYukle();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void gorevSil() {
        int row = taskTable.getSelectedRow();
        if (row == -1) return;
        int modelRow = taskTable.convertRowIndexToModel(row);
        int gercekId = (int) tableModel.getValueAt(modelRow, 6);
        if (JOptionPane.showConfirmDialog(this, "Bu görevi silmek istediğine emin misin?", "Onay", JOptionPane.YES_NO_OPTION) == 0) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM gorevler WHERE id = ?")) {
                pstmt.setInt(1, gercekId);
                pstmt.executeUpdate();
                gorevleriYukle();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void kullaniciBilgisiGetir() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT ad_soyad FROM kullanicilar WHERE id = ?")) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                lblWelcome.setText("Merhaba, " + rs.getString("ad_soyad"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}

