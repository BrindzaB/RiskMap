package component;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Control;
import service.RiskScoreCalculator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RiskHeatmapPane extends GridPane {

    private static final int GRID_SIZE = 6;
    private static final int CELL_SIZE = 80;
    private static final int LABEL_WIDTH = 40;

    private RiskScoreCalculator riskCalculator;
    private List<Control> controls;
    private HeatmapCellClickListener cellClickListener;

    public RiskHeatmapPane(RiskScoreCalculator riskCalculator) {
        this.riskCalculator = riskCalculator;
        this.controls = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        this.setHgap(1);
        this.setVgap(1);
        this.setStyle("-fx-border-color: #cccccc; -fx-padding: 10;");
        this.setPadding(new Insets(10));
    }

    public void loadControls(List<Control> controls) {
        this.controls = controls;
        renderHeatmap();
    }

    private void renderHeatmap() {
        this.getChildren().clear();

        addAxisLabels();

        Map<String, List<Control>> controlsByRisk = controls.stream()
                .collect(Collectors.groupingBy(c -> c.getImpact() + "," + c.getLikelihood()));

        for (int impact = GRID_SIZE - 1; impact >= 0; impact--) {
            for (int likelihood = 0; likelihood < GRID_SIZE; likelihood++) {
                String key = impact + "," + likelihood;
                List<Control> cellControls = controlsByRisk.getOrDefault(key, new ArrayList<>());

                double avgRiskScore = cellControls.isEmpty() ? 0 :
                        cellControls.stream()
                                .mapToDouble(riskCalculator::calculateRiskScore)
                                .average()
                                .orElse(0);

                StackPane cell = createHeatmapCell(cellControls, avgRiskScore);
                this.add(cell, likelihood + 1, GRID_SIZE - impact);
            }
        }
    }

    private void addAxisLabels() {}

    private StackPane createHeatmapCell(List<Control> cellControls, double avgRiskScore) {}

    public interface HeatmapCellClickListener {
        void onCellClicked(List<Control> controls);
    }

}
