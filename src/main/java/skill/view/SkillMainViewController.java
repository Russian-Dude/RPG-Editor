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

    public static void removeElementsAdder (Pane elementsAdder) {
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
        }
        else { nameInEditorFx.setText(skillData.getNameInEditor()); }
        if (skillData.getStaminaReq() != 0) requiredStaminaFx.setText(String.valueOf(skillData.getStaminaReq()));
        if (skillData.getConcentrationReq() != 0) requiredConcentrationFx.setText(String.valueOf(skillData.getConcentrationReq()));
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
        }
        else if (skillData.getDamageMade() != null) {
            forcedCancelAmountFx.setText(skillData.getDamageMade());
            forcedCancelReceivedOrDeal.setValue("Deal");
            forcedCancelHitsOrDamage.setValue("Damage");
        }
        else if (skillData.getHitsReceived() != null) {
            forcedCancelAmountFx.setText(skillData.getHitsReceived());
            forcedCancelReceivedOrDeal.setValue("Received");
            forcedCancelHitsOrDamage.setValue("Hits");
        }
        else if (skillData.getHitsMade() != null) {
            forcedCancelAmountFx.setText(skillData.getHitsMade());
            forcedCancelReceivedOrDeal.setValue("Deal");
            forcedCancelHitsOrDamage.setValue("Hits");
        }
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
        }
        else if (forcedCancelHitsOrDamage.getValue().equals("Damage")) {
            if (forcedCancelReceivedOrDeal.getValue().equals("Receive"))
                skill.setDamageReceived(forcedCancelAmountFx.getText());
            else if (forcedCancelReceivedOrDeal.getValue().equals("Deal"))
                skill.setDamageMade(forcedCancelAmountFx.getText());
        }
    }

    private ArrayList<String> isFieldsCorrect() {
        Being testBeing = new Player();
        SkillParser skillParser = new SkillParser(skill, testBeing, testBeing);
        ArrayList<String> messages = new ArrayList<>();
        if (nameFx.getText().replaceAll(" ", "").isEmpty()) messages.add("Field NAME is empty");
        else if (nameFx.getText().length() > 16) messages.add("Too many symbols in NAME field. Maximum is 16");
        if (nameInEditorFx.getText().length() > 20) messages.add("Too many symbols in NAME IN EDITOR field. Maximum is 20");
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
        return messages;
    }

    private void showUnableToSaveMessage(ArrayList<String> reasons){}

}
