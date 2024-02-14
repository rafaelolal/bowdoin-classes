package Project0;

import java.util.Arrays;
import java.util.ArrayList;

public class NaturalFeature {
    private String name;
    private Gear[] gearRequired;

    public NaturalFeature(String name, Gear[] gearRequired) {
        this.name = name;
        this.gearRequired = gearRequired;
    }

    public NaturalFeature(String name, Gear gearRequired) {
        this.name = name;
        this.gearRequired = new Gear[] { gearRequired };
    }

    public String explore(Person person) {
        String explored = "successfully";
        String verb = "using";
        // converting to ArrayList because all these three variables may be edited
        ArrayList<Gear> newGearRequired = new ArrayList<Gear>(Arrays.asList(gearRequired));

        // checking if the person has the required gear
        for (Gear gearRequired : gearRequired) {
            boolean requirementMet = false;
            for (Gear gear : person.getGear()) {
                if (gear.getClass() == gearRequired.getClass()) {
                    requirementMet = true;
                    break;
                }
            }

            // if the above loop ends and a requirement was not Met, the person cannot go
            if (!requirementMet) {
                explored = "unsuccessfully";
                verb = "needing";
                // creating a copy to iterate over since the original will be edited
                ArrayList<Gear> copyOfNewGearRequired = new ArrayList<Gear>(newGearRequired);

                // removing gear that the person already has
                // allows to display only missing gear if the person cannot go
                for (Gear newGearItem : copyOfNewGearRequired) {
                    boolean found = false;
                    for (Gear personGear : person.getGear()) {
                        if (personGear.getClass() == newGearItem.getClass()) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        newGearRequired.remove(newGearItem);
                    }
                }

                break;
            }

        }

        return String.format(
                "%s %s explored %s by %s the following gear: ",
                person.getName(), explored, getName(), verb)
                + Arrays.toString(newGearRequired.toArray());
    }

    public String getName() {
        return name;
    }
}
