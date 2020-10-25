package enums;

import java.util.Set;

public class FormulaVariables {

    private static Set<String> variables;

    public static Set<String> getAllVariables() {
        if (variables == null)
            initSet();
        return variables;
    }

    private static void initSet() {
        variables = Set.of(
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
    }
}
