package skill.view;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.rdude.rpg.game.logic.data.EntityData;

public interface HasSaveButton {

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

}
