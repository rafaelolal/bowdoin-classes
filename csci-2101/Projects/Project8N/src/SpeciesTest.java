import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * Test class for the Species class, which represents a type of creature in a
 * Darwin simulation.
 * 
 * @author Rafael Almeida
 */
public class SpeciesTest {

    /**
     * Tests the creation of a Species instance.
     * 
     * @throws FileNotFoundException  If a species file is not found.
     * @throws NoSuchFieldException   If a specified field is not found.
     * @throws IllegalAccessException If access to the field is not allowed.
     */
    @Test
    public void testCreate() throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
        Species test = new Species("species/Flytrap.txt", "red");

        assertEquals(test.getName(), "Flytrap");

        Color color = Utility.colorFromString("red");

        assertEquals(test.getColor(), color);
        assertEquals(7, test.programSize());
    }

    /**
     * Tests the programStep method of the Species class.
     * 
     * @throws FileNotFoundException If a species file is not found.
     */
    @Test
    public void testProgramStep() throws FileNotFoundException {
        Species test = new Species("species/Flytrap.txt", "red");

        Instruction instruction = test.programStep(0);
        assertEquals(Opcode.LABEL, instruction.getOpcode());
        assertEquals("start", instruction.getLabel());

        instruction = test.programStep(1);
        assertEquals(Opcode.IFENEMY, instruction.getOpcode());
        assertEquals("doinfect", instruction.getLabel());

        instruction = test.programStep(2);
        assertEquals(Opcode.LEFT, instruction.getOpcode());

        instruction = test.programStep(3);
        assertEquals(Opcode.GO, instruction.getOpcode());
        assertEquals("start", instruction.getLabel());

        instruction = test.programStep(4);
        assertEquals(Opcode.LABEL, instruction.getOpcode());
        assertEquals("doinfect", instruction.getLabel());

        instruction = test.programStep(5);
        assertEquals(Opcode.INFECT, instruction.getOpcode());

        instruction = test.programStep(6);
        assertEquals(Opcode.GO, instruction.getOpcode());
        assertEquals("start", instruction.getLabel());
    }

    /**
     * Tests the getLabelAddress method of the Species class.
     * 
     * @throws FileNotFoundException If a species file is not found.
     */
    @Test
    public void testGetLabelAddress() throws FileNotFoundException {
        Species test = new Species("species/Flytrap.txt", "red");

        assertEquals(0, test.getLabelAddress("start"));
        assertEquals(4, test.getLabelAddress("doinfect"));

        Species test2 = new Species("species/Rover.txt", "red");

        assertEquals(0, test2.getLabelAddress("start"));
        assertEquals(6, test2.getLabelAddress("randomturn"));
        assertEquals(10, test2.getLabelAddress("turnright"));
        assertEquals(13, test2.getLabelAddress("doinfect"));
    }
}
