
package Koneksi;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import koneksi.koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import manhua.PilihChapter;
import manhua.login;

public class admin extends javax.swing.JFrame {
Connection conn;
    
public admin() {
    initComponents();
    // --- Efek Hover untuk jLabel6 ---
    jLabel6.setCursor(new Cursor(Cursor.HAND_CURSOR));
    jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            jLabel6.setForeground(Color.BLUE); // saat hover
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            jLabel6.setForeground(Color.BLACK); // saat keluar
        }
    });

    // --- Genre multi-select (bisa pilih lebih dari 1 genre) ---
    listGenre.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    // --- Inisialisasi koneksi dan load data manhua ---
    koneksi koneksi1 = new koneksi();
    conn = koneksi1.connect();
    loadManhua();
    tampilkanData();

    // --- Aksi saat klik baris tabel untuk buka PilihChapter ---
    tabelManhua.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int row = tabelManhua.getSelectedRow();
            if (row != -1) {
                int manhuaId = Integer.parseInt(tabelManhua.getValueAt(row, 0).toString());
                String judul = tabelManhua.getValueAt(row, 1).toString();
                String coverPath = "";

                Object coverObj = tabelManhua.getValueAt(row, 4); // kolom cover
                if (coverObj instanceof ImageIcon) {
                    coverPath = "covers/default.jpg"; // bisa disesuaikan jika ada coverPath sebenarnya
                }

                new PilihChapter(manhuaId, judul, coverPath).setVisible(true);
            }
        }
    });
}
    
    private void loadManhua() {
    try {
        Connection conn = new koneksi().connect();
        String sql = "SELECT id, judul FROM manhua";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            cbManhua.addItem(rs.getInt("id") + " - " + rs.getString("judul"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal load manhua: " + e.getMessage());
    }
}
public void tampilkanData() {
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public Class<?> getColumnClass(int column) {
            if (column == 4) return ImageIcon.class; // Kolom cover
            return Object.class;
        }
    };

    model.addColumn("ID");
    model.addColumn("Judul");
    model.addColumn("Deskripsi");
    model.addColumn("Genre");
    model.addColumn("Cover");
    model.addColumn("Total Chapter");

    try {
        Connection conn = new koneksi().connect();
        String sql = "SELECT m.id, m.judul, m.deskripsi, m.genre, m.cover, COUNT(c.id) as total_chapter " +
                     "FROM manhua m LEFT JOIN chapter c ON m.id = c.manhua_id " +
                     "GROUP BY m.id";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String coverPath = rs.getString("cover");

            ImageIcon icon;
            if (coverPath != null && !coverPath.isEmpty()) {
                Image image = new ImageIcon(coverPath).getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);
            } else {
                Image image = new ImageIcon("covers/default.jpg").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);
            }

            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("judul"),
                rs.getString("deskripsi"),
                rs.getString("genre"),
                icon,
                rs.getInt("total_chapter")
            });
        }

        tabelManhua.setModel(model);
        tabelManhua.setRowHeight(80); // agar thumbnail muat

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBersihkan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelManhua = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        txtJudul = new javax.swing.JTextField();
        txtDeskripsi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnUbah = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listGenre = new javax.swing.JList<>();
        btnPilihCover = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbManhua = new javax.swing.JComboBox<>();
        txtCoverPath = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Dashboard Admin");

        btnBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        btnBersihkan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBersihkan.setForeground(new java.awt.Color(0, 0, 0));
        btnBersihkan.setText("Bersihkan");
        btnBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihkanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Genre");

        btnKeluar.setBackground(new java.awt.Color(255, 255, 255));
        btnKeluar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnKeluar.setForeground(new java.awt.Color(0, 0, 0));
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(255, 255, 255));
        btnCetak.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(0, 0, 0));
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        tabelManhua.setBackground(new java.awt.Color(255, 255, 255));
        tabelManhua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabelManhua.setForeground(new java.awt.Color(0, 0, 0));
        tabelManhua.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelManhua.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tabelManhua.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tabelManhua);

        btnSimpan.setBackground(new java.awt.Color(255, 255, 255));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(0, 0, 0));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        txtJudul.setBackground(new java.awt.Color(255, 255, 255));
        txtJudul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJudulActionPerformed(evt);
            }
        });

        txtDeskripsi.setBackground(new java.awt.Color(255, 255, 255));
        txtDeskripsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeskripsiActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tambah Manhua");

        btnUbah.setBackground(new java.awt.Color(255, 255, 255));
        btnUbah.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnUbah.setForeground(new java.awt.Color(0, 0, 0));
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Deskripsi");

        btnHapus.setBackground(new java.awt.Color(255, 255, 255));
        btnHapus.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(0, 0, 0));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        listGenre.setBackground(new java.awt.Color(255, 255, 255));
        listGenre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        listGenre.setForeground(new java.awt.Color(0, 0, 0));
        listGenre.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Martial Arts", "Demons", "Reincarnation", "Fantasy", "Comedy", "Magic" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listGenre);

        btnPilihCover.setBackground(new java.awt.Color(255, 255, 255));
        btnPilihCover.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPilihCover.setForeground(new java.awt.Color(0, 0, 0));
        btnPilihCover.setText("Pilih Cover");
        btnPilihCover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihCoverActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(0, 0, 0));
        btnRefresh.setText("Refresh Page");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tambah Chapter Disini");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        cbManhua.setBackground(new java.awt.Color(255, 255, 255));
        cbManhua.setForeground(new java.awt.Color(0, 0, 0));
        cbManhua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbManhuaActionPerformed(evt);
            }
        });

        txtCoverPath.setBackground(new java.awt.Color(255, 255, 255));
        txtCoverPath.setForeground(new java.awt.Color(0, 0, 0));
        txtCoverPath.setText("Cover Path");
        txtCoverPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCoverPathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUbah)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnBersihkan)
                        .addGap(18, 18, 18)
                        .addComponent(btnKeluar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCetak)
                        .addGap(445, 445, 445)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(395, 395, 395)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel5))
                            .addGap(54, 54, 54)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(555, 555, 555)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnPilihCover)
                                .addComponent(jLabel1))
                            .addGap(183, 183, 183)
                            .addComponent(cbManhua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtCoverPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(48, 48, 48)
                            .addComponent(btnRefresh))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(150, 150, 150)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1337, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPilihCover)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbManhua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCoverPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnBersihkan)
                    .addComponent(btnKeluar)
                    .addComponent(btnCetak)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihkanActionPerformed
    int konfirmasi = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus SEMUA data manhua?", 
        "Konfirmasi Hapus Semua", JOptionPane.YES_NO_OPTION);
    
    if (konfirmasi == JOptionPane.YES_OPTION) {
        try {
            String sqlDeleteChapters = "DELETE FROM chapter";
            String sqlDeleteManhua = "DELETE FROM manhua";

            PreparedStatement stmtChapters = conn.prepareStatement(sqlDeleteChapters);
            PreparedStatement stmtManhua = conn.prepareStatement(sqlDeleteManhua);

            stmtChapters.executeUpdate(); // Hapus chapter dulu kalau ada relasi foreign key
            stmtManhua.executeUpdate();

            JOptionPane.showMessageDialog(null, "Semua data berhasil dihapus.");
            tampilkanData(); // refresh tabel
            resetFormInput(); // juga reset form input setelah hapus
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus semua data: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnBersihkanActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        new login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        int row = tabelManhua.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Pilih manhua yang ingin dipublikasikan.");
        return;
    }

    int id = Integer.parseInt(tabelManhua.getValueAt(row, 0).toString());

    String sql = "UPDATE manhua SET status = 'publish' WHERE id = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int updated = stmt.executeUpdate();

        if (updated > 0) {
            JOptionPane.showMessageDialog(null, "Manhua berhasil diperbarui dan ditampilkan di Home & Daftar!");
            tampilkanData(); // refresh tabel admin
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal memperbarui status: " + e.getMessage());
    }

    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String judul = txtJudul.getText().trim();
        String deskripsi = txtDeskripsi.getText().trim();
        List<String> selectedGenres = listGenre.getSelectedValuesList();
        String cover = txtCoverPath.getText();

        if (judul.isEmpty() || deskripsi.isEmpty() || selectedGenres.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mohon lengkapi semua kolom dan pilih setidaknya satu genre!");
            return;
        }

        String genre = String.join(", ", selectedGenres);

        String sql = "INSERT INTO manhua (judul, deskripsi, genre, cover, status) VALUES (?, ?, ?, ?, 'draft')";


        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, judul);
            stmt.setString(2, deskripsi);
            stmt.setString(3, genre);
            stmt.setString(4, cover);

            int inserted = stmt.executeUpdate();
            if (inserted > 0) {
                JOptionPane.showMessageDialog(null, "Manhua berhasil ditambahkan!");
                tampilkanData();
                resetFormInput();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtJudulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJudulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJudulActionPerformed

    private void txtDeskripsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeskripsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeskripsiActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
    int row = tabelManhua.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah!");
        return;
    }

    int id = Integer.parseInt(tabelManhua.getValueAt(row, 0).toString());
    String judul = txtJudul.getText().trim();
    String deskripsi = txtDeskripsi.getText().trim();
    String cover = txtCoverPath.getText().trim();
    String genre = String.join(", ", listGenre.getSelectedValuesList());

    if (judul.isEmpty() || deskripsi.isEmpty() || genre.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Mohon lengkapi semua kolom terlebih dahulu!");
        return;
    }

    String sql = "UPDATE manhua SET judul=?, deskripsi=?, genre=?, cover=? WHERE id=?";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, judul);
        stmt.setString(2, deskripsi);
        stmt.setString(3, genre);
        stmt.setString(4, cover);
        stmt.setInt(5, id);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        tampilkanData();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal mengubah: " + e.getMessage());
    }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int row = tabelManhua.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus!");
        return;
    }
    int id = Integer.parseInt(tabelManhua.getValueAt(row, 0).toString());
    int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus manhua dan semua chapternya?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (konfirmasi != JOptionPane.YES_OPTION) {
        return;
    }
    try {
        // Hapus semua chapter
        String deleteChapterSql = "DELETE FROM chapter WHERE manhua_id=?";
        PreparedStatement pstChapter = conn.prepareStatement(deleteChapterSql);
        pstChapter.setInt(1, id);
        pstChapter.executeUpdate();
        // Hapus manhua
        String deleteManhuaSql = "DELETE FROM manhua WHERE id=?";
        PreparedStatement pstManhua = conn.prepareStatement(deleteManhuaSql);
        pstManhua.setInt(1, id);
        pstManhua.executeUpdate();
        JOptionPane.showMessageDialog(null, "Manhua dan semua chapternya berhasil dihapus!");
        tampilkanData();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal menghapus: " + e.getMessage());
    }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnPilihCoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihCoverActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String fileName = selectedFile.getName();
        String destinationFolder = "covers";
        File folder = new File(destinationFolder);
        if (!folder.exists()) folder.mkdirs(); // Buat folder kalau belum ada

        String destinationPath = destinationFolder + File.separator + fileName;

        try {
            Files.copy(selectedFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            txtCoverPath.setText(destinationPath);
            ImageIcon icon = new ImageIcon(destinationPath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan gambar: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnPilihCoverActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
    this.dispose(); // Menutup form yang sedang aktif
    // Membuka ulang form Admin
    SwingUtilities.invokeLater(() -> {
        new admin().setVisible(true);
    });
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
         new EditChapter().setVisible(true);
         this.dispose(); // Menutup form sekarang (optional)
    }//GEN-LAST:event_jLabel6MouseClicked

    private void cbManhuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbManhuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbManhuaActionPerformed

    private void txtCoverPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCoverPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCoverPathActionPerformed
    
    private void resetFormInput() {
        txtJudul.setText("");
        txtDeskripsi.setText("");
        listGenre.clearSelection();
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBersihkan;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPilihCover;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cbManhua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listGenre;
    private javax.swing.JTable tabelManhua;
    private javax.swing.JTextField txtCoverPath;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtJudul;
    // End of variables declaration//GEN-END:variables
}