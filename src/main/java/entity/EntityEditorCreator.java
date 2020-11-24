package entity;

import data.Data;
import data.io.packer.Packer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.Module;

import java.io.File;
import java.io.IOException;

public class EntityEditorCreator {

    private static Alert loadingAlert;

    public static void createNew(TabPane entityTabsHolder, EntityEditorController.Type type) {
        try {
            FXMLLoader loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPath()));
            TabPane entityNode = loader.load();
            Tab entityTab = new Tab();
            entityTab.setContent(entityNode);
            entityTab.setText("Unnamed " + type.name().toLowerCase());
            EntityEditorController controller = loader.getController();
            controller.setMainTab(entityTab);
            setTabOnCloseRequest(entityTab, controller);
            entityTabsHolder.getTabs().add(entityTabsHolder.getTabs().size() - 1, entityTab);
            entityTabsHolder.getSelectionModel().select(entityTab);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. New " + type.name().toLowerCase() + " can not be created", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void loadFromModule(TabPane entityTabsHolder, EntityEditorController.Type type) {
        try {
            SearchDialog<? extends EntityData> searchDialog = new SearchDialog<>(type.getDataList());
            SearchConfigurator.configure(searchDialog, type);
            EntityData entityData = searchDialog.showAndWait().orElse(null);
            if (entityData != null) {
                // if this entity already opened
                if (EntityEditorController.openEntities.containsKey(entityData)) {
                    entityTabsHolder.getSelectionModel().select(EntityEditorController.openEntities.get(entityData).getMainTab());
                }
                // if this entity is not already opened
                else {
                    EntityEditorController controller = createEntityNodeAndAdd(entityData, entityTabsHolder, type);
                    controller.getInsideModuleOrFile().setText("Inside module");
                    Module insideModule = Data.getInsideModule(entityData);
                    controller.getInsideModule().setText(insideModule.getNameInEditor());
                    controller.setInsideFile(null);
                    controller.setInsideModuleGuid(insideModule.getGuid());
                    EntityEditorController.openEntities.putIfAbsent(entityData, controller);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, " + type.name() + " can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void loadFromFile(TabPane entityTabsHolder, EntityEditorController.Type type) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(createExtensionFilter(type));
            File file = fileChooser.showOpenDialog(entityTabsHolder.getScene().getWindow());
            if (file != null) {
                Packer packer = new Packer();
                EntityData entityData = packer.unpack(file.getPath());
                if (entityData != null) {
                    // if this entity already opened
                    if (EntityEditorController.openEntities.containsKey(entityData)) {
                        entityTabsHolder.getSelectionModel().select(EntityEditorController.openEntities.get(entityData).getMainTab());
                    }
                    // if this entity is not already opened
                    else {
                        EntityEditorController controller = createEntityNodeAndAdd(entityData, entityTabsHolder, type);
                        controller.getInsideModuleOrFile().setText("Inside file");
                        controller.getInsideModule().setText(file.getAbsolutePath());
                        controller.setInsideFile(file.getAbsolutePath());
                        controller.setInsideModuleGuid(null);
                        Data.addEntityData(entityData);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, " + type.name() + " can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private static EntityEditorController createEntityNodeAndAdd(EntityData entityData, TabPane entityTabsHolder, EntityEditorController.Type type) throws IOException {
        FXMLLoader loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPath()));
        Tab entityTab = new Tab();
        TabPane entityNode = loader.load();
        EntityEditorController controller = loader.getController();
        controller.setMainTab(entityTab);
        entityTab.setContent(entityNode);
        entityTab.setText(entityData.getNameInEditor());
        controller.load(entityData);
        setTabOnCloseRequest(entityTab, controller);
        entityTabsHolder.getTabs().add(entityTabsHolder.getTabs().size() - 1, entityTab);
        entityTabsHolder.getSelectionModel().select(entityTab);
        return controller;
    }

    private static void setTabOnCloseRequest(Tab tab, EntityEditorController controller) {
        tab.setOnCloseRequest(event -> {
            if (controller.isWasChanged()) {
                Alert closeRequestAlert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                closeRequestAlert.setHeaderText("There are unsaved changes in " + tab.getText() + ". Save before close?");
                ButtonType result = closeRequestAlert.showAndWait().orElse(ButtonType.CANCEL);
                if (result.equals(ButtonType.CANCEL)) {
                    event.consume();
                } else if (result.equals(ButtonType.YES)) {
                    if (controller.getInsideFile() == null && controller.getInsideModuleGuid() == null) {
                        ButtonType toFile = new ButtonType("File");
                        ButtonType toModule = new ButtonType("Module");
                        Dialog<ButtonType> whereSave = new Dialog<>();
                        whereSave.setHeaderText("Save to");
                        whereSave.getDialogPane().getButtonTypes().add(toFile);
                        whereSave.getDialogPane().getButtonTypes().add(toModule);
                        whereSave.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                        ButtonType whereSaveResult = whereSave.showAndWait().orElse(ButtonType.CANCEL);
                        if (whereSaveResult.equals(toFile)) {
                            if (!controller.getSaveButtons().saveToFile()) {
                                event.consume();
                            }
                        } else if (whereSaveResult.equals(toModule)) {
                            if (!controller.getSaveButtons().saveToModule()) {
                                event.consume();
                            }
                        } else {
                            event.consume();
                        }
                    } else if (controller.getInsideFile() != null) {
                        if (!controller.getSaveButtons().saveToFile()) {
                            event.consume();
                        }
                    } else if (controller.getInsideModule() != null) {
                        if (!controller.getSaveButtons().saveToModule()) {
                            event.consume();
                        }
                    }
                }
            }
        });
    }

    private static FileChooser.ExtensionFilter createExtensionFilter(EntityEditorController.Type type) {
        FileChooser.ExtensionFilter extensionFilter;
        switch (type) {
            case SKILL:
                extensionFilter = new FileChooser.ExtensionFilter("Skill", "*.skill");
                break;
            case ITEM:
                extensionFilter = new FileChooser.ExtensionFilter("Item", "*.item");
                break;
            case MONSTER:
                extensionFilter = new FileChooser.ExtensionFilter("Monster", "*.monster");
                break;
            case MODULE:
                extensionFilter = new FileChooser.ExtensionFilter("Module", "*.module");
                break;
            default:
                extensionFilter = new FileChooser.ExtensionFilter("Any", "*.*");
                break;
        }
        return extensionFilter;
    }
}
