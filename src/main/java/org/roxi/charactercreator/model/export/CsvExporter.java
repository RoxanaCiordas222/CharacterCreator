package org.roxi.charactercreator.model.export;

import org.roxi.charactercreator.model.DnDCharacter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvExporter implements ExportStrategy {
    @Override
    public void export(List<DnDCharacter> characters, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("ID,Name,Owner,Species,Class,Level,STR,DEX,CON,INT,WIS,CHA");

            for (DnDCharacter c : characters) {
                writer.printf("%s,%s,%s,%s,%s,%d,%d,%d,%d,%d,%d,%d%n",
                        c.getId(), c.getName(), c.getOwnerUsername(),
                        c.getSpecies(), c.getCharacterClass(), c.getLevel(),
                        c.getStrength(), c.getDexterity(), c.getConstitution(),
                        c.getIntelligence(), c.getWisdom(), c.getCharisma()
                );
            }
            System.out.println("CSV Export successful: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }
}
