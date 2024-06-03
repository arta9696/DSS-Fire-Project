package app.DataObjectModels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProtectionObject implements Nameable {
    public String id = UUID.randomUUID().toString();
    public String name;
    public List<Room> roomList = new ArrayList<>();

    public double height;
    public double width;
    public double lenght;
    public Integer maxPeopleCount;
    public Integer floorCount;
    public double area;
    public double volume;
    public String funcClass;

    public String SOUE;
    public String NPV;
    public String VPV;

    public ProtectionObject(String name) {
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
