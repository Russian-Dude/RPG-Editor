package data;

import data.io.packer.Packer;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.SkillData;

public class Saver {

    private static Saver instance;
    private Packer packer = new Packer();

    private static Saver getInstance() {
        if (instance == null) {
            instance = new Saver();
        }
        return instance;
    }

    public static void save(EntityData entityData) {
        getInstance().savePr(entityData);
    }

    private void savePr(EntityData entityData) {
        if (entityData instanceof SkillData) {
            Data.addSkillData((SkillData) entityData);
            packer.pack((SkillData) entityData);
        }
    }

}
