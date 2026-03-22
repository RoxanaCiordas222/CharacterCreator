package org.roxi.charactercreator.model.service;
import org.roxi.charactercreator.model.DnDCharacter;

import java.util.HashMap;
import java.util.Map;
public class StatsManager {
    private static final int MAX_BONUS_POINTS = 4;
    private int availablePoints;

    private final Map<String, Integer> baseStats = new HashMap<>();
    private final Map<String, Integer> bonusStats = new HashMap<>();

    public StatsManager() {
        resetAllStats();
    }

    public void setSpecies(String species) {
        this.availablePoints = MAX_BONUS_POINTS;
        resetBonusStats();

        if (species == null) {
            setAllBaseStats(8, 8, 8, 8, 8, 8);
            return;
        }

        switch (species.toUpperCase()) {
            case "ELF":
                setAllBaseStats(8, 14, 10, 12, 10, 10);
                break;
            case "ORC":
                setAllBaseStats(14, 10, 12, 8, 8, 8);
                break;
            case "HUMAN":
                setAllBaseStats(11, 11, 11, 11, 11, 11);
                break;
            case "DWARF":
                setAllBaseStats(12, 8, 14, 10, 10, 8);
                break;
            case "TIEFLING":
                setAllBaseStats(8, 10, 10, 12, 10, 14);
                break;
            default:
                setAllBaseStats(8, 8, 8, 8, 8, 8);
        }
    }

    public void addPoint(String stat) {
        if (availablePoints > 0) {
            bonusStats.put(stat, bonusStats.get(stat) + 1);
            availablePoints--;
        }
    }

    public void removePoint(String stat) {
        if (bonusStats.get(stat) > 0) {
            bonusStats.put(stat, bonusStats.get(stat) - 1);
            availablePoints++;
        }
    }

    public int getTotal(String stat) {
        return baseStats.get(stat) + bonusStats.get(stat);
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    private void setAllBaseStats(int str, int dex, int con, int intel, int wis, int cha) {
        baseStats.put("STR", str);
        baseStats.put("DEX", dex);
        baseStats.put("CON", con);
        baseStats.put("INT", intel);
        baseStats.put("WIS", wis);
        baseStats.put("CHA", cha);
    }

    private void resetBonusStats() {
        bonusStats.put("STR", 0);
        bonusStats.put("DEX", 0);
        bonusStats.put("CON", 0);
        bonusStats.put("INT", 0);
        bonusStats.put("WIS", 0);
        bonusStats.put("CHA", 0);
    }
    public void loadExistingCharacter(DnDCharacter character) {
        setSpecies(character.getSpecies().name());
        bonusStats.put("STR", character.getStrength() - baseStats.get("STR"));
        bonusStats.put("DEX", character.getDexterity() - baseStats.get("DEX"));
        bonusStats.put("CON", character.getConstitution() - baseStats.get("CON"));
        bonusStats.put("INT", character.getIntelligence() - baseStats.get("INT"));
        bonusStats.put("WIS", character.getWisdom() - baseStats.get("WIS"));
        bonusStats.put("CHA", character.getCharisma() - baseStats.get("CHA"));

        int spentPoints = 0;
        for (int points : bonusStats.values()) {
            spentPoints += points;
        }
        availablePoints = MAX_BONUS_POINTS - spentPoints;
    }

    private void resetAllStats() {
        this.availablePoints = MAX_BONUS_POINTS;
        setAllBaseStats(8, 8, 8, 8, 8, 8);
        resetBonusStats();
    }
}
