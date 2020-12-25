package ru.mondayish.models;

import lombok.Getter;
import lombok.Setter;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
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
