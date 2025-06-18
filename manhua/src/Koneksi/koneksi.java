package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {
    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/javamanhua"; // Ganti sesuai nama DB kamu
            String user = "root"; // sesuaikan
            String pass = "";     // sesuaikan
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi ke database berhasil!");
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}
