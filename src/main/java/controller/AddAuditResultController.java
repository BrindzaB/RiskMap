package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import manager.SceneManager;
import model.AuditResult;
import model.Control;
import model.Result;
import service.ControlService;

import java.time.LocalDate;

public class AddAuditResultController implements SceneManager.Refreshable {

    @FXML private Label controlNameLabel;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Result> resultComboBox;

    private ControlService controlService;
    private Control currentControl;

    @FXML
    private void initialize() {
        resultComboBox.setItems(FXCollections.observableArrayList(Result.values()));
        datePicker.setValue(LocalDate.now());
    }

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }

    private void loadControl(Control control) {
        this.currentControl = control;
        controlNameLabel.setText(control.getName());
    }

    @FXML
    private void onSave() {
        if (currentControl == null) {
            showAlert("Error", "No control selected");
            return;
        }

        if (datePicker.getValue() == null) {
            showAlert("Error", "Please select a date");
            return;
        }

        if (resultComboBox.getValue() == null) {
            showAlert("Error", "Please select a result");
            return;
        }

        try {
            AuditResult auditResult = new AuditResult(
                    currentControl.getId(),
                    datePicker.getValue(),
                    resultComboBox.getValue()
            );

            controlService.addAuditResult(auditResult);
            showAlert("Success", "Audit result added successfully");
            onBack();
        } catch (Exception e) {
            showAlert("Error", "Failed to add audit result: " + e.getMessage());
        }
    }

    @FXML
    private void onBack() {
        SceneManager.getInstance().switchScene("controlDetails", currentControl);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void refresh() {
        Object data = SceneManager.getInstance().getCurrentData();
        if (data instanceof Control) {
            loadControl((Control) data);
            SceneManager.getInstance().clearCurrentData();
        }
    }
}
