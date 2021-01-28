package com.rdude.editor;

import com.rdude.editor.view.ImagePickerController;
import com.rdude.editor.view.SaveButtons;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ru.rdude.rpg.game.logic.data.EntityData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// skills / monsters / items etc
public interface EntityEditorController<T extends EntityData> {

    Map<EntityData, EntityEditorController<?>> openEntities = new HashMap<>();

    Label getInsideModule();
    Label getInsideModuleOrFile();
    String getInsideFile();
    void setInsideFile(String path);
    T getEntityData();
    void setEntityData(T entityData);
    T saveToNewData();
    boolean save();
    void load(T entityData) throws IOException;
    void setMainTab(Tab tab);
    Tab getMainTab();
    boolean wasChanged();
    void setWasChanged(boolean value);
    void initNew();
    void replaceEntityDataDependencies(long oldValue, long newValue);
    boolean hasEntityDataDependency(long value);
    SaveButtons<T> getSaveButtons();
    Set<ImagePickerController> getImagePickers();
    boolean isImagesWereChanged();
    void setImagesWereChanged(boolean value);
}
