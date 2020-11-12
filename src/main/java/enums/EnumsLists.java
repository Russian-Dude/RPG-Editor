package enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.rdude.rpg.game.logic.entities.skills.Buff;
import ru.rdude.rpg.game.logic.enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumsLists {
    public static ObservableList<String> elementsString = FXCollections.observableList(getEnumElements());
    public static ObservableList<Element> elements = FXCollections.observableArrayList(List.of(Element.values()));
    public static ObservableList<String> attackTypesString = FXCollections.observableList(getEnumAttackTypes());
    public static ObservableList<AttackType> attackTypes = FXCollections.observableList(List.of(AttackType.values()));
    public static ObservableList<String> attackTypesStringWithNull = FXCollections.observableList(getAttackTypesWithNull());
    public static ObservableList<String> skillEffectsString = FXCollections.observableList(getEnumSkillEffects());
    public static ObservableList<SkillEffect> skillEffects = FXCollections.observableList(List.of(SkillEffect.values()));
    public static ObservableList<String> skillEffectsStringWithNull = FXCollections.observableList(getSkillEffectsWithNull());
    public static ObservableList<String> beingTypesString = FXCollections.observableList(getEnumBeingTypes());
    public static ObservableList<BeingType> beingTypes = FXCollections.observableList(List.of(BeingType.values()));
    public static ObservableList<String> sizesString = FXCollections.observableList(getEnumSizesPlusNo());
    public static ObservableList<Size> sizes = FXCollections.observableList(List.of(Size.values()));
    public static ObservableList<String> buffTypesString = FXCollections.observableList(getEnumBuffTypes());
    public static ObservableList<BuffType> buffTypes = FXCollections.observableList(List.of(BuffType.values()));
    public static ObservableList<String> skillTypesString = FXCollections.observableList(getEnumSkillTypes());
    public static ObservableList<SkillType> skillTypes = FXCollections.observableList(List.of(SkillType.values()));
    public static ObservableList<String> skillTypesStringWithNull = FXCollections.observableList(getSkillTypesWithNull());
    public static ObservableList<String> mainTargetsString = FXCollections.observableList(getEnumMainTargets());
    public static ObservableList<String> subTargetsString = FXCollections.observableList(getEnumSubTargets());
    public static ObservableList<Target> targetsAll = FXCollections.observableList(List.of(Target.values()));

    private static List<String> getSkillTypesWithNull() {
        List<String> list = List.of(SkillType.values()).stream().map(SkillType::name).collect(Collectors.toList());
        list.add(null);
        return list;
    }

    private static List<String> getAttackTypesWithNull() {
        List<String> list = List.of(AttackType.values()).stream().map(AttackType::name).collect(Collectors.toList());
        list.add(null);
        return list;
    }

    private static List<String> getSkillEffectsWithNull() {
        List<String> list = List.of(SkillEffect.values()).stream().map(SkillEffect::name).collect(Collectors.toList());
        list.add(null);
        return list;
    }

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

    private static List<String> getEnumMainTargets() {
        return Arrays.stream(Target.values())
                .filter(Target::isCanBeMainTarget)
                .map(Target::name)
                .collect(Collectors.toList());
    }

    private static List<String> getEnumSubTargets() {
        return Arrays.stream(Target.values())
                .filter(Target::isCanBeSubTarget)
                .map(Target::name)
                .collect(Collectors.toList());
    }

}
