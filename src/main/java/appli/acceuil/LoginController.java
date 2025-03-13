package appli.acceuil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField mdp;
    @FXML
    private Label erreurLabel;

    @FXML
    protected void btnConnexion(ActionEvent actionEvent) {
        String login = email.getText();
        String pass = mdp.getText();

        if (login.equals("admin") && pass.equals("admin")) {
            System.out.println("Login OK");
            erreurLabel.setText("");
        } else {
            System.out.println("Erreur ! Login ou mot de passe incorrect");
            erreurLabel.setText("Erreur : Login ou mot de passe incorrect");
        }
    }

    @FXML
    protected void btnInscrition(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InscriptionView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) email.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void btnMdpOublie(ActionEvent actionEvent) {

        System.out.println("Mot de passe oublié cliqué");
    }
}