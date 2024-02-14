package Shop;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Rafael");
        Shop<Item> s1 = new Shop<>("The Shop");
        Cookie c1 = new Cookie("Chip's Ahoy");
        s1.addItem(c1);
        s1.createWishlist(p1, "Rafael's Wishes");
        s1.addToWishlist(p1, c1);
        System.out.println(s1.toString());
        System.out.println(s1.getWishlist(p1).toString());
    }
}
