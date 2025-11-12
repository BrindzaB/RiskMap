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
    }

    public void addSceneWithSetup(String name, String fxmlPath, SetupController setup) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());

        Object controller = loader.getController();
        setup.setup(controller);
        scenes.put(name, scene);
    }

    public void switchScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            stage.setScene(scenes.get(sceneName));
            stage.show();
        } else {
            System.out.println("Scene `" + sceneName + "' not found");
        }
    }

    @FunctionalInterface
    public interface SetupController {
        void setup(Object controller);
    }

}
