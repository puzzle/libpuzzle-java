package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class ListActionMatchers<TEntity> implements ActionMatchers<ListAction<TEntity>> {

    public static <TEntity> ListActionMatchers<TEntity> listAction() {
        return new ListActionMatchers<>();
    }

    @Override
    public Class<ListActionConfigurer> configurer() {
        return ListActionConfigurer.class;
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
}
