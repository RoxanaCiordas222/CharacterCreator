package org.roxi.charactercreator.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.roxi.charactercreator.HelloApplication;
import org.roxi.charactercreator.model.User;
import org.roxi.charactercreator.model.UserSession;
import org.roxi.charactercreator.model.repository.UserRepository;
import org.roxi.charactercreator.model.service.UserService;
import java.io.IOException;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private UserService userService = new UserService(new UserRepository());
    @FXML
    protected void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        User loggedInUser = userService.login(email, password);

        if (loggedInUser != null) {
            UserSession.getInstance().setLoggedInUser(loggedInUser);
            switchScene(event, "view/dashboard-view.fxml", "My Characters");
        } else {
            statusLabel.setText("Invalid email or password.");
        }
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading screen.");
        }
    }
}
