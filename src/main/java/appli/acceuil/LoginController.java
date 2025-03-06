package appli.acceuil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        this.email.setText("test@gmail.com");
        this.mdp.setText("123");
    }

    @FXML
    protected void buttonMDP(ActionEvent actionEvent) {
    this.setMdp(mdp);
    }
}