package lv.id.jc.correcter.coder;

/**
 * Implements Hamming error-correction encoding
 */
public class HammingEncoder implements Coder {

    /**
     * Applies Hamming error-correction encoding to the given data.
     *
     * The Hamming code [7,4] is used, i.e. will write 7 bits, 
     * 4 of them would be significant bits, 3 of them would be parity bits 
     * and the last one would be unused (it should always be set to zero).
     *
     * The returned array of bytes is two times bigger than source data
     *
     * @param data - source binary data to encode
     * @return encoded binary data.
     */
    @Override
    public byte[] apply(byte[] data) {
        var out = new byte[2 * data.length];
        var index = 0;
        for (var element : data) {
            var partOne = (element & 0b11110000) >> 4;
            var partTwo = element & 0b00001111;
            out[index++] = encode(partOne);
            out[index++] = encode(partTwo);
        }
        return out;
    }

    private byte encode(int data) {
        int d3 = (data & 0b1000) >> 3;
        int d5 = (data & 0b0100) >> 2;
        int d6 = (data & 0b0010) >> 1;
        int d7 = (data & 0b0001);
        int p1 = d3 ^ d5 ^ d7;
        int p2 = d3 ^ d6 ^ d7;
        int p4 = d5 ^ d6 ^ d7;

        int result = (p1 << 7) + (p2 << 6) + (d3 << 5) + (p4 << 4) + (d5 << 3) + (d6 << 2) + (d7 << 1);

        return (byte) result;
    }
}
