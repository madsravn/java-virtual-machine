package dk.madsravn.vm.code;

import dk.madsravn.vm.utility.UByte;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeTest {

    private record TestMakeData(Opcode opcode, List<Integer> operands, List<UByte> expected) { }
    @Test
    public void testMake() {
        List<TestMakeData> inputs = Arrays.asList(
                new TestMakeData(
                        Opcode.CONSTANT,
                        Arrays.asList(65534),
                        Arrays.asList(Opcode.CONSTANT.getOpcodeValue(), new UByte(255), new UByte(254))
                )
        );
        // TODO: Do we want to declare this all the time? Do we need to?
        for(TestMakeData input : inputs) {
            List<UByte> instruction = Code.make(input.opcode, input.operands);
            assertEquals(instruction.size(), input.expected.size(), "Instruction has wrong length");
            for(int i = 0; i < input.expected.size(); ++i) {
                assertEquals(instruction.get(i), input.expected.get(i), "Wrong byte at position: " + i);
            }
        }
    }

    private record TestReadOperandsData(Opcode opcode, List<Integer> operands, int bytesRead) { }

    @Test
    public void testReadOperands() {

        List<TestReadOperandsData> inputs = Arrays.asList(
                new TestReadOperandsData(Opcode.CONSTANT, Arrays.asList(65535), 2),
                new TestReadOperandsData(Opcode.CONSTANT, Arrays.asList(65534), 2)
        );

        for(TestReadOperandsData input : inputs) {
            List<UByte> instructions = Code.make(input.opcode, input.operands);

            Optional<Definition> definition = Code.lookup(input.opcode.getOpcodeValue());
            assertTrue(definition.isPresent());

            Tuple<List<Integer>, Integer> readOperands = Code.readOperands(definition.get(), instructions.subList(1, instructions.size()));
            assertEquals(readOperands.getSecond(), input.bytesRead);

            assertEquals(readOperands.getFirst().get(0), input.operands.get(0));
        }
    }

    @Test
    public void testInstructionString() {
        List<List<UByte>> instructions = Arrays.asList(
                Code.make(Opcode.CONSTANT, Arrays.asList(1)),
                Code.make(Opcode.CONSTANT, Arrays.asList(2)),
                Code.make(Opcode.CONSTANT, Arrays.asList(65535))
            );
        List<UByte> flatInstructions = instructions.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.println(Code.string(flatInstructions));
    }
}
