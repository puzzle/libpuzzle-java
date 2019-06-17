package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.exception;

import junit.framework.ComparisonFailure;

import java.io.PrintWriter;
import java.util.regex.Pattern;

public class ActionAssertionError extends ComparisonFailure {

    private static final int FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE = 2;

    private static final Pattern EXECUTE_PARAM_PATTERN = Pattern.compile("(?<=\\().*(?=\\))", Pattern.DOTALL);

    private int stackTraceLevelsToRemove;

    private ActionAssertionError(String message, String expected, String actual, int stackTraceLevelsToRemove) {
        super(message, expected, actual);
        this.stackTraceLevelsToRemove = stackTraceLevelsToRemove;
    }

    private ActionAssertionError(String message, ComparisonFailure cause, int stackTraceLevelsToRemove) {
        this(
                message,
                extractExecuteParam(cause.getExpected()),
                extractExecuteParam(cause.getActual()),
                stackTraceLevelsToRemove
        );
    }

    /**
     * Prints the stack trace using the given PrintWriter.
     * <p>
     * Ensures that all internal method calls are removed from the stack trace so that the first element references to
     * the method call in the test class.
     *
     * @param s PrintWriter to print the stack trace using.
     */
    @Override
    public void printStackTrace(PrintWriter s) {
        var originTrace = getStackTrace();
        if (originTrace != null && originTrace.length >= stackTraceLevelsToRemove) {
            var reducedTrace = new StackTraceElement[originTrace.length - stackTraceLevelsToRemove];
            System.arraycopy(
                    originTrace,
                    stackTraceLevelsToRemove,
                    reducedTrace,
                    0,
                    originTrace.length - stackTraceLevelsToRemove
            );
            setStackTrace(reducedTrace);
        }
        super.printStackTrace(s);
    }

    public static ActionAssertionError wrongActionParams(ComparisonFailure error) {
        return wrongActionParams(error, FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE);
    }

    public static ActionAssertionError wrongActionParams(ComparisonFailure error, int stackTraceLevelsToRemove) {
        return new ActionAssertionError(
                "Action is executed using wrong params.",
                error,
                ++stackTraceLevelsToRemove
        );
    }

    public static ActionAssertionError missingActionExecution() {
        return missingActionExecution(FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE);
    }

    public static ActionAssertionError missingActionExecution(int stackTraceLevelsToRemove) {
        return new ActionAssertionError(
                "Action was not executed.",
                "<action was executed>",
                "<action was not executed>",
                ++stackTraceLevelsToRemove
        );
    }

    private static String extractExecuteParam(String error) {
        var matcher = EXECUTE_PARAM_PATTERN.matcher(error);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return error;
    }
}
