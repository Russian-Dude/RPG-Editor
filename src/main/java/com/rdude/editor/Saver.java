package com.rdude.editor;

import com.rdude.editor.data.Data;
import com.rdude.editor.packer.Packer;
import com.rdude.editor.settings.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.Module;

import java.util.Optional;

public class Saver<T extends EntityData> {

    private Packer packer = new Packer();


    public void save(T entityData, Module module) {

        // if entity data is already inside module
        if (module.equals(Data.getInsideModule(entityData))) {
            EntityEditorController<?> controller = EntityEditorController.openEntities.get(entityData);
            if (controller != null) {
                controller.save();
                controller.setWasChanged(false);
                controller.setImagesWereChanged(false);
            }
        }

        // if entity data is not inside module
        else {
            EntityData oldData = entityData;
            EntityData newData = EntityEditorController.openEntities.containsKey(entityData) ?
                    EntityEditorController.openEntities.get(entityData).saveToNewData() : EntityCloner.clone(entityData);
            Data.addEntityData(newData, module);
            // checking dependencies
            if (module.hasEntityDependency(entityData.getGuid())) {
                String alertText = "Module " + module.getNameInEditor() + " has entities with reference to the saving entity version inside "
                + Data.getInsideModule(oldData) +" module.\r\n" + "Swap references to the saving entity so they will be presented in the same module?\r\n"
                        + "(recommended - YES)";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertText, ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get().equals(ButtonType.YES)) {
                    module.replaceEntityDependency(oldData.getGuid(), newData.getGuid());
                    EntityEditorController<Module> openModule = (EntityEditorController<Module>) EntityEditorController.openEntities.get(module);
                    if (openModule != null) {
                        openModule.replaceEntityDataDependencies(oldData.getGuid(), newData.getGuid());
                    }
                }
                // changing labels if entity data is open
                if (EntityEditorController.openEntities.containsKey(oldData)) {
                    EntityEditorController.openEntities.put(newData, EntityEditorController.openEntities.get(oldData));
                    EntityEditorController.openEntities.remove(oldData);
                    EntityEditorController.openEntities.get(newData).getInsideModuleOrFile().setText("From module");
                    EntityEditorController.openEntities.get(newData).getInsideModule().setText(module.getNameInEditor());
                }
            }
            // add entity resources to module
            // images
            newData.getResources().getImageResources().forEach(imageResource -> {
                if (imageResource != null && !module.getResources().getImageResources().contains(imageResource)) {
                    module.getResources().addImageResource(imageResource);
                    Data.getModuleState(module).setImagesWereChanged(true);
                }
            });
            // sounds
            newData.getResources().getSoundResources().forEach(soundResource -> {
                if (soundResource != null && !module.getResources().getSoundResources().contains(soundResource)) {
                    module.getResources().addSoundResource(soundResource);
                }
            });
        }

        entityData.getModuleDependencies().stream()
                .filter(dep -> !dep.equals(module.getGuid()))
                .forEach(module::addModuleDependency);

        if (Settings.isAutosaveModulesWhenEntityAdded()) {
            module.addEntity(entityData);
            packer.pack(module);
        }
    }

    public void save(T entityData, String path) {
        EntityEditorController<?> controller = EntityEditorController.openEntities.get(entityData);
        boolean isSaved = controller.save();
        if (isSaved) {
            packer.pack(entityData, path);
        }
    }
}
