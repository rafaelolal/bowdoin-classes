package Project0;

public class Gear {

    public String toString() {
        String className = this.getClass().toString();
        return className.substring(className.indexOf(".") + 1);
    }

}
