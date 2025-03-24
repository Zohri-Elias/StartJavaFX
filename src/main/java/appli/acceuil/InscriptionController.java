package appli.acceuil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Utilisateur;
import repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.io.IOException;


public class InscriptionController {

    @FXML
    private TextField confirmation;

    @FXML
    private TextField email;

    @FXML
    private TextField mdp;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private Label erreurLabel;
    private UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    @FXML
    protected void btnInscription(ActionEvent actionEvent) {
        erreurLabel.setVisible(false);
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String emailText = email.getText();
        String mdpText = mdp.getText();
        String confirmationText = confirmation.getText();

        System.out.println("Nom : " + nomText);
        System.out.println("Prénom : " + prenomText);
        System.out.println("Email : " + emailText);
        System.out.println("Mot de passe : " + mdpText);
        System.out.println("Confirmation : " + confirmationText);

        if (nomText.isEmpty() || prenomText.isEmpty() || emailText.isEmpty() || mdpText.isEmpty() || confirmationText.isEmpty()) {
            erreurLabel.setVisible(true);
            erreurLabel.setText("Erreur : Tous les champs doivent être remplis");
        } else if (!mdpText.equals(confirmationText)) {
            erreurLabel.setVisible(true);
            erreurLabel.setText("Erreur : Les mots de passe ne correspondent pas");
        } else {
            String role = "client";
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            Utilisateur utilisateur =  new Utilisateur(nomText, prenomText, emailText, mdpText , role);
            utilisateurRepository.ajouterUtilisateur(utilisateur);
            erreurLabel.setText("Inscription réussie !");
        }
        Utilisateur utilisateurExistant = utilisateurRepository.getUtilisateurByEmail(emailText);
        if (utilisateurExistant != null) {
            erreurLabel.setVisible(true);
            erreurLabel.setText("Erreur : Un utilisateur existe déjà avec cet email");
            return;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String mdpHash = passwordEncoder.encode(mdpText);

        String role = "client";
        Utilisateur utilisateur = new Utilisateur(nomText, prenomText, emailText, mdpText, role);

        utilisateurRepository.ajouterUtilisateur(utilisateur);

        erreurLabel.setVisible(true);
        erreurLabel.setText("Inscription réussie !");
    }

    @FXML
    protected void btnRetour(ActionEvent actionEvent) throws IOException {
        System.out.println("Redirection vers L'inscription");
        StartApplication.changeScene("accueil/Login");
    }
}