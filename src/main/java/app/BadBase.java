package app;

import app.DataObjectModels.*;

public class BadBase {
    public static ProtectionObject test(){
        ProtectionObject test = new ProtectionObject("Testaetagka");
        test.height = Double.parseDouble("16");
        test.width = Double.parseDouble("12.8");
        test.lenght = Double.parseDouble("67.3");
        test.area = test.width * test.lenght;
        test.volume = test.area * test.height;
        test.maxPeopleCount = Integer.parseInt("300");
        test.floorCount = Integer.parseInt("5");
        test.funcClass = "Ф1.3";
        Room testRoom1 = new Room("Гостинная");
        testRoom1.height = Double.parseDouble("3");
        testRoom1.width = Double.parseDouble("5.8");
        testRoom1.lenght = Double.parseDouble("3.5");
        testRoom1.area = testRoom1.width * testRoom1.lenght;
        testRoom1.volume = testRoom1.area * testRoom1.height;
        testRoom1.maxVoltage = Integer.parseInt("240");
        test.roomList.add(testRoom1);
        Room testRoom2 = new Room("Кухня");
        testRoom2.height = Double.parseDouble("3");
        testRoom2.width = Double.parseDouble("2.42");
        testRoom2.lenght = Double.parseDouble("3.61");
        testRoom2.area = testRoom2.width * testRoom2.lenght;
        testRoom2.volume = testRoom2.area * testRoom2.height;
        testRoom2.maxVoltage = Integer.parseInt("240");
        test.roomList.add(testRoom2);
        Room testRoom3 = new Room("Ванна");
        testRoom3.height = Double.parseDouble("3");
        testRoom3.width = Double.parseDouble("1.67");
        testRoom3.lenght = Double.parseDouble("2.45");
        testRoom3.area = testRoom3.width * testRoom3.lenght;
        testRoom3.volume = testRoom3.area * testRoom3.height;
        testRoom3.maxVoltage = Integer.parseInt("120");
        test.roomList.add(testRoom3);
        Material material11 = new Material("Фанера");
        testRoom1.materialList.add(material11);
        Material material12 = new Material("Кожа");
        testRoom1.materialList.add(material12);
        Material material21 = new Material("Фанера");
        testRoom2.materialList.add(material21);
        Material material22 = new Material("Метан");
        testRoom2.materialList.add(material22);
        Material material31 = new Material("Фанера");
        testRoom3.materialList.add(material31);
        Material material32 = new Material("Вода");
        testRoom3.materialList.add(material32);
        return test;
    }

    public static String[] SpecialF = new String[]{
            "Ф5.1",
            "Ф5.2",
            "Ф5.3"
    };

    public static String[] NPV = new String[]{
            "Пожарный резервуар требуется",
            "Не требуется"
    };
}