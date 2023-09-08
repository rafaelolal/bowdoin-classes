package Project0;

import java.util.List;
import java.util.ArrayList;

public class Person {
    private String name;
    private List<Gear> gear = new ArrayList<Gear>();

    public Person(String name) {
        this.name = name;
    }

    public void exploreAll(List<NaturalFeature> naturalFeatures) {
        for (NaturalFeature naturalFeature : naturalFeatures) {
            naturalFeature.explore(this);
        }
    }

    public void addGear(Gear[] gearList) {
        for (Gear gear : gearList) {
            addGear(gear);
        }
    }

    public void addGear(Gear gear) {
        this.gear.add(gear);
    }

    public List<Gear> getGear() {
        return gear;
    }

    public String getName() {
        return name;
    }
}