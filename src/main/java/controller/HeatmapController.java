package controller;

import component.RiskHeatmapPane;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import manager.SceneManager;
import model.Control;
import service.ControlService;
import service.RiskScoreCalculator;

import java.util.List;

public class HeatmapController implements SceneManager.Refreshable {

    @FXML
    private VBox heatmapContainer;

    private ControlService controlService;
    private RiskHeatmapPane heatmapPane;

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }

    private void initializeHeatmap() {

        if (heatmapPane == null) {
            heatmapPane = new RiskHeatmapPane();
            heatmapContainer.getChildren().add(heatmapPane);

            heatmapPane.setOnCellClicked(controls -> {
                System.out.println("Clicked controls: " + controls.size());
            });
        }
    }

    @FXML
    private void onDashboard() {
        SceneManager.getInstance().switchScene("dashboard");
    }

    public void loadHeatmap() {
        initializeHeatmap();

        if (controlService != null) {
            List<Control> allControls = controlService.getAllControls();
            heatmapPane.loadControls(allControls);
        } else {
            System.out.println("ERROR: controlService is null!");
        }
    }

    @Override
    public void refresh() {
        loadHeatmap();
    }
}