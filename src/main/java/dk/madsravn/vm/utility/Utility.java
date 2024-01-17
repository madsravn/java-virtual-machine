package dk.madsravn.vm.utility;

import java.util.List;

public class Utility {
    public static<T> List<T> removeFirst(List<T> input) {
        return input.subList(1, input.size());
    }
}
