
import entity.CreateAndLoadMenu;
import entity.EntityEditorController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.io.IOException;

public class MainViewController {

    @FXML
    private TabPane skillsTabPane;
    @FXML
    private Tab skillCreationTab;

    @FXML
    private TabPane modulesTabPane;
    @FXML
    private Tab moduleCreationTab;

    @FXML
    public void initialize() throws IOException {
        // skills:
        skillsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        CreateAndLoadMenu skillsCreateAndLoadMenu = new CreateAndLoadMenu(skillsTabPane, EntityEditorController.Type.SKILL);
        skillsCreateAndLoadMenu.setAlignment(Pos.CENTER);
        skillCreationTab.setContent(skillsCreateAndLoadMenu);
        // modules:
        modulesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        CreateAndLoadMenu modulesCreateAndLoadMenu = new CreateAndLoadMenu(modulesTabPane, EntityEditorController.Type.MODULE);
        modulesCreateAndLoadMenu.setAlignment(Pos.CENTER);
        moduleCreationTab.setContent(modulesCreateAndLoadMenu);
    }
}
