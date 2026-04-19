package org.roxi.charactercreator.model.service;

import org.roxi.charactercreator.model.User;
import org.roxi.charactercreator.model.UserSession;
import org.roxi.charactercreator.model.event.CharacterEvent;
import org.roxi.charactercreator.model.event.CharacterEventListener;

public class NotificationService implements CharacterEventListener {

    @Override
    public void onCharacterEvent(CharacterEvent event) {
        User currentUser = UserSession.getInstance().getLoggedInUser();
        if (currentUser == null) return;

        String action = event.getType().name().toLowerCase();
        String charName = event.getCharacter() != null ? event.getCharacter().getName() : "Unknown";

        System.out.println("\nEMAIL NOTIFICATION\n");
        System.out.println("To: " + currentUser.getEmail());
        System.out.println("Subject: Character " + action + " successfully!");
        System.out.println("Body: Hello " + currentUser.getUsername() + ", your character '" +
                charName + "' was recently " + action + " in our system.\n");
    }
}
