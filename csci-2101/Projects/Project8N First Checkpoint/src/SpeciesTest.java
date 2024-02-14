import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import org.junit.Test;

public class SpeciesTest {
    @Test
    public void testCreate() throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
        Species test = new Species("species/Flytrap.txt", "red");

        assertEquals(test.getName(), "Flytrap");

        Field field = Color.class.getField("red");
        Color color = (Color) field.get(null);

        assertEquals(test.getColor(), color);
        assertEquals(7, test.programSize());
    }

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
