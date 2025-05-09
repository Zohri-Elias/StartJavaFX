package appli.acceuil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Tache;
import repository.TacheRepository;
import repository.TypeRepository;
import appli.StartApplication;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class TacheController implements Initializable {
    @FXML private TableView<Tache> tacheTable;
    @FXML private TextField tfNomTache;
    @FXML private ComboBox<String> cbType;
    @FXML private ComboBox<String> cbEtat;
    @FXML private TextField tfFiltre;

    private TacheRepository tacheRepo;
    private TypeRepository typeRepo;
    private ObservableList<Tache> toutesLesTaches;
    private FilteredList<Tache> tachesFiltrees;
    private int listeCouranteId;
    private Connection cnx;

    // Données à injecter AVANT le changement de scène
    private static int staticListeId;
    private static Connection staticConnexion;

    public static void setInitParams(int listeId, Connection connection) {
        staticListeId = listeId;
        staticConnexion = connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialiserFiltres();

        // Injection des données statiques
        if (staticConnexion != null) {
            this.listeCouranteId = staticListeId;
            this.cnx = staticConnexion;
            this.tacheRepo = new TacheRepository(cnx);
            this.typeRepo = new TypeRepository(cnx);
            chargerTypes();
            chargerTaches();
        }
    }

    private void chargerTypes() {
        typeRepo.getAllTypes().forEach(type ->
                cbType.getItems().add(type.getNom())
        );
    }

    private void initialiserFiltres() {
        cbEtat.getItems().addAll("Tous", "À faire", "En cours", "Terminé");
        cbEtat.getSelectionModel().selectFirst();

        cbEtat.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filtrerTaches();
        });

        tfFiltre.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrerTaches();
        });
    }

    private void chargerTaches() {
        toutesLesTaches = FXCollections.observableArrayList(
                tacheRepo.getTachesParListe(listeCouranteId)
        );
        tachesFiltrees = new FilteredList<>(toutesLesTaches);
        tacheTable.setItems(tachesFiltrees);
    }

    private void filtrerTaches() {
        tachesFiltrees.setPredicate(tache -> {
            // Filtre par état
            String etatSelectionne = cbEtat.getValue();
            if (!etatSelectionne.equals("Tous") &&
                    !tache.getEtatString().equals(etatSelectionne)) {
                return false;
            }

            // Filtre par texte
            String filtre = tfFiltre.getText().toLowerCase();
            return filtre.isEmpty() || tache.getNom().toLowerCase().contains(filtre);
        });
    }

    @FXML
    private void ajouterTache() {
        String nom = tfNomTache.getText().trim();
        if (!nom.isEmpty()) {
            Tache nouvelleTache = new Tache(
                    0, nom, 0, listeCouranteId,
                    cbType.getSelectionModel().getSelectedIndex() + 1
            );
            if (tacheRepo.ajouterTache(nouvelleTache)) {
                toutesLesTaches.add(nouvelleTache);
                tfNomTache.clear();
            }
        }
    }

    @FXML
    private void modifierTache() {
        Tache tacheSelectionnee = tacheTable.getSelectionModel().getSelectedItem();
        if (tacheSelectionnee != null) {
            tacheSelectionnee.setNom(tfNomTache.getText());
            tacheSelectionnee.setEtat(cbEtat.getSelectionModel().getSelectedIndex());
            if (tacheRepo.modifierTache(tacheSelectionnee)) {
                tacheTable.refresh();
            }
        }
    }

    @FXML
    private void supprimerTache() {
        Tache tacheSelectionnee = tacheTable.getSelectionModel().getSelectedItem();
        if (tacheSelectionnee != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setContentText("Supprimer cette tâche ?");
            if (confirmation.showAndWait().get() == ButtonType.OK) {
                if (tacheRepo.supprimerTache(tacheSelectionnee.getId())) {
                    toutesLesTaches.remove(tacheSelectionnee);
                }
            }
        }
    }

    @FXML
    private void changerEtat() {
        Tache tache = tacheTable.getSelectionModel().getSelectedItem();
        if (tache != null) {
            int nouvelEtat = (tache.getEtat() + 1) % 3; // Rotation 0→1→2→0
            tache.setEtat(nouvelEtat);
            tacheRepo.modifierTache(tache);
            tacheTable.refresh();
        }
    }
}