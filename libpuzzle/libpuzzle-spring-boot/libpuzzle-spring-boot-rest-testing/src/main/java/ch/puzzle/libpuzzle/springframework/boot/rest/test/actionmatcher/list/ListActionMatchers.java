package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionMatchers;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.ActionResultMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.mockito.hamcrest.MockitoHamcrest.intThat;

public class ListActionMatchers implements ActionMatchers<ListActionBuilder<Object, ?>> {

    public static ListActionMatchers listAction() {
        return new ListActionMatchers();
    }

    @Override
    public Class<ListActionConfigurer> configurer() {
        return ListActionConfigurer.class;
    }

    public ActionResultMatcher skip(int offset) {
        return skip(equalTo(offset));
    }

    public ActionResultMatcher skip(Matcher<Integer> matcher) {
        return result -> verify(result.action(this)).skip(intThat(matcher));
    }

    public ActionResultMatcher limit(int offset) {
        return limit(equalTo(offset));
    }

    public ActionResultMatcher limit(Matcher<Integer> matcher) {
        return result -> verify(result.action(this)).limit(intThat(matcher));
    }

    public ActionResultMatcher matching(Object filter) {
        return matching(equalTo(filter));
    }

    public ActionResultMatcher matching(Matcher<Object> matcher) {
        return result -> verify(result.action(this)).matching(argThat(matcher));
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

    public ActionResultMatcher notExecuted() {
        return result -> verify(result.action(this), never()).execute(any());
    }
}
