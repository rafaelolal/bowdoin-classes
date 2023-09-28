package ComparisonString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponMain {

	public static void main(String[] args) {

		Weapon ex = new Weapon(Weapon.BUC.Blessed, Weapon.WeaponType.Sword, 3, "Excalibur", "1d12+1d10");
		Weapon s = new Weapon(Weapon.BUC.Uncursed, Weapon.WeaponType.Sword, 0, "Broadsword", "1d6+1d4");
		Weapon s2 = new Weapon(Weapon.BUC.Uncursed, Weapon.WeaponType.Sword, 0, "Two-handed sword", "3d6");
		Weapon pa = new Weapon(Weapon.BUC.Blessed, Weapon.WeaponType.PickAxe, 1, "Dwarvish Mattock", "1d8+2d6");
		Weapon cb = new Weapon(Weapon.BUC.Cursed, Weapon.WeaponType.Crossbow, -2, "Elvish Crossbow", "1d2");
		Weapon pa2 = new Weapon(Weapon.BUC.Uncursed, Weapon.WeaponType.PickAxe, 0, "Dwarvish Pickaxe", "1d3");
		Weapon d = new Weapon(Weapon.BUC.Cursed, Weapon.WeaponType.Dagger, -1, "Orcish Dagger", "1d3");
		Weapon ex2 = new Weapon(Weapon.BUC.Blessed, Weapon.WeaponType.Sword, 5, "Excalibur", "1d12+1d10");

		System.out.println("Demonstration of our inner class in action:");
		System.out.printf("Attacking with %s does %d damage\n", ex, ex.damage());
		System.out.printf("Attacking with %s does %d damage\n", ex, ex.damage());
		System.out.printf("Attacking with %s does %d damage\n", ex, ex.damage());

		System.out.println("**************");

		// Forbidden: `The type Weapon.WeaponDamage is not visible`
		// Weapon.WeaponDamage wd = new Weapon.WeaponDamage("3d6");
		// Encapsulation successfully achieved -- others can't mess with inner
		// implementation details

		List<Weapon> allWeapons = new ArrayList<Weapon>();

		allWeapons.add(ex);
		allWeapons.add(s);
		allWeapons.add(s2);
		allWeapons.add(pa);
		allWeapons.add(cb);
		allWeapons.add(pa2);
		allWeapons.add(d);
		allWeapons.add(ex2);

		System.out.println("Ordering as created:");
		allWeapons.forEach(System.out::println);
		System.out.println("**************");

		Collections.shuffle(allWeapons);

		System.out.println("Sorted into \"natural\" order, with the `compareTo` method");
		Collections.sort(allWeapons);
		allWeapons.forEach(System.out::println);

		System.out.println("**************");
		System.out.println("Explicitly comparing two weapons together");
		// negative number, means that excalibur < mattock
		System.out.printf("`excalibur.compareTo(mattock)` -> %d\n", ex.compareTo(pa));
		System.out.println("**************");

		Collections.shuffle(allWeapons);

		allWeapons.sort(Weapon.defaultOrdering());
		System.out.println("Sorted into \"natural\" order, with our custom comparator"); // should be same as above
		allWeapons.forEach(System.out::println);
		System.out.println("**************");

		Collections.shuffle(allWeapons);

		allWeapons.sort(Weapon.reverseOrdering());
		System.out.println("Sorted into \"reverse\" order, with our custom comparator");
		allWeapons.forEach(System.out::println);
		System.out.println("**************");

		Collections.shuffle(allWeapons);

		allWeapons.sort(Weapon.typeOrdering());
		System.out.println("Sorted into order by type, with our custom comparator");
		allWeapons.forEach(System.out::println);
		System.out.println("**************");

		Collections.shuffle(allWeapons);

		allWeapons.sort(Weapon.maximumDamageOrdering());
		System.out.println("Sorted into order by maximum damage, with our custom comparator");
		allWeapons.forEach(System.out::println);
		System.out.println("**************");

		Collections.shuffle(allWeapons);

		allWeapons.sort(Weapon.averageDamageOrdering());
		System.out.println("Sorted into order by average damage, with our custom comparator");
		allWeapons.forEach(System.out::println);
		System.out.println("**************");

	}

}
