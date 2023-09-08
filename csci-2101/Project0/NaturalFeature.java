package Project0;

import java.util.Arrays;
import java.util.ArrayList;

public class NaturalFeature {
    private String name;
    private Gear[] gearRequirements;

    public NaturalFeature(String n, Gear[] g) {
        name = n;
        gearRequirements = g;
    }

    public NaturalFeature(String n, Gear g) {
        name = n;
        gearRequirements = new Gear[] { g };
    }

    public String explore(Person p) {
        String explored = "successfully";
        String verb = "using";
        // converting to ArrayList because all these three variables may be edited
        ArrayList<Gear> newGearRequirements = new ArrayList<Gear>(Arrays.asList(gearRequirements));

        // checking if the person has the required gear
        for (Gear gearRequired : gearRequirements) {
            boolean requirementMet = false;
            for (Gear gear : p.getGear()) {
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
                ArrayList<Gear> copyOfNewGearRequirements = new ArrayList<Gear>(newGearRequirements);

                // removing gear that the person already has
                // allows to display only missing gear if the person cannot go
                for (Gear newGearRequired : copyOfNewGearRequirements) {
                    boolean found = false;
                    for (Gear currentGear : p.getGear()) {
                        if (currentGear.getClass() == newGearRequired.getClass()) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        newGearRequirements.remove(newGearRequired);
                    }
                }

                break;
            }

        }

        return String.format(
                "%s %s explored %s by %s the following gear: ",
                p.getName(), explored, getName(), verb)
                + Arrays.toString(newGearRequirements.toArray());
    }

    public String getName() {
        return name;
    }
}
