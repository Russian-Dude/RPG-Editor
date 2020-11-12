package enums;

import ru.rdude.rpg.game.logic.stats.Stat;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum FormulaVariable {
    LVL("LVL", "   caster level"),
    TLVL("TLVL", "   target level"),
    EXP("EXP", "   caster experience"),
    TEXP("TEXP", "   target experience"),
    DEF("DEF", "   caster defence"),
    TDEF("TDEF", "   target defence"),
    AGI("AGI", "   caster agility"),
    DEX("DEX", "   caster dexterity"),
    INT("INT", "   caster intelligence"),
    LUCK("LUCK", "   caster luck"),
    STR("STR", "   caster strength"),
    VIT("VIT", "   caster vitality"),
    TAGI("TAGI", "   target agility"),
    TDEX("TDEX", "   target dexterity"),
    TINT("TINT", "   target intelligence"),
    TLUCK("TLUCK", "   target luck"),
    TSTR("TSTR", "   target strength"),
    TVIT("TVIT", "   target vitality"),
    STM("STM", "   caster current stamina"),
    TSTM("TSTM", "   target current stamina"),
    STMATK("STMATK", "   caster stamina usage per default attack"),
    TSTMATK("TSTMATK", "   target stamina usage per default attack"),
    STMMAX("STMMAX", "   caster maximum stamina"),
    TSTMMAX("TSTMMAX", "   target maximum stamina"),
    STMREST("STMREST", "   caster stamina regeneration"),
    TSTMREST("TSTMREST", "   target stamina regeneration"),
    HP("HP", "caster current health"),
    THP("THP", "target current health"),
    HPMAX("HPMAX", "   caster maximum health"),
    THPMAX("THPMAX", "   target maximum health"),
    HPREST("HPREST", "   caster health regeneration"),
    THPREST("THPREST", "   target health regeneration"),
    ATK("ATK", "   caster calculated attack (with attack type of wielding weapon or monster default attack type)"),
    ATKMIN("ATK", "   caster minimum attack (with attack type of wielding weapon or monster default attack type)"),
    ATKMAX("ATKMAX", "   caster maximum attack (with attack type of wielding weapon or monster default attack type)"),
    TATK("TATK", "   target calculated attack (with attack type of wielding weapon or monster default attack type)"),
    TATKMIN("TATKMIN", "   target minimum attack (with attack type of wielding weapon or monster default attack type)"),
    TATKMAX("TATKMAX", "   target maximum attack (with attack type of wielding weapon or monster default attack type)"),
    MELEEATKMIN("MELEEATKMIN", "   caster minimum melee damage"),
    MELEEATKMAX("MELEEATKMAX", "   caster maximum melee damage"),
    RANGEATKMIN("RANGEATKMIN", "   caster minimum range damage"),
    RANGEATKMAX("RANGEATKMAX", "   caster maximum range damage"),
    MAGICATKMIN("MAGICATKMIN", "   caster minimum magic damage"),
    MAGICATKMAX("MAGICATKMAX", "   caster maximum magic damage"),
    TMELEEATKMIN("TMELEEATKMIN", "   target minimum melee damage"),
    TMELEEATKMAX("TMELEEATKMAX", "   target maximum melee damage"),
    TRANGEATKMIN("TRANGEATKMIN", "   target minimum range damage"),
    TRANGEATKMAX("TRANGEATKMAX", "   target maximum range damage"),
    TMAGICATKMIN("TMAGICATKMIN", "   target minimum magic damage"),
    TMAGICATKMAX("TMAGICATKMAX", "   target maximum magic damage"),
    CRIT("CRIT", "   caster critical chance"),
    TCRIT("TCRIT", "   target critical chance"),
    PARRY("PARRY", "   caster parry chance"),
    TPARRY("TPARRY", "   target parry chance"),
    HIT("HIT", "   caster hit value"),
    THIT("THIT", "   target hit value"),
    BLOCK("BLOCK", "   caster block chance"),
    TBLOCK("TBLOCK", "   target block chance"),
    CONC("CONC", "   caster current concentration"),
    TCONC("TCONC", "   target current concentration"),
    LKYDODGE("LKYDODGE", "   caster chance of lucky dodge"),
    TLKYDODGE("TLKYDODGE", "   target chance of lucky dodge"),
    FLEE("FLEE", "caster flee value"),
    TFLEE("TFLEE", "target flee value"),
    PRES("PRES", "   caster physic resistance"),
    TPRES("TPRES", "   target physic resistance"),
    MRES("MRES", "   caster magic resistance"),
    TMRES("TMRES", "   target magic resistance");

    private static Set<FormulaVariable> variablesSet;

    private String variable;
    private String description;

    FormulaVariable(String variable, String description) {
        this.variable = variable;
        this.description = description;
    }

    public String getVariable() {
        return variable;
    }

    public String getDescription() {
        return description;
    }

    public static Set<FormulaVariable> getAllVariables() {
        if (variablesSet == null) {
            variablesSet = Arrays.stream(values())
                    .collect(Collectors.toSet());
        }
        return variablesSet;
    }

}