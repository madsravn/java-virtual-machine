package dk.madsravn.vm.code;

import dk.madsravn.vm.utility.UByte;

import java.sql.Array;
import java.util.*;

public class Code {
    // type Instructions List<Byte>
    // type Opcode Byte

    private static Map<UByte, Definition> definitions;
    static {
        definitions = new HashMap<>();
        definitions.put(Opcode.CONSTANT.getOpcodeValue(), new Definition("OpConstant", Arrays.asList(2)));
    }

    public static Optional<Definition> lookup(UByte opcode) {
        return Optional.ofNullable(definitions.get(opcode));
    }

    public static List<UByte> make(Opcode opcode, List<Integer> operands) {
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
                        break;
                    default:
                }
            }

            return instruction;
        }
        return new ArrayList<>();
    }

    public static Tuple<List<Integer>,Integer> readOperands(Definition definition, List<UByte> instructions) {
        List<Integer> operands = new ArrayList<>();
        for(int width : definition.getOperandWidths()) {
            switch (width) {
                case 2:
                    int value = (instructions.get(0).toInt() << 8) + instructions.get(1).toInt();
                    operands.add(value);
                default:
            }
        }
        return new Tuple(operands, 2);
    }

    public static String string(List<UByte> instructions) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(i < instructions.size()) {
            UByte instruction = instructions.get(i);
            Optional<Definition> definition = Code.lookup(instruction);
            if(definition.isPresent()) {
                Tuple<List<Integer>, Integer> operands = Code.readOperands(definition.get(), instructions.subList(i+1, instructions.size()));
                sb.append(String.format("%04d %s\n", i, formatInstruction(definition.get(), operands.getFirst())));
                i +=  1 + operands.getSecond();
            }
        }
        return sb.toString();
    }

    private static String formatInstruction(Definition definition, List<Integer> operands) {
        if(definition.getOperandWidths().size() != operands.size()) {
            throw new IllegalStateException("Operand length " + operands.size() + " does not match defined size of " + definition.getOperandWidths().size());
        }
        switch (definition.getOperandWidths().size()) {
            case 1:
                return definition.getName() + " " + operands.get(0);
        }

        throw new IllegalArgumentException("Unhandled operandCount for " + definition.getName());
    }
}
