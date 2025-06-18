package manhua;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BacaManhuaPDF extends JFrame {

    public BacaManhuaPDF(String pdfFilePath) {
        setTitle("Baca Manhua - PDF Viewer");
        setSize(800, 1000);
        setLocationRelativeTo(null); // Tampilkan di tengah layar
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel utama dengan layout vertikal
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.BLACK); // Optional: warna latar belakang

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // agar scroll lebih halus
        add(scrollPane, BorderLayout.CENTER);

        try {
            File file = new File(pdfFilePath);
            PDDocument document = Loader.loadPDF(file);
            PDFRenderer renderer = new PDFRenderer(document);
            int totalPages = document.getNumberOfPages();

            for (int i = 0; i < totalPages; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 150);
                ImageIcon icon = new ImageIcon(image);

                // Panel untuk setiap halaman, center-aligned
                JPanel pagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                pagePanel.setBackground(Color.BLACK);
                JLabel imgLabel = new JLabel(icon);
                pagePanel.add(imgLabel);

                contentPanel.add(pagePanel);
            }

            document.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BacaManhuaPDF("pdf/eleceed_ch1.pdf").setVisible(true);
        });
    }
}