package ru.mondayish.models;

import lombok.Getter;
import lombok.Setter;
import ru.mondayish.utils.IdGenerator;

@Getter
@Setter
public class AnimalSquad extends BasicNode {

    private long id;
    private String name;

    public AnimalSquad(String name) {
        this.id = IdGenerator.getSquadId();
        this.name = name;
    }
}
