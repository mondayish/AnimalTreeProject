package ru.mondayish.models.node;

import ru.mondayish.models.tree.*;

public enum NodeLevel {

    ROOT(AnimalRoot.class, 0),
    TYPE(AnimalType.class, 1),
    CLASS(AnimalClass.class, 2),
    SQUAD(AnimalSquad.class, 3),
    FAMILY(AnimalFamily.class, 4),
    CONCRETE_ANIMAL(ConcreteAnimal.class, 5);

    private final Class<?> nodeClass;
    private final int level;

    NodeLevel(Class<?> nodeClass, int level) {
        this.nodeClass = nodeClass;
        this.level = level;
    }

    public Class<?> getNodeClass() {
        return nodeClass;
    }

    public int getLevel() {
        return level;
    }
}
