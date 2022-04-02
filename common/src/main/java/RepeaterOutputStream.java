import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class RepeaterOutputStream extends OutputStream {

    private List<OutputStream> toOutputStreams = new LinkedList<>();

    public void add(OutputStream toOutputStream) {
        toOutputStreams.add(toOutputStream);
    }

    @Override
    public void write(int b) throws IOException {
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(b);
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(b);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.write(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.flush();
        }
    }

    @Override
    public void close() throws IOException {
        for (var toOutputStream : toOutputStreams) {
            toOutputStream.close();
        }
    }
}
