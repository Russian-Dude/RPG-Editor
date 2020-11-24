package com.rdude.editor;

import com.rdude.editor.data.Data;
import com.rdude.editor.view.SaveButtons;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// skills / monsters / items etc
public interface EntityEditorController {

    Map<EntityData, EntityEditorController> openEntities = new HashMap<>();

    enum Type {
        SKILL ("/fxml/skill/SkillMainView.fxml",
                Data.getSkills(),
                SkillData.class,
                "/fxml/skill/SkillSearch.fxml"),

        ITEM ("/fxml/skill/view/ItemMainView.fxml",
                Data.getItems(),
                ItemData.class,
                "/fxml/skill/view/ItemSearch.fxml"),

        MONSTER ("/fxml/skill/view/MonsterMainView.fxml",
                Data.getMonsters(),
                MonsterData.class,
                "/fxml/skill/view/MonsterSearch.fxml"),

        MODULE("/fxml/module/ModuleMainView.fxml",
                Data.getModules(),
                Module.class,
                "/fxml/module/ModuleSearch.fxml");

        private String fxmlPath;
        private ObservableList<? extends EntityData> dataList;
        private Class<? extends EntityData> cl;
        private String fxmlPathToSearchController;

        Type(String fxmlPath, ObservableList<? extends EntityData> dataList, Class<? extends EntityData> cl, String fxmlPathToSearchController) {
            this.fxmlPath = fxmlPath;
            this.dataList = dataList;
            this.cl = cl;
            this.fxmlPathToSearchController = fxmlPathToSearchController;
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
    boolean isWasChanged();
    SaveButtons getSaveButtons();

}
