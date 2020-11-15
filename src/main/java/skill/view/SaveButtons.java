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
                }
            }
            else if (node.getInsideFile() != null && node.getInsideModuleGuid() == null) {
                if (node.save()) {
                    Saver.save(node.getEntityData(), node.getInsideFile());
                }
            }
        });
    }

    /*
    private void configSaveButtons() {

        // save as:
        saveAsButton.setOnAction(event -> saveMenu.show(saveAsButton, Side.RIGHT, 0, 0));
        asFile.setOnAction(event -> {
            asFileMenu.show(saveAsButton, Side.RIGHT, 0, 0);
        });
        // save to module:
        toModule.setOnAction(event -> {
            // if only one module available:
            if (Data.getModules().size() == 1 && (insideModule == null || insideModule.equals(Data.getModules().get(0).getGuid()))) {
                if (Saver.save(skill, Data.getModules().get(0))) {
                    insideModule = Data.getModules().get(0).getGuid();
                    insideModuleOrFileFx.setText("Inside module");
                    insideFx.setText(Data.getModules().get(0).getNameInEditor());
                }
            }
            // if more than one module available:
            else {
                ContextMenu modulesMenu = new ContextMenu();
                Data.getModules().forEach(module -> {
                    MenuItem menuItem = new MenuItem(module.getNameInEditor());
                    menuItem.setOnAction(e -> {
                        if (Saver.save(skill, module)) {
                            insideModule = module.getGuid();
                            insideModuleOrFileFx.setText("Inside module");
                            insideFx.setText(module.getNameInEditor());
                        }
                    });
                    modulesMenu.getItems().add(menuItem);
                });
                modulesMenu.show(saveAsButton, Side.RIGHT, 0, 0);
            }
        });
        // save to default file path:
        asFileInSaveDirectory.setOnAction(event -> {
            MenuItem setNameMenuItem = new MenuItem("set name");
            setNameMenuItem.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.APPLY, ButtonType.CANCEL);
                alert.setHeaderText("Set name");
                TextField textField = new TextField();
                alert.setGraphic(textField);
                boolean notOk = true;
                while (notOk) {
                    if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.CANCEL) {
                        return;
                    } else {
                        File file = new File(Settings.getSkillsFolder() + textField.getText() + ".skill");
                        if (file.exists()) {
                            Alert existAlert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                            existAlert.setHeaderText("File with this name already exists. Overwrite?");
                            if (existAlert.showAndWait().orElse(ButtonType.NO) == ButtonType.NO) {
                                return;
                            } else {
                                if (Saver.save(skill, Settings.getSkillsFolder() + textField.getText())) {
                                    insideModuleOrFileFx.setText("Inside file");
                                    insideFx.setText(Settings.getSkillsFolder() + textField.getText());
                                }
                                notOk = false;
                            }
                        } else {
                            if (Saver.save(skill, Settings.getSkillsFolder() + textField.getText())) {
                                insideModuleOrFileFx.setText("Inside file");
                                insideFx.setText(Settings.getSkillsFolder() + textField.getText());
                            }
                            notOk = false;
                        }
                    }
                }
            });
            MenuItem useInEditorNameMenuItem = new MenuItem("use in editor name");
            useInEditorNameMenuItem.setOnAction(e -> {
                File file = new File(Settings.getSkillsFolder() + skill.getNameInEditor() + ".skill");
                if (file.exists()) {
                    Alert existAlert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                    existAlert.setHeaderText("File with this name already exists. Overwrite?");
                    if (existAlert.showAndWait().orElse(ButtonType.NO) == ButtonType.NO) {
                        return;
                    } else {
                        Saver.save(skill);
                    }
                }
            });
            ContextMenu contextMenu = new ContextMenu(setNameMenuItem, useInEditorNameMenuItem);
            contextMenu.show(saveAsButton, Side.RIGHT, 0, 0);
        });
        // save to custom folder:
        asFileToAnyPlace.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            //fileChooser.showSaveDialog()
        });


        saveTab.setGraphic(buttonsBox);
        saveTab.setDisable(true);
        saveTab.setStyle("-fx-opacity: 1; -fx-background-color: transparent");
        saveButton.setMinWidth(35);
        saveButton.setOnAction(event -> {
        });
    }
     */
}
