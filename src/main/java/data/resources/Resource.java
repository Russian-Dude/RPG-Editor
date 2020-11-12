package data.resources;

import ru.rdude.rpg.game.utils.Functions;

public abstract class Resource<T> {

    private String name;
    private Long guid;

    public Resource(String name) {
        this.name = name;
        guid = Functions.generateGuid();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGuid() {
        return guid;
    }

    public abstract <T> T get();
}
