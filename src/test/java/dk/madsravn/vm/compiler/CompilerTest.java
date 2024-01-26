package dk.madsravn.vm.compiler;

import dk.madsravn.interpreter.ast.Program;
import dk.madsravn.interpreter.lexer.Lexer;
import dk.madsravn.interpreter.object.IObject;
import dk.madsravn.interpreter.object.IntegerObject;
import dk.madsravn.interpreter.parser.Parser;
import dk.madsravn.vm.code.Code;
import dk.madsravn.vm.code.Opcode;
import dk.madsravn.vm.utility.UByte;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompilerTest {


    private record CompilerIntegerTestCase(String input, List<Integer> expectedConstants, List<List<UByte>> expectedInstructions) {}

    @Test
    public void testIntegerArithmetic() {
        List<CompilerIntegerTestCase> inputs = Arrays.asList(
                new CompilerIntegerTestCase(
                        "1 + 2", Arrays.asList(1, 2), Arrays.asList(Code.make(Opcode.CONSTANT, Arrays.asList(0)), Code.make(Opcode.CONSTANT, Arrays.asList(1)))
                )
        );

        for(CompilerIntegerTestCase input : inputs) {
            Program program = parse(input.input);
            Compiler compiler = new Compiler();
            compiler.compile(program);

            Bytecode bytecode = compiler.bytecode();

            testInstructions(input.expectedInstructions, bytecode.getInstructions());

            testConstants(input.expectedConstants, bytecode.getConstants());
        }
    }

    private void testInstructions(List<List<UByte>> expected, List<UByte> actual) {
        List<UByte> flatExpected = expected.stream().flatMap(List::stream).collect(Collectors.toList());
        assertEquals(flatExpected.size(), actual.size());
        assertEquals(flatExpected, actual);
    }

    private void testConstants(List<Integer> expected, List<IObject> actual) {
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); ++i) {
            assertTrue(actual.get(i) instanceof IntegerObject);
            IntegerObject integerObject = (IntegerObject) actual.get(i);
            assertEquals(integerObject.getValue(), expected.get(i));
        }
    }

    private Program parse(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();
        assertEquals(parser.getErrors().size(), 0);
        return program;
    }
}
