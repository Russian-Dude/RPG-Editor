package skill.view;

import enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.BeingType;
import ru.rdude.rpg.game.logic.enums.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BeingTypeAdderController {

    public static Map<Pane, BeingTypeAdderController> controllers = new HashMap<>();

    private BeingType selectedBeingType = BeingType.ANIMAL;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> types;

    @FXML
    public void initialize() throws IOException {
        types.setItems(EnumsLists.beingTypes);
        controllers.put(self, this);
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeBeingTypeAdder(self);
        controllers.remove(self);
    }

    @FXML
    private void setSelectedBeingType() {
        selectedBeingType = BeingType.valueOf(types.getValue());
    }

    public void setSelectedBeingType(BeingType beingType) {
        selectedBeingType = beingType;
        types.setValue(beingType.name());
    }

    public BeingType getSelectedElement() {
        return selectedBeingType;
    }
}
