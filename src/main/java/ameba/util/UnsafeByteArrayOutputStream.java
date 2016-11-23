package ameba.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * <p>UnsafeByteArrayOutputStream class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class UnsafeByteArrayOutputStream extends OutputStream {

    protected byte[] buffer;

    protected int  count;

    /**
     * <p>Constructor for UnsafeByteArrayOutputStream.</p>
     */
    public UnsafeByteArrayOutputStream(){
        this(32);
    }

    /**
     * <p>Constructor for UnsafeByteArrayOutputStream.</p>
     *
     * @param size a int.
     */
    public UnsafeByteArrayOutputStream(int size){
        if (size < 0) throw new IllegalArgumentException("Negative initial size: " + size);
        buffer = new byte[size];
    }

    private static byte[] copyOf(byte[] src, int length) {
        byte[] dest = new byte[length];
        System.arraycopy(src, 0, dest, 0, Math.min(src.length, length));
        return dest;
    }

    /**
     * <p>write.</p>
     *
     * @param b a int.
     */
    public void write(int b) {
        int newcount = count + 1;
        if (newcount > buffer.length) buffer = copyOf(buffer, Math.max(buffer.length << 1, newcount));
        buffer[count] = (byte) b;
        count = newcount;
    }

    /**
     * {@inheritDoc}
     */
    public void write(byte b[], int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) throw new IndexOutOfBoundsException();
        if (len == 0) return;
        int newcount = count + len;
        if (newcount > buffer.length) buffer = copyOf(buffer, Math.max(buffer.length << 1, newcount));
        System.arraycopy(b, off, buffer, count, len);
        count = newcount;
    }

    /**
     * <p>size.</p>
     *
     * @return a int.
     */
    public int size() {
        return count;
    }

    /**
     * <p>reset.</p>
     */
    public void reset() {
        count = 0;
    }

    /**
     * <p>toByteArray.</p>
     *
     * @return an array of byte.
     */
    public byte[] toByteArray() {
        return copyOf(buffer, count);
    }

    /**
     * <p>toByteBuffer.</p>
     *
     * @return a {@link java.nio.ByteBuffer} object.
     */
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(buffer, 0, count);
    }

    /**
     * <p>writeTo.</p>
     *
     * @param out a {@link java.io.OutputStream} object.
     * @throws java.io.IOException if any.
     */
    public void writeTo(OutputStream out) throws IOException {
        out.write(buffer, 0, count);
    }

    /**
     * <p>toString.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String toString() {
        return new String(buffer, 0, count);
    }

    /**
     * <p>toString.</p>
     *
     * @param charset a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.UnsupportedEncodingException if any.
     */
    public String toString(String charset) throws UnsupportedEncodingException {
        return new String(buffer, 0, count, charset);
    }

    /**
     * <p>close.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void close() throws IOException {
    }
}
