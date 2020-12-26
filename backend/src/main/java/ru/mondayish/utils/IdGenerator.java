package ru.mondayish.utils;

public class IdGenerator {

    private static long TYPE_ID = 0;
    private static long CLASS_ID = 0;
    private static long SQUAD_ID = 0;
    private static long FAMILY_ID = 0;
    private static long CONCRETE_ANIMAL_ID = 0;

    public static long getTypeId() {
        return ++TYPE_ID;
    }

    public static long getClassId() {
        return ++CLASS_ID;
    }

    public static long getSquadId() {
        return ++SQUAD_ID;
    }

    public static long getFamilyId() {
        return ++FAMILY_ID;
    }

    public static long getConcreteAnimalId() {
        return ++CONCRETE_ANIMAL_ID;
    }
}
