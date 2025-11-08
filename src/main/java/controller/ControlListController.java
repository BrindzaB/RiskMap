package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Control;
import service.ControlService;

import java.util.List;

public class ControlListController {

    @FXML
    private TableView<Control> controlTable;

    @FXML
    private TableColumn<Control, String> nameColumn;

    @FXML
    private TableColumn<Control, String> descriptionColumn;

    @FXML
    private TableColumn<Control, Integer> impactColumn;

    @FXML
    private TableColumn<Control, Integer> likelihoodColumn;

    private ControlService controlService;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        impactColumn.setCellValueFactory(new PropertyValueFactory<>("impact"));
        likelihoodColumn.setCellValueFactory(new PropertyValueFactory<>("likelihood"));
    }

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }

    public void loadControls() {
        List<Control> controls = controlService.getAllControls();
        ObservableList<Control> data = FXCollections.observableArrayList(controls);
        controlTable.setItems(data);
    }

}
