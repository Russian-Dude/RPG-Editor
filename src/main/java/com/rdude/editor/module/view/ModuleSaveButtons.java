package com.rdude.editor.module.view;

import com.rdude.editor.EntityEditorController;
import com.rdude.editor.data.Data;
import com.rdude.editor.view.SaveButtons;
import javafx.scene.control.Button;

public class ModuleSaveButtons extends SaveButtons {

    public ModuleSaveButtons(EntityEditorController node) {
        this.node = node;
        Button saveButton = new Button("Save");
        Button saveToFileButton = new Button("Save to file");
        getChildren().addAll(saveButton, saveToFileButton);
        saveToFileButton.setOnAction(event -> {
            saveToFile();
            if (!Data.getModules().contains(node.getEntityData())) {
                Data.addModule((ru.rdude.rpg.game.logic.data.Module) node.getEntityData());
            }
        });
        saveButton.setOnAction(event -> {
            if (node.getInsideFile() == null) {
                saveToFile();
                if (!Data.getModules().contains(node.getEntityData())) {
                    Data.addModule((ru.rdude.rpg.game.logic.data.Module) node.getEntityData());
                }
            }
            else {
                save();
            }
        });
    }

}
