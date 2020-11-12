package data;

import javafx.collections.*;
import javafx.scene.image.Image;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;

public class Data {

    private static Data instance;

    private ObservableList<Module> modules;
    private ObservableMap<Long, SkillData> skillsMap;
    private ObservableList<SkillData> skills;
    private ObservableMap<Long, ItemData> itemsMap;
    private ObservableList<ItemData> items;
    private ObservableMap<Long, MonsterData> monstersMap;
    private ObservableList<MonsterData> monsters;

    private ObservableList<Image> images;

    private Data() {
        // init fields:
        modules = FXCollections.observableArrayList();
        skillsMap = FXCollections.observableHashMap();
        itemsMap = FXCollections.observableHashMap();
        monstersMap = FXCollections.observableHashMap();
        skills = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        monsters = FXCollections.observableArrayList();

        // link maps to modules:
        modules.addListener((ListChangeListener<Module>) change -> {
            // when new modules added:
            if (change.wasAdded()) {
                change.getAddedSubList().forEach(module -> module.getSkillData().forEach(skillData -> skillsMap.putIfAbsent(skillData.getGuid(), skillData)));
                change.getAddedSubList().forEach(module -> module.getItemData().forEach(itemData -> itemsMap.putIfAbsent(itemData.getGuid(), itemData)));
                change.getAddedSubList().forEach(module -> module.getMonsterData().forEach(monsterData -> monstersMap.putIfAbsent(monsterData.getGuid(), monsterData)));
            }
            // when modules removed:
            else if (change.wasRemoved()) {
                change.getRemoved().forEach(module -> module.getSkillData().forEach(skillData -> skillsMap.remove(skillData.getGuid())));
                change.getRemoved().forEach(module -> module.getItemData().forEach(itemData -> itemsMap.remove(itemData.getGuid())));
                change.getRemoved().forEach(module -> module.getMonsterData().forEach(monsterData -> monstersMap.remove(monsterData.getGuid())));
            }
        });

        // link lists to maps:
        // skills:
        skillsMap.addListener((MapChangeListener<Long, SkillData>) change -> {
            if (change.wasAdded()) {
                skills.add(change.getValueAdded());
            }
            else if (change.wasRemoved()) {
                skills.remove(change.getValueRemoved());
            }
        });
        //items:
        itemsMap.addListener((MapChangeListener<Long, ItemData>) change -> {
            if (change.wasAdded()) {
                items.add(change.getValueAdded());
            }
            else if (change.wasRemoved()) {
                items.remove(change.getValueRemoved());
            }
        });
        // monsters:
        monstersMap.addListener((MapChangeListener<Long, MonsterData>) change -> {
            if (change.wasAdded()) {
                monsters.add(change.getValueAdded());
            }
            else if (change.wasRemoved()) {
                monsters.remove(change.getValueRemoved());
            }
        });
    }

    private static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public static ObservableList<Module> getModules() {
        return getInstance().modules;
    }

    public static ObservableList<SkillData> getSkills() {
        return getInstance().skills;
    }

    public static ObservableList<ItemData> getItems() {
        return getInstance().items;
    }

    public static ObservableList<MonsterData> getMonsters() {
        return getInstance().monsters;
    }

    public static SkillData getSkillData(long guid) {
        return getInstance().skillsMap.get(guid);
    }

    public static ItemData getItemData(long guid) {
        return getInstance().itemsMap.get(guid);
    }

    public static MonsterData getMonsterData(long guid) {
        return getInstance().monstersMap.get(guid);
    }

    public ObservableList<Image> getImages() {
        return images;
    }

    public static void addSkillData(SkillData skillData) {
        getInstance().skillsMap.put(skillData.getGuid(), skillData);
    }

    public static void removeSkillData(SkillData skillData) {
        getInstance().skillsMap.remove(skillData.getGuid());
    }

    public static void addItemData(ItemData itemData) {
        getInstance().itemsMap.put(itemData.getGuid(), itemData);
    }

    public static void removeItemData(ItemData itemData) {
        getInstance().itemsMap.remove(itemData.getGuid());
    }

    public static void addMonsterData(MonsterData monsterData) {
        getInstance().monstersMap.put(monsterData.getGuid(), monsterData);
    }

    public static void removeMonsterData(MonsterData monsterData) {
        getInstance().monstersMap.remove(monsterData.getGuid());
    }

    public static void addModule(Module module) {
        if (!getInstance().modules.contains(module)) {
            getInstance().modules.add(module);
        }
    }

    public static void removeModule(Module module) {
        getInstance().modules.remove(module);
    }


}
