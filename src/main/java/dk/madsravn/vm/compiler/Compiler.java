package dk.madsravn.vm.compiler;

import dk.madsravn.interpreter.ast.INode;
import dk.madsravn.interpreter.object.IObject;
import dk.madsravn.vm.utility.UByte;
import java.util.*;
public class Compiler {
    private List<UByte> instructions;
    private List<IObject> constants;

    public Bytecode bytecode() {
        return new Bytecode(this.instructions, this.constants);
    }

    public void compile(INode node) {

    }


}
