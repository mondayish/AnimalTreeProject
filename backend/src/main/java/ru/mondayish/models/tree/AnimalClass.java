package ru.mondayish.models.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mondayish.models.node.BasicNode;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
