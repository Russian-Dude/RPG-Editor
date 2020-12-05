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
public interface EntityEditorController<T extends EntityData> {

    Map<EntityData, EntityEditorController<?>> openEntities = new HashMap<>();

    enum Type {
        SKILL ("/fxml/skill/SkillMainView.fxml",
                "/fxml/skill/view/SkillDescriberMainView.fxml",
                Data.getSkills(),
                SkillData.class,
                "/fxml/skill/view/SkillSearch.fxml",
                MainViewController::getSkillsTabPane),

        ITEM ("/fxml/skill/view/ItemMainView.fxml",
                "/fxml/skill/view/ItemDescriberMainView.fxml",
                Data.getItems(),
                ItemData.class,
                "/fxml/skill/view/ItemSearch.fxml",
                MainViewController::getItemsTabPane),

        MONSTER ("/fxml/skill/view/MonsterMainView.fxml",
                "/fxml/skill/view/ItemDescriberMainView.fxml",
                Data.getMonsters(),
                MonsterData.class,
                "/fxml/skill/view/MonsterSearch.fxml",
                MainViewController::getMonstersTabPane),

        MODULE("/fxml/module/ModuleMainView.fxml",
                "not needed",
                Data.getModules(),
                Module.class,
                "/fxml/module/ModuleSearch.fxml",
                MainViewController::getModulesTabPane);

        private String fxmlPathToMainView;
        private String fxmlPathToDescriber;
        private ObservableList<? extends EntityData> dataList;
        private Class<? extends EntityData> cl;
        private String fxmlPathToSearchController;
        private Supplier<TabPane> entityTabsHolder;

        Type(String fxmlPathToMainView, String fxmlPathToDescriber, ObservableList<? extends EntityData> dataList, Class<? extends EntityData> cl, String fxmlPathToSearchController, Supplier<TabPane> entityTabsHolder) {
            this.fxmlPathToMainView = fxmlPathToMainView;
            this.fxmlPathToDescriber = fxmlPathToDescriber;
            this.dataList = dataList;
            this.cl = cl;
            this.fxmlPathToSearchController = fxmlPathToSearchController;
            this.entityTabsHolder = entityTabsHolder;
        }

        public String getFxmlPathToMainView() {
            return fxmlPathToMainView;
        }

        public String getFxmlPathToDescriber() {
            return fxmlPathToDescriber;
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
    String getInsideFile();
    void setInsideFile(String path);
    T getEntityData();
    void setEntityData(T entityData);
    T saveToNewData();
    boolean save();
    void load(T entityData) throws IOException;
    void setMainTab(Tab tab);
    Tab getMainTab();
    boolean wasChanged();
    void setWasChanged(boolean value);
    void initNew();
    void replaceEntityDataDependencies(long oldValue, long newValue);
    boolean hasEntityDataDependency(long value);
    SaveButtons<T> getSaveButtons();
}
