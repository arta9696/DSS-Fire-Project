package app.DataObjectModels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room implements Nameable{
    public String id = UUID.randomUUID().toString();
    public String name;
    public List<Material> materialList = new ArrayList<>();
    public Integer maxVoltage;
    public double height;
    public double width;
    public double lenght;
    public double area;
    public double volume;


    public RoomCategories categories;

    public Room(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return id;
    }
}
