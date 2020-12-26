# AnimalTrreeProject
Simple project with tree as view

#### List of url:

**GET /tree/root** - возвращает всю иерархию животных

**POST /tree/type** - добавляет новый тип животных в дерево

**PUT /tree/type** - редактирует тип животных в дереве

**DELETE /tree/type/{typeId}** - удаляет тип животного из дерева по идентификатору 

**POST /tree/type/{typeId}/class** - добавляет новый класс животных к типу по идентификатору

**PUT /tree/type/{typeId}/class** - редактирует класс животных у типа по идентификатору

**DELETE /tree/type/{typeId}/class/{classId}** - удаляет класс животных из типа по идентификатору

**POST /tree/class/{classId}/squad** - добавляет новый отряд животных к классу по идентификатору

**PUT /tree/class/{classId}/squad** - редактирует отряд животных у класса по идентификатору

**DELETE /tree/class/{classId}/squad/{squadId}** - удаляет отряд животных из класса по идентификатору

**POST /tree/squad/{squadId}/family** - добавляет новое семейство животных к отряду по идентификатору

**PUT /tree/squad/{squadId}/family** - редактирует семейство животных у отряда по идентификатору

**DELETE /tree/squad/{squadId}/family/{familyId}** - удаляет семейство животных из отряда по идентификатору

**POST /tree/family/{familyId}/animal** - добавляет конкретный вид животного к семейству по идентификатору

**PUT /tree/family/{familyId}/animal** - редактирует конкретный вид животного у семейства по идентификатору

**DELETE /tree/family/{familyId}/animal/{animalId}** - удаляет конкретный вид животного из семейства по идентификатору
