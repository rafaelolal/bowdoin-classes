package Project0;

public class ExplorationMain {
    public static void main(String[] args) {
        Person[] people = {
                new Person("Rafael"),
                new Person("Deejuanae"),
                new Person("Dr. Kai")
        };

        people[0].addGear(new Gear[] { new Boat(), new Backpack() });
        people[1].addGear(new Gear[] { new Headlamp(), new Backpack() });
        people[2].addGear(new Boat());
        people[2].addGear(new Headlamp());

        // I do not like how a NaturalFeature take in Gear objects
        NaturalFeature[] naturalFeatures = {
                new Cave("Cave 1", new Gear[] { new Headlamp() }),
                new Cave("Cave 2", new Gear[] { new Headlamp(), new Backpack() }),
                new Forest("Forest 1", new Gear[] {}),
                new Mountain("Mountain 1", new Gear[] { new Backpack() }),
                new River("River 1", new Gear[] { new Boat() }),
                new River("River 2", new Gear[] { new Boat(), new Backpack() })
        };

        // Taking each person to each natural feature
        for (Person person : people) {
            for (NaturalFeature naturalFeature : naturalFeatures) {
                System.out.println(naturalFeature.explore(person));
            }
        }

    }
}
