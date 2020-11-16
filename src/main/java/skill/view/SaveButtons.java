package skill.view;

import data.Data;
import data.Saver;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;
import settings.Settings;

import java.io.File;

public class SaveButtons extends HBox {

    private HasSaveButton node;

    private Button saveButton;
    private Button saveToButton;

    private ContextMenu saveToMenu;
    private MenuItem toFile;
    private MenuItem toModule;

    private SearchDialog<Module> searchDialogModule;

    public SaveButtons(HasSaveButton node) {
        this.node = node;
        // create nodes
        saveButton = new Button("Save");
        saveToButton = new Button("Save to");
        toFile = new MenuItem("file");
        toModule = new MenuItem("module");
        saveToMenu = new ContextMenu(toFile, toModule);
        searchDialogModule = new SearchDialog<>(Data.getModules());
        searchDialogModule.getSearchPane().setNameBy(Module::getNameInEditor);
        searchDialogModule.getSearchPane().setTextFieldSearchBy(Module::getNameInEditor);
        searchDialogModule.setTitle("Search modules");
        // config buttons:
        saveToButton.setOnAction(event -> saveToMenu.show(saveToButton, Side.BOTTOM, 0, 0));
        configMenuItemsPressed();
        configSaveButton();
        getChildren().addAll(saveButton, saveToButton);
    }

    private void saveToFileExtensionFilterConfig(FileChooser fileChooser) {
        if (node.getEntityData() instanceof SkillData) {
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Skill", "*.skill"));
        }
        else if (node.getEntityData() instanceof ItemData) {
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Item", "*.item"));
        }
        else if (node.getEntityData() instanceof MonsterData) {
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Monster", "*.monster"));
        }
        else if (node.getEntityData() instanceof Module) {
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Module", "*.module"));
        }
    }

    private void configMenuItemsPressed() {
        // save to module:
        toModule.setOnAction(event -> {
            searchDialogModule.showAndWait().ifPresent(result -> {
                if (node.save()) {
                    Saver.save(node.getEntityData(), result);
                    node.getInsideModuleOrFile().setText("Inside module");
                    node.getInsideModule().setText(result.getNameInEditor());
                    node.setInsideFile(null);
                    node.setInsideModuleGuid(result.getGuid());
                    node.getMainTab().setText(node.getEntityData().getNameInEditor());
                }
            });
        });
        // save to file:
        toFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save to file");
            saveToFileExtensionFilterConfig(fileChooser);
            File file = fileChooser.showSaveDialog(this.getScene().getWindow());
            if (file != null && node.save()) {
                Saver.save(node.getEntityData(), file.getPath());
                node.setInsideFile(file.getPath());
                node.setInsideModuleGuid(null);
                node.getInsideModuleOrFile().setText("Inside file");
                node.getInsideModule().setText(file.getPath());
                node.getMainTab().setText(node.getEntityData().getNameInEditor());
            }
        });
    }

    private void configSaveButton() {
        saveButton.setOnAction(event -> {
            if (node.getInsideFile() == null && node.getInsideModuleGuid() == null) {
                saveToMenu.show(saveButton, Side.BOTTOM, 0, 0);
            }
            else if (node.getInsideFile() == null && node.getInsideModuleGuid() != null) {
                if (node.save()) {
                    Data.getModules().stream()
                            .filter(module -> module.getGuid() == node.getInsideModuleGuid())
                            .findFirst()
                            .ifPresent(module -> Saver.save(node.getEntityData(), module));
                    node.getMainTab().setText(node.getEntityData().getNameInEditor());
                }
            }
            else if (node.getInsideFile() != null && node.getInsideModuleGuid() == null) {
                if (node.save()) {
                    Saver.save(node.getEntityData(), node.getInsideFile());
                    node.getMainTab().setText(node.getEntityData().getNameInEditor());
                }
            }
        });
    }
}
