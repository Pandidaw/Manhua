package Koneksi;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import koneksi.koneksi;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditChapter extends JFrame {
    private JComboBox<String> cbManhua;
    private DefaultListModel<String> chapterModel;
    private JList<String> listChapter;
    private JTextField txtJudulChapter, txtPathPDF;
    private JButton btnTambah, btnHapus;
    private Connection conn;
    private int selectedManhuaId = -1;
    private JButton btnKeluar;
    private JButton btnBrowse;
    private void browsePDF() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Pilih File PDF");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
    fileChooser.setFileFilter(filter);

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
        txtPathPDF.setText(selectedPath);
    }
}
    
    public EditChapter() {
        setTitle("Edit Chapter");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        conn = new koneksi().connect();

        // Panel atas: pilih manhua
        JPanel topPanel = new JPanel();
        cbManhua = new JComboBox<>();
        cbManhua.addActionListener(e -> loadChapters());
        topPanel.add(new JLabel("Pilih Manhua: "));
        topPanel.add(cbManhua);

        add(topPanel, BorderLayout.NORTH);

        // Panel tengah: daftar chapter
        chapterModel = new DefaultListModel<>();
        listChapter = new JList<>(chapterModel);
        JScrollPane scrollPane = new JScrollPane(listChapter);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Chapter"));
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah: tambah chapter
        JPanel bottomPanel = new JPanel(new GridLayout(5, 3, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtJudulChapter = new JTextField();
        txtPathPDF = new JTextField();
        btnBrowse = new JButton("Browse");
        btnTambah = new JButton("Tambah Chapter");
        btnHapus = new JButton("Hapus Chapter");
        btnKeluar = new JButton("Keluar");

        btnBrowse.addActionListener(e -> browsePDF());
        btnTambah.addActionListener(e -> tambahChapter());
        btnHapus.addActionListener(e -> hapusChapter());
        btnKeluar.addActionListener(e -> {
            dispose();
            new admin().setVisible(true);
        });

        bottomPanel.add(new JLabel("Judul Chapter:"));
        bottomPanel.add(txtJudulChapter);
        bottomPanel.add(new JLabel(""));

        bottomPanel.add(new JLabel("Path PDF:"));
        bottomPanel.add(txtPathPDF);
        bottomPanel.add(btnBrowse);

        bottomPanel.add(btnTambah);
        bottomPanel.add(btnHapus);
        bottomPanel.add(new JLabel(""));

        bottomPanel.add(btnKeluar);
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));

        add(bottomPanel, BorderLayout.SOUTH);

        loadManhua();
    }

    private void loadManhua() {
        cbManhua.removeAllItems();
        try {
            String sql = "SELECT id, judul FROM manhua";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cbManhua.addItem(rs.getInt("id") + " - " + rs.getString("judul"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat manhua: " + e.getMessage());
        }
    }

    private void loadChapters() {
        chapterModel.clear();
        String selected = (String) cbManhua.getSelectedItem();
        if (selected == null) return;

        selectedManhuaId = Integer.parseInt(selected.split(" - ")[0]);

        try {
            String sql = "SELECT id, judul_chapter FROM chapter WHERE manhua_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedManhuaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                chapterModel.addElement(rs.getInt("id") + " - " + rs.getString("judul_chapter"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat chapter: " + e.getMessage());
        }
    }

    private void tambahChapter() {
        if (selectedManhuaId == -1) return;

        String judul = txtJudulChapter.getText().trim();
        String path = txtPathPDF.getText().trim();

        if (judul.isEmpty() || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul dan path harus diisi.");
            return;
        }

        try {
            String sql = "INSERT INTO chapter (manhua_id, judul_chapter, path_pdf) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedManhuaId);
            stmt.setString(2, judul);
            stmt.setString(3, path);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Chapter berhasil ditambahkan.");
            txtJudulChapter.setText("");
            txtPathPDF.setText("");
            loadChapters();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambah chapter: " + e.getMessage());
        }
    }

    private void hapusChapter() {
        String selected = listChapter.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Pilih chapter yang ingin dihapus.");
            return;
        }

        int chapterId = Integer.parseInt(selected.split(" - ")[0]);

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus chapter ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            String sql = "DELETE FROM chapter WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, chapterId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Chapter berhasil dihapus.");
            loadChapters();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus chapter: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditChapter().setVisible(true);
        });
    }
}
