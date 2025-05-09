    package repository;

    import model.Tache;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class TacheRepository {
        private Connection cnx;

        public TacheRepository(Connection cnx) {
            this.cnx = cnx;
        }

        public boolean ajouterTache(Tache tache) {
            String sql = "INSERT INTO tache (nom, etat, ref_liste, ref_type) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
                stmt.setString(1, tache.getNom());
                stmt.setInt(2, tache.getEtat());
                stmt.setInt(3, tache.getListeId());
                stmt.setInt(4, tache.getTypeId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Échec de l'insertion de la tâche, aucune ligne affectée.");
                }

                // Récupérer l'ID généré automatiquement
                try (Statement stmtId = cnx.createStatement();
                     ResultSet rs = stmtId.executeQuery("SELECT LAST_INSERT_ID()")) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        tache.setId(generatedId); // Met à jour l'objet Tache avec l'ID généré
                    } else {
                        throw new SQLException("Échec de la récupération de l'ID généré.");
                    }
                }

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


        public List<Tache> getTachesParListe(int listeId) {
            List<Tache> taches = new ArrayList<>();
            String sql = "SELECT * FROM tache WHERE ref_liste = ?";
            try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
                stmt.setInt(1, listeId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    taches.add(new Tache(
                            rs.getInt("id_tache"),
                            rs.getString("nom"),
                            rs.getInt("etat"),
                            rs.getInt("ref_liste"),
                            rs.getInt("ref_type")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return taches;
        }

        public boolean modifierTache(Tache tache) {
            String sql = "UPDATE tache SET nom = ?, etat = ?, ref_type = ? WHERE id_tache = ?";
            try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
                stmt.setString(1, tache.getNom());
                stmt.setInt(2, tache.getEtat());
                stmt.setInt(3, tache.getTypeId());
                stmt.setInt(4, tache.getId());
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean supprimerTache(int idTache) {
            String sql = "DELETE FROM tache WHERE id_tache = ?";
            try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
                stmt.setInt(1, idTache);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }