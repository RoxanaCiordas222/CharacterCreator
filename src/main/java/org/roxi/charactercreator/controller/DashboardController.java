package org.roxi.charactercreator.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.roxi.charactercreator.HelloApplication;
import org.roxi.charactercreator.model.DnDCharacter;
import org.roxi.charactercreator.model.Role;
import org.roxi.charactercreator.model.User;
import org.roxi.charactercreator.model.UserSession;
import org.roxi.charactercreator.model.repository.SqlCharacterRepository;
import org.roxi.charactercreator.model.repository.UserRepository;
import org.roxi.charactercreator.model.service.CharacterService;
import org.roxi.charactercreator.model.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ListView<String> dataListView;
    @FXML private TextField searchField;
    @FXML private Button deleteButton;
    @FXML private Button createButton;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private Button editButton;

    private UserService userService = new UserService(new UserRepository());
    private ObservableList<DnDCharacter> allCharacters = FXCollections.observableArrayList();
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    private CharacterService characterService = new CharacterService(new SqlCharacterRepository());

    public void initialize() {
        User currentUser = UserSession.getInstance().getLoggedInUser();
        deleteButton.setManaged(true);
        createButton.setManaged(true);
        sortComboBox.getItems().addAll("Name (A-Z)", "Species");
        sortComboBox.setValue("Name (A-Z)");

        if (currentUser.getRole() == Role.VISITOR) {
            welcomeLabel.setText("Welcome, Guest (View Only)");
            deleteButton.setVisible(false);
            deleteButton.setManaged(false);
            createButton.setVisible(true);
        }
        else if (currentUser.getRole() == Role.ADMIN) {
            welcomeLabel.setText("Admin Mode: Manage Users");
            deleteButton.setVisible(true);
            deleteButton.setText("Delete User");
            sortComboBox.setVisible(false);
            sortComboBox.setManaged(false);
            createButton.setVisible(false);
            createButton.setManaged(false);
            editButton.setVisible(false);
            editButton.setManaged(false);
            loadAllUsers();
        }
        else {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");
            deleteButton.setVisible(true);
            deleteButton.setText("Delete Character");
            editButton.setVisible(true);
            editButton.setManaged(true);
            createButton.setVisible(true);
            loadCharacters();
        }
    }

    private void loadCharacters() {
        User currentUser = UserSession.getInstance().getLoggedInUser();
        String searchEmail = currentUser.getEmail();
        List<DnDCharacter> list = characterService.getCharactersByOwner(searchEmail);
        allCharacters.setAll(list);
        updateCharacterListView(list);
    }

    private void updateCharacterListView(List<DnDCharacter> list) {
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (DnDCharacter c : list) {
            displayList.add("Character: " + c.getName() + " (" + c.getSpecies() + ")");
        }
        dataListView.setItems(displayList);
    }

    private void loadAllUsers() {
        List<User> users = userService.getAllUsers();
        allUsers.setAll(users);
        updateUserListView(users);
    }

    private void updateUserListView(List<User> list) {
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (User u : list) {
            displayList.add(u.getEmail() + " [" + u.getRole() + "]");
        }
        dataListView.setItems(displayList);
    }

    @FXML
    protected void handleDeleteAction() {
        User currentUser = UserSession.getInstance().getLoggedInUser();
        String selectedItem = dataListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        if (currentUser.getRole() == Role.ADMIN) {
            String emailToDelete = selectedItem.split(" ")[0];

            if (emailToDelete.equals(currentUser.getEmail())) {
                System.out.println("You cannot delete yourself!");
                return;
            }
            userService.deleteUserAccount(currentUser,emailToDelete);
            loadAllUsers();
        }

        else if (currentUser.getRole() == Role.PLAYER) {
            DnDCharacter characterToDelete = null;

            for (DnDCharacter c : allCharacters) {
                String displayString = "Character: " + c.getName() + " (" + c.getSpecies() + ")";
                if (displayString.equals(selectedItem)) {
                    characterToDelete = c;
                    break;
                }
            }

            if (characterToDelete != null) {
                characterService.deleteCharacter(characterToDelete.getId());
                loadCharacters();
            }
        }
    }

    @FXML
    protected void handleSearch() {
        String query = searchField.getText().toLowerCase();
        User currentUser = UserSession.getInstance().getLoggedInUser();

        if (currentUser.getRole() == Role.ADMIN) {
            List<User> filtered = allUsers.stream()
                    .filter(u -> u.getEmail().toLowerCase().contains(query))
                    .collect(Collectors.toList());
            updateUserListView(filtered);
        } else {
            List<DnDCharacter> filtered = allCharacters.stream()
                    .filter(c -> c.getName().toLowerCase().contains(query))
                    .collect(Collectors.toList());

            String sortOption = sortComboBox.getValue();

            if ("Species".equals(sortOption)) {
                filtered.sort((c1, c2) -> {
                    String species1 = c1.getSpecies() != null ? c1.getSpecies().name() : "UNKNOWN";
                    String species2 = c2.getSpecies() != null ? c2.getSpecies().name() : "UNKNOWN";
                    return species1.compareTo(species2);
                });
            } else {
                filtered.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
            }

            updateCharacterListView(filtered);
        }
    }

    @FXML protected void goToCreateCharacter(ActionEvent event) {
        switchScene(event, "view/create-view.fxml", "Create");
    }
    @FXML protected void handleLogout(ActionEvent event) {
        UserSession.getInstance().logout();
        switchScene(event, "view/hello-view.fxml", "D&D Creator");
    }

    @FXML protected void handleEditAction(ActionEvent event) {
        String selectedItem = dataListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;

        for (DnDCharacter c : allCharacters) {
            String displayString = "Character: " + c.getName() + " (" + c.getSpecies() + ")";
            if (displayString.equals(selectedItem)) {
                UserSession.getInstance().setCharacterToEdit(c);
                switchScene(event, "view/create-view.fxml", "Edit Character");
                break;
            }
        }
    }
    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (IOException e) { e.printStackTrace(); }
    }
}