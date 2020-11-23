package skill.view;

import entity.EntitySearchController;
import enums.EnumsLists;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.util.StringConverter;
import ru.rdude.fxlib.containers.MultipleChoiceContainer;
import ru.rdude.fxlib.containers.MultipleChoiceContainerElement;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.enums.*;
import ru.rdude.rpg.game.logic.gameStates.Battle;
import ru.rdude.rpg.game.logic.gameStates.Camp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SkillSearchController implements EntitySearchController<SkillData> {

    @FXML
    private ComboBox<String> skillType;
    @FXML
    private ComboBox<String> attackType;
    @FXML
    private ComboBox<String> effect;
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


    @FXML
    public void initialize() {
        StringConverter<String> stringConverter = new StringConverter<>() {
            @Override
            public String toString(String s) {
                return s == null ? "ANY" : s;
            }

            @Override
            public String fromString(String s) {
                return s.equals("ANY") ? null : s;
            }
        };
        skillType.setItems(EnumsLists.skillTypesStringWithNull);
        skillType.setConverter(stringConverter);
        attackType.setItems(EnumsLists.attackTypesStringWithNull);
        attackType.setConverter(stringConverter);
        effect.setItems(EnumsLists.skillEffectsStringWithNull);
        effect.setConverter(stringConverter);
        elements.setElements(EnumsLists.elements);
    }

    @FXML
    private void resetSearch() {
        skillType.setValue(null);
        attackType.setValue(null);
        effect.setValue(null);
        elements.getNodesElements().forEach(MultipleChoiceContainerElement::removeFromParent);
        canBeUsedInBattle.setSelected(true);
        canBeUsedInCamp.setSelected(true);
        canBeUsedInMap.setSelected(true);
        canBeBlocked.setSelected(true);
        canBeResisted.setSelected(true);
        canBeDodged.setSelected(true);
        dealDamageAny.setSelected(true);
        changeStatsAny.setSelected(true);
        receiveItemsAny.setSelected(true);
        requireItemsAny.setSelected(true);
        hasDurationAny.setSelected(true);
        applyTransformationAny.setSelected(true);
        summonAny.setSelected(true);
    }

    private static Map<Control, Function<SkillData, ?>> controlFunctionMap;

    private static Map<Function<SkillSearchController, Control>, Function<SkillData, ?>> functionMap;

    @Override
    public Map<Control, Function<SkillData, ?>> getControlFunctionMap() {
        if (controlFunctionMap == null) {
            controlFunctionMap = new HashMap<>();
            controlFunctionMap.put(getSkillType(), skillData -> skillData.getType().name());
            controlFunctionMap.put(getAttackType(), skillData -> skillData.getAttackType().name());
            controlFunctionMap.put(getEffect(), skillData -> skillData.getEffect().name());
            controlFunctionMap.put(getElements(), SkillData::getElements);
            controlFunctionMap.put(getCanBeUsedInBattle(), skillData -> skillData.getUsableInGameStates().get(GameState.BATTLE));
            controlFunctionMap.put(getCanBeUsedInCamp(), skillData -> skillData.getUsableInGameStates().get(GameState.CAMP));
            controlFunctionMap.put(getCanBeUsedInMap(), skillData -> skillData.getUsableInGameStates().get(GameState.MAP));
            controlFunctionMap.put(getCanBeBlocked(), SkillData::isCanBeBlocked);
            controlFunctionMap.put(getCanBeResisted(), SkillData::isCanBeResisted);
            controlFunctionMap.put(getCanBeDodged(), SkillData::isCanBeDodged);
            controlFunctionMap.put(getDealDamageAny(), skillData -> true);
            controlFunctionMap.put(getDealDamageYes(), skillData -> skillData.getDamage() != null && !skillData.getDamage().isEmpty());
            controlFunctionMap.put(getDealDamageNo(), skillData -> skillData.getDamage() == null || skillData.getDamage().isEmpty());
            controlFunctionMap.put(getChangeStatsAny(), skillData -> true);
            controlFunctionMap.put(getChangeStatsYes(), skillData -> skillData.getStats().entrySet().stream()
                    .anyMatch(entry -> entry.getValue() == null || !entry.getValue().isEmpty()));
            controlFunctionMap.put(getChangeStatsNo(), skillData -> skillData.getStats().entrySet().stream()
                    .allMatch(entry -> entry.getValue() != null && entry.getValue().isEmpty()));
            controlFunctionMap.put(getReceiveItemsAny(), skillData -> true);
            controlFunctionMap.put(getReceiveItemsYes(), skillData -> !skillData.getReceiveItems().isEmpty());
            controlFunctionMap.put(getReceiveItemsNo(), skillData -> skillData.getReceiveItems().isEmpty());
            controlFunctionMap.put(getRequireItemsAny(), skillData -> true);
            controlFunctionMap.put(getRequireItemsYes(), skillData -> !skillData.getRequirements().getItems().isEmpty());
            controlFunctionMap.put(getRequireItemsNo(), skillData -> skillData.getRequirements().getItems().isEmpty());
            controlFunctionMap.put(getHasDurationAny(), skillData -> true);
            controlFunctionMap.put(getHasDurationYes(),
                    skillData -> (skillData.getDurationInMinutes() != null && !skillData.getDurationInMinutes().isEmpty())
                            || (skillData.getDurationInTurns() != null && !skillData.getDurationInTurns().isEmpty()));
            controlFunctionMap.put(getHasDurationNo(),
                    skillData -> (skillData.getDurationInMinutes() == null || skillData.getDurationInMinutes().isEmpty())
                            && (skillData.getDurationInTurns() == null || skillData.getDurationInTurns().isEmpty()));
            controlFunctionMap.put(getApplyTransformationAny(), skillData -> true);
            controlFunctionMap.put(getApplyTransformationYes(),
                    skillData -> !skillData.getTransformation().getElements().isEmpty()
                            || skillData.getTransformation().getSize() != null
                            || !skillData.getTransformation().getBeingTypes().isEmpty());
            controlFunctionMap.put(getApplyTransformationNo(),
                    skillData -> skillData.getTransformation().getElements().isEmpty()
                            && skillData.getTransformation().getSize() == null
                            && skillData.getTransformation().getBeingTypes().isEmpty());
            controlFunctionMap.put(getSummonAny(), skillData -> true);
            controlFunctionMap.put(getSummonYes(), skillData -> !skillData.getSummon().isEmpty());
            controlFunctionMap.put(getSummonNo(), skillData -> skillData.getSummon().isEmpty());
        }
        return controlFunctionMap;
    }

    public static Map<Function<SkillSearchController, Control>, Function<SkillData, ?>> getFunctionMap() {
        if (functionMap == null) {
            functionMap = new HashMap<>();
            functionMap.put(SkillSearchController::getSkillType, skillData -> skillData.getType().name());
            functionMap.put(SkillSearchController::getAttackType, skillData -> skillData.getAttackType().name());
            functionMap.put(SkillSearchController::getEffect, skillData -> skillData.getEffect().name());
            functionMap.put(SkillSearchController::getElements, SkillData::getElements);
            functionMap.put(SkillSearchController::getCanBeUsedInBattle, skillData -> skillData.getUsableInGameStates().get(GameState.BATTLE));
            functionMap.put(SkillSearchController::getCanBeUsedInCamp, skillData -> skillData.getUsableInGameStates().get(GameState.CAMP));
            functionMap.put(SkillSearchController::getCanBeUsedInMap, skillData -> skillData.getUsableInGameStates().get(GameState.MAP));
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


    public ComboBox<String> getSkillType() {
        return skillType;
    }

    public ComboBox<String> getAttackType() {
        return attackType;
    }

    public ComboBox<String> getEffect() {
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
