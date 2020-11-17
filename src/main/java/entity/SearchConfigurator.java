package entity;

import item.ItemSearchConfigurator;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.SkillData;
import skill.view.SkillSearchConfigurator;

import java.io.IOException;

public class SearchConfigurator {

    public static <T extends EntityData> void configure(SearchDialog<T> searchDialog, Class<T> cl) throws IOException {
        if (cl.equals(SkillData.class)) {
            SkillSearchConfigurator.configure((SearchDialog<SkillData>) searchDialog);
        }
    }

}
