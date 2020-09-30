package skill.view;


import enums.EnumsLists;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.entities.beings.Being;
import ru.rdude.rpg.game.logic.entities.beings.Player;
import ru.rdude.rpg.game.logic.entities.skills.SkillParser;
import ru.rdude.rpg.game.logic.enums.AttackType;
import ru.rdude.rpg.game.logic.enums.SkillEffect;
import ru.rdude.rpg.game.logic.enums.SkillType;
import ru.rdude.rpg.game.logic.gameStates.Battle;
import ru.rdude.rpg.game.logic.gameStates.Camp;
import ru.rdude.rpg.game.logic.gameStates.Map;
import ru.rdude.rpg.game.logic.stats.Stat;
import ru.rdude.rpg.game.logic.stats.Stats;
import ru.rdude.rpg.game.logic.stats.primary.*;
import ru.rdude.rpg.game.logic.stats.secondary.*;
import ru.rdude.rpg.game.utils.Functions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SkillMainViewController {


    private SkillData skill;

    private static HashMap<Pane, SkillMainViewController> elementsMapFx = new HashMap<>();


    @FXML
    private TextField nameFx;
    @FXML
    private TextField nameInEditorFx;
    @FXML
    private ComboBox<String> typeFx;
    @FXML
    private TextField requiredStaminaFx;
    @FXML
    private TextField requiredConcentrationFx;
    @FXML
    private TextField damageFx;
    @FXML
    private VBox elementsFx;
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
    private ComboBox<String> onDuplicatingFx;

    // buffs:

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


    @FXML
    public void initialize() throws IOException {
        loadSimpleComboBoxes();
        loadDefaultAdders();
    }

    private void loadSimpleComboBoxes() {
        typeFx.setItems(EnumsLists.attackTypes);
        typeFx.setValue(AttackType.MELEE.name());
        effectFx.setItems(EnumsLists.skillEffects);
        effectFx.setValue(SkillEffect.NO.name());
        forcedCancelHitsOrDamage.setItems(FXCollections.observableList(Arrays.asList("Hits", "Damage")));
        forcedCancelHitsOrDamage.setValue("Hits");
        forcedCancelReceivedOrDeal.setItems(FXCollections.observableList(Arrays.asList("Received", "Deal")));
        forcedCancelReceivedOrDeal.setValue("Received");
    }

    private void loadDefaultAdders() throws IOException {
        loadElementsAdder();
    }

    @FXML
    private void loadElementsAdder() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SkillMainViewController.class.getResource("/fxml/skill/view/ElementsAdder.fxml"));
        Pane elementsAdder = loader.load();
        elementsFx.getChildren().add(elementsFx.getChildren().size() - 1, elementsAdder);
        elementsMapFx.put(elementsAdder, this);
    }

    public static void removeElementsAdder(Pane elementsAdder) {
        elementsMapFx.get(elementsAdder).elementsFx.getChildren().remove(elementsAdder);
        elementsMapFx.remove(elementsAdder);
    }


    @FXML
    private void showNameInEditorSameAsName() {
        if (nameInEditorFx.getText().isEmpty())
            nameInEditorFx.setPromptText(nameFx.getText());
    }

    private void loadSkill(SkillData skillData) {
        this.skill = skillData;
        nameFx.setText(skillData.getName());
        typeFx.setValue(skillData.getAttackType().name());
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

        // buff fields:
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
        skill.setType(SkillType.valueOf(typeFx.getValue()));
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

        // buff fields:
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
