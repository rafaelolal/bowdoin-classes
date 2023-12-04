import java.awt.Color;
import java.lang.reflect.Field;

/**
 * A class containing methods used across the application.
 * 
 * @author Rafael Almeida
 */
final

class Utility {
    /**
     * Gets the Color specified by the given name.
     * For a list of valid color names, see the javadoc for the Color class.
     *
     * @param colorName The name of the color to lookup.
     * 
     * @return The specified color, or null if no such color exists.
     */
    static Color

            colorFromString(String colorName) {
        try {
            Field field = Color.class.getField(colorName);
            return (Color) field.get(null);
        } catch (Exception e) {
            return null; // no such color
        }
    }
}
