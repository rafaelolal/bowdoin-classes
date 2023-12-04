public class App {
    public static void main(String[] args) throws Exception {
        DoublyLinkedList<Integer> test = new DoublyLinkedList<>();
        test.add(0);
        test.add(1);
        test.add(2);
        test.add(3);
        for (Integer i : test) {
            System.out.println(i.toString());
        }
    }
}
