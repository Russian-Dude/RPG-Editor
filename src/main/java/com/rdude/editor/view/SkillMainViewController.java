package com.rdude.editor.view;


import com.rdude.editor.EntityEditorController;
import com.rdude.editor.SearchConfigurator;
import com.rdude.editor.data.Data;
import com.rdude.editor.enums.EnumsLists;
import com.rdude.editor.enums.FormulaVariable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.rdude.fxlib.boxes.SearchComboBox;
import ru.rdude.fxlib.containers.*;
import ru.rdude.fxlib.textfields.AutocomplitionTextField;
import ru.rdude.rpg.game.logic.coefficients.Coefficients;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.*;
import ru.rdude.rpg.game.logic.data.resources.ModuleResources;
import ru.rdude.rpg.game.logic.entities.beings.Being;
import ru.rdude.rpg.game.logic.entities.beings.BeingAction;
import ru.rdude.rpg.game.logic.entities.beings.Player;
import ru.rdude.rpg.game.logic.entities.skills.SkillParser;
import ru.rdude.rpg.game.logic.enums.*;
import ru.rdude.rpg.game.logic.gameStates.Battle;
import ru.rdude.rpg.game.logic.gameStates.Camp;
import ru.rdude.rpg.game.logic.gameStates.Map;
import ru.rdude.rpg.game.logic.stats.Stat;
import ru.rdude.rpg.game.utils.Functions;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SkillMainViewController implements EntityEditorController<SkillData> {


    private String fileName;
    private SkillData skill;
    private boolean wasChanged;
    private SaveButtons<SkillData> saveButtons;
    private boolean imagesChanged;

    private Tab mainTab;

    @FXML
    private Tab saveTab;
    @FXML
    private Label insideFx;
    @FXML
    private Label insideModuleOrFileFx;
    @FXML
    private TextField nameFx;
    @FXML
    private TextField nameInEditorFx;
    @FXML
    private ComboBox<String> attackType;
    @FXML
    private ComboBox<String> skillTypeFx;
    @FXML
    private TextField requiredStaminaFx;
    @FXML
    private TextField requiredConcentrationFx;
    @FXML
    private AutocomplitionTextField<FormulaVariable> damageFx;
    @FXML
    private ComboBox<String> mainTargetFx;
    @FXML
    private MultipleChoiceContainer<Target> targetsFx;
    @FXML
    private MultipleChoiceContainer<Element> damageElementCoefficientsFx;
    @FXML
    private MultipleChoiceContainer<BeingType> damageBeingTypesCoefficientsFx;
    @FXML
    private MultipleChoiceContainer<Size> damageSizeCoefficientsFx;
    @FXML
    private MultipleChoiceContainer<Element> elementsFx;
    @FXML
    private ComboBox<String> effectFx;
    @FXML
    private CheckBox canBeUsedInBattleFx;
    @FXML
    private CheckBox canBeUsedInCampFx;
    @FXML
    private CheckBox canBeUsedInMapFx;
    @FXML
    private CheckBox canBeBlockedFx;
    @FXML
    private CheckBox canBeResistedFx;
    @FXML
    private CheckBox canBeDodgedFx;
    @FXML
    private TextField durationTurnsFx;
    @FXML
    private TextField durationMinutesFx;
    @FXML
    private CheckBox permanentFx;
    @FXML
    private TextField actsEveryMinuteFx;
    @FXML
    private TextField actsEveryTurnFx;
    @FXML
    private TextField forcedCancelAmountFx;
    @FXML
    private ComboBox<String> forcedCancelHitsOrDamage;
    @FXML
    private ComboBox<String> forcedCancelReceivedOrDeal;
    @FXML
    private CheckBox recalculateEveryIterationFx;
    @FXML
    private SearchComboBox<SkillOverlay> onDuplicatingFx;

    // buffs:
    @FXML
    private ComboBox<String> buffTypeFx;
    @FXML
    private MultipleChoiceContainer<StatName> statsFx;

    // transformation:
    @FXML
    private MultipleChoiceContainer<BeingType> transformationBeingTypesFx;
    @FXML
    private MultipleChoiceContainer<Element> transformationElementsFx;
    @FXML
    private ComboBox<String> transformationSizeFx;
    @FXML
    private RadioButton transformationOverrideFx;

    // requirements:
    @FXML
    private MultipleChoiceContainer<StatName> statsRequirementsFx;
    @FXML
    private MultipleChoiceContainerExtended<ItemData, ItemSearchController> itemsRequirementsFx;

    // Skill chaining
    @FXML
    private MultipleChoiceContainerExtended<SkillData, SkillSearchController> skillsCanCastFx;
    @FXML
    private MultipleChoiceContainerExtended<SkillData, SkillSearchController> skillsMustCastFx;
    @FXML
    private MultipleChoiceContainer<BeingAction.Action> skillsOnBeingActionFx;
    @FXML
    private RadioButton onBeingActionCastToEnemyFx;
    @FXML
    private RadioButton onBeingActionCastToSelfFx;

    // summon:
    @FXML
    private MultipleChoiceContainerExtended<MonsterData, MonsterSearchController> summonFx;

    // receive items:
    @FXML
    private MultipleChoiceContainerExtended<ItemData, ItemSearchController> receiveItemsFx;
    @FXML
    private RadioButton keepItemsFx;
    @FXML
    private RadioButton takeItemsFx;

    // buff coefficients:
    @FXML
    private MultipleChoiceContainer<AttackType> buffAttackTypeAtkFx;
    @FXML
    private MultipleChoiceContainer<AttackType> buffAttackTypeDefFx;
    @FXML
    private MultipleChoiceContainer<BeingType> buffBeingTypeAtkFx;
    @FXML
    private MultipleChoiceContainer<BeingType> buffBeingTypeDefFx;
    @FXML
    private MultipleChoiceContainer<Element> buffElementAtkFx;
    @FXML
    private MultipleChoiceContainer<Element> buffElementDefFx;
    @FXML
    private MultipleChoiceContainer<Size> buffSizeAtkFx;
    @FXML
    private MultipleChoiceContainer<Size> buffSizeDefFx;

    // visual
    private ImagePickerController iconPicker;
    @FXML
    private AnchorPane iconAnchorPane;


    @FXML
    public void initialize() {
        wasChanged = false;
        loadSimpleComboBoxes();
        loadMultipleChoiceContainers();
        configAutoCompletionTextFields();
        configSaveButtons();
        configWasChangedListeners();
        configVisuals();
    }

    private void configVisuals() {
        try {
            FXMLLoader loader = new FXMLLoader(SkillMainViewController.class.getResource("/fxml/resource/ImagePicker.fxml"));
            VBox iconPickerNode = loader.load();
            iconPicker = loader.getController();
            iconPicker.config(this, null, 64d, 64d);
            iconAnchorPane.getChildren().add(iconPickerNode);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configWasChangedListeners() {
        setWasChangedListenerToNode(insideFx, insideModuleOrFileFx, nameFx, nameInEditorFx,
                attackType, skillTypeFx, requiredStaminaFx, requiredConcentrationFx,
                damageFx, mainTargetFx, targetsFx, damageElementCoefficientsFx, damageBeingTypesCoefficientsFx,
                damageSizeCoefficientsFx, elementsFx, effectFx, canBeUsedInBattleFx, canBeUsedInCampFx,
                canBeUsedInMapFx, canBeBlockedFx, canBeResistedFx, canBeDodgedFx, durationTurnsFx,
                durationMinutesFx, permanentFx, actsEveryMinuteFx, actsEveryTurnFx, forcedCancelAmountFx,
                forcedCancelHitsOrDamage, forcedCancelReceivedOrDeal, recalculateEveryIterationFx,
                onDuplicatingFx, buffTypeFx, statsFx, transformationBeingTypesFx, transformationElementsFx,
                transformationSizeFx, transformationOverrideFx, statsRequirementsFx, itemsRequirementsFx,
                skillsCanCastFx, skillsMustCastFx, skillsOnBeingActionFx, onBeingActionCastToEnemyFx,
                onBeingActionCastToSelfFx, summonFx, receiveItemsFx, keepItemsFx, takeItemsFx,
                buffAttackTypeAtkFx, buffAttackTypeDefFx, buffBeingTypeAtkFx, buffBeingTypeDefFx,
                buffElementAtkFx, buffElementDefFx, buffSizeAtkFx, buffSizeDefFx);
    }

    private void setWasChangedListenerToNode(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof ComboBox) {
                ((ComboBox<?>) node).valueProperty().addListener((observableValue, oldV, newV) -> {
                    if (oldV != newV) {
                        wasChanged = true;
                    }
                });
            } else if (node instanceof TextField) {
                ((TextField) node).textProperty().addListener((observableValue, oldV, newV) -> {
                    if (!oldV.equals(newV)) {
                        wasChanged = true;
                    }
                });
            } else if (node instanceof MultipleChoiceContainer) {
                ((MultipleChoiceContainer) node).getElementsObservable().addListener((ListChangeListener<?>) change -> {
                    while (change.next()) {
                        if (change.wasUpdated()) {
                            wasChanged = true;
                        }
                    }
                });
            } else if (node instanceof CheckBox) {
                ((CheckBox) node).selectedProperty().addListener((observableValue, oldV, newV) -> {
                    wasChanged = true;
                });
            } else if (node instanceof RadioButton) {
                ((RadioButton) node).selectedProperty().addListener((observableValue, oldV, newV) -> {
                    wasChanged = true;
                });
            }
        }
    }

    private void configSaveButtons() {
        // buttons creation
        this.saveButtons = new SaveButtons<>(this);
        saveTab.setGraphic(saveButtons);
        saveTab.setDisable(true);
        saveTab.setStyle("-fx-opacity: 1; -fx-background-color: transparent");
    }

    private void configAutoCompletionTextFields() {
        AutocomplitionTextFieldConfigurator.configureFormulaTextFields(damageFx);
    }

    private void loadSimpleComboBoxes() {
        attackType.setItems(EnumsLists.attackTypesString);
        attackType.setValue(AttackType.MELEE.name());
        effectFx.setItems(EnumsLists.skillEffectsString);
        effectFx.setValue(SkillEffect.NO.name());
        forcedCancelHitsOrDamage.setItems(FXCollections.observableList(Arrays.asList("Hits", "Damage")));
        forcedCancelHitsOrDamage.setValue("Hits");
        forcedCancelReceivedOrDeal.setItems(FXCollections.observableList(Arrays.asList("Received", "Deal")));
        forcedCancelReceivedOrDeal.setValue("Received");
        transformationSizeFx.setItems(EnumsLists.sizesString);
        transformationSizeFx.setValue("NO");
        buffTypeFx.setItems(EnumsLists.buffTypesString);
        buffTypeFx.setValue("PHYSIC");
        skillTypeFx.setItems(EnumsLists.skillTypesString);
        skillTypeFx.setValue(SkillType.NO_TYPE.name());
        mainTargetFx.setItems(EnumsLists.mainTargetsString);
        mainTargetFx.setValue("ENEMY");
        onDuplicatingFx.setCollection(Arrays.asList(SkillOverlay.values()));
        onDuplicatingFx.setValue(SkillOverlay.UPDATE);
    }

    private void loadMultipleChoiceContainers() {
        // simple containers:
        elementsFx.setElements(Arrays.asList(Element.values()));
        elementsFx.setUniqueElements(true);
        targetsFx.setElements(Arrays.stream(Target.values()).filter(Target::isCanBeSubTarget).collect(Collectors.toSet()));
        damageSizeCoefficientsFx.setElements(Arrays.asList(Size.values()));
        damageSizeCoefficientsFx.setUniqueElements(true);
        damageBeingTypesCoefficientsFx.setElements(Arrays.asList(BeingType.values()));
        damageBeingTypesCoefficientsFx.setUniqueElements(true);
        damageElementCoefficientsFx.setElements(Arrays.asList(Element.values()));
        damageElementCoefficientsFx.setUniqueElements(true);
        transformationElementsFx.setElements(Arrays.asList(Element.values()));
        transformationElementsFx.setUniqueElements(true);
        transformationBeingTypesFx.setElements(Arrays.asList(BeingType.values()));
        transformationBeingTypesFx.setUniqueElements(true);

        // skills chain:
        // skills can cast:
        skillsCanCastFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        skillsCanCastFx.setUniqueElements(true);
        skillsCanCastFx.setElements(Data.getSkills());
        skillsCanCastFx.setNameBy(SkillData::getNameInEditor);
        skillsCanCastFx.setSearchBy(SkillData::getName, SkillData::getNameInEditor);
        skillsCanCastFx.setExtendedSearchOptions(new FXMLLoader(SkillMainViewController.class.getResource("/fxml/skill/view/SkillSearch.fxml")), SkillSearchController.getFunctionMap());
        SearchConfigurator.skillPopupConfigurator.configure(skillsCanCastFx);

        // skills must cast:
        skillsMustCastFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        skillsMustCastFx.setUniqueElements(true);
        skillsMustCastFx.setElements(Data.getSkills());
        skillsMustCastFx.setNameBy(SkillData::getNameInEditor);
        skillsMustCastFx.setSearchBy(SkillData::getName, SkillData::getNameInEditor);
        skillsMustCastFx.setExtendedSearchOptions(new FXMLLoader(SkillMainViewController.class.getResource("/fxml/skill/view/SkillSearch.fxml")), SkillSearchController.getFunctionMap());
        SearchConfigurator.skillPopupConfigurator.configure(skillsMustCastFx);

        // skills on being action:
        skillsOnBeingActionFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.WITH_TWO_VALUES_AND_PERCENTS);
        skillsOnBeingActionFx.setUniqueElements(true);
        skillsOnBeingActionFx.setElements(Arrays.asList(BeingAction.Action.values()));
        MultipleChoiceContainerElementTwoChoice.ExtendedOptionsBuilder<SkillData, SkillSearchController> onBeingActionBuilder = MultipleChoiceContainerElementTwoChoice.extendedOptionsBuilder();
        onBeingActionBuilder.setCollection(Data.getSkills())
                .setNameByFunction(SkillData::getNameInEditor)
                .setSearchByFunction(SkillData::getName)
                .setExtendedSearchNode(SkillMainViewController.class.getResource("/fxml/skill/view/SkillSearch.fxml"))
                .setExtendedSearchFunctions(SkillSearchController.getFunctionMap());
        skillsOnBeingActionFx.setExtendedOptions(onBeingActionBuilder);

        // requirements:
        //stat requirements:
        statsRequirementsFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.WITH_TEXT_FIELD);
        statsRequirementsFx.setUniqueElements(true);
        statsRequirementsFx.setElements(List.of(StatName.values()));
        statsRequirementsFx.setNameBy(StatName::getName);
        statsRequirementsFx.setSearchBy(StatName::getName, StatName::name);

        // items requirements:
        itemsRequirementsFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.WITH_TEXT_FIELD);
        itemsRequirementsFx.setUniqueElements(true);
        itemsRequirementsFx.setElements(Data.getItems());
        itemsRequirementsFx.setNameBy(ItemData::getNameInEditor);
        itemsRequirementsFx.setSearchBy(ItemData::getName, ItemData::getNameInEditor);

        //summon:
        summonFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.BASIC);
        summonFx.setUniqueElements(true);
        summonFx.setElements(Data.getMonsters());
        summonFx.setNameBy(MonsterData::getNameInEditor);
        summonFx.setSearchBy(MonsterData::getName, MonsterData::getNameInEditor);

        // receive items:
        receiveItemsFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.BASIC);
        receiveItemsFx.setUniqueElements(true);
        receiveItemsFx.setElements(Data.getItems());
        receiveItemsFx.setNameBy(ItemData::getNameInEditor);
        receiveItemsFx.setSearchBy(ItemData::getName, ItemData::getNameInEditor);

        // stats buff:
        statsFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.WITH_AUTOFILL_TEXT_FIELD);
        statsFx.setUniqueElements(true);
        statsFx.setElements(List.of(StatName.values()));
        statsFx.setNameBy(StatName::getName);
        statsFx.setSearchBy(StatName::getName, StatName::name);
        MultipleChoiceContainerElementWithAutofillTextField.AutocomplitionTextFieldBuilder<FormulaVariable> statsBuilder =
                MultipleChoiceContainerElementWithAutofillTextField.builder();
        statsBuilder.setNameFunction(FormulaVariable::getVariable)
                .setExtendedDescriptionFunction(FormulaVariable::getDescription)
                .setCollection(FormulaVariable.getAllVariables());
        statsFx.setExtendedOptions(statsBuilder);

        // buff coefficients:
        List<AttackType> attackTypeWithoutWeaponType = new ArrayList<>(List.of(AttackType.values()));
        attackTypeWithoutWeaponType.remove(AttackType.WEAPON_TYPE);
        // attack type atk:
        buffAttackTypeAtkFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffAttackTypeAtkFx.setUniqueElements(true);
        buffAttackTypeAtkFx.setElements(attackTypeWithoutWeaponType);
        // attack type def:
        buffAttackTypeDefFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffAttackTypeDefFx.setUniqueElements(true);
        buffAttackTypeDefFx.setElements(attackTypeWithoutWeaponType);
        // being type atk:
        buffBeingTypeAtkFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffBeingTypeAtkFx.setUniqueElements(true);
        buffBeingTypeAtkFx.setElements(Arrays.asList(BeingType.values()));
        // being type def:
        buffBeingTypeDefFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffBeingTypeDefFx.setUniqueElements(true);
        buffBeingTypeDefFx.setElements(Arrays.asList(BeingType.values()));
        // elements atk:
        buffElementAtkFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffElementAtkFx.setUniqueElements(true);
        buffElementAtkFx.setElements(Arrays.asList(Element.values()));
        // elements def:
        buffElementDefFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffElementDefFx.setUniqueElements(true);
        buffElementDefFx.setElements(Arrays.asList(Element.values()));
        // size atk:
        buffSizeAtkFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffSizeAtkFx.setUniqueElements(true);
        buffSizeAtkFx.setElements(Arrays.asList(Size.values()));
        // size def:
        buffSizeDefFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        buffSizeDefFx.setUniqueElements(true);
        buffSizeDefFx.setElements(Arrays.asList(Size.values()));
    }

    @FXML
    private void showNameInEditorSameAsName() {
        if (nameInEditorFx.getText().isEmpty())
            nameInEditorFx.setPromptText(nameFx.getText());
    }

    @Override
    public void load(SkillData entityData) throws IOException {
        if (entityData != null) {
            loadSkill(entityData);
        }
    }

    private void loadSkill(SkillData skillData) throws IOException {
        this.skill = skillData;
        nameFx.setText(skillData.getName());
        attackType.setValue(skillData.getAttackType().name());
        skillTypeFx.setValue(skillData.getType().name());
        if (skillData.getNameInEditor().equals(skillData.getName())) {
            nameInEditorFx.setPromptText(skillData.getName());
        } else {
            nameInEditorFx.setText(skillData.getNameInEditor());
        }
        if (skillData.getStaminaReq() != 0) requiredStaminaFx.setText(String.valueOf(skillData.getStaminaReq()));
        if (skillData.getConcentrationReq() != 0)
            requiredConcentrationFx.setText(String.valueOf(skillData.getConcentrationReq()));
        if (!skillData.getDamage().isEmpty()) damageFx.setText(skillData.getDamage());
        effectFx.setValue(skillData.getEffect().name());
        canBeBlockedFx.setSelected(skillData.isCanBeBlocked());
        canBeDodgedFx.setSelected(skillData.isCanBeDodged());
        canBeResistedFx.setSelected(skillData.isCanBeResisted());
        canBeUsedInBattleFx.setSelected(skillData.getUsableInGameStates().get(GameState.BATTLE));
        canBeUsedInCampFx.setSelected(skillData.getUsableInGameStates().get(GameState.CAMP));
        canBeUsedInMapFx.setSelected(skillData.getUsableInGameStates().get(GameState.MAP));
        durationMinutesFx.setText(skillData.getDurationInMinutes());
        durationTurnsFx.setText(skillData.getDurationInTurns());
        actsEveryMinuteFx.setText(String.valueOf((int) skillData.getActsEveryMinute()));
        actsEveryTurnFx.setText(String.valueOf((int) skillData.getActsEveryTurn()));
        permanentFx.setSelected(skillData.isPermanent());
        recalculateEveryIterationFx.setSelected(skillData.isRecalculateStatsEveryIteration());
        onDuplicatingFx.setValue(skillData.getOverlay());
        for (Element element : skillData.getElements()) {
            elementsFx.addElement(element);
        }
        if (skillData.getDamageReceived() != null) {
            forcedCancelAmountFx.setText(skillData.getDamageReceived());
            forcedCancelReceivedOrDeal.setValue("Received");
            forcedCancelHitsOrDamage.setValue("Damage");
        } else if (skillData.getDamageMade() != null) {
            forcedCancelAmountFx.setText(skillData.getDamageMade());
            forcedCancelReceivedOrDeal.setValue("Deal");
            forcedCancelHitsOrDamage.setValue("Damage");
        } else if (skillData.getHitsReceived() != null) {
            forcedCancelAmountFx.setText(skillData.getHitsReceived());
            forcedCancelReceivedOrDeal.setValue("Received");
            forcedCancelHitsOrDamage.setValue("Hits");
        } else if (skillData.getHitsMade() != null) {
            forcedCancelAmountFx.setText(skillData.getHitsMade());
            forcedCancelReceivedOrDeal.setValue("Deal");
            forcedCancelHitsOrDamage.setValue("Hits");
        }

        // targets:
        mainTargetFx.setValue(skillData.getMainTarget().name());
        for (Target target : skillData.getTargets()) {
            targetsFx.addElement(target);
        }

        // damage coefficients:
        for (java.util.Map.Entry<Element, Double> coefficient : skillData.getCoefficients().atk().element().getCoefficientsMap().entrySet()) {
            if (coefficient.getValue() != 1d) {
                MultipleChoiceContainerElementWithPercents<Element> node = (MultipleChoiceContainerElementWithPercents<Element>) damageElementCoefficientsFx.addElement(coefficient.getKey());
                node.setTextFieldValue(String.valueOf(coefficient.getValue() * 100d).replaceFirst("\\.0+\\b", ""));
            }
        }
        for (java.util.Map.Entry<BeingType, Double> coefficient : skillData.getCoefficients().atk().beingType().getCoefficientsMap().entrySet()) {
            if (coefficient.getValue() != 1d) {
                MultipleChoiceContainerElementWithPercents<BeingType> node = (MultipleChoiceContainerElementWithPercents<BeingType>) damageBeingTypesCoefficientsFx.addElement(coefficient.getKey());
                node.setTextFieldValue(String.valueOf(coefficient.getValue() * 100d).replaceFirst("\\.0+\\b", ""));
            }
        }
        for (java.util.Map.Entry<Size, Double> coefficient : skillData.getCoefficients().atk().size().getCoefficientsMap().entrySet()) {
            if (coefficient.getValue() != 1d) {
                MultipleChoiceContainerElementWithPercents<Size> node = (MultipleChoiceContainerElementWithPercents<Size>) damageSizeCoefficientsFx.addElement(coefficient.getKey());
                node.setTextFieldValue(String.valueOf(coefficient.getValue() * 100d).replaceFirst("\\.0+\\b", ""));
            }
        }

        // transformation:
        for (BeingType beingType : skillData.getTransformation().getBeingTypes()) {
            transformationBeingTypesFx.addElement(beingType);
        }
        for (Element element : skillData.getTransformation().getElements()) {
            transformationElementsFx.addElement(element);
        }
        transformationSizeFx.setValue(skillData.getTransformation().getSize() == null ? "NO" : skillData.getTransformation().getSize().name());
        transformationOverrideFx.setSelected(skillData.getTransformation().isOverride());

        // skill chaining:
        skillData.getSkillsCouldCast().forEach((guid, chance) -> ((MultipleChoiceContainerElementWithPercents<SkillData>) skillsCanCastFx.addElement(Data.getSkillData(guid))).setTextFieldValue(String.valueOf(chance)));
        skillData.getSkillsMustCast().forEach((guid, chance) -> ((MultipleChoiceContainerElementWithPercents<SkillData>) skillsMustCastFx.addElement(Data.getSkillData(guid))).setTextFieldValue(String.valueOf(chance)));
        skillData.getSkillsOnBeingAction().forEach((action, guidChanceMap) -> {
            guidChanceMap.forEach((guid, chance) -> {
                MultipleChoiceContainerElementTwoChoiceWithPercents<BeingAction.Action, SkillData> element =
                        (MultipleChoiceContainerElementTwoChoiceWithPercents<BeingAction.Action, SkillData>) skillsOnBeingActionFx.addElement(action);
                element.setText(String.valueOf(chance));
                element.setSecondValue(Data.getSkillData(guid));
            });
        });
        if (skillData.isOnBeingActionCastToEnemy()) {
            onBeingActionCastToEnemyFx.setSelected(true);
            onBeingActionCastToSelfFx.setSelected(false);
        } else {
            onBeingActionCastToEnemyFx.setSelected(false);
            onBeingActionCastToSelfFx.setSelected(true);
        }

        // requirements:
        // stats requirements:
        skillData.getRequirements().getStats().forEachWithNestedStats(stat -> {
            if (stat.value() != 0d) {
                ((MultipleChoiceContainerElementWithTextField<StatName>) statsRequirementsFx.addElement(StatName.get(stat.getClass())))
                        .setTextFieldValue(String.valueOf(stat.value()).replaceFirst("\\.0+\\b", ""));
            }
        });
        // items requirements:
        skillData.getRequirements().getItems().forEach((guid, amount) ->
                ((MultipleChoiceContainerElementWithTextField<ItemData>) itemsRequirementsFx.addElement(Data.getItemData(guid)))
                        .setTextFieldValue(String.valueOf(amount)));
        if (skillData.getRequirements().isTakeItems()) {
            takeItemsFx.setSelected(true);
            keepItemsFx.setSelected(false);
        } else {
            keepItemsFx.setSelected(true);
            takeItemsFx.setSelected(false);
        }

        // summon:
        skillData.getSummon().forEach(guid -> summonFx.addElement(Data.getMonsterData(guid)));

        // receive items:
        skillData.getReceiveItems().forEach((guid, amount) ->
                ((MultipleChoiceContainerElementWithTextField<ItemData>) receiveItemsFx.addElement(Data.getItemData(guid)))
                        .getTextFieldNode().setText(String.valueOf(amount)));

        // buff type:
        buffTypeFx.setValue(skillData.getBuffType().name());
        // buff stats:
        skillData.getStats().forEach((statName, formula) -> {
            MultipleChoiceContainerElement<StatName> nodeElement = statsFx.addElement(statName);
            ((MultipleChoiceContainerElementWithAutofillTextField<StatName, FormulaVariable>) nodeElement)
                    .setTextFieldValue(statName == null ? formula : shortBuffField(statName.getVariableName(), formula));
        });

        // buff coefficients:
        if (skillData.getBuffCoefficients() != null) {
            // attack type atk
            skillData.getBuffCoefficients().atk().attackType().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<AttackType>) buffAttackTypeAtkFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // attack type def
            skillData.getBuffCoefficients().def().attackType().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<AttackType>) buffAttackTypeDefFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // being type atk
            skillData.getBuffCoefficients().atk().beingType().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<BeingType>) buffBeingTypeAtkFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // being type def
            skillData.getBuffCoefficients().def().beingType().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<BeingType>) buffBeingTypeDefFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // elements type atk
            skillData.getBuffCoefficients().atk().element().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<Element>) buffElementAtkFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // elements type def
            skillData.getBuffCoefficients().def().element().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<Element>) buffElementDefFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // size atk
            skillData.getBuffCoefficients().atk().size().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<Size>) buffSizeAtkFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
            // size def
            skillData.getBuffCoefficients().def().size().getCoefficientsMap().forEach((type, value) -> {
                if (value != 1d) {
                    ((MultipleChoiceContainerElementWithPercents<Size>) buffSizeDefFx.addElement(type))
                            .setTextFieldValue(String.valueOf(value * 100d).replaceFirst("\\.0+\\b", ""));
                }
            });
        }

        // visual

        // icon
        if (skillData.getResources().getSkillIcon() != null) {
            iconPicker.setImage(skillData.getResources().getSkillIcon());
        }
        wasChanged = false;
    }

    private void loadBuffFieldIfPossible(SkillData data, String fieldParsedName, Class<? extends Stat> statClass, TextField fieldFx) {
        if (data.getStats().containsKey(StatName.get(statClass))) {
            String equation = data.getStats().get(StatName.get(statClass));
            fieldFx.setText(shortBuffField(fieldParsedName, equation));
        }
    }


    private String shortBuffField(String name, String field) {
        if (
                field.startsWith(name + "+(")
                        || field.startsWith(name + "-(")
                        || field.startsWith(name + "*(")
                        || field.startsWith(name + "/(")) {
            return field.substring(0, field.length() - 1).substring(name.length()).replaceFirst("\\(", "").toUpperCase();
        } else {
            return field.toUpperCase();
        }
    }

    private String extendBuffField(String name, String field) {
        if (
                field.startsWith("+")
                        || field.startsWith("-")
                        || field.startsWith("/")
                        || field.startsWith("*")
        )
            return (name + field.substring(0, 1) + "(" + field.substring(1) + ")").toUpperCase();
        else
            return field.toUpperCase();
    }

    private boolean saveFieldsToData(SkillData skill) {
        ArrayList<String> reasonsWhyCantSave = isFieldsCorrect();
        if (reasonsWhyCantSave.size() > 0) {
            showUnableToSaveMessage(reasonsWhyCantSave);
            return false;
        }
        skill.setDescriber(false);
        skill.setName(nameFx.getText());
        skill.setAttackType(AttackType.valueOf(attackType.getValue()));
        skill.setType(SkillType.valueOf(skillTypeFx.getValue()));
        skill.setNameInEditor(nameInEditorFx.getText().isEmpty() ? nameFx.getText() : nameInEditorFx.getText());
        skill.setStaminaReq(requiredStaminaFx.getText().replaceAll(" ", "").isEmpty() ?
                0 : Integer.parseInt(requiredStaminaFx.getText().replaceAll(" ", "")));
        skill.setConcentrationReq(requiredConcentrationFx.getText().replaceAll(" ", "").isEmpty() ?
                0 : Integer.parseInt(requiredConcentrationFx.getText().replaceAll(" ", "")));
        skill.setDamage(damageFx.getText());
        skill.setEffect(SkillEffect.valueOf(effectFx.getValue()));
        skill.setCanBeBlocked(canBeBlockedFx.isSelected());
        skill.setCanBeDodged(canBeDodgedFx.isSelected());
        skill.setCanBeResisted(canBeResistedFx.isSelected());
        skill.setUsableInGameStates(Battle.class, canBeUsedInBattleFx.isSelected());
        skill.setUsableInGameStates(Camp.class, canBeUsedInCampFx.isSelected());
        skill.setUsableInGameStates(Map.class, canBeUsedInMapFx.isSelected());
        skill.setDurationInMinutes(durationMinutesFx.getText());
        skill.setDurationInTurns(durationTurnsFx.getText());
        skill.setPermanent(permanentFx.isSelected());
        skill.setRecalculateStatsEveryIteration(recalculateEveryIterationFx.isSelected());
        skill.setActsEveryTurn(actsEveryTurnFx.getText().replaceAll(" ", "").isEmpty() ?
                0d : Double.parseDouble(actsEveryTurnFx.getText().replaceAll(" ", "")));
        skill.setActsEveryMinute(actsEveryMinuteFx.getText().replaceAll(" ", "").isEmpty() ?
                0d : Double.parseDouble(actsEveryMinuteFx.getText()));

        skill.setElements(new HashSet<>(elementsFx.getElements()));
        skill.setOverlay(onDuplicatingFx.getValue());

        if (forcedCancelHitsOrDamage.getValue().equals("Hits")) {
            if (forcedCancelReceivedOrDeal.getValue().equals("Receive"))
                skill.setHitsReceived(forcedCancelAmountFx.getText());
            else if (forcedCancelReceivedOrDeal.getValue().equals("Deal"))
                skill.setHitsMade(forcedCancelAmountFx.getText());
        } else if (forcedCancelHitsOrDamage.getValue().equals("Damage")) {
            if (forcedCancelReceivedOrDeal.getValue().equals("Receive"))
                skill.setDamageReceived(forcedCancelAmountFx.getText());
            else if (forcedCancelReceivedOrDeal.getValue().equals("Deal"))
                skill.setDamageMade(forcedCancelAmountFx.getText());
        }

        // targets:
        skill.setMainTarget(Target.valueOf(mainTargetFx.getValue()));
        skill.setTargets(targetsFx.getElements());

        // damage coefficients:
        damageElementCoefficientsFx.getNodesElements()
                .forEach(node -> {
                    Element element = node.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<Element>) node).getPercents() / 100d;
                    skill.getCoefficients().atk().element().set(element, coefficient);
                });
        damageBeingTypesCoefficientsFx.getNodesElements()
                .forEach(node -> {
                    BeingType beingType = node.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<BeingType>) node).getPercents() / 100d;
                    skill.getCoefficients().atk().beingType().set(beingType, coefficient);
                });
        damageSizeCoefficientsFx.getNodesElements()
                .forEach(node -> {
                    Size size = node.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<Size>) node).getPercents() / 100d;
                    skill.getCoefficients().atk().size().set(size, coefficient);
                });

        // buff fields:
        skill.setBuffType(BuffType.valueOf(buffTypeFx.getValue()));
        if (skill.getStats() == null) {
            skill.setStats(new HashMap<>());
        }
        statsFx.getNodesElements().forEach(nodeElement ->
                skill.getStats().put(StatName.get(nodeElement.getSelectedElement().getClazz()), extendBuffField(nodeElement.getSelectedElement().getVariableName(),
                        ((MultipleChoiceContainerElementWithAutofillTextField<StatName, FormulaVariable>) nodeElement).getTextFieldValue())));


        // transformation:
        skill.getTransformation().setBeingTypes(new HashSet<>(transformationBeingTypesFx.getElements()));
        skill.getTransformation().setElements(new HashSet<>(transformationElementsFx.getElements()));
        skill.getTransformation().setSize(transformationSizeFx.getValue().equals("NO") ? null : Size.valueOf(transformationSizeFx.getValue()));
        skill.getTransformation().setOverride(transformationOverrideFx.isSelected());

        // skill chaining:
        // skills can cast:
        skill.setSkillsCouldCast(skillsCanCastFx.getNodesElements().stream()
                .collect(Collectors.toMap(
                        nodeElement -> nodeElement.getSelectedElement().getGuid(),
                        nodeElement -> (float) ((MultipleChoiceContainerElementWithPercents<SkillData>) nodeElement).getPercents(),
                        (a, b) -> a,
                        HashMap::new)));
        // skills must cast:
        skill.setSkillsCouldCast(skillsMustCastFx.getNodesElements().stream()
                .collect(Collectors.toMap(
                        nodeElement -> nodeElement.getSelectedElement().getGuid(),
                        nodeElement -> (float) ((MultipleChoiceContainerElementWithPercents<SkillData>) nodeElement).getPercents(),
                        (a, b) -> a,
                        HashMap::new)));
        // skills on being action:
        java.util.Map<BeingAction.Action, java.util.Map<Long, Float>> skillsOnBeingActionMap = new HashMap<>();
        skillsOnBeingActionFx.getNodesElements().forEach(element -> {
            java.util.Map<Long, Float> subMap;
            if (!skillsOnBeingActionMap.containsKey(element.getSelectedElement())) {
                subMap = new HashMap<>();
            } else {
                subMap = skillsOnBeingActionMap.get(element.getSelectedElement());
            }
            subMap.put(((MultipleChoiceContainerElementTwoChoiceWithPercents<BeingAction.Action, SkillData>) element).getSecondValue().getGuid(), (float) (double) ((MultipleChoiceContainerElementTwoChoiceWithPercents<BeingAction.Action, SkillData>) element).getPercents());
            skillsOnBeingActionMap.put(element.getSelectedElement(), subMap);
        });
        skill.setSkillsOnBeingAction(skillsOnBeingActionMap);
        // on being action cast to:
        skill.setOnBeingActionCastToEnemy(onBeingActionCastToEnemyFx.isSelected());
        // requirements:
        // stats requirements:
        statsRequirementsFx.getNodesElements().forEach(nodeElement -> skill.getRequirements().getStats().get(nodeElement.getSelectedElement().getClazz())
                .set(Double.parseDouble(((MultipleChoiceContainerElementWithTextField<StatName>) nodeElement).getTextFieldValue())));
        // item requirements:
        itemsRequirementsFx.getNodesElements().forEach(nodeElement -> skill.getRequirements().getItems()
                .put(nodeElement.getSelectedElement().getGuid(), Integer.parseInt(((MultipleChoiceContainerElementWithTextField<ItemData>) nodeElement).getTextFieldValue())));
        skill.getRequirements().setTakeItems(takeItemsFx.isSelected());

        // summon:
        skill.setSummon(summonFx.getNodesElements().stream().map(nodeElement -> nodeElement.getSelectedElement().getGuid()).collect(Collectors.toList()));

        // receive items:
        skill.setReceiveItems(receiveItemsFx.getNodesElements().stream()
                .collect(Collectors.toMap(
                        nodeElement -> nodeElement.getSelectedElement().getGuid(),
                        nodeElement -> Integer.parseInt(((MultipleChoiceContainerElementWithTextField<ItemData>) nodeElement).getTextFieldNode().getText()),
                        (a, b) -> a,
                        HashMap::new)));

        // buff coefficients:
        // attack type atk:
        buffAttackTypeAtkFx.getNodesElements()
                .forEach(nodeElement -> {
                    AttackType selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<AttackType>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().atk().attackType().set(selectedElement, coefficient);
                    }
                });
        // attack type def:
        buffAttackTypeDefFx.getNodesElements()
                .forEach(nodeElement -> {
                    AttackType selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<AttackType>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().def().attackType().set(selectedElement, coefficient);
                    }
                });
        // being type atk:
        buffBeingTypeAtkFx.getNodesElements()
                .forEach(nodeElement -> {
                    BeingType selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<BeingType>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().atk().beingType().set(selectedElement, coefficient);
                    }
                });
        // being type def:
        buffBeingTypeDefFx.getNodesElements()
                .forEach(nodeElement -> {
                    BeingType selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<BeingType>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().def().beingType().set(selectedElement, coefficient);
                    }
                });
        // elements atk:
        buffElementAtkFx.getNodesElements()
                .forEach(nodeElement -> {
                    Element selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<Element>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().atk().element().set(selectedElement, coefficient);
                    }
                });
        // elements def:
        buffElementDefFx.getNodesElements()
                .forEach(nodeElement -> {
                    Element selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<Element>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().def().element().set(selectedElement, coefficient);
                    }
                });
        // size atk:
        buffSizeAtkFx.getNodesElements()
                .forEach(nodeElement -> {
                    Size selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<Size>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().atk().size().set(selectedElement, coefficient);
                    }
                });
        // size def:
        buffSizeDefFx.getNodesElements()
                .forEach(nodeElement -> {
                    Size selectedElement = nodeElement.getSelectedElement();
                    double coefficient = ((MultipleChoiceContainerElementWithPercents<Size>) nodeElement).getPercents() / 100d;
                    if (coefficient != 1d) {
                        if (skill.getBuffCoefficients() == null) {
                            skill.setBuffCoefficients(new Coefficients());
                        }
                        skill.getBuffCoefficients().def().size().set(selectedElement, coefficient);
                    }
                });

        // visual
        Module insideModule = Data.getInsideModule(skill);
        // icon
        skill.getResources().setSkillIcon(iconPicker.getResource());
        if (insideModule != null) {
            ((ModuleResources) insideModule.getResources()).addImageResource(iconPicker.getResource());
        }

        wasChanged = false;
        return true;
    }

    private void saveBuffField(String fieldParsedName, TextField fieldFx, Class<? extends Stat> statClass) {
        String textField = fieldFx.getText().replaceAll(" ", "");
        if (!textField.isEmpty()) {
            skill.getStats().put(StatName.get(statClass), extendBuffField(fieldParsedName, textField));
        }
    }

    private ArrayList<String> isFieldsCorrect() {
        Being testBeing = new Player();
        SkillParser skillParser = new SkillParser(skill, testBeing, testBeing);
        ArrayList<String> messages = new ArrayList<>();
        if (nameFx.getText().replaceAll(" ", "").isEmpty()) messages.add("Field NAME is empty");
        else if (nameFx.getText().length() > 16) messages.add("Too many symbols in NAME field. Maximum is 16");
        if (nameInEditorFx.getText().length() > 20)
            messages.add("Too many symbols in NAME IN EDITOR field. Maximum is 20");
        if (!requiredStaminaFx.getText().replaceAll(" ", "").isEmpty() && !requiredStaminaFx.getText().matches("-?\\d+"))
            messages.add("Field REQUIRED STAMINA contains non numeric characters");
        if (!requiredConcentrationFx.getText().replaceAll(" ", "").isEmpty() && !requiredConcentrationFx.getText().matches("-?\\d+"))
            messages.add("Field REQUIRED CONCENTRATION contains non numeric characters");
        if (!damageFx.getText().replaceAll(" ", "").isEmpty() && !skillParser.testParse(damageFx.getText().toUpperCase()))
            messages.add("Equation in the DAMAGE field can not be parsed");
        if (!durationTurnsFx.getText().replaceAll(" ", "").isEmpty() && !durationTurnsFx.getText().matches("\\d+"))
            messages.add("Field DURATION IN TURNS contains non numeric characters or negative");
        if (!durationMinutesFx.getText().replaceAll(" ", "").isEmpty() && !durationMinutesFx.getText().matches("\\d+"))
            messages.add("Field DURATION IN MINUTES contains non numeric characters or negative");
        if (!forcedCancelAmountFx.getText().replaceAll(" ", "").isEmpty() && !skillParser.testParse(forcedCancelAmountFx.getText().toUpperCase()))
            messages.add("Equation in the FORCED CANCEL AFTER field can not be parsed");
        try {
            if (!actsEveryMinuteFx.getText().replaceAll(" ", "").isEmpty())
                Double.parseDouble(actsEveryMinuteFx.getText());
        } catch (Exception e) {
            messages.add("ACTS EVERY MINUTE field has non numeric characters");
        }
        try {
            if (!actsEveryTurnFx.getText().replaceAll(" ", "").isEmpty())
                Double.parseDouble(actsEveryTurnFx.getText());
        } catch (Exception e) {
            messages.add("ACTS EVERY TURN field has non numeric characters");
        }
        // stat buffs:
        statsFx.getNodesElements().forEach(nodeElement -> {
            String formula = ((MultipleChoiceContainerElementWithAutofillTextField<StatName, FormulaVariable>) nodeElement).getTextFieldValue();
            if (!skillParser.testParse(formula)) {
                messages.add("Equation in stat buff " + nodeElement.getSelectedElement().getName().toUpperCase() + " can not be parsed");
            }
        });
        // requirements:
        // stats requirements:
        statsRequirementsFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithTextField<StatName>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Requirement of stat " + nodeElement.getSelectedElement().getName().toUpperCase() + " is empty");
            } else {
                try {
                    Double.parseDouble(textValue);
                } catch (NumberFormatException e) {
                    messages.add("Requirement of stat " + nodeElement.getSelectedElement().getName().toUpperCase() + " has non numeric characters");
                }
            }
        });
        // items requirements:
        itemsRequirementsFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithTextField<ItemData>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Requirement amount of item " + nodeElement.getSelectedElement().getNameInEditor().toUpperCase() + " is empty");
            } else {
                try {
                    Double.parseDouble(textValue);
                } catch (NumberFormatException e) {
                    messages.add("Requirement amount of item " + nodeElement.getSelectedElement().getNameInEditor().toUpperCase() + " has non numeric characters");
                }
            }
        });

        // receive items:
        receiveItemsFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithTextField<ItemData>) nodeElement).getTextFieldNode().getText();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Receiving amount of item " + nodeElement.getSelectedElement().getNameInEditor().toUpperCase() + " is empty");
            } else {
                try {
                    Integer.parseInt(textValue);
                } catch (NumberFormatException e) {
                    messages.add("Receiving amount of item " + nodeElement.getSelectedElement().getNameInEditor().toUpperCase() + " has non numeric characters");
                }
            }
        });

        // buff coefficients:
        buffAttackTypeAtkFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<AttackType>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff attack with " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffAttackTypeDefFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<AttackType>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff defence from " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffBeingTypeAtkFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<BeingType>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff attack to " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffBeingTypeDefFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<BeingType>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff defence from " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffElementAtkFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<Element>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff attack to " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffElementDefFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<Element>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff defence from " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffSizeAtkFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<Size>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff attack to " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });
        buffSizeDefFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<Size>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field buff defence from " + nodeElement.getSelectedElement().name().toUpperCase() + " is empty");
            }
        });

        // skill chaining:
        skillsMustCastFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<SkillData>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field must cast " + nodeElement.getSelectedElement().getName().toUpperCase() + " is empty");
            }
        });
        skillsCanCastFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithPercents<SkillData>) nodeElement).getTextFieldValue();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field can cast " + nodeElement.getSelectedElement().getName().toUpperCase() + " is empty");
            }
        });
        skillsOnBeingActionFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementTwoChoiceWithPercents) nodeElement).getText();
            if (textValue == null || textValue.replaceAll(" ", "").isEmpty()) {
                messages.add("Field chance of cast on " + nodeElement.getSelectedElement().name().toUpperCase() + " being action is empty");
            }
            if (((MultipleChoiceContainerElementTwoChoiceWithPercents) nodeElement).getSecondValue() == null) {
                messages.add("Field chance of cast on " + nodeElement.getSelectedElement().name().toUpperCase() + " has no skill chosen");
            }
        });

        return messages;
    }

    private boolean isBuffFieldCorrect(SkillParser parser, String parsedName, TextField textField) {
        return textField.getText().replaceAll(" ", "").isEmpty() || parser.testParse(extendBuffField(parsedName, textField.getText()));
    }

    private void showUnableToSaveMessage(List<String> reasons) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        dialog.setTitle("Skill can not be saved");
        dialog.setHeaderText("Following fields are incorrect");
        String reasonsText = reasons.stream()
                .map(line -> line + "\r\n")
                .limit(20)
                .reduce((line1, line2) -> line1 + line2)
                .orElse("");
        dialog.setContentText(reasonsText);
        dialog.showAndWait();
    }

    @Override
    public void initNew() {
        if (skill == null) {
            skill = new SkillData(Functions.generateGuid());
            EntityEditorController.openEntities.putIfAbsent(skill, this);
        }
    }

    @Override
    public Label getInsideModule() {
        return insideFx;
    }

    @Override
    public Label getInsideModuleOrFile() {
        return insideModuleOrFileFx;
    }

    @Override
    public String getInsideFile() {
        return fileName;
    }

    @Override
    public void setInsideFile(String path) {
        fileName = path;
    }

    @Override
    public SkillData getEntityData() {
        return skill;
    }

    @Override
    public boolean save() {
        if (skill == null) skill = new SkillData(Functions.generateGuid());
        return saveFieldsToData(skill);
    }

    @Override
    public SkillData saveToNewData() {
        SkillData skillData = new SkillData(Functions.generateGuid());
        if (saveFieldsToData(skillData)) {
            return skillData;
        }
        else return null;
    }

    @Override
    public void setMainTab(Tab tab) {
        this.mainTab = tab;
    }

    @Override
    public Tab getMainTab() {
        return mainTab;
    }

    @Override
    public boolean wasChanged() {
        return wasChanged;
    }

    @Override
    public void setWasChanged(boolean value) {
        wasChanged = value;
    }

    @Override
    public void setEntityData(SkillData entityData) {
        this.skill = entityData;
    }

    @Override
    public SaveButtons<SkillData> getSaveButtons() {
        return this.saveButtons;
    }

    private List<MultipleChoiceContainerElement<? extends EntityData>> getAllEntityElementsInsideContainers() {
        List<MultipleChoiceContainerElement<? extends EntityData>> result = new ArrayList<>();
        result.addAll(itemsRequirementsFx.getNodesElements());
        result.addAll(skillsCanCastFx.getNodesElements());
        result.addAll(skillsMustCastFx.getNodesElements());
        result.addAll(summonFx.getNodesElements());
        result.addAll(receiveItemsFx.getNodesElements());
        return result;
    }

    @Override
    public boolean hasEntityDataDependency(long value) {
        return getAllEntityElementsInsideContainers().stream().anyMatch(element -> element.getSelectedElement().getGuid() == value)
                        || skillsOnBeingActionFx.getNodesElements().stream()
                        .anyMatch(element -> ((MultipleChoiceContainerElementTwoChoiceWithPercents<BeingAction.Action, SkillData>) element).getSecondValue().getGuid() == value);
    }

    @Override
    public void replaceEntityDataDependencies(long oldValue, long newValue) {
        // simple containers
        getAllEntityElementsInsideContainers().stream()
                .filter(element -> element.getSelectedElement().getGuid() == oldValue)
                .forEach(element -> element.setSelectedElement(Data.getEntityData(newValue)));
        // complex container (skills cast on being action)
        skillsOnBeingActionFx.getNodesElements().stream()
                .map(element -> ((MultipleChoiceContainerElementTwoChoiceWithPercents<BeingAction.Action, SkillData>) element))
                .filter(element -> element.getSecondValue().getGuid() == oldValue)
                .forEach(element -> element.setSecondValue(Data.getSkillData(newValue)));
    }

    @Override
    public Set<ImagePickerController> getImagePickers() {
        return Set.of(iconPicker);
    }

    @Override
    public boolean isImagesWereChanged() {
        return imagesChanged;
    }

    @Override
    public void setImagesWereChanged(boolean value) {
        this.imagesChanged = value;
    }
}
