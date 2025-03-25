package appli.acceuil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionJDBC {
    private String serveur = “localhost:3306”; // 3307 pour mariaDB
    private String base = “nomDeLaBase”;
    private String utilisateur = “root”;
    private String mdp = “motDePasse”;
    public static void main(String[] args) throws SQLException {
        Connection maConnection = DriverManager.getConnection(
                "jdbc:mysql://”+ serveur +”/"+base, utilisateur, mdp);
        System.out.println("Connexion réussie !");
    }