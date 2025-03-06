package appli.acceuil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController {
    private Label text2;
    @FXML
    private PasswordField mdp;
    @FXML
    private Label text1;
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    protected void setText1(String text) {
        text1.setText(text);
    }
    protected void setMdp(PasswordField mdp) {
        this.mdp = mdp;
    }
    protected void text2(String text) {
        text2.setText(text);
    }
}