package dk.madsravn.vm.code;

import dk.madsravn.vm.utility.UByte;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeTest {

    private record testMakeData(Opcode opcode, List<Integer> operands, List<UByte> expected) { }
    @Test
    public void testMake() {
        List<testMakeData> inputs = Arrays.asList(
                new testMakeData(
                        Opcode.CONSTANT,
                        Arrays.asList(65534),
                        Arrays.asList(Opcode.CONSTANT.getOpcodeValue(), new UByte(255), new UByte(254))
                )
        );
        // TODO: Do we want to declare this all the time? Do we need to?
        Code code = new Code();
        for(testMakeData input : inputs) {
            List<UByte> instruction = code.make(input.opcode, input.operands);
            assertEquals(instruction.size(), input.expected.size(), "Instruction has wrong length");
            for(int i = 0; i < input.expected.size(); ++i) {
                assertEquals(instruction.get(i), input.expected.get(i), "Wrong byte at position: " + i);
            }
        }

    }
}
