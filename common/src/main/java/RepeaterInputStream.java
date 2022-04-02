import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RepeaterInputStream extends InputStream {

    private InputStream fromInputStream;
    private List<OutputStream> toOutputStreams = new LinkedList<>();

    public RepeaterInputStream(InputStream fromInputStream) {
        this.fromInputStream = fromInputStream;
    }

    public void add(OutputStream toOutputStream) {
        toOutputStreams.add(toOutputStream);
    }

    @Override
    public int read() throws IOException {
        var c = fromInputStream.read();
        if (c == -1) {
            for (var toOutputStream : toOutputStreams) {
                toOutputStream.close();
            }
        } else {
            for (var toOutputStream : toOutputStreams) {
                toOutputStream.write(c);
            }
        }
        return c;
    }

    @Override
    public int read(byte[] b) throws IOException {
        var result = fromInputStream.read(b);
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(Arrays.copyOf(b, result));
        }
        return result;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        var result = fromInputStream.read(b, off, len);
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(Arrays.copyOfRange(b, off, result));
        }
        return result;
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        var result = fromInputStream.readAllBytes();
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(result);
        }
        return result;
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        var result = fromInputStream.readNBytes(len);
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(result);
        }
        return result;
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        var result = fromInputStream.readNBytes(b, off, len);
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(Arrays.copyOfRange(b, off, result));
        }
        return result;
    }

    @Override
    public long skip(long n) throws IOException {
        return fromInputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return fromInputStream.available();
    }

    @Override
    public void close() throws IOException {
        fromInputStream.close();
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.close();
        }
    }

    @Override
    public synchronized void mark(int readlimit) {
        fromInputStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        fromInputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return fromInputStream.markSupported();
    }

}
