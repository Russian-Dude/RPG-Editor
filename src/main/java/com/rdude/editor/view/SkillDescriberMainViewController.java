package com.rdude.editor.view;

import com.rdude.editor.EntityEditorController;
import com.rdude.editor.enums.EnumsLists;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import ru.rdude.fxlib.containers.MultipleChoiceContainer;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.entities.beings.Being;
import ru.rdude.rpg.game.logic.entities.beings.Player;
import ru.rdude.rpg.game.logic.entities.skills.SkillParser;
import ru.rdude.rpg.game.logic.enums.*;
import ru.rdude.rpg.game.utils.Functions;

import java.io.IOException;
import java.util.*;

public class SkillDescriberMainViewController implements EntityEditorController<SkillData> {

    private String fileName;
    private SkillData skill;
    private boolean wasChanged;
    private SaveButtons<SkillData> saveButtons;

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
    private MultipleChoiceContainer<Element> elementsFx;
    @FXML
    private ComboBox<String> effectFx;
    @FXML
    private ComboBox<String> buffTypeFx;


    @FXML
    public void initialize() throws IOException {
        wasChanged = false;
        loadSimpleComboBoxes();
        loadMultipleChoiceContainers();
        configSaveButtons();
        configWasChangedListeners();
    }

    private void configWasChangedListeners() {
        setWasChangedListenerToNode(insideFx, insideModuleOrFileFx, nameFx, nameInEditorFx,
                attackType, skillTypeFx, elementsFx, effectFx, buffTypeFx);
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
        this.saveButtons = new SaveButtons(this);
        saveTab.setGraphic(saveButtons);
        saveTab.setDisable(true);
        saveTab.setStyle("-fx-opacity: 1; -fx-background-color: transparent");
    }

    private void loadSimpleComboBoxes() {
        attackType.setItems(EnumsLists.attackTypesString);
        attackType.setValue(AttackType.MELEE.name());
        effectFx.setItems(EnumsLists.skillEffectsString);
        effectFx.setValue(SkillEffect.NO.name());
        buffTypeFx.setItems(EnumsLists.buffTypesString);
        buffTypeFx.setValue("PHYSIC");
        skillTypeFx.setItems(EnumsLists.skillTypesString);
        skillTypeFx.setValue(SkillType.NO_TYPE.name());
    }

    private void loadMultipleChoiceContainers() {
        // simple containers:
        elementsFx.setElements(Arrays.asList(Element.values()));
        elementsFx.setUniqueElements(true);
        // buff coefficients:
        List<AttackType> attackTypeWithoutWeaponType = new ArrayList<>(List.of(AttackType.values()));
        attackTypeWithoutWeaponType.remove(AttackType.WEAPON_TYPE);
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
        effectFx.setValue(skillData.getEffect().name());
        for (Element element : skillData.getElements()) {
            elementsFx.addElement(element);
        }
        // buff type:
        buffTypeFx.setValue(skillData.getBuffType().name());
        wasChanged = false;
    }

    private boolean saveFieldsToData(SkillData skill) {
        ArrayList<String> reasonsWhyCantSave = isFieldsCorrect();
        if (reasonsWhyCantSave.size() > 0) {
            showUnableToSaveMessage(reasonsWhyCantSave);
            return false;
        }
        skill.setDescriber(true);
        skill.setName(nameFx.getText());
        skill.setNameInEditor(nameFx.getText());
        skill.setEffect(SkillEffect.valueOf(effectFx.getValue()));
        skill.setAttackType(AttackType.valueOf(attackType.getValue()));
        skill.setType(SkillType.valueOf(skillTypeFx.getValue()));
        skill.setNameInEditor(nameInEditorFx.getText().isEmpty() ? nameFx.getText() : nameInEditorFx.getText());
        skill.setElements(new HashSet<>(elementsFx.getElements()));
        // buff fields:
        skill.setBuffType(BuffType.valueOf(buffTypeFx.getValue()));
        // transformation (empty):
        skill.getTransformation().setBeingTypes(new HashSet<>());
        skill.getTransformation().setElements(new HashSet<>());
        skill.getTransformation().setSize(null);
        skill.getTransformation().setOverride(false);
        wasChanged = false;
        return true;
    }


    private ArrayList<String> isFieldsCorrect() {
        Being testBeing = new Player();
        SkillParser skillParser = new SkillParser(skill, testBeing, testBeing);
        ArrayList<String> messages = new ArrayList<>();
        if (nameFx.getText().replaceAll(" ", "").isEmpty()) messages.add("Field NAME is empty");
        else if (nameFx.getText().length() > 16) messages.add("Too many symbols in NAME field. Maximum is 16");
        if (nameInEditorFx.getText().length() > 20)
            messages.add("Too many symbols in NAME IN EDITOR field. Maximum is 20");
        return messages;
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
            skill.setDescriber(true);
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

    @Override
    public boolean hasEntityDataDependency(long value) {
        return false;
    }

    @Override
    public void replaceEntityDataDependencies(long oldValue, long newValue) {
        // descriptor do not has such dependencies
    }

    @Override
    public Set<ImagePickerController> getImagePickers() {
        return new HashSet<>();
    }
}
