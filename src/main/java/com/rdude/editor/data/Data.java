package com.rdude.editor.data;

import javafx.collections.*;
import javafx.scene.image.Image;
import ru.rdude.rpg.game.logic.data.*;
import ru.rdude.rpg.game.logic.data.Module;

public class Data {

    private static Data instance;

    private final ObservableList<Module> modules;
    private final ObservableMap<Long, SkillData> skillsMap;
    private final ObservableList<SkillData> skills;
    private final ObservableMap<Long, ItemData> itemsMap;
    private final ObservableList<ItemData> items;
    private final ObservableMap<Long, MonsterData> monstersMap;
    private final ObservableList<MonsterData> monsters;
    private final ObservableMap<Long, Module> entityInsideModule;

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
        entityInsideModule = FXCollections.observableHashMap();

        // link maps to modules:
        modules.addListener((ListChangeListener<Module>) change -> {
            while (change.next()) {
                // when new modules added:
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(module -> module.getSkillData().forEach(skillData -> skillsMap.putIfAbsent(skillData.getGuid(), skillData)));
                    change.getAddedSubList().forEach(module -> module.getSkillData().forEach(skillData -> entityInsideModule.putIfAbsent(skillData.getGuid(), module)));
                    change.getAddedSubList().forEach(module -> module.getItemData().forEach(itemData -> itemsMap.putIfAbsent(itemData.getGuid(), itemData)));
                    change.getAddedSubList().forEach(module -> module.getItemData().forEach(itemData -> entityInsideModule.putIfAbsent(itemData.getGuid(), module)));
                    change.getAddedSubList().forEach(module -> module.getMonsterData().forEach(monsterData -> monstersMap.putIfAbsent(monsterData.getGuid(), monsterData)));
                    change.getAddedSubList().forEach(module -> module.getMonsterData().forEach(monsterData -> entityInsideModule.putIfAbsent(monsterData.getGuid(), module)));
                }
                // when modules removed:
                else if (change.wasRemoved()) {
                    change.getRemoved().forEach(module -> module.getSkillData().forEach(skillData -> skillsMap.remove(skillData.getGuid())));
                    change.getRemoved().forEach(module -> module.getSkillData().forEach(skillData -> entityInsideModule.remove(skillData.getGuid())));
                    change.getRemoved().forEach(module -> module.getItemData().forEach(itemData -> itemsMap.remove(itemData.getGuid())));
                    change.getRemoved().forEach(module -> module.getItemData().forEach(itemData -> entityInsideModule.remove(itemData.getGuid())));
                    change.getRemoved().forEach(module -> module.getMonsterData().forEach(monsterData -> monstersMap.remove(monsterData.getGuid())));
                    change.getRemoved().forEach(module -> module.getMonsterData().forEach(monsterData -> entityInsideModule.remove(monsterData.getGuid())));

                }
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

    public static Module getInsideModule(EntityData entityData) {
        return getInstance().entityInsideModule.get(entityData.getGuid());
    }

    public ObservableList<Image> getImages() {
        return images;
    }

    public static void addEntityData(EntityData entityData) {
        addEntityData(entityData, null);
    }

    public static void addEntityData(EntityData entityData, Module module) {
        if (module != null) {
            getInstance().entityInsideModule.putIfAbsent(entityData.getGuid(), module);
        }
        if (entityData instanceof SkillData) {
            addSkillData((SkillData) entityData);
        }
        else if (entityData instanceof ItemData) {
            addItemData((ItemData) entityData);
        }
        else if (entityData instanceof MonsterData) {
            addMonsterData((MonsterData) entityData);
        }
        else if (entityData instanceof Module) {
            addModule((Module) entityData);
        }
        else throw new IllegalArgumentException(entityData.getClass() + " not implemented");
    }

    public static void addSkillData(SkillData skillData) {
        getInstance().skillsMap.putIfAbsent(skillData.getGuid(), skillData);
    }

    public static void removeSkillData(SkillData skillData) {
        getInstance().skillsMap.remove(skillData.getGuid());
    }

    public static void addItemData(ItemData itemData) {
        getInstance().itemsMap.putIfAbsent(itemData.getGuid(), itemData);
    }

    public static void removeItemData(ItemData itemData) {
        getInstance().itemsMap.remove(itemData.getGuid());
    }

    public static void addMonsterData(MonsterData monsterData) {
        getInstance().monstersMap.putIfAbsent(monsterData.getGuid(), monsterData);
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
