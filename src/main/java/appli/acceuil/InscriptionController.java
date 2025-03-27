package appli.acceuil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Utilisateur;
import repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

public class InscriptionController {

    @FXML private TextField confirmation;
    @FXML private TextField email;
    @FXML private TextField mdp;
    @FXML private TextField nom;
    @FXML private TextField prenom;
    @FXML private Label erreurLabel;

    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    protected void btnInscription(ActionEvent actionEvent) {
        erreurLabel.setVisible(false);

        String nomText = nom.getText().trim();
        String prenomText = prenom.getText().trim();
        String emailText = email.getText().trim();
        String mdpText = mdp.getText();
        String confirmationText = confirmation.getText();

        if (nomText.isEmpty() || prenomText.isEmpty() || emailText.isEmpty() || mdpText.isEmpty() || confirmationText.isEmpty()) {
            showError("Erreur : Tous les champs doivent être remplis");
            return;
        }

        if (!mdpText.equals(confirmationText)) {
            showError("Erreur : Les mots de passe ne correspondent pas");
            return;
        }

        if (utilisateurRepository.getUtilisateurByEmail(emailText) != null) {
            showError("Erreur : Un utilisateur existe déjà avec cet email");
            return;
        }

        try {
            String mdpHash = passwordEncoder.encode(mdpText);

            String role = "client";
            Utilisateur utilisateur = new Utilisateur(nomText, prenomText, emailText, mdpHash, role);
            utilisateurRepository.ajouterUtilisateur(utilisateur);

            erreurLabel.setVisible(true);
            erreurLabel.setText("Inscription réussie !");

        } catch (Exception e) {
            showError("Erreur lors de l'inscription : " + e.getMessage());
        }
    }

    @FXML
    protected void btnRetour(ActionEvent actionEvent) throws IOException {
        StartApplication.changeScene("accueil/Login");
    }

    private void showError(String message) {
        erreurLabel.setVisible(true);
        erreurLabel.setText(message);
    }
}