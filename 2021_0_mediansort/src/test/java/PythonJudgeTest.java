import com.foilen.smalltools.tools.ExecutorsTools;
import com.foilen.smalltools.tools.StreamsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

class PythonJudgeTest {

    private void executeWithArguments(String args) {
        // Create the pipes
        var appToJudge = StreamsTools.createPipe();
        var judgeToApp = StreamsTools.createPipe();

        // Start the solution
        ExecutorsTools.getCachedDaemonThreadPool().submit(() -> {
            try {
                new Solution(judgeToApp.getA(), new PrintStream(appToJudge.getB())).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the judge
        var pythonJudge = new PythonJudge("python", "local_testing_tool.py", appToJudge.getA(), judgeToApp.getB(), args);
        pythonJudge.run();

        // Assert Judge
        Assertions.assertTrue(pythonJudge.isSuccess());
    }

    @Test
    void testRun0() throws Exception {
        executeWithArguments("0");
    }

    @Test
    void testRun1() throws Exception {
        executeWithArguments("1");
    }

    @Test
    void testRun2() throws Exception {
        executeWithArguments("2");
    }

}