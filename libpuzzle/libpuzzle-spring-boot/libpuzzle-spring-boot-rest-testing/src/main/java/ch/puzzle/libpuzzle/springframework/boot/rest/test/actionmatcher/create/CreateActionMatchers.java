package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class CreateActionMatchers implements ActionMatchers<CreateActionBuilder<Object, ?>> {

    public static CreateActionMatchers createAction() {
        return new CreateActionMatchers();
    }

    @Override
    public Class<CreateActionConfigurer> configurer() {
        return CreateActionConfigurer.class;
    }

    public ActionResultMatcher executed() {
        return result -> verify(result.action(this)).execute(any(Class.class)); // FIXME: Handle ResponseFactory
    }

    public ActionResultMatcher executed(Class<?> responseClass) {
        return executed(equalTo(responseClass));
    }

    public ActionResultMatcher notExecuted() {
        return result -> verify(result.action(this), never()).execute(any(Class.class)); // FIXME: Handle ResponseFactory
    }

    public ActionResultMatcher executed(Matcher<Class<?>> matcher) {
        return result -> verify(result.action(this)).execute(argThat(matcher));
    }

    public ActionResultMatcher using(Object id) {
        return using(equalTo(id));
    }

    public ActionResultMatcher using(Matcher<Object> matcher) {
        return result -> verify(result.action(this)).using(argThat(matcher));
    }

    public <TDto> ActionResultMatcher with(TDto dto) {
        return with(equalTo(dto));
    }

    public <TDto> ActionResultMatcher with(Matcher<TDto> matcher) {
        return result -> verify(result.action(this)).with(argThat(matcher));
    }

}
