package org.roxi.charactercreator.controller;

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
import org.roxi.charactercreator.model.service.CharacterService;
import org.roxi.charactercreator.model.service.StatsManager;

import java.io.IOException;

public class CreateCharacterController {

    @FXML private TextField nameField;
    @FXML private ComboBox<DnDCharacter.Species> speciesBox;
    @FXML private ComboBox<DnDCharacter.CharClass> classBox;
    @FXML private Label statusLabel;
    @FXML private Button saveButton;
    @FXML private Label pointsLabel, strLabel, dexLabel, conLabel, intLabel, wisLabel, chaLabel;

    private final CharacterService characterService = new CharacterService(new SqlCharacterRepository());
    private final StatsManager statManager = new StatsManager();

    @FXML
    public void initialize() {
        speciesBox.getItems().setAll(DnDCharacter.Species.values());
        classBox.getItems().setAll(DnDCharacter.CharClass.values());

        DnDCharacter charToEdit = UserSession.getInstance().getCharacterToEdit();

        if (charToEdit != null) {
            nameField.setText(charToEdit.getName());
            speciesBox.setValue(charToEdit.getSpecies());
            classBox.setValue(charToEdit.getCharacterClass());
            saveButton.setText("Update Character");
            statManager.loadExistingCharacter(charToEdit);
        }

        speciesBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                statManager.setSpecies(newVal.name());
                updateStatUI();
            }
        });

        updateStatUI();
    }

    @FXML protected void addStr() { statManager.addPoint("STR"); updateStatUI(); }
    @FXML protected void subStr() { statManager.removePoint("STR"); updateStatUI(); }
    @FXML protected void addDex() { statManager.addPoint("DEX"); updateStatUI(); }
    @FXML protected void subDex() { statManager.removePoint("DEX"); updateStatUI(); }
    @FXML protected void addCon() { statManager.addPoint("CON"); updateStatUI(); }
    @FXML protected void subCon() { statManager.removePoint("CON"); updateStatUI(); }
    @FXML protected void addInt() { statManager.addPoint("INT"); updateStatUI(); }
    @FXML protected void subInt() { statManager.removePoint("INT"); updateStatUI(); }
    @FXML protected void addWis() { statManager.addPoint("WIS"); updateStatUI(); }
    @FXML protected void subWis() { statManager.removePoint("WIS"); updateStatUI(); }
    @FXML protected void addCha() { statManager.addPoint("CHA"); updateStatUI(); }
    @FXML protected void subCha() { statManager.removePoint("CHA"); updateStatUI(); }

    private void updateStatUI() {
        pointsLabel.setText("Points Remaining: " + statManager.getAvailablePoints());
        strLabel.setText(String.valueOf(statManager.getTotal("STR")));
        dexLabel.setText(String.valueOf(statManager.getTotal("DEX")));
        conLabel.setText(String.valueOf(statManager.getTotal("CON")));
        intLabel.setText(String.valueOf(statManager.getTotal("INT")));
        wisLabel.setText(String.valueOf(statManager.getTotal("WIS")));
        chaLabel.setText(String.valueOf(statManager.getTotal("CHA")));
    }

    @FXML
    protected void handleSave(ActionEvent event) {
        try {
            if (nameField.getText().isEmpty() || speciesBox.getValue() == null || classBox.getValue() == null) {
                statusLabel.setText("Please fill out name, species, and class.");
                return;
            }
            User currentUser = UserSession.getInstance().getLoggedInUser();
            DnDCharacter charToEdit = UserSession.getInstance().getCharacterToEdit();
            DnDCharacter c = (charToEdit != null) ? charToEdit : new DnDCharacter();

            c.setOwnerUsername(currentUser.getEmail());
            c.setName(nameField.getText());
            c.setSpecies(speciesBox.getValue());
            c.setCharacterClass(classBox.getValue());
            c.setStrength(statManager.getTotal("STR"));
            c.setDexterity(statManager.getTotal("DEX"));
            c.setConstitution(statManager.getTotal("CON"));
            c.setIntelligence(statManager.getTotal("INT"));
            c.setWisdom(statManager.getTotal("WIS"));
            c.setCharisma(statManager.getTotal("CHA"));

            if (currentUser.getRole() != Role.VISITOR) {
                if (charToEdit != null) {
                    characterService.updateCharacter(c);
                } else {
                    characterService.createCharacter(c);
                }
            }

            handleCancel(event);

        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    protected void handleCancel(ActionEvent event) {
        UserSession.getInstance().clearCharacterToEdit();
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/dashboard-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("My Characters");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}