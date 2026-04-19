package org.roxi.charactercreator.model.export;

import org.roxi.charactercreator.model.DnDCharacter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JsonExporter implements ExportStrategy{
    @Override
    public void export(List<DnDCharacter> characters, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("["); // Start JSON array

            for (int i = 0; i < characters.size(); i++) {
                DnDCharacter c = characters.get(i);
                writer.println("  {");
                writer.println("    \"id\": \"" + c.getId() + "\",");
                writer.println("    \"name\": \"" + c.getName() + "\",");
                writer.println("    \"ownerUsername\": \"" + c.getOwnerUsername() + "\",");
                writer.println("    \"species\": \"" + c.getSpecies() + "\",");
                writer.println("    \"characterClass\": \"" + c.getCharacterClass() + "\",");
                writer.println("    \"level\": " + c.getLevel());
                writer.print("  }");

                // Add a comma if it's not the last character
                if (i < characters.size() - 1) writer.println(",");
                else writer.println();
            }

            writer.println("]"); // End JSON array
            System.out.println("JSON Export successful: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting to JSON: " + e.getMessage());
        }
    }
}

