package component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    private List<Control> controls;
    private HeatmapCellClickListener cellClickListener;

    public RiskHeatmapPane() {
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

        for (int impact = GRID_SIZE - 1; impact > 0; impact--) {
            for (int likelihood = 1; likelihood < GRID_SIZE; likelihood++) {
                String key = impact + "," + likelihood;
                List<Control> cellControls = controlsByRisk.getOrDefault(key, new ArrayList<>());

                StackPane cell = createHeatmapCell(impact, likelihood, cellControls);
                this.add(cell, likelihood + 1, GRID_SIZE - impact);
            }
        }
    }

    private void addAxisLabels() {
        for (int i = 1; i < GRID_SIZE; i++) {
            Label label = createLabel(String.valueOf(i));
            this.add(label, i + 1, 0);
        }

        for (int i = GRID_SIZE - 1; i > 0; i--) {
            Label label = createLabel(String.valueOf(i));
            this.add(label, 0, GRID_SIZE - i);
        }
    }

    private StackPane createHeatmapCell(int impact, int likelihood, List<Control> cellControls) {
        Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE);

        background.setFill(getRiskColorByPosition(impact, likelihood));
        background.setStroke(Color.LIGHTGRAY);

        StackPane cell = new StackPane();
        cell.setMinSize(CELL_SIZE, CELL_SIZE);
        cell.setStyle("-fx-cursor: hand;");

        if (cellControls.isEmpty()) {
            cell.getChildren().add(background);
        } else {
            Label countLabel = new Label(String.valueOf(cellControls.size()));
            countLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            countLabel.setTextFill(Color.BLACK);

            cell.getChildren().addAll(background, countLabel);
            StackPane.setAlignment(countLabel, Pos.CENTER);

            cell.setOnMouseClicked(event -> {
                if (cellClickListener != null) {
                    cellClickListener.onCellClicked(cellControls);
                }
            });

            cell.setOnMouseEntered(event -> {
                background.setOpacity(0.8);
            });
            cell.setOnMouseExited(event -> {
                background.setOpacity(1.0);
            });
        }

        return cell;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setMinSize(LABEL_WIDTH, LABEL_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-weight: bold");
        return label;
    }

    private Color getRiskColorByPosition(int impact, int likelihood) {
        if (
            (impact == 3 && likelihood == 5) ||
            (impact == 4 && likelihood == 5) ||
            (impact == 5 && likelihood == 5) ||
            (impact == 5 && likelihood == 4) ||
            (impact == 5 && likelihood == 3) ||
            (impact == 4 && likelihood == 4)
        ) {
            return Color.web("#F44336");
        }

        else if (
            (impact == 2 && likelihood == 5) ||
            (impact == 2 && likelihood == 4) ||
            (impact == 3 && likelihood == 4) ||
            (impact == 3 && likelihood == 3) ||
            (impact == 4 && likelihood == 3) ||
            (impact == 4 && likelihood == 2) ||
            (impact == 5 && likelihood == 2) ||
            (impact == 1 && likelihood == 5) ||
            (impact == 5 && likelihood == 1) ||
            (impact == 4 && likelihood == 1) ||
            (impact == 1 && likelihood == 4) ||
            (impact == 3 && likelihood == 2) ||
            (impact == 2 && likelihood == 3)
        ) {
            return Color.web("#FFC107");
        }

        else {
            return Color.web("#4CAF50");
        }
    }

    public interface HeatmapCellClickListener {
        void onCellClicked(List<Control> controls);
    }

    public void setOnCellClicked(HeatmapCellClickListener listener) {
        this.cellClickListener = listener;
    }

}
