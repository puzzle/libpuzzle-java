package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class FindActionMatchers implements ActionMatchers<FindActionBuilder<Object, ?>> {

    public static FindActionMatchers findAction() {
        return new FindActionMatchers();
    }

    @Override
    public Class<FindActionConfigurer> configurer() {
        return FindActionConfigurer.class;
    }

    public ActionResultMatcher executed() {
        return result -> verify(result.action(this)).execute(any());
    }

    public ActionResultMatcher executed(Class<?> responseClass) {
        return executed(equalTo(responseClass));
    }

    public ActionResultMatcher executed(Matcher<Class<?>> matcher) {
        return result -> verify(result.action(this)).execute(argThat(matcher));
    }

    public ActionResultMatcher by(Object id) {
        return by(equalTo(id));
    }

    public ActionResultMatcher by(Matcher<Object> matcher) {
        return result -> verify(result.action(this)).by(argThat(matcher));
    }

    public ActionResultMatcher notExecuted() {
        return result -> verify(result.action(this), never()).execute(any());
    }
}
