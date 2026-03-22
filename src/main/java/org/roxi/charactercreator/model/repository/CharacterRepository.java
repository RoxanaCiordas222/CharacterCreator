package org.roxi.charactercreator.model.repository;
import org.roxi.charactercreator.model.DnDCharacter;
import java.util.List;

public interface CharacterRepository {
    void create(DnDCharacter character);
    List<DnDCharacter> readAll();
    void update(DnDCharacter character);
    void delete(String id);
    List<DnDCharacter> readByOwner(String ownerEmail);
}
