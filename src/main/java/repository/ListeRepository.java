package repository;

import database.Database;
import model.Liste;
import session.SessionUtilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListeRepository {
    private Connection cnx;

    public ListeRepository() {
        this.cnx = Database.getConnexion();
    }

    public boolean creerListe(Liste liste) {
        String sql = "INSERT INTO liste (nom, createur_id) VALUES (?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, liste.getNom());
            int id = liste.getCreateurId();
            System.out.println("Tentative de création avec createur_id = " + id);
            stmt.setInt(2, id);


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenere = generatedKeys.getInt(1);
                        liste.setIdListe(idGenere);
                        System.out.println("ID généré = " + idGenere);
                    }
                }
                return true;
            } else {
                return false;
            }
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
    public List<Liste> getListesParUtilisateurConnecte() {
        int id = SessionUtilisateur.getInstance().getUtilisateur().getId();
        System.out.println("ID utilisateur connecté = " + id);
        List<Liste> listes = this.getListesParUtilisateur(id);
        System.out.println("Nombre de listes récupérées : " + listes.size());
        return listes;
    }

    public List<Liste> getListesParUtilisateur(int idUtilisateur) {
        List<Liste> listes = new ArrayList<>();
        String sql = "SELECT l.* FROM liste l LEFT JOIN utilisateur_liste ul ON l.id_liste = ul.ref_liste WHERE ul.ref_utilisateur = ? OR l.createur_id = ?";

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