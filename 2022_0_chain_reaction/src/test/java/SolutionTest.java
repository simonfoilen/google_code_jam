import com.foilen.smalltools.test.asserts.AssertTools;
import com.foilen.smalltools.tools.FileTools;
import com.foilen.smalltools.tools.ResourceTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    void executeSample(String inResourceName, String expectedOutResourceName) throws Exception {
        var actualFile = Files.createTempFile(null, null).toFile();
        var out = new PrintStream(actualFile);

        Solution.execute(ResourceTools.getResourceAsStream(inResourceName, getClass()), out);
        out.close();

        String actual = FileTools.getFileAsString(actualFile);
        String expected = ResourceTools.getResourceAsString(expectedOutResourceName, getClass());
        AssertTools.assertIgnoreLineFeed(expected, actual);
    }

    @Test
    void executeSample1() throws Exception {
        executeSample("SolutionTest-executeSample1-in.txt", "SolutionTest-executeSample1-out.txt");
    }

    @Test
    void executeSample2() throws Exception {
        executeSample("SolutionTest-executeSample2-in.txt", "SolutionTest-executeSample2-out.txt");
    }

    @Test
    void executeSample3() throws Exception {
        executeSample("SolutionTest-executeSample3-in.txt", "SolutionTest-executeSample3-out.txt");
    }

}