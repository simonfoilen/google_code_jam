import com.foilen.smalltools.test.asserts.AssertTools;
import com.foilen.smalltools.tools.FileTools;
import com.foilen.smalltools.tools.ResourceTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
    void executeSample_4() throws Exception {
        executeSample("SolutionTest-executeSample_4-in.txt", "SolutionTest-executeSample_4-out.txt");
    }

    @Test
    void executeSample_5() throws Exception {
        executeSample("SolutionTest-executeSample_5-in.txt", "SolutionTest-executeSample_5-out.txt");
    }

    @Test
    void reverse_1_1() {
        var l = new int[]{4};

        Assertions.assertEquals(1, Solution.reverse(l, 0, 0));
        AssertTools.assertJsonComparison(new int[]{4}, l);
    }

    @Test
    void reverse_1_2() {
        var l = new int[]{1, 2, 3, 4, 5};

        Assertions.assertEquals(1, Solution.reverse(l, 2, 2));
        AssertTools.assertJsonComparison(new int[]{1, 2, 3, 4, 5}, l);
    }

    @Test
    void reverse_2_1() {
        var l = new int[]{1, 2, 3, 4, 5};

        Assertions.assertEquals(2, Solution.reverse(l, 2, 3));
        AssertTools.assertJsonComparison(new int[]{1, 2, 4, 3, 5}, l);
    }

    @Test
    void reverse_3_1() {
        var l = new int[]{1, 2, 3, 4, 5};

        Assertions.assertEquals(3, Solution.reverse(l, 2, 4));
        AssertTools.assertJsonComparison(new int[]{1, 2, 5, 4, 3}, l);
    }

    @Test
    void reverse_4_1() {
        var l = new int[]{1, 2, 3, 4, 5};

        Assertions.assertEquals(4, Solution.reverse(l, 1, 4));
        AssertTools.assertJsonComparison(new int[]{1, 5, 4, 3, 2}, l);
    }
}