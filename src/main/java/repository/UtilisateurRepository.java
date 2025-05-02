

    package repository;

    import model.Utilisateur;
    import database.Database;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;

    public class UtilisateurRepository {


        private Connection cnx;

        public UtilisateurRepository() {
            this.cnx = Database.getConnexion();
        }

        public void ajouterUtilisateur(Utilisateur utilisateur) {
            String sql = "INSERT INTO utilisateur (id_utilisateur,nom, prenom, email, mot_de_passe, role) VALUES (null,?, ?, ?, ?, ?)";
            try {
                PreparedStatement stmt = this.cnx.prepareStatement(sql);
                stmt.setString(1, utilisateur.getNom());
                stmt.setString(2, utilisateur.getPrenom());
                stmt.setString(3, utilisateur.getEmail());
                stmt.setString(4, utilisateur.getMdp());
                stmt.setString(5, utilisateur.getRole());
                stmt.executeUpdate();
                System.out.println("Utilisateur ajouté avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            }
        }

        public Utilisateur getUtilisateurByEmail(String email) {
            String sql = "SELECT * FROM utilisateur WHERE email = ?";
            try {
                PreparedStatement stmt = this.cnx.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Utilisateur user = new Utilisateur(
                            rs.getInt("id_utilisateur"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("role")
                    );
                    return user;
                } else {
                    System.out.println("L'email ne correspond à aucun utilisateur.");
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("l'Email ne correspond à aucun utilisateur");
                ;
            }
            return null;
        }

        public Utilisateur ConnectionUser(String email, String mdp) {
            String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
            try {
                PreparedStatement stmt = this.cnx.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, mdp);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Utilisateur user = new Utilisateur(
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("role")
                    );
                    return user;
                } else {
                    System.out.println("Email ou mot de passe incorect");
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la requete dans la connexion");
            }
            return null;
        }
        public ArrayList<Utilisateur> getTousLesUtilisateurs() {
            ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
            String sql = "SELECT * FROM utilisateur";

            try (PreparedStatement stmt = this.cnx.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Utilisateur user = new Utilisateur(
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("role")
                    );
                    utilisateurs.add(user);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            }

            return utilisateurs;
        }

        public boolean supprimerByEmail(String email) {
            String sql = "DELETE FROM utilisateur WHERE email = ?";
            try (PreparedStatement stmt = this.cnx.prepareStatement(sql)) {
                stmt.setString(1, email);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression : " + e.getMessage());
                return false;
            }
        }
        public boolean updateUtilisateur(Utilisateur utilisateur) {

            String sql = " UPDATE utilisateur SET nom = ?, prenom = ?, mdp = ?, role = ? WHERE email = ?";
            try {
                PreparedStatement stmt = this.cnx.prepareStatement(sql);
                stmt.setString(1, utilisateur.getNom());
                stmt.setString(2, utilisateur.getPrenom());
                stmt.setString(3, utilisateur.getMdp());
                stmt.setString(4, utilisateur.getRole());
                stmt.executeUpdate();
                System.out.println("Utilisateur ajouté avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            }
            return true;
        }
        public boolean updateMotDePasse(String email, String nouveauMdp) {
            String sql = "UPDATE utilisateur SET mot_de_passe = ? WHERE email = ?";
            try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
                stmt.setString(1, nouveauMdp);
                stmt.setString(2, email);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Erreur lors de la mise à jour du mot de passe: " + e.getMessage());
                return false;
            }
        }
    }