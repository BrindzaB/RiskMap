import controller.AddControlController;
import controller.ControlDetailsController;
import controller.ControlListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.SceneManager;
import model.User;
import model.dao.Database;
import model.dao.auditResult.AuditResultDao;
import model.dao.auditResult.AuditResultDaoJDBC;
import model.dao.control.ControlDao;
import model.dao.control.ControlDaoJDBC;
import model.dao.user.UserDao;
import model.dao.user.UserDaoJDBC;
import service.ControlService;
import service.UserService;

import java.sql.Connection;

public class Main extends Application {

    private static Connection connection;

    public void start(Stage primaryStage) throws Exception {
        try {
            connection = Database.getConnection();

            ControlDao controlDao = new ControlDaoJDBC(connection);
            AuditResultDao auditResultDao = new AuditResultDaoJDBC(connection);
            UserDao userDao = new UserDaoJDBC(connection);
            ControlService controlService = new ControlService(controlDao, auditResultDao);
            UserService userService = new UserService(userDao);

            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.setStage(primaryStage);

            sceneManager.addScene("dashboard", "/view/dashboard.fxml");

            sceneManager.addSceneWithSetup("controlList", "/view/control_list.fxml",
                    controller -> {
                            ControlListController ctrl = (ControlListController) controller;
                            ctrl.setControlService(controlService);
                            ctrl.loadControls();
                    });

            sceneManager.addSceneWithSetup("controlDetails", "/view/control_details.fxml",
                    controller -> {
                            ControlDetailsController ctrl = (ControlDetailsController) controller;
                            ctrl.setControlService(controlService);
                    });

            sceneManager.addSceneWithSetup("addControl", "/view/add_control.fxml",
                    controller -> {
                            AddControlController ctrl = (AddControlController) controller;
                            ctrl.setControlService(controlService);
                            ctrl.setUserService(userService);
                            ctrl.loadUsers();
                    });

            primaryStage.setTitle("Control Management System");
            primaryStage.setWidth(900);
            primaryStage.setHeight(700);

            sceneManager.switchScene("dashboard");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void stop() throws Exception {
        if (connection != null && !connection.isClosed()) {
            Database.closeConnection();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
