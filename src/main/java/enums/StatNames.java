package enums;

import ru.rdude.rpg.game.logic.stats.Stat;
import ru.rdude.rpg.game.logic.stats.primary.*;
import ru.rdude.rpg.game.logic.stats.secondary.*;

import java.util.Arrays;

public enum StatNames {
    LVL(Lvl.class, "Level"),
    LVL_POINT(Lvl.Points.class, "Level Point"),
    EXP_CLASS(Lvl.ExpBase.class, "Experience class"),
    EXP_BASE(Lvl.ExpBase.class, "Experience base"),
    DEF(Def.class, "Defence"),
    AGI(Agi.class, "Agility"),
    DEX(Dex.class, "Dexterity"),
    INT(Int.class, "Intelligence"),
    LUCK(Luck.class, "Luck"),
    STR(Str.class, "Strength"),
    VIT(Vit.class, "Vitality"),
    STM(Stm.class, "Stamina current"),
    STM_ATK(Stm.PerHit.class, "Stamina per hit"),
    STM_MAX(Stm.Max.class, "Stamina max"),
    STM_REST(Stm.Recovery.class, "Stamina recovery"),
    HP(Hp.class, "Health"),
    HP_MAX(Hp.Max.class, "Health max"),
    HP_REST(Hp.Recovery.class, "Health recovery"),
    MELEE_MIN(Dmg.Melee.MeleeMin.class, "Melee min damage"),
    MELEE_MAX(Dmg.Melee.MeleeMax.class, "Melee max damage"),
    RANGE_MIN(Dmg.Range.RangeMin.class, "Range min damage"),
    RANGE_MAX(Dmg.Range.RangeMax.class, "Range max damage"),
    MAGIC_MIN(Dmg.Magic.MagicMin.class, "Magic min damage"),
    MAGIC_MAX(Dmg.Magic.MagicMax.class, "Magic max damage"),
    CRIT(Crit.class, "Critical"),
    PARRY(Parry.class, "Parry"),
    HIT(Hit.class, "Hit"),
    BLOCK(Block.class, "Block"),
    CONCENTRATION(Concentration.class, "Concentration"),
    LUCKY_DODGE(Flee.LuckyDodgeChance.class, "Lucky dodge"),
    FLEE(Flee.class, "Flee"),
    PHYSIC_RESISTANCE(PhysicResistance.class, "Physic resistance"),
    MAGIC_RESISTANCE(MagicResistance.class, "Magic resistance");

    private Class<? extends Stat> cl;
    private String name;

    StatNames(Class<? extends Stat> cl, String name) {
        this.cl = cl;
        this.name = name;
    }

    public static StatNames get(Class<? extends Stat> statClass) {
        return Arrays.stream(values())
                .filter(statName -> statName.cl == statClass)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Class<? extends Stat> getClazz() {
        return cl;
    }

    public String getName() {
        return name;
    }
}
