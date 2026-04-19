package org.roxi.charactercreator.model.export;

import org.roxi.charactercreator.model.DnDCharacter;

import java.util.List;

public interface ExportStrategy {
    void export(List<DnDCharacter> characters, String filePath);
}
