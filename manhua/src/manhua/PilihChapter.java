package manhua;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import koneksi.koneksi;

public class PilihChapter extends JFrame {
    private int manhuaId;
    private String judulManhua;
    private String coverPath;
    private JPanel panelChapter;

    public PilihChapter(int manhuaId, String judul, String cover) {
        this.manhuaId = manhuaId;
        this.judulManhua = judul;
        this.coverPath = cover;

        setTitle("Pilih Chapter - " + judul);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Atas: Judul, Cover, Deskripsi, Genre
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblJudul = new JLabel(judulManhua);
        lblJudul.setFont(new Font("Serif", Font.BOLD, 24));

        // Cover
        JLabel lblCover = new JLabel();
        try {
            ImageIcon imgIcon = new ImageIcon(coverPath);
            Image img = imgIcon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            lblCover.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblCover.setText("Cover not found");
        }

        // Panel tengah (kanan): deskripsi & genre
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel lblDeskripsi = new JLabel("Deskripsi: ");
        JLabel lblGenre = new JLabel("Genre: ");

        // Ambil deskripsi & genre dari database
        try {
            Connection conn = new koneksi().connect();
            String sql = "SELECT deskripsi, genre FROM manhua WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, manhuaId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                lblDeskripsi.setText("<html><b>Deskripsi:</b> " + rs.getString("deskripsi") + "</html>");
                lblGenre.setText("<html><b>Genre:</b> " + rs.getString("genre") + "</html>");
            }
        } catch (Exception e) {
            lblDeskripsi.setText("Deskripsi: (gagal memuat)");
            lblGenre.setText("Genre: (gagal memuat)");
        }

        infoPanel.add(lblJudul);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblDeskripsi);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblGenre);

        headerPanel.add(lblCover, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Panel Tengah: Daftar Chapter
        panelChapter = new JPanel();
        panelChapter.setLayout(new BoxLayout(panelChapter, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelChapter);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Bawah: Tombol keluar
        JPanel bottomPanel = new JPanel();
        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.addActionListener(e -> dispose());
        bottomPanel.add(btnKeluar);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load chapter dari database
        loadChapters();
    }

    private void loadChapters() {
        try {
            Connection conn = new koneksi().connect();
            String sql = "SELECT * FROM chapter WHERE manhua_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, manhuaId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int chapterId = rs.getInt("id");
                String judulChapter = rs.getString("judul_chapter");
                String path = rs.getString("path_pdf");

                JButton btn = new JButton("Chapter: " + judulChapter);
                btn.setAlignmentX(Component.LEFT_ALIGNMENT);
                btn.addActionListener(e -> {
                    new BacaManhuaPDF(path).setVisible(true);
                });

                panelChapter.add(btn);
                panelChapter.add(Box.createVerticalStrut(5));
            }

            panelChapter.revalidate();
            panelChapter.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat chapter: " + e.getMessage());
        }
    }
}