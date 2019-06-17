package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.exception;

import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.RestActionConfigurer;

public class InvalidMatcherConfigDetected extends RuntimeException {

    public InvalidMatcherConfigDetected(
            String reason,
            Throwable cause,
            Class<? extends RestActionConfigurer> configurer
    ) {
        super(
                String.format(
                        "%s Did you apply '%s'?",
                        reason,
                        configurer
                ),
                cause
        );
    }

    public static InvalidMatcherConfigDetected actionNotFound(Class<? extends RestActionConfigurer> configurer) {
        return new InvalidMatcherConfigDetected("Action mock / spy not found.", null, configurer);
    }

    public static InvalidMatcherConfigDetected actionNotSupported(
            Object action,
            ClassCastException cause,
            Class<? extends RestActionConfigurer> configurer
    ) {
        return new InvalidMatcherConfigDetected(
                String.format("Unsupported action mock / spy of type '%s' found.", action.getClass()), cause, configurer
        );
    }
}
