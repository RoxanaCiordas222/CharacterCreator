package org.roxi.charactercreator.model.service;
import org.roxi.charactercreator.model.DnDCharacter;
import org.roxi.charactercreator.model.repository.CharacterRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterService {
    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public void createCharacter(DnDCharacter character) throws IllegalArgumentException {
        validateCharacter(character);
        repository.create(character);
    }

    public List<DnDCharacter> getAllCharacters() {
        return repository.readAll();
    }

    public void updateCharacter(DnDCharacter character) throws IllegalArgumentException {
        validateCharacter(character);
        repository.update(character);
    }

    public void deleteCharacter(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete");
        }
        repository.delete(id);
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

}
