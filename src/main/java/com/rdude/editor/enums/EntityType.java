package com.rdude.editor.enums;

import com.rdude.editor.MainViewController;
import com.rdude.editor.data.Data;
import com.rdude.editor.settings.Settings;
import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.*;

import java.io.File;
import java.util.function.Supplier;

public enum EntityType {
    SKILL ("/fxml/skill/SkillMainView.fxml",
            "/fxml/skill/view/SkillDescriberMainView.fxml",
            Data.getSkills(),
            SkillData.class,
            "/fxml/skill/view/SkillSearch.fxml",
            MainViewController::getSkillsTabPane,
            Settings.getSkillsFolder(),
            ".skill",
            "skilldata"),

    ITEM ("/fxml/skill/view/ItemMainView.fxml",
            "/fxml/skill/view/ItemDescriberMainView.fxml",
            Data.getItems(),
            ItemData.class,
            "/fxml/skill/view/ItemSearch.fxml",
            MainViewController::getItemsTabPane,
            Settings.getItemsFolder(),
            ".item",
            "itemdata"),

    MONSTER ("/fxml/skill/view/MonsterMainView.fxml",
            "/fxml/skill/view/ItemDescriberMainView.fxml",
            Data.getMonsters(),
            MonsterData.class,
            "/fxml/skill/view/MonsterSearch.fxml",
            MainViewController::getMonstersTabPane,
            Settings.getMonstersFolder(),
            ".monster",
            "monsterdata"),

    MODULE("/fxml/module/ModuleMainView.fxml",
            "not needed",
            Data.getModules(),
            Module.class,
            "/fxml/module/ModuleSearch.fxml",
            MainViewController::getModulesTabPane,
            Settings.getModulesFolder(),
            ".module",
            "moduledata");

    private final String fxmlPathToMainView;
    private final String fxmlPathToDescriber;
    private final ObservableList<? extends EntityData> dataList;
    private final Class<? extends EntityData> cl;
    private final String fxmlPathToSearchController;
    private final Supplier<TabPane> entityTabsHolder;
    private final File initialLoadDirectory;
    private final String extension;
    private final String mainDataFileName;

    EntityType(String fxmlPathToMainView, String fxmlPathToDescriber, ObservableList<? extends EntityData> dataList,
               Class<? extends EntityData> cl, String fxmlPathToSearchController, Supplier<TabPane> entityTabsHolder,
               File initialLoadDirectory, String extension, String mainDataFileName) {
        this.fxmlPathToMainView = fxmlPathToMainView;
        this.fxmlPathToDescriber = fxmlPathToDescriber;
        this.dataList = dataList;
        this.cl = cl;
        this.fxmlPathToSearchController = fxmlPathToSearchController;
        this.entityTabsHolder = entityTabsHolder;
        this.initialLoadDirectory = initialLoadDirectory;
        this.extension = extension;
        this.mainDataFileName = mainDataFileName;
    }

    public static EntityType of(EntityData entityData) {
        if (entityData instanceof SkillData) {
            return SKILL;
        }
        else if (entityData instanceof ItemData) {
            return ITEM;
        }
        else if (entityData instanceof MonsterData) {
            return MONSTER;
        }
        else if (entityData instanceof Module) {
            return MODULE;
        }
        else throw new IllegalArgumentException("this type of entity data (" + entityData.getClass() + ") is not implemented");
    }

    public String getFxmlPathToMainView() {
        return fxmlPathToMainView;
    }

    public String getFxmlPathToDescriber() {
        return fxmlPathToDescriber;
    }

    public ObservableList<? extends EntityData> getDataList() {
        return dataList;
    }

    public Class<? extends EntityData> getCl() {
        return cl;
    }

    public String getFxmlPathToSearchController() {
        return fxmlPathToSearchController;
    }

    public TabPane getEntityTabsHolder() {
        return entityTabsHolder.get();
    }

    public File getInitialLoadDirectory() {
        return initialLoadDirectory;
    }

    public String getExtension() {
        return extension;
    }

    public String getMainDataFileName() {
        return mainDataFileName;
    }
}
