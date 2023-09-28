package ComparisonString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * This class represents a weapon, comparable to one like you'd find in D&D or
 * NetHack. For simplification, we're ignoring that weapons can do different
 * amounts of damage based on the size of the target. We're also ignoring the
 * skill level of the weapon's user (although, that really probably shouldn't go
 * here anyways, even in a more complete modeling of things -- that would be
 * applied later on).
 * 
 * A weapon tracks its beatitude, type, damage bonus, name, and the dice rolled
 * when determining its hit damage.
 * 
 * @author Kai Presler-Marshall
 */
public class Weapon implements Comparable<Weapon> {

	public enum BUC {
		Blessed, Uncursed, Cursed;
	}

	public enum WeaponType {
		Sword, Dagger, Crossbow, Arrow, Axe, PickAxe;
	}

	private BUC status;

	private WeaponType type;

	private Integer damageBonus;

	private String name;

	List<WeaponDamage> dmg;

	public Weapon(BUC status, WeaponType type, Integer damageBonus, String name, String damageString) {
		super();
		this.status = status;
		this.type = type;
		this.damageBonus = damageBonus;
		this.name = name;
		dmg = new ArrayList<WeaponDamage>();

		// we might get something like 2d8+1d12
		String[] diceDamage = damageString.split("\\+");
		for (String dmgString : diceDamage) {
			dmg.add(new WeaponDamage(dmgString));
		}

	}

	public Integer damage() {
		int totalDamage = damageBonus;
		for (WeaponDamage wd : dmg) {
			totalDamage += wd.damage();
		}
		return 0 > totalDamage ? 0 : totalDamage;
	}

	static private class WeaponDamage {

		// might as well make it static to avoid recreating
		static final private Random r = new Random();

		private Integer numDice;

		public Integer getNumDice() {
			return numDice;
		}

		private Integer diceSides;

		public Integer getDiceSides() {
			return diceSides;
		}

		public WeaponDamage(String dmgString) {
			String[] pieces = dmgString.split("d");
			numDice = Integer.valueOf(pieces[0]);
			diceSides = Integer.valueOf(pieces[1]);
		}

		public Integer damage() {
			int totalDamage = 0;
			for (int i = 0; i < numDice; i++) {
				totalDamage += r.nextInt(1, diceSides + 1); // +1 to make inclusive
			}
			return totalDamage;
		}

	}

	@Override
	public int compareTo(Weapon other) {

		if (this.status != other.status) {
			return this.status.compareTo(other.status);
		}

		if (this.type != other.type) {
			return this.type.compareTo(other.type);
		}

		if (this.damageBonus != other.damageBonus) {
			return -this.damageBonus.compareTo(other.damageBonus);
		}

		return -this.name.compareTo(other.name);

	}

	@Override
	public String toString() {
		return String.format("%s %s%d %s (%s)", status, damageBonus >= 0 ? "+" : "", damageBonus, name, type);
	}

	static public Comparator<Weapon> defaultOrdering() {
		return new WC();
	}

	static public Comparator<Weapon> reverseOrdering() {
		return new WC().reversed();
	}

	static public Comparator<Weapon> typeOrdering() {
		return new WTC();
	}

	static public Comparator<Weapon> maximumDamageOrdering() {
		return new WDC();
	}

	static public Comparator<Weapon> averageDamageOrdering() {
		return new WADC();
	}

	static public Comparator<Weapon> bonusOrdering() {

		// anonymous inner class :)
		return new Comparator<Weapon>() {
			@Override
			public int compare(Weapon o1, Weapon o2) {
				if (o1.damageBonus != o2.damageBonus) {
					return -o1.damageBonus.compareTo(o2.damageBonus);
				}
				if (o1.status != o2.status) {
					return o1.status.compareTo(o2.status);
				}

				if (o1.type != o2.type) {
					return o1.type.compareTo(o2.type);
				}
				return -o1.name.compareTo(o2.name);
			}

		};
	}

	static private class WC implements Comparator<Weapon> {
		public int compare(Weapon o1, Weapon o2) {
			if (o1.status != o2.status) {
				return o1.status.compareTo(o2.status);
			}

			if (o1.type != o2.type) {
				return o1.type.compareTo(o2.type);
			}

			if (o1.damageBonus != o2.damageBonus) {
				return -o1.damageBonus.compareTo(o2.damageBonus);
			}

			return -o1.name.compareTo(o2.name);

		}
	}

	static private class WTC implements Comparator<Weapon> {

		@Override
		public int compare(Weapon o1, Weapon o2) {
			if (o1.type != o2.type) {
				return o1.type.compareTo(o2.type);
			}
			if (o1.damageBonus != o2.damageBonus) {
				return -o1.damageBonus.compareTo(o2.damageBonus);
			}
			if (o1.status != o2.status) {
				return o1.status.compareTo(o2.status);
			}
			return -o1.name.compareTo(o2.name);
		}

	}

	static private class WDC implements Comparator<Weapon> {
		@Override
		public int compare(Weapon o1, Weapon o2) {
			int maxDO1 = calculateMaximumDamage(o1);
			int maxDO2 = calculateMaximumDamage(o2);
			if (maxDO1 != maxDO2) {
				return Integer.valueOf(maxDO1).compareTo(Integer.valueOf(maxDO2));
			}
			if (o1.status != o2.status) {
				return o1.status.compareTo(o2.status);
			}
			if (o1.type != o2.type) {
				return o1.type.compareTo(o2.type);
			}
			if (o1.damageBonus != o2.damageBonus) {
				return -o1.damageBonus.compareTo(o2.damageBonus);
			}
			return -o1.name.compareTo(o2.name);
		}

		private int calculateMaximumDamage(Weapon o) {
			int maximumDamage = 0;
			for (WeaponDamage dmg : o.dmg) {
				maximumDamage += dmg.getNumDice() * dmg.getDiceSides();
			}
			return maximumDamage;
		}
	}

	static private class WADC implements Comparator<Weapon> {
		@Override
		public int compare(Weapon o1, Weapon o2) {
			int maxDO1 = calculateAverageDamage(o1);
			int maxDO2 = calculateAverageDamage(o2);
			if (maxDO1 != maxDO2) {
				return Integer.valueOf(maxDO1).compareTo(Integer.valueOf(maxDO2));
			}
			if (o1.status != o2.status) {
				return o1.status.compareTo(o2.status);
			}
			if (o1.type != o2.type) {
				return o1.type.compareTo(o2.type);
			}
			if (o1.damageBonus != o2.damageBonus) {
				return -o1.damageBonus.compareTo(o2.damageBonus);
			}
			return -o1.name.compareTo(o2.name);
		}

		private int calculateAverageDamage(Weapon o) {
			int maximumDamage = 0;
			for (WeaponDamage dmg : o.dmg) {
				maximumDamage += (dmg.getNumDice() * dmg.getDiceSides()) / 2;
			}
			return maximumDamage;
		}
	}

}
