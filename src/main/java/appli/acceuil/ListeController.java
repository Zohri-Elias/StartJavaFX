package appli.acceuil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Liste;
import repository.ListeRepository;
import java.sql.Connection;
import java.util.List;

public class ListeController {
    @FXML private ListView<Liste> listViewListes;
    @FXML private TextField tfNomListe;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

    private VBox view;
    private ListeRepository listeRepo;
    private Connection cnx;
    private int idUtilisateurCourant;

    public void initialize(int idUtilisateur, Connection cnx) {
        this.idUtilisateurCourant = idUtilisateur;
        this.cnx = cnx;
        this.listeRepo = new ListeRepository(cnx);

        chargerListes();
        configurerSelection();
    }

    private void chargerListes() {
        List<Liste> listes = listeRepo.getListesParUtilisateur(idUtilisateurCourant);
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