package entity;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.rdude.rpg.game.logic.data.EntityData;
import skill.view.SaveButtons;

// skills / monsters / items etc
public interface EntityEditorController {

    enum Type {
        SKILL ("/fxml/skill/view/SkillMainView.fxml"),
        ITEM ("/fxml/skill/view/ItemMainView.fxml"),
        MONSTER ("/fxml/skill/view/MonsterMainView.fxml");

        private String fxmlPath;

        Type(String fxmlPath) {
            this.fxmlPath = fxmlPath;
        }

        public String getFxmlPath() {
            return fxmlPath;
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
