package appli.acceuil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Type;
import repository.TypeRepository;
import java.sql.Connection;

public class TypeController {
    @FXML private TableView<Type> typeTable;
    @FXML private TextField nomField;
    @FXML private ColorPicker colorPicker;
    @FXML private CheckBox adminOnlyCheck;
    @FXML private TableColumn<Type, String> colorColumn;

    private TypeRepository typeRepo;
    private boolean isAdmin;

    public void initialize(Connection connection, boolean isAdmin) {
        this.typeRepo = new TypeRepository(connection);
        this.isAdmin = isAdmin;

        configureTable();
        loadTypes();

        if (!isAdmin) {
            adminOnlyCheck.setDisable(true);
            adminOnlyCheck.setSelected(false);
        }
    }

    private void configureTable() {
        // Configurer la colonne couleur
        colorColumn.setCellFactory(column -> new TableCell<>() {
            private final javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(20, 20);

            @Override
            protected void updateItem(String colorHex, boolean empty) {
                super.updateItem(colorHex, empty);
                if (empty || colorHex == null) {
                    setGraphic(null);
                } else {
                    rect.setFill(Color.web(colorHex));
                    setGraphic(rect);
                }
            }
        });
    }

    private void loadTypes() {
        typeTable.getItems().setAll(typeRepo.getAllTypes(isAdmin));
    }

    @FXML
    private void handleAddType() {
        String nom = nomField.getText().trim();
        if (!nom.isEmpty()) {
            Color color = colorPicker.getValue();
            String hexColor = String.format("#%02X%02X%02X",
                    (int)(color.getRed() * 255),
                    (int)(color.getGreen() * 255),
                    (int)(color.getBlue() * 255));

            Type newType = new Type(0, nom, hexColor, adminOnlyCheck.isSelected());
            if (typeRepo.createType(newType)) {
                loadTypes();
                nomField.clear();
                colorPicker.setValue(Color.WHITE);
            } else {
                showAlert("Erreur", "Échec de la création du type");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}