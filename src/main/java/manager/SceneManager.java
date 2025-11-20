package manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, Object> controllers = new HashMap<>();
    private Object currentData;

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addScene(String name, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        scenes.put(name, scene);
        controllers.put(name, loader.getController());
    }

    public void addSceneWithSetup(String name, String fxmlPath, SetupController setup) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());

        Object controller = loader.getController();
        setup.setup(controller);
        scenes.put(name, scene);
        controllers.put(name, controller);
    }

    public void switchScene(String sceneName) {
        switchScene(sceneName, null);
    }

    public void switchScene(String sceneName, Object data) {
        if (scenes.containsKey(sceneName)) {
            this.currentData = data;
            stage.setScene(scenes.get(sceneName));
            stage.show();

            refreshScene(sceneName);
        } else {
            System.out.println("Scene `" + sceneName + "' not found");
        }
    }

    public Object getCurrentData() {
        return currentData;
    }

    public void clearCurrentData() {
        currentData = null;
    }

    private void refreshScene(String sceneName) {
        Object controller = controllers.get(sceneName);

        if (controller instanceof Refreshable) {
            ((Refreshable) controller).refresh();
        }
    }

    @FunctionalInterface
    public interface SetupController {
        void setup(Object controller);
    }

    @FunctionalInterface
    public interface Refreshable {
        void refresh();
    }

}
