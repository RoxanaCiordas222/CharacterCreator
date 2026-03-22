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
import org.roxi.charactercreator.model.Role;
import org.roxi.charactercreator.model.User;
import org.roxi.charactercreator.model.repository.UserRepository;
import org.roxi.charactercreator.model.service.UserService;
import java.io.IOException;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;

    private UserService userService = new UserService(new UserRepository());

    @FXML
    protected void handleRegister(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = passwordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("All fields must be filled.");
            return;
        }

        if (!userService.isValidEmail(email)) {
            statusLabel.setText("Please enter a valid email address.");
            return;
        }

        if (!pass.equals(confirmPass)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }

        User newUser = new User(name, pass,email, Role.PLAYER);
        boolean success = userService.registerNewUser(newUser);

        if (success) {
            goToLogin(event);
        } else {
            statusLabel.setText("Email already exists.");
        }
    }

    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/login-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}