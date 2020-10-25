package skill.view;


import enums.EnumsLists;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import ru.rdude.fxlib.containers.MultipleChoiceContainer;
import ru.rdude.fxlib.containers.MultipleChoiceContainerElementWithPercents;
import ru.rdude.fxlib.containers.TitledMultipleChoiceContainer;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.entities.beings.Being;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
    private ComboBox<String> onDuplicatingFx;

    // buffs:
    @FXML
    private ComboBox<String> buffTypeFx;
    @FXML
    private TextField meleeMinDmgFx;
    @FXML
    private TextField meleeMaxDmgFx;
    @FXML
    private TextField rangeMinDmgFx;
    @FXML
    private TextField rangeMaxDmgFx;
    @FXML
    private TextField magicMinDmgFx;
    @FXML
    private TextField magicMaxDmgFx;
    @FXML
    private TextField timeFx;
    @FXML
    private TextField goldFx;
    @FXML
    private TextField fleeFx;
    @FXML
    private TextField expFx;
    @FXML
    private TextField intFx;
    @FXML
    private TextField dexFx;
    @FXML
    private TextField strFx;
    @FXML
    private TextField agiFx;
    @FXML
    private TextField vitFx;
    @FXML
    private TextField luckFx;
    @FXML
    private TextField critFx;
    @FXML
    private TextField parryFx;
    @FXML
    private TextField defFx;
    @FXML
    private TextField hitFx;
    @FXML
    private TextField blockFx;
    @FXML
    private TextField hpMaxFx;
    @FXML
    private TextField hpRestFx;
    @FXML
    private TextField concentrationFx;
    @FXML
    private TextField luckyDodgeFx;
    @FXML
    private TextField stmFx;
    @FXML
    private TextField stmByHitFx;
    @FXML
    private TextField stmRecoveryFx;
    @FXML
    private TextField stmMaxFx;
    @FXML
    private TextField physicResistanceFx;
    @FXML
    private TextField magicResistanceFx;

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
    private TextField reqmeleeMinDmgFx;
    @FXML
    private TextField reqmeleeMaxDmgFx;
    @FXML
    private TextField reqrangeMinDmgFx;
    @FXML
    private TextField reqrangeMaxDmgFx;
    @FXML
    private TextField reqmagicMinDmgFx;
    @FXML
    private TextField reqmagicMaxDmgFx;
    @FXML
    private TextField reqtimeFx;
    @FXML
    private TextField reqgoldFx;
    @FXML
    private TextField reqfleeFx;
    @FXML
    private TextField reqexpFx;
    @FXML
    private TextField reqintFx;
    @FXML
    private TextField reqdexFx;
    @FXML
    private TextField reqstrFx;
    @FXML
    private TextField reqagiFx;
    @FXML
    private TextField reqvitFx;
    @FXML
    private TextField reqluckFx;
    @FXML
    private TextField reqcritFx;
    @FXML
    private TextField reqparryFx;
    @FXML
    private TextField reqdefFx;
    @FXML
    private TextField reqhitFx;
    @FXML
    private TextField reqblockFx;
    @FXML
    private TextField reqhpMaxFx;
    @FXML
    private TextField reqhpRestFx;
    @FXML
    private TextField reqconcentrationFx;
    @FXML
    private TextField reqluckyDodgeFx;
    @FXML
    private TextField reqstmFx;
    @FXML
    private TextField reqstmByHitFx;
    @FXML
    private TextField reqstmRecoveryFx;
    @FXML
    private TextField reqstmMaxFx;
    @FXML
    private TextField reqphysicResistanceFx;
    @FXML
    private TextField reqmagicResistanceFx;


    @FXML
    private TitledMultipleChoiceContainer<String> testFx;

    @FXML
    public void initialize() throws IOException {
        loadSimpleComboBoxes();
        loadMultipleChoiceContainers();

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
    }

    private void loadMultipleChoiceContainers() {
        elementsFx.setAvailableElements(Arrays.asList(Element.values()));
        targetsFx.setAvailableElements(Arrays.stream(Target.values()).filter(Target::isCanBeSubTarget).collect(Collectors.toSet()));
        damageSizeCoefficientsFx.setAvailableElements(Arrays.asList(Size.values()));
        damageBeingTypesCoefficientsFx.setAvailableElements(Arrays.asList(BeingType.values()));
        damageElementCoefficientsFx.setAvailableElements(Arrays.asList(Element.values()));
        transformationElementsFx.setAvailableElements(Arrays.asList(Element.values()));
        transformationBeingTypesFx.setAvailableElements(Arrays.asList(BeingType.values()));
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

        // requirements:
        reqmeleeMinDmgFx.setText(String.valueOf((int) skillData.getRequirements().getStats().dmg().melee().minValue()));
        reqmeleeMaxDmgFx.setText(String.valueOf((int) skillData.getRequirements().getStats().dmg().melee().maxValue()));
        reqrangeMinDmgFx.setText(String.valueOf((int) skillData.getRequirements().getStats().dmg().range().minValue()));
        reqrangeMaxDmgFx.setText(String.valueOf((int) skillData.getRequirements().getStats().dmg().range().maxValue()));
        reqmagicMinDmgFx.setText(String.valueOf((int) skillData.getRequirements().getStats().dmg().magic().minValue()));
        reqmagicMaxDmgFx.setText(String.valueOf((int) skillData.getRequirements().getStats().dmg().magic().maxValue()));
        reqfleeFx.setText(String.valueOf((int) skillData.getRequirements().getStats().flee().value()));
        /*
    @FXML
    private TextField expFx;
    @FXML
    private TextField intFx;
    @FXML
    private TextField dexFx;
    @FXML
    private TextField strFx;
    @FXML
    private TextField agiFx;
    @FXML
    private TextField vitFx;
    @FXML
    private TextField luckFx;
    @FXML
    private TextField critFx;
    @FXML
    private TextField parryFx;
    @FXML
    private TextField defFx;
    @FXML
    private TextField hitFx;
    @FXML
    private TextField blockFx;
    @FXML
    private TextField hpMaxFx;
    @FXML
    private TextField hpRestFx;
    @FXML
    private TextField concentrationFx;
    @FXML
    private TextField luckyDodgeFx;
    @FXML
    private TextField stmFx;
    @FXML
    private TextField stmByHitFx;
    @FXML
    private TextField stmRecoveryFx;
    @FXML
    private TextField stmMaxFx;
    @FXML
    private TextField physicResistanceFx;
    @FXML
    private TextField magicResistanceFx;
         */
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
            messages.add("ACTS EVERY MINUTE field has not numeric characters");
        }
        try {
            if (!actsEveryTurnFx.getText().replaceAll(" ", "").isEmpty())
                Double.parseDouble(actsEveryTurnFx.getText());
        } catch (Exception e) {
            messages.add("ACTS EVERY TURN field has not numeric characters");
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

        return messages;
    }

    private boolean isBuffFieldCorrect(SkillParser parser, String parsedName, TextField textField) {
        return textField.getText().replaceAll(" ", "").isEmpty() || parser.testParse(extendBuffField(parsedName, textField.getText()));
    }

    private void showUnableToSaveMessage(ArrayList<String> reasons) {
    }

}
