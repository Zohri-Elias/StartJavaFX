package appli.acceuil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Utilisateur;
import repository.UtilisateurRepository;
import service.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MotDePasseOublieController {
    @FXML private TextField emailField;
    @FXML private TextField codeField;
    @FXML private PasswordField nouveauMdpField;
    @FXML private PasswordField confirmationMdpField;
    @FXML private Label messageLabel;

    private String codeVerification;
    private final UtilisateurRepository userRepo = new UtilisateurRepository();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    private void envoyerCode() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            showError("Veuillez entrer une adresse e-mail");
            return;
        }

        // Vérifie si l'utilisateur existe
        Utilisateur utilisateur = userRepo.getUtilisateurByEmail(email);
        if (utilisateur == null) {
            showError("Aucun compte associé à cette adresse email");
            return;
        }

        // Génère et envoie le code
        codeVerification = EmailService.genererCode();
        EmailService.envoyerEmail(
                email,
                "Réinitialisation de mot de passe",
                "Votre code de réinitialisation est : " + codeVerification
        );

        showSuccess("Code envoyé à " + email);
    }

    @FXML
    private void verifierCode() {
        // Vérification du code
        if (!codeField.getText().equals(codeVerification)) {
            showError("Code incorrect");
            return;
        }

        String email = emailField.getText().trim();
        String nouveauMdp = nouveauMdpField.getText();
        String confirmation = confirmationMdpField.getText();

        // Validation des champs
        if (nouveauMdp.isEmpty() || confirmation.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        if (!nouveauMdp.equals(confirmation)) {
            showError("Les mots de passe ne correspondent pas");
            return;
        }

        // Hash du nouveau mot de passe
        String mdpHash = passwordEncoder.encode(nouveauMdp);

        // Mise à jour directe dans la base de données
        if (userRepo.updateMotDePasse(email, mdpHash)) {
            showSuccess("Mot de passe modifié avec succès");
            // Ici vous pourriez rediriger vers la page de connexion
        } else {
            showError("Erreur lors de la modification du mot de passe");
        }
    }

    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setText(message);
    }

    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
    }
}