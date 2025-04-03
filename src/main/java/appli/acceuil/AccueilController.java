package appli.acceuil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import session.SessionUtilisateur;
import model.Utilisateur;

import java.io.IOException;


public class AccueilController {

    public void initialize() {
        Utilisateur utilisateurActuel = SessionUtilisateur.getInstance().getUtilisateur();

        if (utilisateurActuel != null) {
            System.out.println("Utilisateur connecté: " + utilisateurActuel.getNom());
        } else {
            System.out.println("Aucun utilisateur connecté");
        }
    }

    @FXML
    protected void handleLogout(ActionEvent actionEvent) throws IOException {
        SessionUtilisateur.getInstance().deconnecter();
        System.out.println("Utilisateur déconnecté");
        StartApplication.changeScene("accueil/Inscription");
    }
}