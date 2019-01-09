package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ListAction;
import junit.framework.ComparisonFailure;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ListAssert<TEntity> {

    private Executor<TEntity> executor;

    private ListAssert(Executor<TEntity> executor) {
        this.executor = executor;
    }

    public static class Executor<TEntity> {

        private TestRestTemplate testRestTemplate;

        private RestActions<TEntity, ?> restActions;

        private ListAction<TEntity, ?> listAction;

        Executor(TestRestTemplate testRestTemplate, RestActions<TEntity, ?> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            listAction = mock(ListAction.class);
            doReturn(listAction).when(restActions).list(any());
        }

        public ListAssert<TEntity> using(String url, Class<?> responseClass) {
            testRestTemplate.exchange(url, HttpMethod.GET, null, responseClass);
            try {
                verify(restActions).list(eq(responseClass));
            } catch (ComparisonFailure e) {
                throw RestAssertionError.wrongResponseBody(e);
            }
            return new ListAssert<>(this);
        }
    }

    public ListAssert<TEntity> executed() {
        try {
            verify(executor.listAction).execute();
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }
}
