package dk.madsravn.vm.code;

import dk.madsravn.vm.utility.UByte;

public enum Opcode {
    CONSTANT(new UByte(1)),
    ;
    private final UByte opcodeValue;

    Opcode(UByte opcodeValue) {
        this.opcodeValue = opcodeValue;
    }

    public UByte getOpcodeValue() {
        return opcodeValue;
    }

    @Override
    public String toString() {
        return "[" + this.name() + " - " + this.opcodeValue + "]";
    }
}
