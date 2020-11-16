
import data.Data;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public void initialize() throws IOException {
        skillsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    @FXML
    private void createNewSkill() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/skill/view/SkillMainView.fxml"));
            Tab skillTab = new Tab();
            TabPane skillTabPane = loader.load();
            ((SkillMainViewController) loader.getController()).setMainTab(skillTab);
            skillTab.setContent(skillTabPane);
            skillTab.setText("Unnamed skill");
            skillsTabPane.getTabs().add(skillsTabPane.getTabs().size() - 1, skillTab);
            skillsTabPane.getSelectionModel().select(skillTab);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. New skill can not be created", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void createNewSkillFromCopy() {

    }

    @FXML
    private void loadSkill() {
        try {
            SearchDialog<SkillData> searchDialog = new SearchDialog<>(Data.getSkills());
            SkillSearchConfigurator.configure(searchDialog);
            SkillData skillData = searchDialog.showAndWait().orElse(null);
            if (skillData != null) {
                FXMLLoader skillMainViewLoader = new FXMLLoader(Main.class.getResource("/fxml/skill/view/SkillMainView.fxml"));
                Tab skillTab = new Tab();
                TabPane skillTabPane = skillMainViewLoader.load();
                ((SkillMainViewController) skillMainViewLoader.getController()).setMainTab(skillTab);
                skillTab.setContent(skillTabPane);
                skillTab.setText(skillData.getNameInEditor());
                skillsTabPane.getTabs().add(skillsTabPane.getTabs().size() - 1, skillTab);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. Skill can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }

    }
}
