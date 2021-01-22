package com.rdude.editor;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.rdude.editor.settings.Settings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;

public class MainViewController {

    private static MainViewController mainInstance;

    @FXML
    private TabPane mainPane;

    @FXML
    private TabPane skillsTabPane;
    @FXML
    private Tab skillCreationTab;

    @FXML
    private TabPane itemsTabPane;

    @FXML
    private TabPane monstersTabPane;

    @FXML
    private TabPane modulesTabPane;
    @FXML
    private Tab moduleCreationTab;

    @FXML
    public void initialize() throws IOException {
        mainInstance = this;
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

    public static TabPane getMainPane() {
        return mainInstance.mainPane;
    }

    public static TabPane getSkillsTabPane() {
        return mainInstance.skillsTabPane;
    }

    public static TabPane getItemsTabPane() {
        return mainInstance.itemsTabPane;
    }

    public static TabPane getMonstersTabPane() {
        return mainInstance.monstersTabPane;
    }

    public static TabPane getModulesTabPane() {
        return mainInstance.modulesTabPane;
    }
}
