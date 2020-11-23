package entity;

import javafx.scene.control.Control;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.SkillData;

import java.util.Map;
import java.util.function.Function;

public interface EntitySearchController <T> {

    Map<Control, Function<T, ?>> getControlFunctionMap();
}
