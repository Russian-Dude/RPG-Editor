package skill.view;
import enums.EnumsLists;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.BeingType;
import ru.rdude.rpg.game.logic.enums.Size;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SizeCoefficientsDamageAdderController {

    public static Map<Pane, SizeCoefficientsDamageAdderController> controllers = new HashMap<>();

    private Size selectedSize = Size.SMALL;
    private double coefficient = 1d;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> sizes;
    @FXML
    private TextField coefficientTextField;

    @FXML
    public void initialize() throws IOException {
        sizes.setItems(EnumsLists.sizes);
        controllers.put(self, this);
        coefficientTextField.focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (!t1) {
                setCoefficient();
            }
        }));
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeSizeCoefficientsDamageAdder(self);
        controllers.remove(self);
    }

    @FXML
    private void setSelectedSize() {
        if (Size.valueOf(sizes.getValue()) != selectedSize) {
            coefficientTextField.setText("");
            coefficient = 1d;
        }
        selectedSize = Size.valueOf(sizes.getValue());
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

    public void setSelectedSize(Size size, double coefficient) {
        selectedSize = size;
        sizes.setValue(size.name());
        this.coefficient = coefficient;
        String inPercents = String.valueOf(coefficient * 100d);
        if (Integer.parseInt(inPercents.substring(inPercents.indexOf("."))) == 0)
            inPercents = inPercents.substring(inPercents.indexOf("."));
        coefficientTextField.setText(inPercents);
    }

    public Size getSelectedSize() {
        return selectedSize;
    }
    public double getCoefficient() { return coefficient; }
}
