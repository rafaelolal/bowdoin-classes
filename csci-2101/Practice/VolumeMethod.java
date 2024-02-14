package Practice;

public class VolumeMethod {
    public static void main(String[] args) {
        System.out.println("The volume of a cylinder with radius 5 and height 10 is: "
         + calculateCylinderVolume(5, 10));
    }

    public static double calculateCylinderVolume(double r, double h) {
        return Math.PI*r*r*h;
    }
}

// sc.hasNext
// sc.next