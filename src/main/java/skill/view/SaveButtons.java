package skill.view;

import data.Data;
import data.Saver;
import entity.EntityEditorController;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import module.view.ModuleMainViewController;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class SaveButtons extends HBox {

    protected EntityEditorController node;

    private Button saveButton;
    private Button saveToButton;

    private ContextMenu saveToMenu;
    private MenuItem toFile;
    private MenuItem toModule;

    private SearchDialog<Module> searchDialogModule;

    public SaveButtons() {}

    public SaveButtons(EntityEditorController node) {
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
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Skill", "*.skill"));
        }
        else if (node.getEntityData() instanceof ItemData) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Item", "*.item"));
        }
        else if (node.getEntityData() instanceof MonsterData) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Monster", "*.monster"));
        }
        else if (node.getEntityData() instanceof Module) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Module", "*.module"));
        }
    }

    private void configMenuItemsPressed() {
        toModule.setOnAction(event -> saveToModule());
        toFile.setOnAction(event -> saveToFile());
    }

    public boolean saveToFile() {
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
            return true;
        }
        else {
            return false;
        }
    }

    public boolean saveToModule() {
        AtomicBoolean isSaved = new AtomicBoolean(false);
        searchDialogModule.showAndWait().ifPresent(result -> {
            if (node.save()) {
                Saver.save(node.getEntityData(), result);
                node.getInsideModuleOrFile().setText("Inside module");
                node.getInsideModule().setText(result.getNameInEditor());
                node.setInsideFile(null);
                node.setInsideModuleGuid(result.getGuid());
                node.getMainTab().setText(node.getEntityData().getNameInEditor());

                Data.addEntityData(node.getEntityData(), result);

                isSaved.set(true);
            }
        });
        return isSaved.get();
    }

    public boolean save() {
        if (node.getInsideFile() == null && node.getInsideModuleGuid() != null) {
            if (node.save()) {
                Data.getModules().stream()
                        .filter(module -> module.getGuid() == node.getInsideModuleGuid())
                        .findFirst()
                        .ifPresent(module -> Saver.save(node.getEntityData(), module));
                node.getMainTab().setText(node.getEntityData().getNameInEditor());
                return true;
            }
        }
        else if (node.getInsideFile() != null && node.getInsideModuleGuid() == null) {
            if (node.save()) {
                Saver.save(node.getEntityData(), node.getInsideFile());
                node.getMainTab().setText(node.getEntityData().getNameInEditor());
            }
            return true;
        }
        return false;
    }

    private void configSaveButton() {
        saveButton.setOnAction(event -> {
            if (node.getInsideFile() == null && node.getInsideModuleGuid() == null) {
                saveToMenu.show(saveButton, Side.BOTTOM, 0, 0);
            }
            else {
                save();
            }
        });
    }
}
