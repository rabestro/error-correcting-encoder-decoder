package lv.id.jc.correcter.coder;

import java.util.Arrays;
import java.util.Random;

/**
 * Bit-level error emulator
 * <p>
 * This coder makes an error in one bit per byte.
 */
public class ErrorEmulator implements Coder {
    private final Random random = new Random();

    @Override
    public byte[] apply(byte[] bytes) {
        var out = Arrays.copyOf(bytes, bytes.length);

        for (int i = out.length; i-- > 0; ) {
            out[i] ^= (byte) (1 << random.nextInt(8));
        }

        return out;
    }
}
