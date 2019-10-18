package ch.puzzle.libpuzzle.springframework.boot.rest.action;

public class ParameterNotSetException extends RuntimeException {

    ParameterNotSetException(Class<?> builderClass, String paramMethod) {
        super(String.format(
                "Action parameter missing. Consider calling '%s.%s(...)'.",
                builderClass.getSimpleName(),
                paramMethod
        ));
    }
}
