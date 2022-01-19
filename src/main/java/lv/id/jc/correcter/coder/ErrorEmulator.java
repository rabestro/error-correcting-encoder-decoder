package lv.id.jc.correcter.coder;

import java.util.Arrays;
import java.util.random.RandomGenerator;

/**
 * Bit-level error emulator
 * <p>
 * This coder makes an error in one bit per byte.
 */
public record ErrorEmulator(RandomGenerator randomGenerator) implements Coder {
    @Override
    public byte[] apply(byte[] bytes) {
        var out = Arrays.copyOf(bytes, bytes.length);

        for (int i = out.length; i-- > 0; ) {
            out[i] ^= (byte) (1 << randomGenerator().nextInt(Byte.SIZE));
        }

        return out;
    }
}
