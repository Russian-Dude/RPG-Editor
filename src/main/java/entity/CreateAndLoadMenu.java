package entity;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class CreateAndLoadMenu extends VBox {

    private Button createNewButton;
    private Button createNewFromCopyButton;
    private Button loadButton;

    public CreateAndLoadMenu(TabPane entityTabsHolder, EntityEditorController.Type type) {
        super();
        setSpacing(25);
        // creating buttons:
        createNewButton = new Button("CREATE NEW " + type.name());
        createNewFromCopyButton = new Button("COPY " + type.name());
        loadButton = new Button("LOAD " + type.name());
        configButtonSize(createNewButton, createNewFromCopyButton, loadButton);
        getChildren().add(createNewButton);
        getChildren().add(createNewFromCopyButton);
        getChildren().add(loadButton);
        // buttons on action:
        createNewButton.setOnAction(event -> EntityEditorCreator.createNew(entityTabsHolder, type));
    }

    private void configButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setMinWidth(200);
            button.setMinHeight(100);
        }
    }
}
