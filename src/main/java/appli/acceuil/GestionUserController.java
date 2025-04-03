package appli.acceuil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Utilisateur;
import repository.UtilisateurRepository;
import appli.StartApplication;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionUserController implements javafx.fxml.Initializable {
    @FXML private TableView<Utilisateur> tableauUser;
    @FXML private Button btnSupprimer;

    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    private final ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        String [][] colonnes = {
                { "Id Utilisateur","idUser" },
                { "Nom","nom" },
                { "Prénom","prenom" },
                { "Email","mail" },
                { "Rôle","role" },
        };

        for ( int i = 0 ; i < colonnes.length ; i ++ ){
            TableColumn<Utilisateur,String> maCol = new TableColumn<>(colonnes[i][0]);
            maCol.setCellValueFactory(
                    new PropertyValueFactory<Utilisateur,String>(colonnes[i][1]));
            tableauUser.getColumns().add(maCol);
        }
    }


    private void chargerUtilisateurs() {
        utilisateurs.setAll(utilisateurRepository.getTousLesUtilisateurs());
        tableauUser.setItems(utilisateurs);
    }

    @FXML
    private void cliqueTableauEvent(MouseEvent event) {
        Utilisateur selection = tableauUser.getSelectionModel().getSelectedItem();
        btnSupprimer.setDisable(selection == null);

        // Gestion du double-clic pour modification
        if (event.getClickCount() == 2 && selection != null) {
            try {
                StartApplication.changeScene("user/modificationUser");
                ModificationUserController controller =
                        (ModificationUserController) StartApplication.getControllerFromStage();
                controller.initData(selection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void supprimerUtilisateur() {
        Utilisateur selection = tableauUser.getSelectionModel().getSelectedItem();
        if (selection != null && utilisateurRepository.supprimerUtilisateur(selection.getId())) {
            utilisateurs.remove(selection);
            btnSupprimer.setDisable(true);
        } else {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression").show();
        }
    }
}