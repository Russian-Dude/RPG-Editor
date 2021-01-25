package com.rdude.editor;

import com.rdude.editor.enums.EntityType;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class CreateAndLoadMenu extends VBox {

    private Button createNewButton;
    private Button createNewDescriptorButton;
    private Button loadFromModuleButton;
    private Button loadFromFileButton;

    public CreateAndLoadMenu(TabPane entityTabsHolder, EntityType type) {
        super();
        setSpacing(25);
        // creating buttons:
        createNewButton = new Button("CREATE NEW " + type.name());
        createNewDescriptorButton = new Button("CREATE " + type.name() + " DESCRIBER");
        loadFromModuleButton = new Button("LOAD " + type.name());
        loadFromFileButton = new Button("LOAD FILE");
        configButtonSize(createNewButton, createNewDescriptorButton, loadFromModuleButton, loadFromFileButton);
        getChildren().add(createNewButton);
        if (type != EntityType.MODULE) {
            getChildren().add(createNewDescriptorButton);
        }
        getChildren().add(loadFromModuleButton);
        getChildren().add(loadFromFileButton);
        // buttons on action:
        createNewButton.setOnAction(event -> EntityEditorCreator.createNew(type));
        if (type != EntityType.MODULE) {
            createNewDescriptorButton.setOnAction(event -> EntityEditorCreator.createNewDescriptor(type));
        }
        loadFromModuleButton.setOnAction(event -> EntityEditorCreator.loadFromModule(type));
        loadFromFileButton.setOnAction(event -> EntityEditorCreator.loadFromFile(type));
    }

    private void configButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setMinWidth(200);
            button.setMinHeight(100);
        }
    }
}
