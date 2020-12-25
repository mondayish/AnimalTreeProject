package ru.mondayish.models;

import lombok.Getter;
import lombok.Setter;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
public class AnimalFamily {

    private long id;
    private String name;

    public AnimalFamily(String name) {
        this.id = IdGenerator.getFamilyId();
        this.name = name;
    }
}
