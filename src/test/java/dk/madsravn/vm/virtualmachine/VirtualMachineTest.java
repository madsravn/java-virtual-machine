package dk.madsravn.vm.virtualmachine;

import dk.madsravn.interpreter.ast.Program;
import dk.madsravn.interpreter.lexer.Lexer;
import dk.madsravn.interpreter.object.IObject;
import dk.madsravn.interpreter.object.IntegerObject;
import dk.madsravn.interpreter.parser.Parser;
import dk.madsravn.vm.compiler.Compiler;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VirtualMachineTest {

    private record vmIntegerTestCase(String input, int expected) {}

    @Test
    public void testIntegerArithmetic() {
        List<vmIntegerTestCase> inputs = Arrays.asList(
                new vmIntegerTestCase("1", 1),
                new vmIntegerTestCase("2", 2),
                new vmIntegerTestCase("1 + 2", 2) // FIXME
        );
        runVirtualMachineTests(inputs);
    }

    public void runVirtualMachineTests(List<vmIntegerTestCase> inputs) {
        for(vmIntegerTestCase input : inputs) {
            Program program = parse(input.input);
            Compiler compiler = new Compiler();
            compiler.compile(program);

            VirtualMachine virtualMachine = new VirtualMachine(compiler.bytecode());
            virtualMachine.run();

            var stackElement = virtualMachine.stackTop();
            assertTrue(stackElement.isPresent());

            testIntegerObject(input.expected, stackElement.get());
        }
    }

    private void testIntegerObject(int expected, IObject actual) {
        assertTrue(actual instanceof IntegerObject);
        IntegerObject integerObject = (IntegerObject) actual;
        assertEquals(integerObject.getValue(), expected);
    }
    private Program parse(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();
        assertEquals(parser.getErrors().size(), 0);
        return program;
    }
}
