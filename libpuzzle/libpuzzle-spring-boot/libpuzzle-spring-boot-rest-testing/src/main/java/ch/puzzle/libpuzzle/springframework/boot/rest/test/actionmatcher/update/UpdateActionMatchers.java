package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class UpdateActionMatchers<TEntity, TId> implements ActionMatchers<UpdateAction<TEntity, TId>> {

    public static <TEntity, TId> UpdateActionMatchers<TEntity, TId> updateAction() {
        return new UpdateActionMatchers<>();
    }

    @Override
    public Class<UpdateActionConfigurer> configurer() {
        return UpdateActionConfigurer.class;
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

    public <TDto> ActionResultMatcher dto(TDto dto) {
        return dto(equalTo(dto));
    }

    public <TDto> ActionResultMatcher dto(Matcher<TDto> matcher) {
        return result -> verify(result.action(this)).dto(argThat(matcher));
    }

}
