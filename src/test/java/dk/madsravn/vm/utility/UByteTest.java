package dk.madsravn.vm.utility;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UByteTest {
    @Test
    public void testUByteEquality() {
        UByte firstByte = new UByte(54);
        UByte secondByte = new UByte(54);
        UByte thirdByte = new UByte(55);
        assertEquals(firstByte, secondByte);
        assertNotEquals(firstByte, thirdByte);
    }

    @Test
    public void testUByteToString() {
        List<UByte> bytes = IntStream.range(0, 255).mapToObj(b -> new UByte(b)).collect(Collectors.toList());
        List<String> stringBytes = IntStream.range(0, 255).mapToObj(b -> "" + b).collect(Collectors.toList());
        assertEquals(bytes.size(), stringBytes.size());
        for(int i = 0; i < bytes.size(); ++i) {
            assertEquals(bytes.get(i).toString(), stringBytes.get(i));
        }
    }

    @Test
    public void testUByteClampedValue() {
        UByte min = new UByte(0);
        UByte max = new UByte(255);
        List<UByte> belowMin = IntStream.range(-10, 0).mapToObj(b -> new UByte(b)).collect(Collectors.toList());
        List<UByte> aboveMax = IntStream.range(256, 266).mapToObj(b -> new UByte(b)).collect(Collectors.toList());
        for(UByte b : belowMin) {
            assertEquals(min, b);
        }
        for(UByte b : aboveMax)
            assertEquals(max, b);
    }
}
