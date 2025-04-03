package appli.acceuil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Utilisateur;
import repository.UtilisateurRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GestionUserController implements Initializable {

    @FXML
    private TableView<Utilisateur> tableauUser;
    @FXML
    private Button btnSupprimer;

    private UtilisateurRepository userRepo = new UtilisateurRepository();
    private ObservableList<Utilisateur> usersData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configuration des colonnes
        String[][] colonnes = {
                {"Id Utilisateur", "idUser"},
                {"Nom", "nom"},
                {"Prénom", "prenom"},
                {"Email", "email"},
                {"Rôle", "role"}
        };

        for (String[] col : colonnes) {
            TableColumn<Utilisateur, String> column = new TableColumn<>(col[0]);
            column.setCellValueFactory(new PropertyValueFactory<>(col[1]));
            tableauUser.getColumns().add(column);
        }
    }

    private void chargerUtilisateurs() {
        usersData.clear();
        usersData.addAll(userRepo.getTousLesUtilisateurs());
        tableauUser.setItems(usersData);
    }

    @FXML
    void cliqueTableauEvent(MouseEvent event) throws IOException {
        Utilisateur selection = tableauUser.getSelectionModel().getSelectedItem();
        btnSupprimer.setDisable(selection == null);

        // Gestion du double-clic
        if (event.getClickCount() == 2 && selection != null) {
            try {
                StartApplication.changeScene("user/modificationUser");
                ModificationUserController controller =
                        (ModificationUserController) StartApplication.getControllerFromStage();
                controller.initData(selection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void supprimerUtilisateur() {
        Utilisateur selection = tableauUser.getSelectionModel().getSelectedItem();
        if (selection != null && userRepo.supprimerByEmail(selection.getEmail())) {
            usersData.remove(selection);
            btnSupprimer.setDisable(true);
        }
    }
}