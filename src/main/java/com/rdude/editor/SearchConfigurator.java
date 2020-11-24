package com.rdude.editor;

import com.rdude.editor.entity.EntitySearchController;
import com.rdude.editor.item.ItemPopupConfigurator;
import com.rdude.editor.module.ModulePopupConfigurator;
import com.rdude.editor.monster.MonsterPopupConfigurator;
import com.rdude.editor.view.SkillPopupConfigurator;
import javafx.fxml.FXMLLoader;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.*;
import ru.rdude.rpg.game.logic.data.Module;

import java.io.IOException;

public class SearchConfigurator {

    public static ItemPopupConfigurator itemPopupConfigurator = new ItemPopupConfigurator();
    public static SkillPopupConfigurator skillPopupConfigurator = new SkillPopupConfigurator();
    public static ModulePopupConfigurator modulePopupConfigurator = new ModulePopupConfigurator();
    public static MonsterPopupConfigurator monsterPopupConfigurator = new MonsterPopupConfigurator();

    public static void configure(SearchDialog<? extends EntityData> searchDialog, EntityEditorController.Type type) throws IOException {
        searchDialog.getSearchPane().setTextFieldSearchBy(EntityData::getName, EntityData::getNameInEditor);
        searchDialog.getSearchPane().setNameBy(EntityData::getNameInEditor);
        FXMLLoader entitySearchLoader = new FXMLLoader(SearchConfigurator.class.getResource(type.getFxmlPathToSearchController()));
        searchDialog.getSearchPane().addExtraSearchNode(entitySearchLoader.load());
        searchDialog.getSearchPane().addSearchOptions(((EntitySearchController) entitySearchLoader.getController()).getControlFunctionMap());
        switch (type) {
            case ITEM:
                itemPopupConfigurator.configure((SearchDialog<ItemData>) searchDialog);
                break;
            case SKILL:
                skillPopupConfigurator.configure((SearchDialog<SkillData>) searchDialog);
                break;
            case MODULE:
                modulePopupConfigurator.configure((SearchDialog<Module>) searchDialog);
                break;
            case MONSTER:
                monsterPopupConfigurator.configure((SearchDialog<MonsterData>) searchDialog);
        }
    }

}
