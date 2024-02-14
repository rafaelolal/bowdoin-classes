package Project0;

public class Gear {

    public String toString() {
        // className contains more info than needed
        // getting only everything after the period
        String className = this.getClass().toString();
        return className.substring(className.indexOf(".") + 1);
    }

}
