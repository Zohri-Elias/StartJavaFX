package appli.acceuil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.UtilisateurRepository;
import model.Utilisateur;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField mdp;
    @FXML
    private Label erreurLabel;
    private UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    @FXML
    protected void btnConnexion(ActionEvent actionEvent) {
        String login = email.getText();
        String pass = mdp.getText();

        if (login.equals("admin") && pass.equals("admin")) {
            System.out.println("Login OK");
            erreurLabel.setVisible(false);
        } else {
            erreurLabel.setVisible(true);
            erreurLabel.setText("Erreur : Login ou mot de passe incorrect");
        }
    }

    @FXML
    protected void btnInscrition(ActionEvent actionEvent) throws IOException {
        System.out.println("Redirection vers L'inscription");
        StartApplication.changeScene("accueil/Inscription");
    }

    @FXML
    protected void btnMdpOublie(ActionEvent actionEvent) {

        System.out.println("Mot de passe oublié cliqué");
    }
}