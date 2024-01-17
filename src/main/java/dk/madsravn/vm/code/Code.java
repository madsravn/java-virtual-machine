package dk.madsravn.vm.code;

import dk.madsravn.vm.utility.UByte;

import java.sql.Array;
import java.util.*;

public class Code {
    // type Instructions List<Byte>
    // type Opcode Byte

    private Map<UByte, Definition> definitions;
    {
        this.definitions = new HashMap<>();
        definitions.put(Opcode.CONSTANT.getOpcodeValue(), new Definition("OpConstant", Arrays.asList(2)));
    }

    public Optional<Definition> lookup(UByte opcode) {
        return Optional.ofNullable(definitions.get(opcode));
    }

    public List<UByte> make(Opcode opcode, List<Integer> operands) {
        var lookupValue = lookup(opcode.getOpcodeValue());
        if(lookupValue.isPresent()) {
            var value = lookupValue.get();
            // Can be a stream instead.
            int instructionLength = 1;
            for(int width : value.getOperandWidths()) {
                instructionLength += width;
            }

            List<UByte> instruction = new ArrayList<>(instructionLength);
            instruction.add(opcode.getOpcodeValue());

            for(int i = 0; i < operands.size(); i++) {
                var width = value.getOperandWidths().get(i);
                var operand = operands.get(i);
                switch (width) {
                    case 2:
                        UByte second = new UByte(0xff & operand);
                        UByte first = new UByte((operand >> 8) & 0xff);
                        instruction.add(first);
                        instruction.add(second);
                    default:
                }
            }

            return instruction;
        }
        return new ArrayList<>();
    }
}
