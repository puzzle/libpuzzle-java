package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class UpdateActionMatchers implements ActionMatchers<UpdateActionBuilder<?, Object, ?>> {

    public static UpdateActionMatchers updateAction() {
        return new UpdateActionMatchers();
    }

    @Override
    public Class<UpdateActionConfigurer> configurer() {
        return UpdateActionConfigurer.class;
    }

    public ActionResultMatcher executed() {
        return result -> verify(result.action(this)).execute(any(Class.class)); // FIXME: Handle ResponseFactory
    }

    public ActionResultMatcher executed(Class<?> responseClass) {
        return executed(equalTo(responseClass));
    }

    public ActionResultMatcher executed(Matcher<Class<?>> matcher) {
        return result -> verify(result.action(this)).execute(argThat(matcher));
    }

    public ActionResultMatcher notExecuted() {
        return result -> verify(result.action(this), never()).execute(any(Class.class)); // FIXME: Handle ResponseFactory
    }

    public ActionResultMatcher by(Object id) {
        return by(equalTo(id));
    }

    public ActionResultMatcher by(Matcher<?> matcher) {
        return result -> verify(result.action(this)).by(argThat(matcher));
    }

    public <TDto> ActionResultMatcher with(TDto requestDto) {
        return with(equalTo(requestDto));
    }

    public <TDto> ActionResultMatcher with(Matcher<TDto> matcher) {
        return result -> verify(result.action(this)).with(argThat(matcher));
    }

}
