import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PrefixLineOutputStream extends OutputStream {

    private String prefix;
    private OutputStream out;

    private boolean startOfLine = true;
    private long initialTime = System.currentTimeMillis();

    public PrefixLineOutputStream(String prefix, OutputStream out) {
        this.prefix = prefix;
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {

        if (b == '\n' || b == '\r') {
            startOfLine = true;
        } else {
            if (startOfLine) {
                out.write((String.valueOf(System.currentTimeMillis() - initialTime) + " ").getBytes(StandardCharsets.UTF_8));
                out.write(prefix.getBytes(StandardCharsets.UTF_8));
                startOfLine = false;
            }
        }

        out.write(b);

    }

}
