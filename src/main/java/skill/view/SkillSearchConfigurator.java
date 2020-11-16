package skill.view;

import javafx.fxml.FXMLLoader;
import ru.rdude.fxlib.containers.MultipleChoiceContainerExtended;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.SkillData;

import java.io.IOException;

public class SkillSearchConfigurator {

    public static void configure(SearchDialog<SkillData> searchDialog) throws IOException {
        searchDialog.getSearchPane().setTextFieldSearchBy(EntityData::getName, EntityData::getNameInEditor);
        searchDialog.getSearchPane().setNameBy(EntityData::getNameInEditor);
        FXMLLoader skillSearchLoader = new FXMLLoader(SkillSearchConfigurator.class.getResource("/fxml/skill/view/SkillSearch.fxml"));
        searchDialog.getSearchPane().addExtraSearchNode(skillSearchLoader.load());
        searchDialog.getSearchPane().addSearchOptions(((SkillSearchController) skillSearchLoader.getController()).getControlFunctionMap());
        SkillPopupConfigurator.configure(searchDialog);
    }
}
