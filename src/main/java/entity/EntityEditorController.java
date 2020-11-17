package entity;

import data.Data;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;
import skill.view.SaveButtons;

// skills / monsters / items etc
public interface EntityEditorController {

    enum Type {
        SKILL ("/fxml/skill/view/SkillMainView.fxml", Data.getSkills(), SkillData.class),
        ITEM ("/fxml/skill/view/ItemMainView.fxml", Data.getItems(), ItemData.class),
        MONSTER ("/fxml/skill/view/MonsterMainView.fxml", Data.getMonsters(), MonsterData.class),
        MODULE("/fxml/skill/view/ModuleMainView.fxml", Data.getModules(), Module.class);

        private String fxmlPath;
        private ObservableList<? extends EntityData> dataList;
        private Class<? extends EntityData> cl;

        Type(String fxmlPath, ObservableList<? extends EntityData> dataList, Class<? extends EntityData> cl) {
            this.fxmlPath = fxmlPath;
            this.dataList = dataList;
            this.cl = cl;
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
    }

    Label getInsideModule();
    Label getInsideModuleOrFile();
    Long getInsideModuleGuid();
    void setInsideModuleGuid(Long guid);
    String getInsideFile();
    void setInsideFile(String path);
    EntityData getEntityData();
    boolean save();
    void setMainTab(Tab tab);
    Tab getMainTab();
    boolean isWasChanged();
    SaveButtons getSaveButtons();

}
