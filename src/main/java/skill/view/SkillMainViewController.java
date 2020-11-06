package skill.view;


import data.Data;
import enums.EnumsLists;
import enums.StatNames;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import ru.rdude.fxlib.boxes.SearchComboBox;
import ru.rdude.fxlib.containers.*;
import ru.rdude.fxlib.textfields.AutocomplitionTextField;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.entities.beings.Being;
import ru.rdude.rpg.game.logic.entities.beings.BeingAction;
import ru.rdude.rpg.game.logic.entities.beings.Player;
import ru.rdude.rpg.game.logic.entities.skills.SkillParser;
import ru.rdude.rpg.game.logic.enums.*;
import ru.rdude.rpg.game.logic.gameStates.Battle;
import ru.rdude.rpg.game.logic.gameStates.Camp;
import ru.rdude.rpg.game.logic.gameStates.Map;
import ru.rdude.rpg.game.logic.stats.Stat;
import ru.rdude.rpg.game.logic.stats.primary.*;
import ru.rdude.rpg.game.logic.stats.secondary.*;
import ru.rdude.rpg.game.utils.Functions;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SkillMainViewController {


    private SkillData skill;

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
    private TextField damageFx;
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
    private AutocomplitionTextField meleeMinDmgFx;
    @FXML
    private AutocomplitionTextField meleeMaxDmgFx;
    @FXML
    private AutocomplitionTextField rangeMinDmgFx;
    @FXML
    private AutocomplitionTextField rangeMaxDmgFx;
    @FXML
    private AutocomplitionTextField magicMinDmgFx;
    @FXML
    private AutocomplitionTextField magicMaxDmgFx;
    @FXML
    private AutocomplitionTextField timeFx;
    @FXML
    private AutocomplitionTextField goldFx;
    @FXML
    private AutocomplitionTextField fleeFx;
    @FXML
    private AutocomplitionTextField expFx;
    @FXML
    private AutocomplitionTextField intFx;
    @FXML
    private AutocomplitionTextField dexFx;
    @FXML
    private AutocomplitionTextField strFx;
    @FXML
    private AutocomplitionTextField agiFx;
    @FXML
    private AutocomplitionTextField vitFx;
    @FXML
    private AutocomplitionTextField luckFx;
    @FXML
    private AutocomplitionTextField critFx;
    @FXML
    private AutocomplitionTextField parryFx;
    @FXML
    private AutocomplitionTextField defFx;
    @FXML
    private AutocomplitionTextField hitFx;
    @FXML
    private AutocomplitionTextField blockFx;
    @FXML
    private AutocomplitionTextField hpMaxFx;
    @FXML
    private AutocomplitionTextField hpRestFx;
    @FXML
    private AutocomplitionTextField concentrationFx;
    @FXML
    private AutocomplitionTextField luckyDodgeFx;
    @FXML
    private AutocomplitionTextField stmFx;
    @FXML
    private AutocomplitionTextField stmByHitFx;
    @FXML
    private AutocomplitionTextField stmRecoveryFx;
    @FXML
    private AutocomplitionTextField stmMaxFx;
    @FXML
    private AutocomplitionTextField physicResistanceFx;
    @FXML
    private AutocomplitionTextField magicResistanceFx;

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
    private MultipleChoiceContainer<StatNames> statsRequirementsFx;
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


    @FXML
    public void initialize() throws IOException {
        loadSimpleComboBoxes();
        loadMultipleChoiceContainers();
        configAutoComplitionTextFields();

    }

    private void configAutoComplitionTextFields() {
        AutocomplitionTextFieldConfigurator.configure(
                meleeMinDmgFx, meleeMaxDmgFx, rangeMinDmgFx, rangeMaxDmgFx, magicMinDmgFx, magicMaxDmgFx,
                timeFx, goldFx, fleeFx, expFx, intFx, dexFx, strFx, agiFx, vitFx, luckFx, critFx, parryFx,
                defFx, hitFx, blockFx, hpMaxFx, hpRestFx, concentrationFx, luckyDodgeFx, stmFx, stmByHitFx,
                stmRecoveryFx, stmMaxFx, physicResistanceFx, magicResistanceFx);
    }

    private void loadSimpleComboBoxes() {
        attackType.setItems(EnumsLists.attackTypes);
        attackType.setValue(AttackType.MELEE.name());
        effectFx.setItems(EnumsLists.skillEffects);
        effectFx.setValue(SkillEffect.NO.name());
        forcedCancelHitsOrDamage.setItems(FXCollections.observableList(Arrays.asList("Hits", "Damage")));
        forcedCancelHitsOrDamage.setValue("Hits");
        forcedCancelReceivedOrDeal.setItems(FXCollections.observableList(Arrays.asList("Received", "Deal")));
        forcedCancelReceivedOrDeal.setValue("Received");
        transformationSizeFx.setItems(EnumsLists.sizes);
        transformationSizeFx.setValue("NO");
        buffTypeFx.setItems(EnumsLists.buffTypes);
        buffTypeFx.setValue("PHYSIC");
        skillTypeFx.setItems(EnumsLists.skillTypes);
        skillTypeFx.setValue("NO");
        mainTargetFx.setItems(EnumsLists.mainTargets);
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
        skillsCanCastFx.setExtendedSearchOptions(SkillSearchController.getFunctionMap());
        SkillPopupConfigurator.configure(skillsCanCastFx);

        // skills must cast:
        skillsMustCastFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.PERCENT_TEXT_FIELD);
        skillsMustCastFx.setUniqueElements(true);
        skillsMustCastFx.setElements(Data.getSkills());
        skillsMustCastFx.setNameBy(SkillData::getNameInEditor);
        skillsMustCastFx.setSearchBy(SkillData::getName, SkillData::getNameInEditor);
        skillsMustCastFx.setExtendedSearchOptions(SkillSearchController.getFunctionMap());
        SkillPopupConfigurator.configure(skillsMustCastFx);

        // skills on being action:
        skillsOnBeingActionFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.WITH_TWO_VALUES_AND_PERCENTS);
        skillsOnBeingActionFx.setUniqueElements(true);
        skillsOnBeingActionFx.setElements(Arrays.asList(BeingAction.Action.values()));
        MultipleChoiceContainerElementTwoChoice.ExtendedOptionsBuilder<SkillData, SkillSearchController> onBeingActionBuilder = MultipleChoiceContainerElementTwoChoice.extendedOptionsBuilder();
        onBeingActionBuilder.setCollection(Data.getSkills())
                .setNameByFunction(SkillData::getNameInEditor)
                .setSearchByFunction(SkillData::getName)
                .setExtendedSearchNode(new FXMLLoader(SkillMainViewController.class.getResource("/fxml/skill/view/SkillSearch.fxml")))
                .setExtendedSearchFunctions(SkillSearchController.getFunctionMap());
        skillsOnBeingActionFx.setExtendedOptions(onBeingActionBuilder);

        // requirements:
        //stat requirements:
        statsRequirementsFx.setVisualElementType(MultipleChoiceContainer.VisualElementType.WITH_TEXT_FIELD);
        statsRequirementsFx.setUniqueElements(true);
        statsRequirementsFx.setElements(List.of(StatNames.values()));
        statsRequirementsFx.setNameBy(StatNames::getName);
        statsRequirementsFx.setSearchBy(StatNames::getName, StatNames::name);

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
    }

    @FXML
    private void showNameInEditorSameAsName() {
        if (nameInEditorFx.getText().isEmpty())
            nameInEditorFx.setPromptText(nameFx.getText());
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
        canBeUsedInBattleFx.setSelected(skillData.getUsableInGameStates().get(Battle.class));
        canBeUsedInCampFx.setSelected(skillData.getUsableInGameStates().get(Camp.class));
        canBeUsedInMapFx.setSelected(skillData.getUsableInGameStates().get(Map.class));
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
            MultipleChoiceContainerElementWithPercents<Element> node = (MultipleChoiceContainerElementWithPercents<Element>) damageElementCoefficientsFx.addElement(coefficient.getKey());
            node.setTextFieldValue(String.valueOf(coefficient.getValue() * 100d));
        }
        for (java.util.Map.Entry<BeingType, Double> coefficient : skillData.getCoefficients().atk().beingType().getCoefficientsMap().entrySet()) {
            MultipleChoiceContainerElementWithPercents<BeingType> node = (MultipleChoiceContainerElementWithPercents<BeingType>) damageBeingTypesCoefficientsFx.addElement(coefficient.getKey());
            node.setTextFieldValue(String.valueOf(coefficient.getValue() * 100d));
        }
        for (java.util.Map.Entry<Size, Double> coefficient : skillData.getCoefficients().atk().size().getCoefficientsMap().entrySet()) {
            MultipleChoiceContainerElementWithPercents<Size> node = (MultipleChoiceContainerElementWithPercents<Size>) damageSizeCoefficientsFx.addElement(coefficient.getKey());
            node.setTextFieldValue(String.valueOf(coefficient.getValue() * 100d));
        }

        // buff fields:
        buffTypeFx.setValue(skillData.getBuffType().name());
        loadBuffFieldIfPossible(skillData, "MELEEATKMIN", Dmg.Melee.MeleeMin.class, meleeMinDmgFx);
        loadBuffFieldIfPossible(skillData, "MELEEATKMAX", Dmg.Melee.MeleeMax.class, meleeMaxDmgFx);
        loadBuffFieldIfPossible(skillData, "RANGEATKMIN", Dmg.Range.RangeMin.class, rangeMinDmgFx);
        loadBuffFieldIfPossible(skillData, "RANGEATKMAX", Dmg.Range.RangeMax.class, rangeMaxDmgFx);
        loadBuffFieldIfPossible(skillData, "MAGICATKMIN", Dmg.Magic.MagicMin.class, magicMinDmgFx);
        loadBuffFieldIfPossible(skillData, "MAGICATKMAX", Dmg.Magic.MagicMax.class, magicMaxDmgFx);
        loadBuffFieldIfPossible(skillData, "EXP", Lvl.Exp.class, expFx);
        loadBuffFieldIfPossible(skillData, "INT", Int.class, intFx);
        loadBuffFieldIfPossible(skillData, "DEX", Dex.class, dexFx);
        loadBuffFieldIfPossible(skillData, "STR", Str.class, strFx);
        loadBuffFieldIfPossible(skillData, "AGI", Agi.class, agiFx);
        loadBuffFieldIfPossible(skillData, "VIT", Vit.class, vitFx);
        loadBuffFieldIfPossible(skillData, "LUCK", Luck.class, luckFx);
        loadBuffFieldIfPossible(skillData, "CRIT", Crit.class, critFx);
        loadBuffFieldIfPossible(skillData, "PARRY", Parry.class, parryFx);
        loadBuffFieldIfPossible(skillData, "DEF", Def.class, defFx);
        loadBuffFieldIfPossible(skillData, "HIT", Hit.class, hitFx);
        loadBuffFieldIfPossible(skillData, "BLOCK", Block.class, blockFx);
        loadBuffFieldIfPossible(skillData, "HPMAX", Hp.Max.class, hpMaxFx);
        loadBuffFieldIfPossible(skillData, "HPREST", Hp.Recovery.class, hpRestFx);
        loadBuffFieldIfPossible(skillData, "CONC", Concentration.class, concentrationFx);
        loadBuffFieldIfPossible(skillData, "LKYDODGE", Flee.LuckyDodgeChance.class, luckyDodgeFx);
        loadBuffFieldIfPossible(skillData, "FLEE", Flee.class, fleeFx);
        loadBuffFieldIfPossible(skillData, "STM", Stm.class, stmFx);
        loadBuffFieldIfPossible(skillData, "STMATK", Stm.PerHit.class, stmByHitFx);
        loadBuffFieldIfPossible(skillData, "STMREST", Stm.Recovery.class, stmRecoveryFx);
        loadBuffFieldIfPossible(skillData, "STMMAX", Stm.Max.class, stmMaxFx);
        loadBuffFieldIfPossible(skillData, "PRES", PhysicResistance.class, physicResistanceFx);
        loadBuffFieldIfPossible(skillData, "MRES", MagicResistance.class, magicResistanceFx);

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
        }
        else {
            onBeingActionCastToEnemyFx.setSelected(false);
            onBeingActionCastToSelfFx.setSelected(true);
        }

        // requirements:
        // stats requirements:
        skillData.getRequirements().getStats().forEachWithNestedStats(stat ->
                ((MultipleChoiceContainerElementWithTextField<StatNames>) statsRequirementsFx.addElement(StatNames.get(stat.getClass())))
                        .setTextFieldValue(String.valueOf(stat.value())));
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
    }

    private void loadBuffFieldIfPossible(SkillData data, String fieldParsedName, Class<? extends Stat> statClass, TextField fieldFx) {
        if (data.getStats().containsKey(statClass)) {
            String equation = data.getStats().get(statClass);
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

    private void saveSkill() {
        if (skill == null) skill = new SkillData(Functions.generateGuid());
        ArrayList<String> reasonsWhyCantSave = isFieldsCorrect();
        if (reasonsWhyCantSave.size() > 0) {
            showUnableToSaveMessage(reasonsWhyCantSave);
            return;
        }
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
        saveBuffField("MELEEATKMIN", meleeMinDmgFx, Dmg.Melee.MeleeMin.class);
        saveBuffField("MELEEATKMAX", meleeMaxDmgFx, Dmg.Melee.MeleeMax.class);
        saveBuffField("RANGEATKMIN", rangeMinDmgFx, Dmg.Range.RangeMin.class);
        saveBuffField("RANGEATKMAX", rangeMaxDmgFx, Dmg.Range.RangeMax.class);
        saveBuffField("MAGICATKMIN", magicMinDmgFx, Dmg.Magic.MagicMin.class);
        saveBuffField("MAGICATKMAX", magicMaxDmgFx, Dmg.Magic.MagicMax.class);
        saveBuffField("FLEE", fleeFx, Flee.class);
        saveBuffField("EXP", expFx, Lvl.Exp.class);
        saveBuffField("INT", intFx, Int.class);
        saveBuffField("DEX", dexFx, Dex.class);
        saveBuffField("STR", strFx, Str.class);
        saveBuffField("AGI", agiFx, Agi.class);
        saveBuffField("VIT", vitFx, Vit.class);
        saveBuffField("LUCK", luckFx, Luck.class);
        saveBuffField("CRIT", critFx, Crit.class);
        saveBuffField("PARRY", parryFx, Parry.class);
        saveBuffField("DEF", defFx, Def.class);
        saveBuffField("HIT", hitFx, Hit.class);
        saveBuffField("BLOCK", blockFx, Block.class);
        saveBuffField("HPMAX", hpMaxFx, Hp.Max.class);
        saveBuffField("HPREST", hpRestFx, Hp.Recovery.class);
        saveBuffField("CONC", concentrationFx, Concentration.class);
        saveBuffField("LKYDODGE", luckyDodgeFx, Flee.LuckyDodgeChance.class);
        saveBuffField("STM", stmFx, Stm.class);
        saveBuffField("STMATK", stmByHitFx, Stm.PerHit.class);
        saveBuffField("STMREST", stmRecoveryFx, Stm.Recovery.class);
        saveBuffField("STMMAX", stmMaxFx, Stm.Max.class);
        saveBuffField("PRES", physicResistanceFx, PhysicResistance.class);
        saveBuffField("MRES", magicResistanceFx, MagicResistance.class);

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
                .set(Double.parseDouble(((MultipleChoiceContainerElementWithTextField<StatNames>) nodeElement).getTextFieldValue())));
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
    }

    private void saveBuffField(String fieldParsedName, TextField fieldFx, Class<? extends Stat> statClass) {
        String textField = fieldFx.getText().replaceAll(" ", "");
        if (!textField.isEmpty()) {
            skill.getStats().put(statClass, extendBuffField(fieldParsedName, textField));
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
        if (!isBuffFieldCorrect(skillParser, "MELEEATKMIN", meleeMinDmgFx))
            messages.add("Equation in the MELEE MIN DAMAGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "MELEEATKMAX", meleeMaxDmgFx))
            messages.add("Equation in the MELEE MAX DAMAGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "RANGEATKMIN", rangeMinDmgFx))
            messages.add("Equation in the RANGE MIN DAMAGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "RANGEATKMAX", rangeMaxDmgFx))
            messages.add("Equation in the RANGE MAX DAMAGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "MAGICATKMIN", magicMinDmgFx))
            messages.add("Equation in the MAGIC MIN DAMAGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "MAGICATKMAX", magicMaxDmgFx))
            messages.add("Equation in the MAGIC MAX DAMAGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "FLEE", fleeFx))
            messages.add("Equation in the FLEE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "EXP", expFx))
            messages.add("Equation in the EXPERIENCE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "INT", intFx))
            messages.add("Equation in the INTELLIGENCE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "DEX", dexFx))
            messages.add("Equation in the DEXTERITY field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "STR", strFx))
            messages.add("Equation in the STRENGTH field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "AGI", agiFx))
            messages.add("Equation in the AGILITY field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "VIT", vitFx))
            messages.add("Equation in the VITALITY field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "LUCK", luckFx))
            messages.add("Equation in the LUCK field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "CRIT", critFx))
            messages.add("Equation in the CRITICAL CHANCE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "PARRY", parryFx))
            messages.add("Equation in the PARRY field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "DEF", defFx))
            messages.add("Equation in the DEFENCE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "HIT", hitFx))
            messages.add("Equation in the HIT field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "BLOCK", blockFx))
            messages.add("Equation in the BLOCK field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "HPMAX", hpMaxFx))
            messages.add("Equation in the MAXIMUM HEALTH field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "HPREST", hpRestFx))
            messages.add("Equation in the HP RESTORATION field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "CONC", concentrationFx))
            messages.add("Equation in the CONCENTRATION field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "LKYDODGE", luckyDodgeFx))
            messages.add("Equation in the LUCKY DODGE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "STM", stmFx))
            messages.add("Equation in the CURRENT STAMINA field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "STMATK", stmByHitFx))
            messages.add("Equation in the STAMINA BY HIT field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "STMREST", stmRecoveryFx))
            messages.add("Equation in the STAMINA RECOVERY field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "STMMAX", stmMaxFx))
            messages.add("Equation in the MAXIMUM STAMINA field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "PRES", physicResistanceFx))
            messages.add("Equation in the PHYSIC RESISTANCE field can not be parsed");
        if (!isBuffFieldCorrect(skillParser, "MRES", magicResistanceFx))
            messages.add("Equation in the MAGIC RESISTANCE field can not be parsed");

        // requirements:
        // stats requirements:
        statsRequirementsFx.getNodesElements().forEach(nodeElement -> {
            String textValue = ((MultipleChoiceContainerElementWithTextField<StatNames>) nodeElement).getTextFieldValue();
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

        return messages;
    }

    private boolean isBuffFieldCorrect(SkillParser parser, String parsedName, TextField textField) {
        return textField.getText().replaceAll(" ", "").isEmpty() || parser.testParse(extendBuffField(parsedName, textField.getText()));
    }

    private void showUnableToSaveMessage(ArrayList<String> reasons) {
    }

}
