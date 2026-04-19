package org.roxi.charactercreator.model.repository;
import org.roxi.charactercreator.model.DnDCharacter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SqlCharacterRepository implements CharacterRepository {
    @Override
    public void create(DnDCharacter character) {
        String sql = "INSERT INTO characters (id, ownerUsername, name, species, characterClass, level, strength, dexterity, constitution, intelligence, wisdom, charisma) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, character.getId());
            pstmt.setString(2, character.getOwnerUsername());
            pstmt.setString(3, character.getName());
            pstmt.setString(4, character.getSpecies() != null ? character.getSpecies().name() : null);
            pstmt.setString(5, character.getCharacterClass() != null ? character.getCharacterClass().name() : null);
            pstmt.setInt(6, character.getLevel());
            pstmt.setInt(7, character.getStrength());
            pstmt.setInt(8, character.getDexterity());
            pstmt.setInt(9, character.getConstitution());
            pstmt.setInt(10, character.getIntelligence());
            pstmt.setInt(11, character.getWisdom());
            pstmt.setInt(12, character.getCharisma());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error when saving character " + e.getMessage());
        }
    }

    @Override
    public List<DnDCharacter> readAll() {
        String sql = "SELECT * FROM characters";
        List<DnDCharacter> characters = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DnDCharacter c = new DnDCharacter();
                c.setId(rs.getString("id"));
                c.setOwnerUsername(rs.getString("ownerUsername"));
                c.setName(rs.getString("name"));

                String speciesStr = rs.getString("species");
                if (speciesStr != null) c.setSpecies(DnDCharacter.Species.valueOf(speciesStr));

                String classStr = rs.getString("characterClass");
                if (classStr != null) c.setCharacterClass(DnDCharacter.CharClass.valueOf(classStr));

                c.setLevel(rs.getInt("level"));
                c.setStrength(rs.getInt("strength"));
                c.setDexterity(rs.getInt("dexterity"));
                c.setConstitution(rs.getInt("constitution"));
                c.setIntelligence(rs.getInt("intelligence"));
                c.setWisdom(rs.getInt("wisdom"));
                c.setCharisma(rs.getInt("charisma"));
                characters.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error when loading characters: " + e.getMessage());
        }
        return characters;
    }

    @Override
    public void update(DnDCharacter character) {
        String sql = "UPDATE characters SET name = ?, species = ?, characterClass = ?, level = ?, strength = ?, dexterity = ?, constitution = ?, intelligence = ?, wisdom = ?, charisma = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, character.getName());
            pstmt.setString(2, character.getSpecies() != null ? character.getSpecies().name() : null);
            pstmt.setString(3, character.getCharacterClass() != null ? character.getCharacterClass().name() : null);
            pstmt.setInt(4, character.getLevel());
            pstmt.setInt(5, character.getStrength());
            pstmt.setInt(6, character.getDexterity());
            pstmt.setInt(7, character.getConstitution());
            pstmt.setInt(8, character.getIntelligence());
            pstmt.setInt(9, character.getWisdom());
            pstmt.setInt(10, character.getCharisma());
            pstmt.setString(11, character.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error when updating character: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM characters WHERE id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error when deleting character: " + e.getMessage());
        }
    }
    @Override
    public List<DnDCharacter> readByOwner(String ownerEmail) {
        String sql = "SELECT * FROM characters WHERE ownerUsername = ?";
        List<DnDCharacter> characters = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ownerEmail);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DnDCharacter c = new DnDCharacter();
                c.setId(rs.getString("id"));
                c.setOwnerUsername(rs.getString("ownerUsername"));
                c.setName(rs.getString("name"));

                String speciesStr = rs.getString("species");
                if (speciesStr != null) c.setSpecies(DnDCharacter.Species.valueOf(speciesStr));

                String classStr = rs.getString("characterClass");
                if (classStr != null) c.setCharacterClass(DnDCharacter.CharClass.valueOf(classStr));

                c.setStrength(rs.getInt("strength"));
                c.setDexterity(rs.getInt("dexterity"));
                c.setConstitution(rs.getInt("constitution"));
                c.setIntelligence(rs.getInt("intelligence"));
                c.setWisdom(rs.getInt("wisdom"));
                c.setCharisma(rs.getInt("charisma"));

                characters.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error loading characters: " + e.getMessage());
        }
        return characters;
}}
