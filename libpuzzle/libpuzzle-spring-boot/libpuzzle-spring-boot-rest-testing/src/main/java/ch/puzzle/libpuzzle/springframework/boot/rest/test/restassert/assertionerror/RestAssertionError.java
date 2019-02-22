package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror;

import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RestAssertionError extends ComparisonFailure {

    private static final int FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE = 2;

    private static final Pattern EXECUTE_PARAM_PATTERN = Pattern.compile("(?<=\\().*(?=\\))", Pattern.DOTALL);

    private int stackTraceLevelsToRemove;

    private RestAssertionError(String message, String expected, String actual, int stackTraceLevelsToRemove) {
        super(message, expected, actual);
        this.stackTraceLevelsToRemove = stackTraceLevelsToRemove;
    }

    private RestAssertionError(String message, ComparisonFailure cause, int stackTraceLevelsToRemove) {
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

    public static RestAssertionError wrongActionParams(ComparisonFailure error) {
        return new RestAssertionError(
                "Action is executed using wrong params.",
                error,
                FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE
        );
    }

    public static RestAssertionError missingActionInitialization() {
        return new RestAssertionError(
                "Action was not initialized.",
                "<action was initialized>",
                "<action was not initialized>",
                FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE
        );
    }

    public static RestAssertionError missingActionExecution() {
        return new RestAssertionError(
                "Action was not executed.",
                "<action was executed>",
                "<action was not executed>",
                FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE
        );
    }

    public static RestAssertionError missingActionParam(Class<?> actionClass, String paramName, Object expected) {
        return new RestAssertionError(
                String.format("Action parameter '%s' was not specified.", extractMethod(actionClass, paramName)),
                String.valueOf(expected),
                "<undefined>",
                FACTORY_METHOD_STACK_TRACE_LEVELS_TO_REMOVE
        );
    }

    private static String extractMethod(Class<?> clazz, String methodName) {
        assert Arrays.stream(clazz.getMethods()).anyMatch(method -> methodName.equals(method.getName()));
        return String.format("%s.%s", clazz.getName(), methodName);
    }

    private static String extractExecuteParam(String error) {
        var matcher = EXECUTE_PARAM_PATTERN.matcher(error);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return error;
    }
}
