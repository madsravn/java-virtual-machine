package dk.madsravn.vm.code;
import java.util.List;

public class Definition {
    private final String name;
    private List<Integer> operandWidths;

    public Definition(String name, List<Integer> operandWidths) {
        this.name = name;
        this.operandWidths = operandWidths;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getOperandWidths() {
        return operandWidths;
    }
}
