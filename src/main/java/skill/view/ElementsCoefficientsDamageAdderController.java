package skill.view;
import enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElementsCoefficientsDamageAdderController {

    public static Map<Pane, ElementsCoefficientsDamageAdderController> controllers = new HashMap<>();

    private Element selectedElement = Element.NEUTRAL;
    private double coefficient = 1d;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> elements;
    @FXML
    private TextField coefficientTextField;

    @FXML
    public void initialize() throws IOException {
        elements.setItems(EnumsLists.elements);
        controllers.put(self, this);
        coefficientTextField.focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (!t1) {
                setCoefficient();
            }
        }));
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeElementsCoefficientsDamageAdder(self);
        controllers.remove(self);
    }

    @FXML
    private void setSelectedElement() {
        if (Element.valueOf(elements.getValue()) != selectedElement) {
            coefficientTextField.setText("");
            coefficient = 1d;
        }
        selectedElement = Element.valueOf(elements.getValue());
    }
    @FXML
    private void setCoefficient() {
        double untransformedValue = 1d;
        try {
            untransformedValue = Double.parseDouble(coefficientTextField.getText());
        }
        catch (Exception e) {
            coefficientTextField.setText("");
            return;
        }
        coefficient = untransformedValue / 100d;
    }

    public void setSelectedElement(Element element, double coefficient) {
        selectedElement = element;
        elements.setValue(element.name());
        this.coefficient = coefficient;
        String inPercents = String.valueOf(coefficient * 100d);
        if (Integer.parseInt(inPercents.substring(inPercents.indexOf("."))) == 0)
            inPercents = inPercents.substring(inPercents.indexOf("."));
        coefficientTextField.setText(inPercents);
    }

    public Element getSelectedElement() {
        return selectedElement;
    }
    public double getCoefficient() { return coefficient; }
}
