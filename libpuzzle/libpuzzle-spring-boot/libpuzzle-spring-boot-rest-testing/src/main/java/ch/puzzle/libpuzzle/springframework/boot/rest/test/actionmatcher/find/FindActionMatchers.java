package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class FindActionMatchers<TEntity, TId> implements ActionMatchers<FindAction<TEntity, TId>> {

    public static <TEntity, TId> FindActionMatchers<TEntity, TId> findAction() {
        return new FindActionMatchers<>();
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

    public ActionResultMatcher by(TId id) {
        return by(equalTo(id));
    }

    public ActionResultMatcher by(Matcher<TId> matcher) {
        return result -> verify(result.action(this)).by(argThat(matcher));
    }

}
