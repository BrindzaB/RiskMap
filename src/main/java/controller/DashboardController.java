package controller;

import javafx.fxml.FXML;
import manager.SceneManager;

public class DashboardController {

    @FXML
    private void onListControls() {
        SceneManager.getInstance().switchScene("controlList");
    }

    @FXML
    private void onAddNewControl() {
        SceneManager.getInstance().switchScene("addControl");
    }
}
