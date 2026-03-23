package org.roxi.charactercreator.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.roxi.charactercreator.HelloApplication;
import org.roxi.charactercreator.model.Role;
import org.roxi.charactercreator.model.User;
import org.roxi.charactercreator.model.UserSession;
import javafx.fxml.FXML;
import java.io.IOException;

public class HelloController {
    @FXML
    private void handleLoginClick(ActionEvent event) {
        switchScene(event, "view/login-view.fxml", "Login");
    }

    @FXML
    private void handleRegisterClick(ActionEvent event) {
        switchScene(event, "view/register-view.fxml", "Register");
    }

    @FXML
    private void handleVisitorClick(ActionEvent event) {
        User visitor = new User("Guest", "visitor@dnd.com", "", Role.VISITOR);
        UserSession.getInstance().setLoggedInUser(visitor);

        switchScene(event, "view/dashboard-view.fxml", "Character Dashboard");
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Could not load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}