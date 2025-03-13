package appli.acceuil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    protected void btnInscription(ActionEvent actionEvent) {
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
            erreurLabel.setText("Erreur : Tous les champs doivent être remplis");
        } else if (!mdpText.equals(confirmationText)) {
            erreurLabel.setText("Erreur : Les mots de passe ne correspondent pas");
        } else if (emailText.equals("admin@example.com")) {
            erreurLabel.setText("Erreur : Cet email est déjà utilisé");
        } else {
            erreurLabel.setText("Inscription réussie !");
        }
    }

    @FXML
    protected void btnRetour(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}