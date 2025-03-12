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
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    protected void setMdp(PasswordField mdp) {
        this.mdp = mdp;
    }

    protected void setEmail(TextField email) {
        this.email = email;
    }

    @FXML
    protected void bouttonConnexion(ActionEvent actionEvent){
        System.out.println("Email ="+email.getText());
        System.out.println("Password ="+mdp.getText());
        String login = email.getText();
        String pass = mdp.getText();
        if (login.equals("admin") && pass.equals("admin")) {
            System.out.println("Login OK");
        }else {
            System.out.println("Erreur ! Login ou mot de passe incorrect");
        }
    }

    @FXML
    protected void buttonMDP(ActionEvent actionEvent) {
    this.setMdp(mdp);
    }

    public void btnConnexion(ActionEvent actionEvent) {

    }

    public void btnInscrition(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InscriptionView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) email.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void btnMdpOublie(ActionEvent actionEvent) {
    }
}