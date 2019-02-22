package ch.puzzle.libpuzzle.springframework.boot.rest.annotation;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActionsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RestActionsConfig.class)
public @interface EnableRestActions {
}
