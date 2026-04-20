package org.roxi.charactercreator.model.export;

import org.roxi.charactercreator.model.DnDCharacter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class XmlExporter implements ExportStrategy{
    @Override
    public void export(List<DnDCharacter> characters, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<characters>");

            for (DnDCharacter c : characters) {
                writer.println("  <character>");
                writer.println("    <id>" + c.getId() + "</id>");
                writer.println("    <name>" + c.getName() + "</name>");
                writer.println("    <ownerUsername>" + c.getOwnerUsername() + "</ownerUsername>");
                writer.println("    <species>" + c.getSpecies() + "</species>");
                writer.println("    <characterClass>" + c.getCharacterClass() + "</characterClass>");
                writer.println("    <level>" + c.getLevel() + "</level>");
                writer.println("  </character>");
            }

            writer.println("</characters>");
            System.out.println("XML Export successful: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting to XML: " + e.getMessage());
        }
    }

}
