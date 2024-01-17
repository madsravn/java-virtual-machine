package dk.madsravn.vm.utility;

import java.util.Objects;

public class UByte {
    private final int value;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 255;

    public UByte(int value) {
        // Clamp value
        int clampedValue = Math.max(Math.min(value, MAX_VALUE), MIN_VALUE);
        this.value = clampedValue & 0xff;
    }

    public int toInt() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if(other instanceof UByte) {
            UByte otherUByte = (UByte)other;
            return this.value == otherUByte.value;
        }
        return false;
    }

    // Effective Java Item 11 - Always override hashCode if equals is overriden
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
