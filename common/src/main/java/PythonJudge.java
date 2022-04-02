import com.foilen.smalltools.JavaEnvironmentValues;
import com.foilen.smalltools.consolerunner.ConsoleRunner;

import java.io.InputStream;
import java.io.OutputStream;

public class PythonJudge implements Runnable {

    private String command;
    private String argument;
    private InputStream appToJudgeIn;
    private OutputStream judgeToAppOut;
    private String[] arguments;

    private boolean quiet = false;

    private boolean success;

    public PythonJudge(String command, String argument, InputStream appToJudgeIn, OutputStream judgeToAppOut, String... arguments) {
        this.command = command;
        this.argument = argument;
        this.appToJudgeIn = appToJudgeIn;
        this.judgeToAppOut = judgeToAppOut;
        this.arguments = arguments;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    @Override
    public void run() {

        // Prepare repeaters to show the discussion on the console
        var repeaterAppToJudgeIn = new RepeaterInputStream(appToJudgeIn);
        if (!quiet) {
            repeaterAppToJudgeIn.add(new PrefixLineOutputStream("A2J> ", System.out));
        }

        var repeaterJudgeToAppOut = new RepeaterOutputStream();
        repeaterJudgeToAppOut.add(judgeToAppOut);
        if (!quiet) {
            repeaterJudgeToAppOut.add(new PrefixLineOutputStream("J2A> ", System.out));
        }

        // Start the application
        var workingDirectory = JavaEnvironmentValues.getWorkingDirectory();
        System.out.println("Run starting in directory " + workingDirectory);

        ConsoleRunner runner = new ConsoleRunner();
        runner.setCommand(command);
        runner.addArguments(argument);
        runner.addArguments(arguments);

        runner.setConsoleInput(repeaterAppToJudgeIn);
        runner.setConsoleOutput(repeaterJudgeToAppOut);

        int status = runner.execute();

        success = status == 0;

        System.out.println("Run completed with status " + status);

    }

    public boolean isSuccess() {
        return success;
    }

}
