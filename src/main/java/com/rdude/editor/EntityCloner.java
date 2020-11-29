package com.rdude.editor;

import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.ItemData;
import ru.rdude.rpg.game.logic.data.MonsterData;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.data.io.EntityDataSerializer;

public class EntityCloner {

    public static <T extends EntityData> T clone(T source, Class<T> cl) {
        EntityDataSerializer serializer = new EntityDataSerializer();
        return serializer.deserialize(serializer.serialize(source), cl);
    }

    public static EntityData clone(EntityData source) {
        if (source instanceof SkillData) {
            return clone((SkillData) source, SkillData.class);
        }
        else if (source instanceof ItemData) {
            return clone((ItemData) source, ItemData.class);
        }
        else if (source instanceof MonsterData) {
            return clone((MonsterData) source, MonsterData.class);
        }
        else {
            throw new IllegalArgumentException("not implemented");
        }
    }
}
