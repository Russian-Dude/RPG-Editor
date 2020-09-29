
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class MainViewController {
    @FXML
    private TabPane skillsTabPane;

    @FXML
    private Label testLabel;


    @FXML
    public void initialize() throws IOException {
        showSkill();
    }

    private void showSkill() throws IOException {
        Tab defaultTab = new Tab();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/skill/view/SkillMainView.fxml"));
        TabPane defaultSkill = loader.load();
        defaultTab.setContent(defaultSkill);
        defaultTab.setText("Default Skill");
        skillsTabPane.getTabs().add(0, defaultTab);
        skillsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }
}
