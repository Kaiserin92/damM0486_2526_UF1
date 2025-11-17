package dam.m6.uf2.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EsportDAO implements DAO<Esport> {
    private Connection conn;

    public EsportDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Esport> getAll() {
        List<Esport> esports = new ArrayList<>();

        String sql = "SELECT * FROM list_dep()";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nom = rs.getString("nom");
                esports.add(new Esport(cod, nom));
            }

        } catch (SQLException e) {
            System.out.println("Error llistant esports: " + e.getMessage());
        }

        return esports;
    }

    public void add(Esport item) {
        String sql = "INSERT INTO deportes(nombre) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getNom());
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Esport afegit correctament: " + item.getNom());

            }

        } catch (SQLException e) {
            System.out.println("Error afegint esport: " + e.getMessage());
        }

    }

}
