package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;

import junit.framework.ComparisonFailure;

import java.io.PrintWriter;
import java.util.regex.Pattern;

public class RestAssertionError extends ComparisonFailure {

    private static final int FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE = 2;

    private static final Pattern EXECUTE_PARAM_PATTERN = Pattern.compile("(?<=\\().*(?=\\))", Pattern.DOTALL);

    private int stackTraceLevelsToRemove;

    public RestAssertionError(String message, ComparisonFailure cause, int stackTraceLevelsToRemove) {
        super(message, extractExecuteParam(cause.getActual()), extractExecuteParam(cause.getExpected()));
        this.stackTraceLevelsToRemove = stackTraceLevelsToRemove;
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

    public static RestAssertionError wrongResponseBody(ComparisonFailure error) {
        return new RestAssertionError(
                "Response body is of wrong type.",
                error,
                FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE
        );
    }

    public static RestAssertionError wrongActionParams(ComparisonFailure error) {
        return new RestAssertionError(
                "Action is executed using wrong executed.",
                error,
                FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE
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
