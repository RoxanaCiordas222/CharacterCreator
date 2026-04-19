package org.roxi.charactercreator.model.service;
import org.roxi.charactercreator.model.DnDCharacter;
import org.roxi.charactercreator.model.event.CharacterEvent;
import org.roxi.charactercreator.model.event.CharacterEventListener;
import org.roxi.charactercreator.model.event.EventPublisher;
import org.roxi.charactercreator.model.repository.CharacterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterService implements EventPublisher {
    private final CharacterRepository repository;
    private final List<CharacterEventListener> listeners = new ArrayList<>();

    public CharacterService(CharacterRepository repository) {

        this.repository = repository;
        this.addListener(new NotificationService());
    }

    public void createCharacter(DnDCharacter character) throws IllegalArgumentException {
        validateCharacter(character);
        repository.create(character);
        notifyListeners(new CharacterEvent(CharacterEvent.EventType.CREATED, character));
    }

    public List<DnDCharacter> getAllCharacters() {
        return repository.readAll();
    }

    public void updateCharacter(DnDCharacter character) throws IllegalArgumentException {
        validateCharacter(character);
        repository.update(character);
        notifyListeners(new CharacterEvent(CharacterEvent.EventType.UPDATED, character));
    }

    public void deleteCharacter(DnDCharacter character) {
        if (character == null || character.getId() == null || character.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete");
        }
        repository.delete(character.getId());
        notifyListeners(new CharacterEvent(CharacterEvent.EventType.DELETED, character));
    }


    private void validateCharacter(DnDCharacter character) {
        if (character.getName() == null || character.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Character must have a name.");
        }
        if (character.getOwnerUsername() == null || character.getOwnerUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Character must have an owner.");
        }

        checkStatBounds(character.getStrength(), "Strength");
        checkStatBounds(character.getDexterity(), "Dexterity");
        checkStatBounds(character.getConstitution(), "Constitution");
        checkStatBounds(character.getIntelligence(), "Intelligence");
        checkStatBounds(character.getWisdom(), "Wisdom");
        checkStatBounds(character.getCharisma(), "Charisma");
    }
    public List<DnDCharacter> searchAndSortCharacters(List<DnDCharacter> rawList, String query, String sortOption) {
        List<DnDCharacter> filtered = rawList.stream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        if ("Species".equals(sortOption)) {
            filtered.sort((c1, c2) -> {
                String s1 = c1.getSpecies() != null ? c1.getSpecies().name() : "";
                String s2 = c2.getSpecies() != null ? c2.getSpecies().name() : "";
                return s1.compareTo(s2);
            });
        } else {
            filtered.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        }

        return filtered;
    }

    private void checkStatBounds(int stat, String statName) {
        if (stat < 1 || stat > 30) {
            throw new IllegalArgumentException(statName + " must be between 1 and 30.");
        }
    }
    public List<DnDCharacter> getCharactersByOwner(String email) {
        return repository.readByOwner(email);
    }

    @Override
    public void addListener(CharacterEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(CharacterEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners(CharacterEvent event) {
            for(CharacterEventListener listener : listeners) {
                listener.onCharacterEvent(event);
            }
    }


}
