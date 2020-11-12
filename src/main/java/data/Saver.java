package data;

import data.io.packer.Packer;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.SkillData;

public class Saver {

    private Packer packer;

    public void save(EntityData entityData) {
        if (entityData instanceof SkillData) {
            Data.addSkillData((SkillData) entityData);
            packer.pack((SkillData) entityData);
        }
    }

}
