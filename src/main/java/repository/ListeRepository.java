package repository;

import model.Liste;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListeRepository {
    private Connection cnx;

    public ListeRepository(Connection cnx) {
        this.cnx = cnx;
    }

    public boolean creerListe(Liste liste) {
        String sql = "INSERT INTO liste (nom, createur_id) VALUES (?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, liste.getNom());
            stmt.setInt(2, liste.getCreateurId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifierListe(Liste liste) {
        String sql = "UPDATE liste SET nom = ? WHERE id_liste = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setString(1, liste.getNom());
            stmt.setInt(2, liste.getIdListe());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerListe(int idListe) {
        String sql = "DELETE FROM liste WHERE id_liste = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setInt(1, idListe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Liste> getListesParUtilisateur(int idUtilisateur) {
        List<Liste> listes = new ArrayList<>();
        String sql = "SELECT l.* FROM liste l JOIN utilisateur_liste ul ON l.id_liste = ul.ref_liste WHERE ul.ref_utilisateur = ? OR l.createur_id = ?";

        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idUtilisateur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listes.add(new Liste(
                        rs.getInt("id_liste"),
                        rs.getString("nom"),
                        rs.getInt("createur_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listes;
    }
}