package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class DeleteActionMatchers implements ActionMatchers<DeleteActionBuilder<Object>> {

    public static DeleteActionMatchers deleteAction() {
        return new DeleteActionMatchers();
    }

    @Override
    public Class<DeleteActionConfigurer> configurer() {
        return DeleteActionConfigurer.class;
    }

    public ActionResultMatcher executed() {
        return result -> verify(result.action(this)).execute();
    }

    public ActionResultMatcher notExecuted() {
        return result -> verify(result.action(this), never()).execute();
    }

    public ActionResultMatcher by(Object id) {
        return by(equalTo(id));
    }

    public ActionResultMatcher by(Matcher<Object> matcher) {
        return result -> verify(result.action(this)).by(argThat(matcher));
    }

}
