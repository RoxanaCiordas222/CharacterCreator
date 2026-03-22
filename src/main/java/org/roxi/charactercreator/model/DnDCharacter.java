package org.roxi.charactercreator.model;
import java.util.UUID;

public class DnDCharacter {
    public enum Species { HUMAN, ELF, DWARF, ORC, TIEFLING }
    public enum CharClass { FIGHTER, WIZARD, ROGUE, CLERIC, PALADIN }

    private String id;
    private String ownerUsername;
    private String name;
    private Species species;
    private CharClass characterClass;
    private int level;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;


    public DnDCharacter() {
        this.id = UUID.randomUUID().toString();
    }

    public DnDCharacter(String ownerUsername, String name, Species species, CharClass characterClass) {
        this.id = UUID.randomUUID().toString();
        this.ownerUsername = ownerUsername;
        this.name = name;
        this.species = species;
        this.characterClass = characterClass;
        this.level = 1;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Species getSpecies() { return species; }
    public void setSpecies(Species species) { this.species = species; }

    public CharClass getCharacterClass() { return characterClass; }
    public void setCharacterClass(CharClass characterClass) { this.characterClass = characterClass; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getDexterity() { return dexterity; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }

    public int getConstitution() { return constitution; }
    public void setConstitution(int constitution) { this.constitution = constitution; }

    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }

    public int getWisdom() { return wisdom; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }

    public int getCharisma() { return charisma; }
    public void setCharisma(int charisma) { this.charisma = charisma; }
}
