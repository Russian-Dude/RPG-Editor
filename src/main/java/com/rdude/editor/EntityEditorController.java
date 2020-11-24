package com.rdude.editor;

import com.rdude.editor.data.Data;
import com.rdude.editor.view.SaveButtons;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// skills / monsters / items etc
public interface EntityEditorController {

    Map<EntityData, EntityEditorController> openEntities = new HashMap<>();

    enum Type {
        SKILL ("/fxml/skill/SkillMainView.fxml",
                Data.getSkills(),
                SkillData.class,
                "/fxml/skill/SkillSearch.fxml",
                MainViewController::getSkillsTabPane),

        ITEM ("/fxml/skill/view/ItemMainView.fxml",
                Data.getItems(),
                ItemData.class,
                "/fxml/skill/view/ItemSearch.fxml",
                MainViewController::getItemsTabPane),

        MONSTER ("/fxml/skill/view/MonsterMainView.fxml",
                Data.getMonsters(),
                MonsterData.class,
                "/fxml/skill/view/MonsterSearch.fxml",
                MainViewController::getMonstersTabPane),

        MODULE("/fxml/module/ModuleMainView.fxml",
                Data.getModules(),
                Module.class,
                "/fxml/module/ModuleSearch.fxml",
                MainViewController::getModulesTabPane);

        private String fxmlPath;
        private ObservableList<? extends EntityData> dataList;
        private Class<? extends EntityData> cl;
        private String fxmlPathToSearchController;
        private Supplier<TabPane> entityTabsHolder;

        Type(String fxmlPath, ObservableList<? extends EntityData> dataList, Class<? extends EntityData> cl, String fxmlPathToSearchController, Supplier<TabPane> entityTabsHolder) {
            this.fxmlPath = fxmlPath;
            this.dataList = dataList;
            this.cl = cl;
            this.fxmlPathToSearchController = fxmlPathToSearchController;
            this.entityTabsHolder = entityTabsHolder;
        }

        public String getFxmlPath() {
            return fxmlPath;
        }

        public ObservableList<? extends EntityData> getDataList() {
            return dataList;
        }

        public Class<? extends EntityData> getCl() {
            return cl;
        }

        public String getFxmlPathToSearchController() {
            return fxmlPathToSearchController;
        }

        public TabPane getEntityTabsHolder() {
            return entityTabsHolder.get();
        }
    }

    Label getInsideModule();
    Label getInsideModuleOrFile();
    Long getInsideModuleGuid();
    void setInsideModuleGuid(Long guid);
    String getInsideFile();
    void setInsideFile(String path);
    EntityData getEntityData();
    boolean save();
    void load(EntityData entityData) throws IOException;
    void setMainTab(Tab tab);
    Tab getMainTab();
    SaveButtons getSaveButtons();
    boolean wasChanged();
    void setWasChanged(boolean value);

}
