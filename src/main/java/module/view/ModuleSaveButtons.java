package module.view;

import data.Data;
import entity.EntityEditorController;
import javafx.scene.control.Button;
import skill.view.SaveButtons;

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
