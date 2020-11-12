package skill.view;

import data.Data;
import ru.rdude.fxlib.containers.MultipleChoiceContainerExtended;
import ru.rdude.rpg.game.logic.data.SkillData;

import java.util.HashMap;
import java.util.stream.Stream;

public class SkillPopupConfigurator {

    public static void configure(MultipleChoiceContainerExtended<SkillData, ?> container) {
        container.extendedSearchPopupBuilder()
                .addText(SkillData::getDescription)
                .addText(skillData -> "In game name: " + skillData.getName())
                .addText(skillData -> "Type: " + skillData.getType())
                .addText(skillData -> "Damage: " + skillData.getDamage())
                .addText(skillData -> "Attack type: " + skillData.getAttackType())
                .addText(skillData -> "Stamina required: " + skillData.getStaminaReq())
                .addText(skillData -> "Concentration required: " + skillData.getConcentrationReq())
                .addText(skillData -> {
                    StringBuilder string = new StringBuilder("Elements: ");
                    skillData.getElements().forEach(element -> string.append(element).append(" "));
                    return string.toString();
                })
                .addText(skillData -> {
                    StringBuilder string = new StringBuilder("Stats: ");
                    skillData.getStats().forEach((stat, value) -> {
                        if (value != null && !value.isEmpty()) {
                            if (!string.toString().equals("Stats: ")) {
                                string.append("\r\n       ");
                            }
                            string.append(stat.getSimpleName()).append(": ").append(value);
                        }
                    });
                    if (string.toString().equals("Stats: ")) {
                        string.append("-");
                    }
                    return string.toString();
                })
                .addText(skillData -> {
                    StringBuilder string = new StringBuilder("Summon: ");
                    skillData.getSummon().forEach(guid -> {
                        if (!string.toString().equals("Summon: ")) {
                            string.append("\r\n        ");
                        }
                        string.append(Data.getMonsterData(guid).getName());
                    });
                    if (string.toString().equals("Summon: ")) {
                        string.append("-");
                    }
                    return string.toString();
                })
                .addText(skillData -> {
                    StringBuilder string = new StringBuilder("Duration: ");
                    String turns = skillData.getDurationInTurns();
                    String minutes = skillData.getDurationInMinutes();
                    if (turns != null && !turns.isEmpty() && !turns.equals("0")) {
                        string.append(turns).append(" turns");
                    }
                    if (minutes != null && !minutes.isEmpty() && !minutes.equals("0")) {
                        if (!string.toString().equals("Duration: ")) {
                            string.append("\r\n          ");
                        }
                        string.append(minutes).append(" minutes");
                    }
                    if (string.toString().equals("Duration: ")) {
                        string.append("-");
                    }
                    return string.toString();
                })
                .addText(skillData -> {
                    StringBuilder string = new StringBuilder("Skill chaining: ");
                    Stream.concat(Stream.concat(
                            skillData.getSkillsCouldCast().keySet().stream(),
                            skillData.getSkillsMustCast().keySet().stream()),
                            skillData.getSkillsOnBeingAction().values().stream().flatMap(longFloatMap -> longFloatMap.keySet().stream()))
                            .distinct()
                            .map(guid -> Data.getSkillData(guid).getName())
                            .forEach(name -> {
                                if (!string.toString().equals("Skill chaining: ")) {
                                    string.append(", ");
                                }
                                string.append(name);
                            });
                    if (string.toString().equals("Skill chaining: ")) {
                        string.append("-");
                    }
                    return string.toString();
                })
                .apply();
    }
}
