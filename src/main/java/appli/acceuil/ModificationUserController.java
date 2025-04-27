package appli.acceuil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Utilisateur;
import repository.UtilisateurRepository;

import java.io.IOException;

public class ModificationUserController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField roleField;
    @FXML private Button btnValider;

    private Utilisateur utilisateurSel;
    private UtilisateurRepository userRepo = new UtilisateurRepository();

    public void initData(Utilisateur utilisateur) {
        this.utilisateurSel = utilisateur;
        nomField.setText(utilisateur.getNom());
        prenomField.setText(utilisateur.getPrenom());
        emailField.setText(utilisateur.getEmail());
        roleField.setText(utilisateur.getRole());
    }

    @FXML
    void validerModification() {
        utilisateurSel.setNom(nomField.getText());
        utilisateurSel.setPrenom(prenomField.getText());
        utilisateurSel.setRole(roleField.getText());

        if (userRepo.updateUtilisateur(utilisateurSel)) {
            try {
                StartApplication.changeScene("accueil/Acceuil");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Échec de la modification");
            alert.setContentText("Une erreur est survenue lors de la modification de l'utilisateur.");
            alert.showAndWait();
        }
    }
    @FXML
    protected void btnRetour(ActionEvent actionEvent) throws IOException {
        StartApplication.changeScene("accueil/Acceuil");
    }
}