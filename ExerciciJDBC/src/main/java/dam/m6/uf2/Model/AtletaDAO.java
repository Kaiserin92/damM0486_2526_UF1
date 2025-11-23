package dam.m6.uf2.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO implements DAO<Atleta> {

    private Connection conn;

    public AtletaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Atleta> getAll() {
        return null;

    }

    @Override
    public void add(Atleta item) {
        String sql = "INSERT INTO deportistas(nombre, cod_deporte) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getNom());
            ps.setInt(2, item.codEsport);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Atleta afegit correctament: " + item.nom);
            }

        } catch (SQLException e) {
            System.out.println("Error afegint atleta: " + e.getMessage());
        }
    }

    public List<Atleta> getBySport(int codSport) {
        List<Atleta> atletes = new ArrayList<>();
        String sql = "SELECT * FROM list_deportistas_sport(?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codSport);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int cod = rs.getInt("cod");
                    String nom = rs.getString("nombre");
                    atletes.add(new Atleta(cod, nom, codSport));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error llistant atletes: " + e.getMessage());
        }
        return atletes;
    }

    public List<Atleta> findByName(String name) {
    List<Atleta> atletes = new ArrayList<>();

    String sql = "SELECT d.cod, d.nombre AS nom, dp.nombre AS esport " +
                 "FROM deportistas d " +
                 "LEFT JOIN deportes dp ON d.cod_deporte = dp.cod " +
                 "WHERE d.nombre ILIKE ? " +
                 "ORDER BY d.cod";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + name + "%"); 

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int cod = rs.getInt("cod");
                String nom = rs.getString("nom");
                String esportNom = rs.getString("esport");

                atletes.add(new Atleta(cod, nom, esportNom));
            }
        }

    } catch (SQLException e) {
        System.out.println("Error cercant atletes: " + e.getMessage());
    }

    return atletes;
}


}