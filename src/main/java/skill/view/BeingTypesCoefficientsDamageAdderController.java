package skill.view;
import enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.BeingType;
import ru.rdude.rpg.game.logic.enums.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BeingTypesCoefficientsDamageAdderController {

    public static Map<Pane, BeingTypesCoefficientsDamageAdderController> controllers = new HashMap<>();

    private BeingType selectedBeingType = BeingType.ANIMAL;
    private double coefficient = 1d;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> beingTypes;
    @FXML
    private TextField coefficientTextField;

    @FXML
    public void initialize() throws IOException {
        beingTypes.setItems(EnumsLists.beingTypes);
        controllers.put(self, this);
        coefficientTextField.focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (!t1) {
                setCoefficient();
            }
        }));
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeBeingTypesCoefficientsDamageAdder(self);
        controllers.remove(self);
    }

    @FXML
    private void setSelectedElement() {
        if (BeingType.valueOf(beingTypes.getValue()) != selectedBeingType) {
            coefficientTextField.setText("");
            coefficient = 1d;
        }
        selectedBeingType = BeingType.valueOf(beingTypes.getValue());
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

    public void setSelectedElement(BeingType beingType, double coefficient) {
        selectedBeingType = beingType;
        beingTypes.setValue(beingType.name());
        this.coefficient = coefficient;
        String inPercents = String.valueOf(coefficient * 100d);
        if (Integer.parseInt(inPercents.substring(inPercents.indexOf("."))) == 0)
            inPercents = inPercents.substring(inPercents.indexOf("."));
        coefficientTextField.setText(inPercents);
    }

    public BeingType getSelectedBeingType() {
        return selectedBeingType;
    }
    public double getCoefficient() { return coefficient; }
}
