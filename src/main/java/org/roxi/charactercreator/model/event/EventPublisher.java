package org.roxi.charactercreator.model.event;

public interface EventPublisher {
    void addListener(CharacterEventListener listener);
    void removeListener(CharacterEventListener listener);
    void notifyListeners(CharacterEvent event);
}
