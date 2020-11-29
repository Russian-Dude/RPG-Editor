package com.rdude.editor.view;

import com.rdude.editor.EntityEditorController;
import com.rdude.editor.Saver;
import com.rdude.editor.data.Data;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.*;
import ru.rdude.rpg.game.logic.data.Module;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class SaveButtons<T extends EntityData> extends HBox {

    protected EntityEditorController<T> node;
    private Saver<T> saver;

    private Button saveButton;
    private Button saveToButton;

    private ContextMenu saveToMenu;
    private MenuItem toFile;
    private MenuItem toModule;

    private SearchDialog<Module> searchDialogModule;

    public SaveButtons() {
        saver = new Saver<>();
    }

    public SaveButtons(EntityEditorController<T> node) {
        this.node = node;
        this.saver = new Saver<>();
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
            saver.save(node.getEntityData(), file.getPath());
            node.setInsideFile(file.getPath());
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
        AtomicBoolean saveResult = new AtomicBoolean(false);
        searchDialogModule.showAndWait().ifPresent(result -> {
            if (node.save()) {
                saver.save(node.getEntityData(), result);
                node.getInsideModuleOrFile().setText("Inside module");
                node.getInsideModule().setText(result.getNameInEditor());
                saveResult.set(true);
            }
        });
        return saveResult.get();
    }

    public boolean save() {
        if (node.getInsideFile() == null && Data.getInsideModule(node.getEntityData()) != null) {
            if (node.save()) {
                saver.save(node.getEntityData(), Data.getInsideModule(node.getEntityData()));
                node.getMainTab().setText(node.getEntityData().getNameInEditor());
                return true;
            }
        }
        else if (node.getInsideFile() != null && Data.getInsideModule(node.getEntityData()) == null) {
            if (node.save()) {
                saver.save(node.getEntityData(), node.getInsideFile());
                node.getMainTab().setText(node.getEntityData().getNameInEditor());
            }
            return true;
        }
        return false;
    }

    private void configSaveButton() {
        saveButton.setOnAction(event -> {
            if (node.getInsideFile() == null && (node.getEntityData() == null || Data.getInsideModule(node.getEntityData()) == null)) {
                saveToMenu.show(saveButton, Side.BOTTOM, 0, 0);
            }
            else {
                save();
            }
        });
    }
}
