import controller.ControlListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dao.Database;
import model.dao.auditResult.AuditResultDao;
import model.dao.auditResult.AuditResultDaoJDBC;
import model.dao.control.ControlDao;
import model.dao.control.ControlDaoJDBC;
import service.ControlService;

import java.sql.Connection;

public class Main extends Application {

    private static Connection connection;

    public void start(Stage primaryStage) throws Exception {
        try {
            connection = Database.getConnection();

            ControlDao controlDao = new ControlDaoJDBC(connection);
            AuditResultDao auditResultDao = new AuditResultDaoJDBC(connection);

            ControlService controlService = new ControlService(controlDao, auditResultDao);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/control_list.fxml"));
            Scene scene = new Scene(loader.load());

            ControlListController controlListController = loader.getController();
            controlListController.setControlService(controlService);
            controlListController.loadControls();

            primaryStage.setTitle("Control List");
            primaryStage.setScene(scene);
            primaryStage.show();

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
