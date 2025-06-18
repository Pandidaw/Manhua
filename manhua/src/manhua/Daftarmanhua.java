package manhua;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import koneksi.koneksi;

public class Daftarmanhua extends javax.swing.JFrame {
private java.util.List<Component> komponenTetap = new ArrayList<>();

    /**
     * Creates new form Daftarmanhua
     */
    public Daftarmanhua() {
        initComponents();
        jLabel5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        komponenTetap.add(jLabel1);   // label genre
        komponenTetap.add(listGenre); // JList genre
        komponenTetap.add(btnFilter); // Tombol filter
        listGenre.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JCheckBox cbAction = new JCheckBox("Action");
        JCheckBox cbRomance = new JCheckBox("Romance");
        JCheckBox cbComedy = new JCheckBox("Comedy");
        // Tambahkan genre lain sesuai kebutuhan

        JPanel genrePanel = new JPanel();
        genrePanel.add(cbAction);
        genrePanel.add(cbRomance);
        genrePanel.add(cbComedy);
        // Tambahkan semua checkbox genre ke panel

        add(genrePanel, BorderLayout.NORTH); // Atur penempatan

        loadManhua();
    }
    
public void loadManhua() {
    panelManhua.removeAll(); // hanya hapus isi kontennya (bukan jPanel1)

    List<String> genreDipilih = listGenre.getSelectedValuesList();

    try {
        Connection conn = new koneksi().connect();
        String sql = "SELECT * FROM manhua WHERE status = 'publish'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int x = 20, y = 20;

        while (rs.next()) {
            String genre = rs.getString("genre");
            boolean tampil = false;

            if (genreDipilih.isEmpty()) {
                tampil = true;
            } else {
                for (String g : genreDipilih) {
                    if (genre.toLowerCase().contains(g.toLowerCase())) {
                        tampil = true;
                        break;
                    }
                }
            }

            if (!tampil) continue;

            String judul = rs.getString("judul");
            String cover = rs.getString("cover");
            int idManhua = rs.getInt("id");

            JLabel labelCover;
            File file = new File(cover);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(150, 210, Image.SCALE_SMOOTH);
                labelCover = new JLabel(new ImageIcon(img));
            } else {
                ImageIcon placeholder = new ImageIcon("icon/placeholder.png");
                Image img = placeholder.getImage().getScaledInstance(150, 210, Image.SCALE_SMOOTH);
                labelCover = new JLabel(new ImageIcon(img));
            }

            labelCover.setBounds(x, y, 150, 210);
            labelCover.setCursor(new Cursor(Cursor.HAND_CURSOR));
            labelCover.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    new PilihChapter(idManhua, judul, cover).setVisible(true);
                }
            });

            JLabel labelJudul = new JLabel(judul);
            labelJudul.setBounds(x, y + 210, 150, 20);

            panelManhua.add(labelCover);
            panelManhua.add(labelJudul);

            x += 180;
            if (x > 600) {
                x = 20;
                y += 260;
            }
        }

        panelManhua.revalidate();
        panelManhua.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan komik: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupGenre = new javax.swing.JPopupMenu();
        genreMartialArts = new javax.swing.JCheckBoxMenuItem();
        genreDemons = new javax.swing.JCheckBoxMenuItem();
        genreReincarnation = new javax.swing.JCheckBoxMenuItem();
        genreFantasy = new javax.swing.JCheckBoxMenuItem();
        genreComedy = new javax.swing.JCheckBoxMenuItem();
        genreMagic = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bntFilter = new javax.swing.JSeparator();
        btnFilter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listGenre = new javax.swing.JList<>();
        panelManhua = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        genreMartialArts.setSelected(true);
        genreMartialArts.setText("Martial Arts");
        genreMartialArts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genreMartialArtsActionPerformed(evt);
            }
        });
        popupGenre.add(genreMartialArts);

        genreDemons.setSelected(true);
        genreDemons.setText("Demons");
        popupGenre.add(genreDemons);

        genreReincarnation.setSelected(true);
        genreReincarnation.setText("Reincarnaton");
        popupGenre.add(genreReincarnation);

        genreFantasy.setSelected(true);
        genreFantasy.setText("Fantasy");
        popupGenre.add(genreFantasy);

        genreComedy.setSelected(true);
        genreComedy.setText("Comedy");
        popupGenre.add(genreComedy);

        genreMagic.setSelected(true);
        genreMagic.setText("Magic");
        popupGenre.add(genreMagic);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Home");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Search by Genre");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel5)
                .addGap(38, 38, 38)
                .addComponent(jLabel4)
                .addContainerGap(1739, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 50, 1970, 40);

        jLabel7.setFont(new java.awt.Font("STHupo", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Site");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(200, 10, 38, 30);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("STHupo", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Manhuaku");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(80, 10, 120, 30);

        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\Z y r n\\Documents\\NetBeansProjects\\manhua\\icon\\logo_resized_50x50.png")); // NOI18N
        jLabel9.setText("jLabel9");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 10, 50, 30);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("STHupo", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Daftar Manhua");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(50, 90, 130, 30);

        bntFilter.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.add(bntFilter);
        bntFilter.setBounds(40, 120, 1800, 20);

        btnFilter.setBackground(new java.awt.Color(204, 204, 204));
        btnFilter.setForeground(new java.awt.Color(0, 0, 0));
        btnFilter.setText("Filter Genre");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jPanel1.add(btnFilter);
        btnFilter.setBounds(1670, 130, 130, 20);

        listGenre.setBackground(new java.awt.Color(204, 204, 204));
        listGenre.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        listGenre.setForeground(new java.awt.Color(0, 0, 0));
        listGenre.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Martial Arts", "Demons", "Reincarnation", "Fantasy", "Comedy", "Magic" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listGenre);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(1670, 160, 140, 160);

        panelManhua.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelManhuaLayout = new javax.swing.GroupLayout(panelManhua);
        panelManhua.setLayout(panelManhuaLayout);
        panelManhuaLayout.setHorizontalGroup(
            panelManhuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1640, Short.MAX_VALUE)
        );
        panelManhuaLayout.setVerticalGroup(
            panelManhuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
        );

        jPanel1.add(panelManhua);
        panelManhua.setBounds(20, 120, 1640, 1060);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(1770, 90, 100, 27);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1924, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void genreMartialArtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genreMartialArtsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genreMartialArtsActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        loadManhua();       
    }//GEN-LAST:event_btnFilterActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
             new Home().setVisible(true);
             this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
             new Daftarmanhua().setVisible(true);
             this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Daftarmanhua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Daftarmanhua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Daftarmanhua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Daftarmanhua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Daftarmanhua().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator bntFilter;
    private javax.swing.JButton btnFilter;
    private javax.swing.JCheckBoxMenuItem genreComedy;
    private javax.swing.JCheckBoxMenuItem genreDemons;
    private javax.swing.JCheckBoxMenuItem genreFantasy;
    private javax.swing.JCheckBoxMenuItem genreMagic;
    private javax.swing.JCheckBoxMenuItem genreMartialArts;
    private javax.swing.JCheckBoxMenuItem genreReincarnation;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listGenre;
    private javax.swing.JPanel panelManhua;
    private javax.swing.JPopupMenu popupGenre;
    // End of variables declaration//GEN-END:variables
}