package dk.madsravn.vm.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OpcodeTest {

    @Test
    public void testToString() {
        Opcode constant = Opcode.CONSTANT;
        assertEquals(constant.toString(), "[CONSTANT - 1]");

        Opcode add = Opcode.ADD;
        assertEquals(add.toString(), "[ADD - 2]");
    }
}
