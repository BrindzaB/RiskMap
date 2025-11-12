package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Control;
import service.ControlService;

import java.util.List;

public class ControlListController {

    @FXML private TableView<Control> controlTable;
    @FXML private TableColumn<Control, String> nameColumn;
    @FXML private TableColumn<Control, String> descriptionColumn;
    @FXML private TableColumn<Control, Integer> impactColumn;
    @FXML private TableColumn<Control, Integer> likelihoodColumn;
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
