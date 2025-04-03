package appli.acceuil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Utilisateur;
import repository.UtilisateurRepository;
import appli.StartApplication;

public class ModificationUserController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField roleField;

    private Utilisateur utilisateurSel;
    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    public void initData(Utilisateur utilisateur) {
        this.utilisateurSel = utilisateur;
        nomField.setText(utilisateur.getNom());
        prenomField.setText(utilisateur.getPrenom());
        emailField.setText(utilisateur.getEmail());
        roleField.setText(utilisateur.getRole());
    }

    @FXML
    private void validerModification() {
        utilisateurSel.setNom(nomField.getText());
        utilisateurSel.setPrenom(prenomField.getText());
        utilisateurSel.setEmail(emailField.getText());
        utilisateurSel.setRole(roleField.getText());

        if (utilisateurRepository.Update(utilisateurSel)) {
            try {
                StartApplication.changeScene("user/GestionUser");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void annulerModification() {
        try {
            StartApplication.changeScene("user/GestionUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}