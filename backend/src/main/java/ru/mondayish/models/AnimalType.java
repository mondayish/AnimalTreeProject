package ru.mondayish.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
@AllArgsConstructor
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
