package org.roxi.charactercreator.model;

public class UserSession {
    private static UserSession instance;
    private User loggedInUser;
    private UserSession() {}
    private DnDCharacter characterToEdit = null;

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logout() {
        this.loggedInUser = null;
    }
    public DnDCharacter getCharacterToEdit() {
        return characterToEdit;
    }

    public void setCharacterToEdit(DnDCharacter character) {
        this.characterToEdit = character;
    }

    public void clearCharacterToEdit() {
        this.characterToEdit = null;
    }
}
