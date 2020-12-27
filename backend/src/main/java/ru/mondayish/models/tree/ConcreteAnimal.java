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
public class ConcreteAnimal extends BasicNode {

    private long id;
    private String name;
    private int age;

    public ConcreteAnimal(String name, int age) {
        this.id = IdGenerator.getConcreteAnimalId();
        this.name = name;
        this.age = age;
    }
}
