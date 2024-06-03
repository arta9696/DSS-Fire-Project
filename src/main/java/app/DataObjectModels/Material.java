package app.DataObjectModels;

public class Material implements Nameable {
    public String id;

    public Material(String name) {
        this.id = name;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public String getName() {
        return id;
    }
}
