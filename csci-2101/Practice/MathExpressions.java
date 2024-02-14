package Practice;
public class MathExpressions {
    public static void main(String[] args) {
        // in class exercise 1
        System.out.println(
            String.format("A cylinder with radius 3 and height 5 has volume: %.4f",
            Math.PI * Math.pow(3, 2) * 5));

        // in class exercise 2
        for (int r = 1; r <= 5; r++) {
            for (int h = 5; h <= 10; h++) {
                System.out.println(String.format(
                    "A cylinder with radius %d and height %d has volume %.3f",
                    r, h, Math.PI*r*r*h));
            }
        }
    } 
}
