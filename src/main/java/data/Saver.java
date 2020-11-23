package data;

import data.io.packer.Packer;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.SkillData;
import settings.Settings;

public class Saver {

    private static Saver instance;
    private Packer packer = new Packer();

    private static Saver getInstance() {
        if (instance == null) {
            instance = new Saver();
        }
        return instance;
    }

    public static boolean save(EntityData entityData, Module module) {
        try {
            getInstance().savePr(entityData, module);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean save(EntityData entityData) {
        try {
            getInstance().savePr(entityData);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean save(EntityData entityData, String path) {
        try {
            getInstance().savePr(entityData, path);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    private void savePr(EntityData entityData) {
        if (entityData instanceof SkillData) {
            packer.pack((SkillData) entityData);
        }
    }

    private void savePr(EntityData entityData, String path) {
        if (entityData instanceof SkillData) {
            packer.pack((SkillData) entityData, path);
        }
        else if (entityData instanceof Module) {
            packer.pack((Module) entityData, path);
        }
    }

    private void savePr(EntityData entityData, Module module) {
        if (entityData instanceof SkillData) {
            entityData.getDependencies().stream()
                    .filter(dep -> !dep.equals(module.getGuid()))
                    .forEach(module::addDependency);
            module.addEntity(entityData);
            if (Settings.isAutosaveModulesWhenEntityAdded()) {
                packer.pack(module);
            }
        }
    }

}
