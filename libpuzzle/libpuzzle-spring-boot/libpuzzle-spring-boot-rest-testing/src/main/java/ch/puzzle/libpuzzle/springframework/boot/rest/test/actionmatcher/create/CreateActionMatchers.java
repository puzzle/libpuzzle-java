package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class CreateActionMatchers<TEntity> implements ActionMatchers<CreateAction<TEntity>> {

    public static <TEntity> CreateActionMatchers<TEntity> createAction() {
        return new CreateActionMatchers<>();
    }

    @Override
    public Class<CreateActionConfigurer> configurer() {
        return CreateActionConfigurer.class;
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

    public ActionResultMatcher using(TEntity id) {
        return using(equalTo(id));
    }

    public ActionResultMatcher using(Matcher<TEntity> matcher) {
        return result -> verify(result.action(this)).using(argThat(matcher));
    }

    public <TDto> ActionResultMatcher with(TDto dto) {
        return with(equalTo(dto));
    }

    public <TDto> ActionResultMatcher with(Matcher<TDto> matcher) {
        return result -> verify(result.action(this)).with(argThat(matcher));
    }

}
