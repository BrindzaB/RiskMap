package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.SceneManager;
import model.AuditResult;
import model.Control;
import service.ControlService;

import java.util.List;

public class ControlDetailsController implements SceneManager.Refreshable {
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label impactLabel;
    @FXML private Label likelihoodLabel;
    @FXML private Label ownerLabel;
    @FXML private TableView<AuditResult> auditResultTable;
    @FXML private TableColumn<AuditResult, Integer> auditIdColumn;
    @FXML private TableColumn<AuditResult, String> auditDateColumn;
    @FXML private TableColumn<AuditResult, String> auditResultColumn;
    @FXML private Button backButton;

    private ControlService controlService;
    private Control currentControl;
    private ObservableList<AuditResult> auditData;

    @FXML
    private void initialize() {
        auditIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        auditDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        auditResultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        auditData = FXCollections.observableArrayList();
        auditResultTable.setItems(auditData);
    }

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }

    public void loadControlDetails(Control control) {
        this.currentControl = control;

        nameLabel.setText(control.getName());
        descriptionLabel.setText(control.getDescription());
        impactLabel.setText(String.valueOf(control.getImpact()));
        likelihoodLabel.setText(String.valueOf(control.getLikelihood()));
        ownerLabel.setText(control.getOwner().getUsername());

        List<AuditResult> auditResults = controlService.getAuditResultsForControl(control.getId());
        auditData.setAll(auditResults);
    }

    @FXML
    private void onAddResult() {
        SceneManager.getInstance().switchScene("addResult", currentControl);
    }

    @FXML
    private void onBack() {
        SceneManager.getInstance().switchScene("controlList");
    }

    @Override
    public void refresh() {
        Object data = SceneManager.getInstance().getCurrentData();

        if (data instanceof Control) {
            loadControlDetails((Control) data);
            SceneManager.getInstance().clearCurrentData();
        }
    }
}
