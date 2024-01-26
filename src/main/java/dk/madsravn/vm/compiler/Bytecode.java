package dk.madsravn.vm.compiler;

import dk.madsravn.interpreter.object.IObject;
import dk.madsravn.vm.utility.UByte;

import java.util.List;

public class Bytecode {
    private final List<UByte> instructions;
    private final List<IObject> constants;

    public Bytecode(List<UByte> instructions, List<IObject> constants) {
        this.instructions = instructions;
        this.constants = constants;
    }

    public List<UByte> getInstructions() {
        return instructions;
    }

    public List<IObject> getConstants() {
        return constants;
    }
}
