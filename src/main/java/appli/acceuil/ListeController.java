package appli.acceuil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Liste;
import repository.ListeRepository;
import session.SessionUtilisateur;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class ListeController implements Initializable {
    @FXML private ListView<Liste> listViewListes;
    @FXML private TextField tfNomListe;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

    private VBox view;
    private ListeRepository listeRepo ;
    private int idUtilisateurCourant;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idUtilisateurCourant = SessionUtilisateur.getInstance().getUtilisateur().getId();
        System.out.println("ID utilisateur courant (depuis SessionUtilisateur) = " + idUtilisateurCourant);
        this.listeRepo = new ListeRepository();
        chargerListes();
        configurerSelection();
    }

    private void chargerListes() {
        List<Liste> listes = listeRepo.getListesParUtilisateurConnecte();
        listViewListes.getItems().setAll(listes);
    }

    private void configurerSelection() {
        listViewListes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean listeSelectionnee = newVal != null;
            btnModifier.setDisable(!listeSelectionnee);
            btnSupprimer.setDisable(!listeSelectionnee);

            if (listeSelectionnee) {
                tfNomListe.setText(newVal.getNom());
            }
        });
    }

    @FXML
    private void creerListe() {
        String nom = tfNomListe.getText().trim();
        if (!nom.isEmpty()) {
            Liste nouvelleListe = new Liste(0, nom, idUtilisateurCourant);
            if (listeRepo.creerListe(nouvelleListe)) {
                chargerListes();
                tfNomListe.clear();
            }
        }
    }

    @FXML
    private void modifierListe() {
        Liste listeSelectionnee = listViewListes.getSelectionModel().getSelectedItem();
        if (listeSelectionnee != null) {
            String nouveauNom = tfNomListe.getText().trim();
            if (!nouveauNom.isEmpty()) {
                listeSelectionnee.setNom(nouveauNom);
                if (listeRepo.modifierListe(listeSelectionnee)) {
                    chargerListes();
                }
            }
        }
    }

    @FXML
    private void supprimerListe() {
        Liste listeSelectionnee = listViewListes.getSelectionModel().getSelectedItem();
        if (listeSelectionnee != null) {
            // Confirmation avant suppression
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer la liste");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer '" + listeSelectionnee.getNom() + "' ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                if (listeRepo.supprimerListe(listeSelectionnee.getIdListe())) {
                    chargerListes();
                    tfNomListe.clear();
                }
            }
        }
    }

    public VBox getView() { return view; }
    public List<Liste> getListes() { return listViewListes.getItems(); }
}