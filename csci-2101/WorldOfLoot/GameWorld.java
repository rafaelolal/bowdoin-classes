package WorldOfLoot;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GameWorld {
    public static void main(String[] args) {

        System.out.println("Welcome to the World of Loot\n"
                + "Look around and enter a number to interact with the environment!\n"
                + "Enter quit at any time to leave.");

        List<MagicContainer<?>> containers = new ArrayList<MagicContainer<?>>();
        MagicContainer<Armor> armorContainer = new MagicContainer<>("Armor Chest");

        armorContainer.put(new Armor("Diamond"), 15);
        armorContainer.put(new Armor("Iron"), 10);

        MagicContainer<Jewelry> jewelryContainer = new MagicContainer<>("Jewelry Chest");
        jewelryContainer.put(new Jewelry("Ruby"), 15);
        jewelryContainer.put(new Jewelry("Topaz"), 20);

        MagicContainer<Ring> ringContainer = new MagicContainer<>("Ring Chest");
        ringContainer.put(new Ring("Strength"), 17);
        ringContainer.put(new Ring("Luck"), 21);

        MagicContainer<Weapon> weaponContainer = new MagicContainer<>("Weapon Chest");
        weaponContainer.put(new Weapon("Spear"), 31);
        weaponContainer.put(new Weapon("Sword"), 19);

        containers.add(armorContainer);
        containers.add(jewelryContainer);
        containers.add(ringContainer);
        containers.add(weaponContainer);

        Door[] doors = { new Door(false), new Door(false), new Door(true) };

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Containers");
            int i = 0;
            for (MagicContainer<?> container : containers) {
                System.out.println(i + ": " + container.getDescription());
                i++;
            }
            System.out.println("Doors");
            for (Door door : doors) {
                System.out.println(i + ": Door");
                i++;
            }
            System.out.print("Interact: ");
            int interact = scan.nextInt();

            if (interact >= 0 && interact < containers.size()) {
                System.out.println(containers.get(interact).toString());
            } else if (interact >= containers.size() && interact < containers.size() + doors.length) {
                System.out.println(doors[interact - containers.size()].toString());
            }
        }
    }
}
