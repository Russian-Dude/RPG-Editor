package com.rdude.editor;

import com.rdude.editor.data.Data;
import com.rdude.editor.enums.EntityType;
import com.rdude.editor.packer.Packer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.Module;

import java.io.File;
import java.io.IOException;

public class EntityEditorCreator {

    public static void createNew(EntityType type) {
        try {
            FXMLLoader loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPathToMainView()));
            TabPane entityNode = loader.load();
            Tab entityTab = new Tab();
            entityTab.setContent(entityNode);
            entityTab.setText("Unnamed " + type.name().toLowerCase());
            EntityEditorController<?> controller = loader.getController();
            controller.setMainTab(entityTab);
            controller.initNew();
            setTabOnCloseRequest(entityTab, controller);
            type.getEntityTabsHolder().getTabs().add(type.getEntityTabsHolder().getTabs().size() - 1, entityTab);
            type.getEntityTabsHolder().getSelectionModel().select(entityTab);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. New " + type.name().toLowerCase() + " can not be created", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void createNewDescriptor(EntityType type) {
        try {
            FXMLLoader loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPathToDescriber()));
            TabPane entityNode = loader.load();
            Tab entityTab = new Tab();
            entityTab.setContent(entityNode);
            entityTab.setText("Unnamed " + type.name().toLowerCase() + " descriptor");
            EntityEditorController<?> controller = loader.getController();
            controller.setMainTab(entityTab);
            controller.initNew();
            setTabOnCloseRequest(entityTab, controller);
            type.getEntityTabsHolder().getTabs().add(type.getEntityTabsHolder().getTabs().size() - 1, entityTab);
            type.getEntityTabsHolder().getSelectionModel().select(entityTab);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. New " + type.name().toLowerCase() + " descriptor can not be created", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void loadFromModule(EntityType type) {
        try {
            SearchDialog<? extends EntityData> searchDialog = new SearchDialog<>(type.getDataList());
            SearchConfigurator.configure(searchDialog, type);
            EntityData entityData = searchDialog.showAndWait().orElse(null);
            loadFromModule(type, entityData);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, " + type.name() + " can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void loadFromModule(EntityType type, EntityData entityData) {
        try {
            if (entityData != null) {
                // if this entity already opened
                if (EntityEditorController.openEntities.containsKey(entityData)) {
                    MainViewController.getMainPane().getSelectionModel().select(
                            MainViewController.getMainPane().getTabs().stream()
                                    .filter(tab -> {
                                        if (tab.getContent() instanceof AnchorPane) {
                                            return type.getEntityTabsHolder().equals(((AnchorPane) tab.getContent()).getChildren().get(0));
                                        }
                                        else {
                                            return false;
                                        }
                                    })
                                    .findFirst()
                                    .orElse(null));
                    type.getEntityTabsHolder().getSelectionModel().select(EntityEditorController.openEntities.get(entityData).getMainTab());
                }
                // if this entity is not already opened
                else {
                    EntityEditorController<?> controller = createEntityNodeAndAdd(entityData, type.getEntityTabsHolder(), type);
                    controller.getInsideModuleOrFile().setText("Inside module");
                    Module insideModule = Data.getInsideModule(entityData);
                    controller.getInsideModule().setText(insideModule.getNameInEditor());
                    controller.setInsideFile(null);
                    EntityEditorController.openEntities.putIfAbsent(entityData, controller);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, " + type.name() + " can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static void loadFromFile(EntityType type) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(createExtensionFilter(type));
            fileChooser.setInitialDirectory(type.getInitialLoadDirectory());
            File file = fileChooser.showOpenDialog(type.getEntityTabsHolder().getScene().getWindow());
            if (file != null) {
                Packer packer = new Packer();
                EntityData entityData = packer.unpack(file.getPath());
                if (entityData != null) {
                    // if entity already exists load it to controller instead of generated by packer
                    EntityData finalEntityData = entityData;
                    entityData = type.getDataList().stream()
                            .filter(ent -> finalEntityData.getGuid() == ent.getGuid())
                            .map(ent -> ((EntityData) ent))
                            .findFirst()
                            .orElse(entityData);
                    // if this entity already opened
                    if (EntityEditorController.openEntities.containsKey(entityData)) {
                        MainViewController.getMainPane().getSelectionModel().select(
                                MainViewController.getMainPane().getTabs().stream()
                                        .filter(tab -> {
                                            if (tab.getContent() instanceof AnchorPane) {
                                                return type.getEntityTabsHolder().equals(((AnchorPane) tab.getContent()).getChildren().get(0));
                                            }
                                            else {
                                                return false;
                                            }
                                        })
                                        .findFirst()
                                        .orElse(null));
                        type.getEntityTabsHolder().getSelectionModel().select(EntityEditorController.openEntities.get(entityData).getMainTab());
                    }
                    // if this entity is not already opened
                    else {
                        EntityEditorController controller = createEntityNodeAndAdd(entityData, type.getEntityTabsHolder(), type);
                        controller.getInsideModuleOrFile().setText("Inside file");
                        controller.getInsideModule().setText(file.getAbsolutePath());
                        controller.setInsideFile(file.getAbsolutePath());
                        Data.addEntityData(entityData);
                        EntityEditorController.openEntities.putIfAbsent(entityData, controller);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, " + type.name() + " can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private static EntityEditorController createEntityNodeAndAdd(EntityData entityData, TabPane entityTabsHolder, EntityType type) throws IOException {
        FXMLLoader loader;
        if (entityData.isDescriber()) {
            loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPathToDescriber()));
        }
        else {
               loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPathToMainView()));
        }
        Tab entityTab = new Tab();
        TabPane entityNode = loader.load();
        EntityEditorController controller = loader.getController();
        controller.setMainTab(entityTab);
        entityTab.setContent(entityNode);
        entityTab.setText(entityData.getNameInEditor());
        controller.load(entityData);
        setTabOnCloseRequest(entityTab, controller);
        entityTabsHolder.getTabs().add(entityTabsHolder.getTabs().size() - 1, entityTab);
        MainViewController.getMainPane().getSelectionModel().select(
                MainViewController.getMainPane().getTabs().stream()
                        .filter(tab -> {
                            if (tab.getContent() instanceof AnchorPane) {
                                return type.getEntityTabsHolder().equals(((AnchorPane) tab.getContent()).getChildren().get(0));
                            }
                            else {
                                return false;
                            }
                        })
                        .findFirst()
                        .orElse(null));
        entityTabsHolder.getSelectionModel().select(entityTab);
        return controller;
    }

    private static void setTabOnCloseRequest(Tab tab, EntityEditorController controller) {
        tab.setOnCloseRequest(event -> {
            if (controller.wasChanged()) {
                Alert closeRequestAlert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                closeRequestAlert.setHeaderText("There are unsaved changes in " + tab.getText() + ". Save before close?");
                ButtonType result = closeRequestAlert.showAndWait().orElse(ButtonType.CANCEL);
                if (result.equals(ButtonType.CANCEL)) {
                    event.consume();
                } else if (result.equals(ButtonType.YES)) {
                    if (controller.getInsideFile() == null && Data.getInsideModule(controller.getEntityData()) == null) {
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
                            else {
                                EntityEditorController.openEntities.remove(controller.getEntityData());
                            }
                        } else if (whereSaveResult.equals(toModule)) {
                            if (!controller.getSaveButtons().saveToModule()) {
                                event.consume();
                            }
                            else {
                                EntityEditorController.openEntities.remove(controller.getEntityData());
                            }
                        } else {
                            event.consume();
                        }
                    } else if (controller.getInsideFile() != null) {
                        if (!controller.getSaveButtons().saveToFile()) {
                            event.consume();
                        }
                        else {
                            EntityEditorController.openEntities.remove(controller.getEntityData());
                        }
                    } else if (controller.getInsideModule() != null) {
                        if (!controller.getSaveButtons().saveToModule()) {
                            event.consume();
                        }
                        else {
                            EntityEditorController.openEntities.remove(controller.getEntityData());
                        }
                    }
                }
                else {
                    EntityEditorController.openEntities.remove(controller.getEntityData());
                }
            }
            else {
                EntityEditorController.openEntities.remove(controller.getEntityData());
            }
        });
    }

    private static FileChooser.ExtensionFilter createExtensionFilter(EntityType type) {
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
