import com.foilen.smalltools.test.asserts.AssertTools;
import com.foilen.smalltools.tools.FileTools;
import com.foilen.smalltools.tools.ResourceTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.nio.file.Files;

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
    void cost_1() throws Exception {
        var x = 3;
        var y = 7;
        Assertions.assertEquals(x + y, Solution.calculateCost(x, y, "CJCCCC"));
        Assertions.assertEquals(x + y, Solution.calculateCost(x, y, "CJJCCC"));
        Assertions.assertEquals(2 * x + y, Solution.calculateCost(x, y, "CJCCCJ"));
        Assertions.assertEquals(2 * x + y, Solution.calculateCost(x, y, "CJJCCJ"));
    }

    @Test
    void replaceUnknowns_1_single() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'?'};
        Solution.replaceUnknowns(x, y, s, 0, 0);
        AssertTools.assertJsonComparison(new char[]{'C'}, s);
    }

    @Test
    void replaceUnknowns_1_C_before() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'C', '?'};
        Solution.replaceUnknowns(x, y, s, 1, 1);
        AssertTools.assertJsonComparison(new char[]{'C', 'C'}, s);
    }

    @Test
    void replaceUnknowns_1_J_before() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'J', '?'};
        Solution.replaceUnknowns(x, y, s, 1, 1);
        AssertTools.assertJsonComparison(new char[]{'J', 'J'}, s);
    }


    @Test
    void replaceUnknowns_1_C_before_lowerCJ() throws Exception {
        var x = -3;
        var y = 7;
        var s = new char[]{'C', '?'};
        Solution.replaceUnknowns(x, y, s, 1, 1);
        AssertTools.assertJsonComparison(new char[]{'C', 'J'}, s);
    }

    @Test
    void replaceUnknowns_1_C_after() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'?', 'C'};
        Solution.replaceUnknowns(x, y, s, 0, 0);
        AssertTools.assertJsonComparison(new char[]{'C', 'C'}, s);
    }

    @Test
    void replaceUnknowns_1_J_after() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'?', 'J'};
        Solution.replaceUnknowns(x, y, s, 0, 0);
        AssertTools.assertJsonComparison(new char[]{'J', 'J'}, s);
    }

    @Test
    void replaceUnknowns_1_C_after_lowerJC() throws Exception {
        var x = 3;
        var y = -7;
        var s = new char[]{'?', 'C'};
        Solution.replaceUnknowns(x, y, s, 0, 0);
        AssertTools.assertJsonComparison(new char[]{'J', 'C'}, s);
    }

    @Test
    void replaceUnknowns_2_single() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'?', '?'};
        Solution.replaceUnknowns(x, y, s, 0, 1);
        AssertTools.assertJsonComparison(new char[]{'C', 'C'}, s);
    }

    @Test
    void replaceUnknowns_2_lowerCJ() throws Exception {
        var x = -3;
        var y = 7;
        var s = new char[]{'?', '?'};
        Solution.replaceUnknowns(x, y, s, 0, 1);
        AssertTools.assertJsonComparison(new char[]{'C', 'J'}, s);
    }

    @Test
    void replaceUnknowns_3_lowerCJ() throws Exception {
        var x = -3;
        var y = 2;
        var s = new char[]{'?', '?', '?'};
        Solution.replaceUnknowns(x, y, s, 0, 2);
        AssertTools.assertJsonComparison(new char[]{'C', 'J', 'C'}, s);
    }

    @Test
    void replaceUnknowns_2_lowerJC() throws Exception {
        var x = 3;
        var y = -7;
        var s = new char[]{'?', '?'};
        Solution.replaceUnknowns(x, y, s, 0, 1);
        AssertTools.assertJsonComparison(new char[]{'J', 'C'}, s);
    }

    @Test
    void replaceUnknowns_4_single() throws Exception {
        var x = 3;
        var y = 7;
        var s = new char[]{'?', '?', '?', '?'};
        Solution.replaceUnknowns(x, y, s, 0, 3);
        AssertTools.assertJsonComparison(new char[]{'C', 'C', 'C', 'C'}, s);
    }

    @Test
    void replaceUnknowns_4_alternateCJ() throws Exception {
        var x = -7;
        var y = 3;
        var s = new char[]{'J', '?', '?', '?', '?', 'J'};
        Solution.replaceUnknowns(x, y, s, 1, 4);
        AssertTools.assertJsonComparison(new char[]{'J', 'C', 'J', 'C', 'J', 'J'}, s);
    }


    @Test
    void replaceUnknowns_8_alternateCJ() throws Exception {
        var x = -7;
        var y = 3;
        var s = new char[]{'J', '?', '?', '?', '?', '?', '?', '?', '?', 'J'};
        Solution.replaceUnknowns(x, y, s, 1, 8);
        AssertTools.assertJsonComparison(new char[]{'J', 'C', 'J', 'C', 'J', 'C', 'J', 'C', 'J', 'J'}, s);
    }

}