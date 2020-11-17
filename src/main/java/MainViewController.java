
import data.Data;
import entity.CreateAndLoadMenu;
import entity.EntityEditorController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.SkillData;
import skill.view.SkillMainViewController;
import skill.view.SkillPopupConfigurator;
import skill.view.SkillSearchConfigurator;
import skill.view.SkillSearchController;

import java.io.IOException;

public class MainViewController {

    @FXML
    private TabPane skillsTabPane;
    @FXML
    private Tab skillCreationTab;

    @FXML
    public void initialize() throws IOException {
        skillsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

        CreateAndLoadMenu createAndLoadMenu = new CreateAndLoadMenu(skillsTabPane, EntityEditorController.Type.SKILL);
        createAndLoadMenu.setAlignment(Pos.CENTER);
        skillCreationTab.setContent(createAndLoadMenu);
    }
}
