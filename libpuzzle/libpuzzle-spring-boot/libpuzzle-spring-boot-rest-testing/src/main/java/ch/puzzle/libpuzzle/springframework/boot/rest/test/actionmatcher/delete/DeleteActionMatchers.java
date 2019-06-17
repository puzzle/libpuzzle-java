package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class DeleteActionMatchers<TId> implements ActionMatchers<DeleteAction<TId>> {

    public static <TId> DeleteActionMatchers<TId> deleteAction() {
        return new DeleteActionMatchers<>();
    }

    @Override
    public Class<DeleteActionConfigurer> configurer() {
        return DeleteActionConfigurer.class;
    }

    public ActionResultMatcher executed() {
        return result -> verify(result.action(this)).execute();
    }

    public ActionResultMatcher by(TId id) {
        return by(equalTo(id));
    }

    public ActionResultMatcher by(Matcher<TId> matcher) {
        return result -> verify(result.action(this)).by(argThat(matcher));
    }

}
