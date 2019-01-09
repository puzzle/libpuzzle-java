package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import junit.framework.ComparisonFailure;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FindAssert<TEntity, TEntityId> {

    private Executor<TEntity, TEntityId> executor;

    private FindAssert(Executor<TEntity, TEntityId> executor) {
        this.executor = executor;
    }

    public static class Executor<TEntity, TEntityId> {

        private TestRestTemplate testRestTemplate;

        private RestActions<TEntity, TEntityId> restActions;

        private FindAction<TEntity, TEntityId, ?> findAction;

        Executor(TestRestTemplate testRestTemplate, RestActions<TEntity, TEntityId> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            findAction = mock(FindAction.class);
            doReturn(findAction).when(restActions).find(any());
        }

        public FindAssert<TEntity, TEntityId> using(String url, Class<?> responseClass) {
            testRestTemplate.exchange(url, HttpMethod.GET, null, responseClass);
            try {
                verify(restActions).find(eq(responseClass));
            } catch (ComparisonFailure e) {
                throw RestAssertionError.wrongResponseBody(e);
            }
            return new FindAssert<>(this);
        }
    }

    public FindAssert<TEntity, TEntityId> executedWith(TEntityId id) {
        try {
            verify(executor.findAction).execute(id);
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }
}
