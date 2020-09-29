package skill.view;
import enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.Element;

import java.io.IOException;

public class ElementsAdderController {

    private Element selectedElement = Element.NEUTRAL;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> elements;

    @FXML
    public void initialize() throws IOException {
        elements.setItems(EnumsLists.elements);
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeElementsAdder(self);
    }

    @FXML
    private void setSelectedElement() {
        selectedElement = Element.valueOf(elements.getValue());
    }

    public Element getSelectedElement() {
        return selectedElement;
    }
}
