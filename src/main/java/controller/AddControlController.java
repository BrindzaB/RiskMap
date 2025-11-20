package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import manager.SceneManager;
import model.User;
import service.ControlService;
import model.Control;
import service.UserService;

import java.util.List;

public class AddControlController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private Spinner<Integer> impactSpinner;
    @FXML private Spinner<Integer> likelihoodSpinner;
    @FXML private ComboBox<User> ownerComboBox;

    private ControlService controlService;
    private UserService userService;

    @FXML
    private void initialize() {
        impactSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1));
        likelihoodSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1));

        configureUserComboBox();
    }

    private void configureUserComboBox() {
        ownerComboBox.setCellFactory(lv -> new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? "" : user.getUsername());
            }
        });

        ownerComboBox.setButtonCell(new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? "" : user.getUsername());
            }
        });
    }

    public void loadUsers() {
        try {
            List<User> users = userService.getAllUsers();
            ObservableList<User> userList = FXCollections.observableArrayList(users);
            ownerComboBox.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading users: " + e.getMessage());
        }
    }

    @FXML
    public void onSave() {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Please enter a control name");
            return;
        }

        if (descriptionField.getText().trim().isEmpty()) {
            showAlert("Please enter a control description");
            return;
        }

        if (ownerComboBox.getValue() == null) {
            showAlert("Please select an owner");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            int impact = impactSpinner.getValue();
            int likelihood = likelihoodSpinner.getValue();
            User owner = ownerComboBox.getValue();

            Control newControl = new Control(name, description, impact, likelihood, owner);

            controlService.addControl(newControl);
            showAlert("Control added successfully");

            SceneManager.getInstance().switchScene("controlList");
        } catch (Exception e) {
            showAlert("Error saving control: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onBack() {
        SceneManager.getInstance().switchScene("dashboard");
    }

    public void setControlService(ControlService controlService) {
        this.controlService = controlService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        loadUsers();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}