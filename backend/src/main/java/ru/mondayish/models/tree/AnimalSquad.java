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
public class AnimalSquad extends BasicNode {

    private long id;
    private String name;

    public AnimalSquad(String name) {
        this.id = IdGenerator.getSquadId();
        this.name = name;
    }
}
