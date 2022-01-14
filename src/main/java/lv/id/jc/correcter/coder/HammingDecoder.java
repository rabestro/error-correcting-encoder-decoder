package lv.id.jc.correcter.coder;

/**
 * Implements Hamming error-correction decoding
 */
public class HammingDecoder implements Coder {

    @Override
    public byte[] apply(byte[] data) {
        var out = new byte[data.length / 2];
        int index = 0;

        for (int i = 0; i < out.length; ++i) {
            out[i] = (byte) ((decode(data[index++]) << 4) + decode(data[index++]));
        }
        return out;
    }

    private byte decode(byte data) {
        var bits = new int[8];
        for (int i = 0; i < 8; ++i) {
            bits[7 - i] = (data & (1 << i)) >> i;
        }
        int error = 0;
        error += bits[0] ^ bits[2] ^ bits[4] ^ bits[6];
        error += 2 * (bits[1] ^ bits[2] ^ bits[5] ^ bits[6]);
        error += 4 * (bits[3] ^ bits[4] ^ bits[5] ^ bits[6]);

        if (error > 0) {
            bits[error - 1] ^= 1;
        }

        return (byte) (bits[6] + 2 * bits[5] + 4 * bits[4] + 8 * bits[2]);
    }
}
