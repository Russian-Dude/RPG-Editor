package skill.view;
import enums.EnumsLists;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import ru.rdude.rpg.game.logic.enums.Element;
import ru.rdude.rpg.game.logic.enums.Target;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SubTargetsAdderController {

    public static Map<Pane, SubTargetsAdderController> controllers = new HashMap<>();

    private Target selectedTarget = Target.ENEMY;

    @FXML
    private Pane self;
    @FXML
    private ComboBox<String> targets;

    @FXML
    public void initialize() throws IOException {
        targets.setItems(EnumsLists.subTargets);
        controllers.put(self, this);
    }

    @FXML
    private void remove() {
        SkillMainViewController.removeSubTargetsAdder(self);
        controllers.remove(self);
    }

    @FXML
    private void setSelectedTarget() {
        selectedTarget = Target.valueOf(targets.getValue());
    }

    public void setSelectedTarget(Target target) {
        selectedTarget = target;
        targets.setValue(target.name());
    }

    public Target getSelectedTarget() {
        return selectedTarget;
    }
}
