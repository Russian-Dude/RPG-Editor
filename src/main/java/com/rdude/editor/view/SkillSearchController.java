package com.rdude.editor.view;

import com.rdude.editor.entity.EntitySearchController;
import com.rdude.editor.enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.util.Pair;
import javafx.util.StringConverter;
import ru.rdude.fxlib.containers.MultipleChoiceContainer;
import ru.rdude.fxlib.containers.MultipleChoiceContainerElement;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.enums.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
    private RadioButton canBeUsedInBattleAny;
    @FXML
    private RadioButton canBeUsedInBattleYes;
    @FXML
    private RadioButton canBeUsedInBattleNo;
    @FXML
    private RadioButton canBeUsedInCampAny;
    @FXML
    private RadioButton canBeUsedInCampYes;
    @FXML
    private RadioButton canBeUsedInCampNo;
    @FXML
    private RadioButton canBeUsedInMapAny;
    @FXML
    private RadioButton canBeUsedInMapYes;
    @FXML
    private RadioButton canBeUsedInMapNo;
    @FXML
    private RadioButton canBeBlockedAny;
    @FXML
    private RadioButton canBeBlockedYes;
    @FXML
    private RadioButton canBeBlockedNo;
    @FXML
    private RadioButton canBeResistedAny;
    @FXML
    private RadioButton canBeResistedYes;
    @FXML
    private RadioButton canBeResistedNo;
    @FXML
    private RadioButton canBeDodgedAny;
    @FXML
    private RadioButton canBeDodgedYes;
    @FXML
    private RadioButton canBeDodgedNo;
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
        canBeBlockedAny.setSelected(true);
        canBeDodgedAny.setSelected(true);
        canBeResistedAny.setSelected(true);
        canBeUsedInBattleAny.setSelected(true);
        canBeUsedInCampAny.setSelected(true);
        canBeUsedInMapAny.setSelected(true);
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
            // comboBoxes & MultipleChoiceContainers
            controlFunctionMap.put(getSkillType(), skillData -> skillData.getType().name());
            controlFunctionMap.put(getAttackType(), skillData -> skillData.getAttackType().name());
            controlFunctionMap.put(getEffect(), skillData -> skillData.getEffect().name());
            controlFunctionMap.put(getElements(), SkillData::getElements);
            // use in battle
            controlFunctionMap.put(getCanBeUsedInBattleAny(), skillData -> true);
            controlFunctionMap.put(getCanBeUsedInBattleYes(), skillData -> skillData.getUsableInGameStates().get(GameState.BATTLE));
            controlFunctionMap.put(getCanBeUsedInBattleNo(), skillData -> !skillData.getUsableInGameStates().get(GameState.BATTLE));
            controlFunctionMap.put(getCanBeUsedInCampAny(), skillData -> true);
            controlFunctionMap.put(getCanBeUsedInCampYes(), skillData -> skillData.getUsableInGameStates().get(GameState.CAMP));
            controlFunctionMap.put(getCanBeUsedInCampNo(), skillData -> !skillData.getUsableInGameStates().get(GameState.CAMP));
            controlFunctionMap.put(getCanBeUsedInMapAny(), skillData -> true);
            controlFunctionMap.put(getCanBeUsedInMapYes(), skillData -> skillData.getUsableInGameStates().get(GameState.MAP));
            controlFunctionMap.put(getCanBeUsedInMapNo(), skillData -> !skillData.getUsableInGameStates().get(GameState.MAP));
            // can be avoided
            controlFunctionMap.put(getCanBeBlockedAny(), skillData -> true);
            controlFunctionMap.put(getCanBeBlockedYes(), SkillData::isCanBeBlocked);
            controlFunctionMap.put(getCanBeBlockedNo(), skillData -> !skillData.isCanBeBlocked());
            controlFunctionMap.put(getCanBeResistedAny(), skillData -> true);
            controlFunctionMap.put(getCanBeResistedYes(), SkillData::isCanBeResisted);
            controlFunctionMap.put(getCanBeResistedNo(), skillData -> !skillData.isCanBeResisted());
            controlFunctionMap.put(getCanBeDodgedAny(), skillData -> true);
            controlFunctionMap.put(getCanBeDodgedYes(), SkillData::isCanBeDodged);
            controlFunctionMap.put(getCanBeDodgedNo(), skillData -> !skillData.isCanBeDodged());
            // deal damage
            controlFunctionMap.put(getDealDamageAny(), skillData -> true);
            controlFunctionMap.put(getDealDamageYes(), skillData -> skillData.getDamage() != null && !skillData.getDamage().isEmpty());
            controlFunctionMap.put(getDealDamageNo(), skillData -> skillData.getDamage() == null || skillData.getDamage().isEmpty());
            // change stats
            controlFunctionMap.put(getChangeStatsAny(), skillData -> true);
            controlFunctionMap.put(getChangeStatsYes(), skillData -> skillData.getStats().entrySet().stream()
                    .anyMatch(entry -> entry.getValue() == null || !entry.getValue().isEmpty()));
            controlFunctionMap.put(getChangeStatsNo(), skillData -> skillData.getStats().entrySet().stream()
                    .allMatch(entry -> entry.getValue() != null && entry.getValue().isEmpty()));
            // receive items
            controlFunctionMap.put(getReceiveItemsAny(), skillData -> true);
            controlFunctionMap.put(getReceiveItemsYes(), skillData -> !skillData.getReceiveItems().isEmpty());
            controlFunctionMap.put(getReceiveItemsNo(), skillData -> skillData.getReceiveItems().isEmpty());
            // require items
            controlFunctionMap.put(getRequireItemsAny(), skillData -> true);
            controlFunctionMap.put(getRequireItemsYes(), skillData -> !skillData.getRequirements().getItems().isEmpty());
            controlFunctionMap.put(getRequireItemsNo(), skillData -> skillData.getRequirements().getItems().isEmpty());
            // duration
            controlFunctionMap.put(getHasDurationAny(), skillData -> true);
            controlFunctionMap.put(getHasDurationYes(),
                    skillData -> (skillData.getDurationInMinutes() != null && !skillData.getDurationInMinutes().isEmpty())
                            || (skillData.getDurationInTurns() != null && !skillData.getDurationInTurns().isEmpty()));
            controlFunctionMap.put(getHasDurationNo(),
                    skillData -> (skillData.getDurationInMinutes() == null || skillData.getDurationInMinutes().isEmpty())
                            && (skillData.getDurationInTurns() == null || skillData.getDurationInTurns().isEmpty()));
            // transformation
            controlFunctionMap.put(getApplyTransformationAny(), skillData -> true);
            controlFunctionMap.put(getApplyTransformationYes(),
                    skillData -> !skillData.getTransformation().getElements().isEmpty()
                            || skillData.getTransformation().getSize() != null
                            || !skillData.getTransformation().getBeingTypes().isEmpty());
            controlFunctionMap.put(getApplyTransformationNo(),
                    skillData -> skillData.getTransformation().getElements().isEmpty()
                            && skillData.getTransformation().getSize() == null
                            && skillData.getTransformation().getBeingTypes().isEmpty());
            // summon
            controlFunctionMap.put(getSummonAny(), skillData -> true);
            controlFunctionMap.put(getSummonYes(), skillData -> !skillData.getSummon().isEmpty());
            controlFunctionMap.put(getSummonNo(), skillData -> skillData.getSummon().isEmpty());
        }
        return controlFunctionMap;
    }

    public static Map<Function<SkillSearchController, Control>, Function<SkillData, ?>> getFunctionMap() {
        if (functionMap == null) {
            functionMap = new HashMap<>();
            // comboBoxes and MultipleChoiceContainers
            functionMap.put(SkillSearchController::getSkillType, skillData -> skillData.getType().name());
            functionMap.put(SkillSearchController::getAttackType, skillData -> skillData.getAttackType().name());
            functionMap.put(SkillSearchController::getEffect, skillData -> skillData.getEffect().name());
            functionMap.put(SkillSearchController::getElements, SkillData::getElements);
            // can be used in
            functionMap.put(SkillSearchController::getCanBeUsedInBattleAny, skillData -> true);
            functionMap.put(SkillSearchController::getCanBeUsedInBattleYes, skillData -> skillData.getUsableInGameStates().get(GameState.BATTLE));
            functionMap.put(SkillSearchController::getCanBeUsedInBattleNo, skillData -> !skillData.getUsableInGameStates().get(GameState.BATTLE));
            functionMap.put(SkillSearchController::getCanBeUsedInCampAny, skillData -> true);
            functionMap.put(SkillSearchController::getCanBeUsedInCampYes, skillData -> skillData.getUsableInGameStates().get(GameState.CAMP));
            functionMap.put(SkillSearchController::getCanBeUsedInCampNo, skillData -> !skillData.getUsableInGameStates().get(GameState.CAMP));
            functionMap.put(SkillSearchController::getCanBeUsedInMapAny, skillData -> true);
            functionMap.put(SkillSearchController::getCanBeUsedInMapYes, skillData -> skillData.getUsableInGameStates().get(GameState.MAP));
            functionMap.put(SkillSearchController::getCanBeUsedInMapNo, skillData -> !skillData.getUsableInGameStates().get(GameState.MAP));
            // can be avoided
            functionMap.put(SkillSearchController::getCanBeBlockedAny, skillData -> true);
            functionMap.put(SkillSearchController::getCanBeBlockedYes, SkillData::isCanBeBlocked);
            functionMap.put(SkillSearchController::getCanBeBlockedNo, skillData -> !skillData.isCanBeBlocked());
            functionMap.put(SkillSearchController::getCanBeResistedAny, skillData -> true);
            functionMap.put(SkillSearchController::getCanBeResistedYes, SkillData::isCanBeResisted);
            functionMap.put(SkillSearchController::getCanBeResistedNo, skillData -> !skillData.isCanBeResisted());
            functionMap.put(SkillSearchController::getCanBeDodgedAny, skillData -> true);
            functionMap.put(SkillSearchController::getCanBeDodgedYes, SkillData::isCanBeDodged);
            functionMap.put(SkillSearchController::getCanBeDodgedNo, skillData -> !skillData.isCanBeDodged());
            // damage
            functionMap.put(SkillSearchController::getDealDamageAny, skillData -> true);
            functionMap.put(SkillSearchController::getDealDamageYes, skillData -> skillData.getDamage() != null && !skillData.getDamage().isEmpty());
            functionMap.put(SkillSearchController::getDealDamageNo, skillData -> skillData.getDamage() == null || skillData.getDamage().isEmpty());
            // change stats
            functionMap.put(SkillSearchController::getChangeStatsAny, skillData -> true);
            functionMap.put(SkillSearchController::getChangeStatsYes, skillData -> skillData.getStats().entrySet().stream()
                    .anyMatch(entry -> entry.getValue() == null || !entry.getValue().isEmpty()));
            functionMap.put(SkillSearchController::getChangeStatsNo, skillData -> skillData.getStats().entrySet().stream()
                    .allMatch(entry -> entry.getValue() != null && entry.getValue().isEmpty()));
            // receive items
            functionMap.put(SkillSearchController::getReceiveItemsAny, skillData -> true);
            functionMap.put(SkillSearchController::getReceiveItemsYes, skillData -> !skillData.getReceiveItems().isEmpty());
            functionMap.put(SkillSearchController::getReceiveItemsNo, skillData -> skillData.getReceiveItems().isEmpty());
            // require items
            functionMap.put(SkillSearchController::getRequireItemsAny, skillData -> true);
            functionMap.put(SkillSearchController::getRequireItemsYes, skillData -> !skillData.getRequirements().getItems().isEmpty());
            functionMap.put(SkillSearchController::getRequireItemsNo, skillData -> skillData.getRequirements().getItems().isEmpty());
            // duration
            functionMap.put(SkillSearchController::getHasDurationAny, skillData -> true);
            functionMap.put(SkillSearchController::getHasDurationYes,
                    skillData -> (skillData.getDurationInMinutes() != null && !skillData.getDurationInMinutes().isEmpty())
                            || (skillData.getDurationInTurns() != null && !skillData.getDurationInTurns().isEmpty()));
            functionMap.put(SkillSearchController::getHasDurationNo,
                    skillData -> (skillData.getDurationInMinutes() == null || skillData.getDurationInMinutes().isEmpty())
                            && (skillData.getDurationInTurns() == null || skillData.getDurationInTurns().isEmpty()));
            // transformation
            functionMap.put(SkillSearchController::getApplyTransformationAny, skillData -> true);
            functionMap.put(SkillSearchController::getApplyTransformationYes,
                    skillData -> !skillData.getTransformation().getElements().isEmpty()
                            || skillData.getTransformation().getSize() != null
                            || !skillData.getTransformation().getBeingTypes().isEmpty());
            functionMap.put(SkillSearchController::getApplyTransformationNo,
                    skillData -> skillData.getTransformation().getElements().isEmpty()
                            && skillData.getTransformation().getSize() == null
                            && skillData.getTransformation().getBeingTypes().isEmpty());
            // summon
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

    public RadioButton getCanBeUsedInBattleAny() {
        return canBeUsedInBattleAny;
    }

    public RadioButton getCanBeUsedInBattleYes() {
        return canBeUsedInBattleYes;
    }

    public RadioButton getCanBeUsedInBattleNo() {
        return canBeUsedInBattleNo;
    }

    public RadioButton getCanBeUsedInCampAny() {
        return canBeUsedInCampAny;
    }

    public RadioButton getCanBeUsedInCampYes() {
        return canBeUsedInCampYes;
    }

    public RadioButton getCanBeUsedInCampNo() {
        return canBeUsedInCampNo;
    }

    public RadioButton getCanBeUsedInMapAny() {
        return canBeUsedInMapAny;
    }

    public RadioButton getCanBeUsedInMapYes() {
        return canBeUsedInMapYes;
    }

    public RadioButton getCanBeUsedInMapNo() {
        return canBeUsedInMapNo;
    }

    public RadioButton getCanBeBlockedAny() {
        return canBeBlockedAny;
    }

    public RadioButton getCanBeBlockedYes() {
        return canBeBlockedYes;
    }

    public RadioButton getCanBeBlockedNo() {
        return canBeBlockedNo;
    }

    public RadioButton getCanBeResistedAny() {
        return canBeResistedAny;
    }

    public RadioButton getCanBeResistedYes() {
        return canBeResistedYes;
    }

    public RadioButton getCanBeResistedNo() {
        return canBeResistedNo;
    }

    public RadioButton getCanBeDodgedAny() {
        return canBeDodgedAny;
    }

    public RadioButton getCanBeDodgedYes() {
        return canBeDodgedYes;
    }

    public RadioButton getCanBeDodgedNo() {
        return canBeDodgedNo;
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
