package skill.view;

import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FormulaAutocompletor {

    private final Set<String> variableNames = Set.of(
            "LVL",
            "TLVL",
            "EXP",
            "TEXP",
            "DEF",
            "TDEF",
            "AGI",
            "DEX",
            "INT",
            "LUCK",
            "STR",
            "VIT",
            "TAGI",
            "TDEX",
            "TINT",
            "TLUCK",
            "TSTR",
            "TVIT",
            "STM",
            "TSTM",
            "STMATK",
            "TSTMATK",
            "STMMAX",
            "TSTMMAX",
            "STMREST",
            "TSTMREST",
            "HPMAX",
            "THPMAX",
            "HPREST",
            "THPREST",
            "ATK",
            "ATKMIN",
            "ATKMAX",
            "TATK",
            "TATKMIN",
            "TATKMAX",
            "MELEEATKMIN",
            "MELEEATKMAX",
            "RANGEATKMIN",
            "RANGEATKMAX",
            "MAGICATKMIN",
            "MAGICATKMAX",
            "TMELEEATKMIN",
            "TMELEEATKMAX",
            "TRANGEATKMIN",
            "TRANGEATKMAX",
            "TMAGICATKMIN",
            "TMAGICATKMAX",
            "CRIT",
            "TCRIT",
            "PARRY",
            "TPARRY",
            "HIT",
            "THIT",
            "BLOCK",
            "TBLOCK",
            "CONC",
            "TCONC",
            "LKYDODGE",
            "TLKYDODGE",
            "FLEE",
            "TFLEE",
            "PRES",
            "TPRES",
            "MRES",
            "TMRES"
    );

    public Set<String> getSuggestions(String input) {
        String remaining = input
                .toUpperCase()
                .replaceAll("\\d", "")
                .replaceAll("\\s", "")
                .replaceAll("\\W", "")
                .replaceAll("_", "");
        for (String variableName : variableNames) {
            remaining = remaining.replaceAll(variableName, "");
        }
        if (remaining.isEmpty())
            return new HashSet<>();
        String finalRemaining = remaining;
        return variableNames.stream()
                .filter(name -> name.contains(finalRemaining))
                .collect(Collectors.toSet());
    }

}
