import com.foilen.smalltools.tools.ExecutorsTools;
import com.foilen.smalltools.tools.StreamsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

class PythonJudgeTest {

    @Test
    void testRun() throws Exception {
        // Create the pipes
        var appToJudge = StreamsTools.createPipe();
        var judgeToApp = StreamsTools.createPipe();

        // Start the solution
        ExecutorsTools.getCachedDaemonThreadPool().submit(() -> new Solution(judgeToApp.getA(), new PrintStream(appToJudge.getB())).execute());

        // Start the judge
        var pythonJudge = new PythonJudge("python", "local_testing_tool.py", appToJudge.getA(), judgeToApp.getB());
        pythonJudge.run();

        // Assert Judge
        Assertions.assertTrue(pythonJudge.isSuccess());
    }

}