package ru.mondayish.models.tree;

import lombok.Getter;
import lombok.Setter;
import ru.mondayish.models.node.BasicNode;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
public class AnimalClass extends BasicNode {

    private long id;
    private String name;
    private long numberOfKinds;

    public AnimalClass(String name, long numberOfKinds) {
        this.id = IdGenerator.getClassId();
        this.name = name;
        this.numberOfKinds = numberOfKinds;
    }
}
