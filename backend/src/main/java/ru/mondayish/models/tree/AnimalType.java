package ru.mondayish.models.tree;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mondayish.models.node.BasicNode;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
@NoArgsConstructor
public class AnimalType extends BasicNode {

    private long id;
    private String name;
    private long numberOfKinds;

    public AnimalType(String name, long numberOfKinds) {
        this.id = IdGenerator.getTypeId();
        this.name = name;
        this.numberOfKinds = numberOfKinds;
    }
}
