package dk.madsravn.vm.virtualmachine;

import dk.madsravn.interpreter.object.IObject;
import dk.madsravn.vm.code.Opcode;
import dk.madsravn.vm.compiler.Bytecode;
import dk.madsravn.vm.utility.UByte;
import dk.madsravn.vm.utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class VirtualMachine {
    private List<IObject> constants;
    private List<UByte> instructions;
    private List<IObject> stack;
    private int stackPointer;

    private final int stackSize = 2048;

    public VirtualMachine(Bytecode bytecode) {
        this.constants = bytecode.getConstants();
        this.instructions = bytecode.getInstructions();
        this.stack = new ArrayList<>(stackSize);
        IntStream.range(0, stackSize).forEach(x -> this.stack.add(null));
        this.stackPointer = 0;
    }

    public Optional<IObject> stackTop() {
        if(stackPointer == 0) {
            return Optional.empty();
        }
        return Optional.of(stack.get(stackPointer - 1));
    }

    // TODO: Error propagation
    public void run() {
        for(int ip = 0; ip < instructions.size(); ++ip) {
            Opcode opcode = Utility.opcodeFromUByte(instructions.get(ip));
            switch(opcode) {
                case Opcode.CONSTANT -> {
                    int constIndex = Utility.readInt16(instructions, ip+1);
                    ip += 2;

                    this.push(constants.get(constIndex));
                }
            }

        }
    }

    private void push(IObject object) {
        if(stackPointer > stackSize) {
            throw new IllegalStateException("Stack overflow");
        }

        stack.set(stackPointer, object);
        ++stackPointer;
    }

}
