package skill.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import ru.rdude.fxlib.containers.MultipleChoiceContainer;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.enums.AttackType;
import ru.rdude.rpg.game.logic.enums.Element;
import ru.rdude.rpg.game.logic.enums.SkillEffect;
import ru.rdude.rpg.game.logic.enums.SkillType;
import ru.rdude.rpg.game.logic.gameStates.Battle;
import ru.rdude.rpg.game.logic.gameStates.Camp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SkillSearchController {

    @FXML
    private ComboBox<SkillType> skillType;
    @FXML
    private ComboBox<AttackType> attackType;
    @FXML
    private ComboBox<SkillEffect> effect;
    @FXML
    private MultipleChoiceContainer<Element> elements;
    @FXML
    private CheckBox canBeUsedInBattle;
    @FXML
    private CheckBox canBeUsedInCamp;
    @FXML
    private CheckBox canBeUsedInMap;
    @FXML
    private CheckBox canBeBlocked;
    @FXML
    private CheckBox canBeResisted;
    @FXML
    private CheckBox canBeDodged;
    @FXML
    private RadioButton dealDamageAny;
    @FXML
    private RadioButton dealDamageYes;
    @FXML
    private RadioButton dealDamageNo;
    @FXML
    private RadioButton changeStatsAny;
    @FXML
    private RadioButton changeStatsYes;
    @FXML
    private RadioButton changeStatsNo;
    @FXML
    private RadioButton receiveItemsAny;
    @FXML
    private RadioButton receiveItemsYes;
    @FXML
    private RadioButton receiveItemsNo;
    @FXML
    private RadioButton requireItemsAny;
    @FXML
    private RadioButton requireItemsYes;
    @FXML
    private RadioButton requireItemsNo;
    @FXML
    private RadioButton hasDurationAny;
    @FXML
    private RadioButton hasDurationYes;
    @FXML
    private RadioButton hasDurationNo;
    @FXML
    private RadioButton applyTransformationAny;
    @FXML
    private RadioButton applyTransformationYes;
    @FXML
    private RadioButton applyTransformationNo;
    @FXML
    private RadioButton summonAny;
    @FXML
    private RadioButton summonYes;
    @FXML
    private RadioButton summonNo;

    private static Map<Function<SkillSearchController, Control>, Function<SkillData, ?>> functionMap;

    public static Map<Function<SkillSearchController, Control>, Function<SkillData, ?>> getFunctionMap() {
        if (functionMap == null) {
            functionMap = new HashMap<>();
            functionMap.put(SkillSearchController::getSkillType, SkillData::getType);
            functionMap.put(SkillSearchController::getAttackType, SkillData::getAttackType);
            functionMap.put(SkillSearchController::getEffect, SkillData::getEffect);
            functionMap.put(SkillSearchController::getElements, SkillData::getElements);
            functionMap.put(SkillSearchController::getCanBeUsedInBattle, skillData -> skillData.getUsableInGameStates().get(Battle.class));
            functionMap.put(SkillSearchController::getCanBeUsedInCamp, skillData -> skillData.getUsableInGameStates().get(Camp.class));
            functionMap.put(SkillSearchController::getCanBeUsedInMap, skillData -> skillData.getUsableInGameStates().get(ru.rdude.rpg.game.logic.gameStates.Map.class));
            functionMap.put(SkillSearchController::getCanBeBlocked, SkillData::isCanBeBlocked);
            functionMap.put(SkillSearchController::getCanBeResisted, SkillData::isCanBeResisted);
            functionMap.put(SkillSearchController::getCanBeDodged, SkillData::isCanBeDodged);
            functionMap.put(SkillSearchController::getDealDamageAny, skillData -> true);
            functionMap.put(SkillSearchController::getDealDamageYes, skillData -> skillData.getDamage() != null && !skillData.getDamage().isEmpty());
            functionMap.put(SkillSearchController::getDealDamageNo, skillData -> skillData.getDamage() == null || skillData.getDamage().isEmpty());
            functionMap.put(SkillSearchController::getChangeStatsAny, skillData -> true);
            functionMap.put(SkillSearchController::getChangeStatsYes, skillData -> skillData.getStats().entrySet().stream()
                    .anyMatch(entry -> entry.getValue() == null || !entry.getValue().isEmpty()));
            functionMap.put(SkillSearchController::getChangeStatsNo, skillData -> skillData.getStats().entrySet().stream()
                    .allMatch(entry -> entry.getValue() != null && entry.getValue().isEmpty()));
            functionMap.put(SkillSearchController::getReceiveItemsAny, skillData -> true);
            functionMap.put(SkillSearchController::getReceiveItemsYes, skillData -> !skillData.getReceiveItems().isEmpty());
            functionMap.put(SkillSearchController::getReceiveItemsNo, skillData -> skillData.getReceiveItems().isEmpty());
            functionMap.put(SkillSearchController::getRequireItemsAny, skillData -> true);
            functionMap.put(SkillSearchController::getRequireItemsYes, skillData -> !skillData.getRequirements().getItems().isEmpty());
            functionMap.put(SkillSearchController::getRequireItemsNo, skillData -> skillData.getRequirements().getItems().isEmpty());
            functionMap.put(SkillSearchController::getHasDurationAny, skillData -> true);
            functionMap.put(SkillSearchController::getHasDurationYes,
                    skillData -> (skillData.getDurationInMinutes() != null && !skillData.getDurationInMinutes().isEmpty())
                            || (skillData.getDurationInTurns() != null && !skillData.getDurationInTurns().isEmpty()));
            functionMap.put(SkillSearchController::getHasDurationNo,
                    skillData -> (skillData.getDurationInMinutes() == null || skillData.getDurationInMinutes().isEmpty())
                            && (skillData.getDurationInTurns() == null || skillData.getDurationInTurns().isEmpty()));
            functionMap.put(SkillSearchController::getApplyTransformationAny, skillData -> true);
            functionMap.put(SkillSearchController::getApplyTransformationYes,
                    skillData -> !skillData.getTransformation().getElements().isEmpty()
                            || skillData.getTransformation().getSize() != null
                            || !skillData.getTransformation().getBeingTypes().isEmpty());
            functionMap.put(SkillSearchController::getApplyTransformationNo,
                    skillData -> skillData.getTransformation().getElements().isEmpty()
                            && skillData.getTransformation().getSize() == null
                            && skillData.getTransformation().getBeingTypes().isEmpty());
            functionMap.put(SkillSearchController::getSummonAny, skillData -> true);
            functionMap.put(SkillSearchController::getSummonYes, skillData -> !skillData.getSummon().isEmpty());
            functionMap.put(SkillSearchController::getSummonNo, skillData -> skillData.getSummon().isEmpty());
        }
        return functionMap;
    }


    public ComboBox<SkillType> getSkillType() {
        return skillType;
    }

    public ComboBox<AttackType> getAttackType() {
        return attackType;
    }

    public ComboBox<SkillEffect> getEffect() {
        return effect;
    }

    public MultipleChoiceContainer<Element> getElements() {
        return elements;
    }

    public CheckBox getCanBeUsedInBattle() {
        return canBeUsedInBattle;
    }

    public CheckBox getCanBeUsedInCamp() {
        return canBeUsedInCamp;
    }

    public CheckBox getCanBeUsedInMap() {
        return canBeUsedInMap;
    }

    public CheckBox getCanBeBlocked() {
        return canBeBlocked;
    }

    public CheckBox getCanBeResisted() {
        return canBeResisted;
    }

    public CheckBox getCanBeDodged() {
        return canBeDodged;
    }

    public RadioButton getDealDamageAny() {
        return dealDamageAny;
    }

    public RadioButton getDealDamageYes() {
        return dealDamageYes;
    }

    public RadioButton getDealDamageNo() {
        return dealDamageNo;
    }

    public RadioButton getChangeStatsAny() {
        return changeStatsAny;
    }

    public RadioButton getChangeStatsYes() {
        return changeStatsYes;
    }

    public RadioButton getChangeStatsNo() {
        return changeStatsNo;
    }

    public RadioButton getReceiveItemsAny() {
        return receiveItemsAny;
    }

    public RadioButton getReceiveItemsYes() {
        return receiveItemsYes;
    }

    public RadioButton getReceiveItemsNo() {
        return receiveItemsNo;
    }

    public RadioButton getRequireItemsAny() {
        return requireItemsAny;
    }

    public RadioButton getRequireItemsYes() {
        return requireItemsYes;
    }

    public RadioButton getRequireItemsNo() {
        return requireItemsNo;
    }

    public RadioButton getHasDurationAny() {
        return hasDurationAny;
    }

    public RadioButton getHasDurationYes() {
        return hasDurationYes;
    }

    public RadioButton getHasDurationNo() {
        return hasDurationNo;
    }

    public RadioButton getApplyTransformationAny() {
        return applyTransformationAny;
    }

    public RadioButton getApplyTransformationYes() {
        return applyTransformationYes;
    }

    public RadioButton getApplyTransformationNo() {
        return applyTransformationNo;
    }

    public RadioButton getSummonAny() {
        return summonAny;
    }

    public RadioButton getSummonYes() {
        return summonYes;
    }

    public RadioButton getSummonNo() {
        return summonNo;
    }
}
