package controller;

import javafx.fxml.FXML;
import manager.SceneManager;
import service.ControlService;

public class AddControlController {

    private ControlService controlService;

    @FXML
    public void onSave() {

    }

    @FXML
    public void onBack() {
        SceneManager.getInstance().switchScene("dashboard");
    }

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }
}
