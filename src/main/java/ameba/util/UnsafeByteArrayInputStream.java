package ameba.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>UnsafeByteArrayInputStream class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class UnsafeByteArrayInputStream extends InputStream {

    protected byte data[];

    protected int  position, limit, mark = 0;

    /**
     * <p>Constructor for UnsafeByteArrayInputStream.</p>
     *
     * @param buf an array of byte.
     */
    public UnsafeByteArrayInputStream(byte buf[]){
        this(buf, 0, buf.length);
    }

    /**
     * <p>Constructor for UnsafeByteArrayInputStream.</p>
     *
     * @param buf    an array of byte.
     * @param offset a int.
     */
    public UnsafeByteArrayInputStream(byte buf[], int offset){
        this(buf, offset, buf.length - offset);
    }

    /**
     * <p>Constructor for UnsafeByteArrayInputStream.</p>
     *
     * @param buf an array of byte.
     * @param offset a int.
     * @param length a int.
     */
    public UnsafeByteArrayInputStream(byte buf[], int offset, int length){
        data = buf;
        position = mark = offset;
        limit = Math.min(offset + length, buf.length);
    }

    /**
     * <p>read.</p>
     *
     * @return a int.
     */
    public int read() {
        return (position < limit) ? (data[position++] & 0xff) : -1;
    }

    /** {@inheritDoc} */
    public int read(byte b[], int off, int len) {
        if (b == null) throw new NullPointerException("read byte[] == null");
        if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException();
        if (position >= limit) return -1;
        if (position + len > limit) len = limit - position;
        if (len <= 0) return 0;
        System.arraycopy(data, position, b, off, len);
        position += len;
        return len;
    }

    /** {@inheritDoc} */
    public long skip(long len) {
        if (position + len > limit) len = limit - position;
        if (len <= 0) return 0;
        position += len;
        return len;
    }

    /**
     * <p>available.</p>
     *
     * @return a int.
     */
    public int available() {
        return limit - position;
    }

    /**
     * <p>markSupported.</p>
     *
     * @return a boolean.
     */
    public boolean markSupported() {
        return true;
    }

    /** {@inheritDoc} */
    public void mark(int readAheadLimit) {
        mark = position;
    }

    /**
     * <p>reset.</p>
     */
    public void reset() {
        position = mark;
    }

    /**
     * <p>close.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void close() throws IOException {
    }

    /**
     * <p>position.</p>
     *
     * @return a int.
     */
    public int position() {
        return position;
    }

    /**
     * <p>position.</p>
     *
     * @param newPosition a int.
     */
    public void position(int newPosition) {
        position = newPosition;
    }

    /**
     * <p>size.</p>
     *
     * @return a int.
     */
    public int size() {
        return data == null ? 0 : data.length;
    }
}
