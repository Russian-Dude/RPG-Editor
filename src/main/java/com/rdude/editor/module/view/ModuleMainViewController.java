package com.rdude.editor.module.view;

import com.rdude.editor.EntityEditorController;
import com.rdude.editor.EntityEditorCreator;
import com.rdude.editor.data.Data;
import com.rdude.editor.view.SaveButtons;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import ru.rdude.fxlib.panes.SearchPane;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.*;
import ru.rdude.rpg.game.utils.Functions;

import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleMainViewController implements EntityEditorController {

    private static final Label dummyLabel = new Label();

    private String insideFile;
    private Tab mainTab;
    private Module module;
    private boolean wasChanged;

    @FXML
    private TextField nameFx;
    @FXML
    private SearchPane<SkillData> skillDataSearchPane;
    @FXML
    private SearchPane<ItemData> itemDataSearchPane;
    @FXML
    private SearchPane<MonsterData> monsterDataSearchPane;
    @FXML
    private Tab saveTab;
    @FXML
    private SaveButtons saveButtons;


    private FilteredList<SkillData> skills;
    private FilteredList<ItemData> items;
    private FilteredList<MonsterData> monsters;

    @FXML
    public void initialize() throws IOException {
        saveButtons = new ModuleSaveButtons(this);
        saveTab.setGraphic(saveButtons);
        saveTab.setDisable(true);
        saveTab.setStyle("-fx-opacity: 1; -fx-background-color: transparent");
    }

    private void init() {
        // entities list
        skills = new FilteredList<>(Data.getSkills(), skillData -> module != null && module.equals(Data.getInsideModule(skillData)));
        items = new FilteredList<>(Data.getItems(), itemData -> module != null && module.equals(Data.getInsideModule(itemData)));
        monsters = new FilteredList<>(Data.getMonsters(), monsterData -> module != null && module.equals(Data.getInsideModule(monsterData)));
        // listen to changes
        skills.addListener((ListChangeListener<SkillData>) change -> {
            while (change.next()) wasChanged = true;
        });
        items.addListener((ListChangeListener<ItemData>) change -> {
            while (change.next()) wasChanged = true;
        });
        monsters.addListener((ListChangeListener<MonsterData>) change -> {
            while (change.next()) wasChanged = true;
        });
        // main search panes
        skillDataSearchPane.setCollection(skills);
        itemDataSearchPane.setCollection(items);
        monsterDataSearchPane.setCollection(monsters);
        configSearchPane(skillDataSearchPane, Type.SKILL);
        configSearchPane(itemDataSearchPane, Type.ITEM);
        configSearchPane(monsterDataSearchPane, Type.MONSTER);
    }

    private void configSearchPane(SearchPane<? extends EntityData> searchPane, Type type) {
        searchPane.setNameBy(EntityData::getNameInEditor);
        searchPane.setTextFieldSearchBy(EntityData::getNameInEditor, EntityData::getName);
        // context menu:
        searchPane.addContextMenuItem("open", entityData -> EntityEditorCreator.loadFromModule(type, entityData));
        searchPane.addContextMenuItem("delete", entityData -> {
            // remove from data:
            Data.removeEntityData(entityData);
            // if entity data open:
            EntityEditorController entityController = EntityEditorController.openEntities.get(entityData);
            if (entityController != null) {
                entityController.setWasChanged(true);
                entityController.setInsideModuleGuid(null);
                entityController.getInsideModuleOrFile().setText("");
                entityController.getInsideModule().setText("not saved");
            }
        });
    }

    @Override
    public Label getInsideModule() {
        return dummyLabel;
    }

    @Override
    public Label getInsideModuleOrFile() {
        return dummyLabel;
    }

    @Override
    public Long getInsideModuleGuid() {
        return null;
    }

    @Override
    public void setInsideModuleGuid(Long guid) {
    }

    @Override
    public String getInsideFile() {
        return insideFile;
    }

    @Override
    public void setInsideFile(String path) {
        insideFile = path;
    }

    @Override
    public EntityData getEntityData() {
        return module;
    }

    @Override
    public boolean save() {
        if (module == null) {
            module = new Module(Functions.generateGuid());
        }
        module.setName(nameFx.getText());
        module.setNameInEditor(nameFx.getText());
        module.setSkillData(new HashSet<>(skills));
        module.setItemData(new HashSet<>(items));
        module.setMonsterData(new HashSet<>(monsters));
        Stream<EntityData> dataStream = Stream.concat(skills.stream(), Stream.concat(items.stream(), monsters.stream()));
        module.setDependencies(dataStream.flatMap(entityData -> entityData.getDependencies().stream()).collect(Collectors.toSet()));
        wasChanged = false;
        return true;
    }

    @Override
    public void load(EntityData entityData) {
        if (!(entityData instanceof Module)) {
            throw new IllegalArgumentException();
        }
        module = (Module) entityData;
        init();
        nameFx.setText(module.getName());
        module.getSkillData().forEach(skillData -> Data.addEntityData(skillData, module));
        module.getItemData().forEach(itemData -> Data.addEntityData(itemData, module));
        module.getMonsterData().forEach(monsterData -> Data.addEntityData(monsterData, module));
        wasChanged = false;
    }

    @Override
    public void initNew() {
        init();
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
    public SaveButtons getSaveButtons() {
        return this.saveButtons;
    }
}
