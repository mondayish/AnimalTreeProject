package ru.mondayish.models;

import lombok.Getter;
import lombok.Setter;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
public class AnimalClass {

    private long id;
    private String name;
    private long numberOfKinds;

    public AnimalClass(String name, long numberOfKinds) {
        this.id = IdGenerator.getClassId();
        this.name = name;
        this.numberOfKinds = numberOfKinds;
    }
}
