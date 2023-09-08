package Project0;

import java.util.List;
import java.util.ArrayList;

public class Person {
    private String name;
    private List<Gear> gear;

    public Person(String n) {
        name = n;
        gear = new ArrayList<Gear>();
    }

    public void exploreAll(List<NaturalFeature> naturalFeatures) {
        for (NaturalFeature naturalFeature : naturalFeatures) {
            naturalFeature.explore(this);
        }
    }

    public void addGear(Gear[] gs) {
        for (Gear g : gs) {
            addGear(g);
        }
    }

    public void addGear(Gear g) {
        gear.add(g);
    }

    public List<Gear> getGear() {
        return gear;
    }

    public String getName() {
        return name;
    }
}