package skill.view;
import enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransformationElementsAdderController {

    public static Map<Pane, TransformationElementsAdderController> controllers = new HashMap<>();

    private Element selectedElement = Element.NEUTRAL;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> elements;

    @FXML
    public void initialize() throws IOException {
        elements.setItems(EnumsLists.elements);
        controllers.put(self, this);
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeTransformationElementsAdder(self);
        controllers.remove(self);
    }

    @FXML
    private void setSelectedElement() {
        selectedElement = Element.valueOf(elements.getValue());
    }

    public void setSelectedElement(Element element) {
        selectedElement = element;
        elements.setValue(element.name());
    }

    public Element getSelectedElement() {
        return selectedElement;
    }
}
