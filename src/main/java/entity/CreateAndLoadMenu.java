package entity;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class CreateAndLoadMenu extends VBox {

    private Button createNewButton;
    private Button createNewFromCopyButton;
    private Button loadFromModuleButton;
    private Button loadFromFileButton;

    public CreateAndLoadMenu(TabPane entityTabsHolder, EntityEditorController.Type type) {
        super();
        setSpacing(25);
        // creating buttons:
        createNewButton = new Button("CREATE NEW " + type.name());
        createNewFromCopyButton = new Button("COPY " + type.name());
        loadFromModuleButton = new Button("LOAD " + type.name());
        loadFromFileButton = new Button("LOAD FILE");
        configButtonSize(createNewButton, createNewFromCopyButton, loadFromModuleButton, loadFromFileButton);
        getChildren().add(createNewButton);
        getChildren().add(createNewFromCopyButton);
        getChildren().add(loadFromModuleButton);
        getChildren().add(loadFromFileButton);
        // buttons on action:
        createNewButton.setOnAction(event -> EntityEditorCreator.createNew(entityTabsHolder, type));
        loadFromModuleButton.setOnAction(event -> EntityEditorCreator.loadFromModule(entityTabsHolder, type));
    }

    private void configButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setMinWidth(200);
            button.setMinHeight(100);
        }
    }
}
