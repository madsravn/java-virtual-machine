package dk.madsravn.vm.utility;

import dk.madsravn.vm.code.Opcode;

import java.util.List;

public class Utility {
    public static<T> List<T> removeFirst(List<T> input) {
        return input.subList(1, input.size());
    }

    public static Opcode opcodeFromUByte(UByte ubyte) {
        switch (ubyte.toInt()) {
            case 1:
                return Opcode.CONSTANT;
            default:
                throw new IllegalArgumentException("Cannot find OPCODE from UByte " + ubyte.toInt());
        }
    }

    // TODO: Book says UInt16. Do we need that?
    public static int readInt16(List<UByte> input, int startIndex) {
        int value = (input.get(startIndex).toInt() << 8) + input.get(startIndex + 1).toInt();
        return value;
    }
}
