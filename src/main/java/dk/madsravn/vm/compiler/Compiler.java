package dk.madsravn.vm.compiler;

import dk.madsravn.interpreter.ast.*;
import dk.madsravn.interpreter.object.IObject;
import dk.madsravn.interpreter.object.IntegerObject;
import dk.madsravn.vm.code.Code;
import dk.madsravn.vm.code.Opcode;
import dk.madsravn.vm.utility.UByte;
import java.util.*;
public class Compiler {
    private List<UByte> instructions;
    private List<IObject> constants;

    public Bytecode bytecode() {
        return new Bytecode(this.instructions, this.constants);
    }

    public Compiler() {
        this.instructions = new ArrayList<>();
        this.constants = new ArrayList<>();
    }

    private int addConstant(IObject object) {
        constants.add(object);
        return constants.size() - 1;
    }

    private int emit(Opcode opcode, List<Integer> operands) {
        List<UByte> instruction = Code.make(opcode, operands);
        int position = addInstruction(instruction);
        return position;
    }

    private int addInstruction(List<UByte> instructions) {
        int instructionPosition = instructions.size();
        this.instructions.addAll(instructions);
        return instructionPosition;
    }

    public void compile(INode node) {
        switch (node) {
            case Program program -> {
                for(IStatement statement : program.getStatements()) {
                    compile(statement);
                }
            }
            case ExpressionStatement expressionStatement -> {
                compile(expressionStatement.getExpression());
            }
            case InfixExpression infixExpression -> {
                compile(infixExpression.getLeft());
                compile(infixExpression.getRight());

            }
            case IntegerLiteral integerLiteral -> {
                IntegerObject literal = new IntegerObject(integerLiteral.getValue());
                emit(Opcode.CONSTANT, Arrays.asList(addConstant(literal)));
            }
            default -> {}
        }

    }


}
