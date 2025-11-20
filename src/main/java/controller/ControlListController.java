package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.SceneManager;
import model.Control;
import service.ControlService;

import java.util.List;

public class ControlListController implements SceneManager.Refreshable {

    @FXML private TableView<Control> controlTable;
    @FXML private TableColumn<Control, String> nameColumn;
    @FXML private TableColumn<Control, String> descriptionColumn;
    @FXML private TableColumn<Control, Integer> impactColumn;
    @FXML private TableColumn<Control, Integer> likelihoodColumn;
    @FXML private TableColumn<Control, Void> actionColumn;
    @FXML private TextField likelihoodField;

    private ControlService controlService;
    private ObservableList<Control> controlData;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        impactColumn.setCellValueFactory(new PropertyValueFactory<>("impact"));
        likelihoodColumn.setCellValueFactory(new PropertyValueFactory<>("likelihood"));

        controlData = FXCollections.observableArrayList();
        controlTable.setItems(controlData);

        addDetailsButtonColumn();
    }

    private void addDetailsButtonColumn() {
        actionColumn.setCellFactory(col -> new TableCell<Control, Void>() {
            private final Button btn = new Button("Details");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> {
                        Control control = getTableView().getItems().get(getIndex());
                        openControlDetails(control);
                    });
                    setGraphic(btn);
                }
            }
        });
    }

    private void openControlDetails(Control control) {
        SceneManager.getInstance().switchScene("controlDetails", control);
    }

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }

    public void loadControls() {
        List<Control> controls = controlService.getAllControls();
        controlData.setAll(controls);
    }

    @FXML
    private void onFilterByLikelihood() {
        String text = likelihoodField.getText().trim();
        if (text.isEmpty()) {
            showAlert("Please enter a number for likelihood.");
            return;
        }

        try {
            int minLikelihood = Integer.parseInt(text);
            List<Control> filtered = controlService.getControlsByCondition(c -> c.getLikelihood() >= minLikelihood);
            controlData.setAll(filtered);
        } catch (NumberFormatException e) {
            showAlert("Invalid number format. Please enter an integer");
        }
    }

    @FXML
    private void onClearFilter() {
        likelihoodField.clear();
        loadControls();
    }

    @FXML
    private void onDashboard() {
        SceneManager.getInstance().switchScene("dashboard");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void refresh() {
        loadControls();
    }
}
