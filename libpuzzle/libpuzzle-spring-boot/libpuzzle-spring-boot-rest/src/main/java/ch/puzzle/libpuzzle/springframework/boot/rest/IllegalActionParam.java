package ch.puzzle.libpuzzle.springframework.boot.rest;

import java.util.Arrays;
import java.util.function.Supplier;

public class IllegalActionParam extends RuntimeException {

    private IllegalActionParam(String message) {
        super(message);
    }

    public static <T> Supplier<T> missingParam(Class<?> actionClass, String methodName) {
        assert Arrays.stream(actionClass.getMethods()).anyMatch(method -> methodName.equals(method.getName()));
        return () -> {
            throw new IllegalActionParam(
                    String.format("Missing invocation: '%s.%s(...)'", actionClass, methodName)
            );
        };
    }

}
