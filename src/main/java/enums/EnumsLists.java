package enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.rdude.rpg.game.logic.enums.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumsLists {
    public static ObservableList<String> elements = FXCollections.observableList(getEnumElements());
    public static ObservableList<String> attackTypes = FXCollections.observableList(getEnumAttackTypes());
    public static ObservableList<String> skillEffects = FXCollections.observableList(getEnumSkillEffects());
    public static ObservableList<String> beingTypes = FXCollections.observableList(getEnumBeingTypes());
    public static ObservableList<String> sizes = FXCollections.observableList(getEnumSizesPlusNo());
    public static ObservableList<String> buffTypes = FXCollections.observableList(getEnumBuffTypes());
    public static ObservableList<String> skillTypes = FXCollections.observableList(getEnumSkillTypes());

    private static List<String> getEnumElements() {
        return Arrays.stream(Element.values())
                .map(Element::name)
                .collect(Collectors.toList());
    }

    private static List<String> getEnumAttackTypes() {
        return Arrays.stream(AttackType.values())
                .map(AttackType::name)
                .collect(Collectors.toList());
    }

    private static List<String> getEnumSkillEffects() {
        return Arrays.stream(SkillEffect.values())
                .map(SkillEffect::name)
                .collect(Collectors.toList());
    }

    private static List<String> getEnumBeingTypes() {
        return Arrays.stream(BeingType.values())
                .map(BeingType::name)
                .collect(Collectors.toList());
    }

    private static List<String> getEnumSizesPlusNo() {
        List<String> list = Arrays.stream(Size.values())
                .map(Size::name)
                .collect(Collectors.toList());
        list.add("NO");
        return list;
    }

    private static List<String> getEnumBuffTypes() {
        return Arrays.stream(BuffType.values())
                .map(BuffType::name)
                .collect(Collectors.toList());
    }

    private static List<String> getEnumSkillTypes() {
        return Arrays.stream(SkillType.values())
                .map(SkillType::name)
                .collect(Collectors.toList());
    }

}
