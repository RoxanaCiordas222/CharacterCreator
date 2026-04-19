package org.roxi.charactercreator.model.event;

import org.roxi.charactercreator.model.DnDCharacter;

public class CharacterEvent {
    public enum EventType { CREATED, UPDATED, DELETED }

    private final EventType type;
    private final DnDCharacter character;

    public CharacterEvent(EventType type, DnDCharacter character) {
        this.type = type;
        this.character = character;
    }

    public EventType getType() { return type; }
    public DnDCharacter getCharacter() { return character; }
}
