package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror.RestAssertionError;
import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FindAssert<TEntity, TEntityId> {

    private Executor<TEntity, TEntityId> executor;

    private FindAssert(Executor<TEntity, TEntityId> executor) {
        this.executor = executor;
    }

    public static class Executor<TEntity, TEntityId> {

        private TestRestTemplate testRestTemplate;

        private RestActions<?, FindAction<TEntity, TEntityId>, ?, ?, ?> restActions;

        private FindAction<TEntity, TEntityId> findAction;

        Executor(TestRestTemplate testRestTemplate, RestActions<?, FindAction<TEntity, TEntityId>, ?, ?, ?> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            findAction = mock(FindAction.class);
            doReturn(findAction).when(restActions).find();
            doReturn(findAction).when(findAction).by(any());
        }

        public FindAssert<TEntity, TEntityId> using(String url) {
            testRestTemplate.exchange(url, HttpMethod.GET, null, String.class);
            try {
                verify(restActions).find();
            } catch (WantedButNotInvoked e) {
                throw RestAssertionError.missingActionInitialization();
            }
            return new FindAssert<>(this);
        }
    }

    public FindAssert<TEntity, TEntityId> by(TEntityId id) {
        try {
            verify(executor.findAction).by(eq(id));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        } catch (WantedButNotInvoked e) {
            throw RestAssertionError.missingActionParam(FindAction.class, "by", id);
        }
        return this;
    }

    public <TResponse> void withResponse(Class<TResponse> responseClass) {
        try {
            verify(executor.findAction).execute(responseClass);
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        } catch (WantedButNotInvoked e) {
            throw RestAssertionError.missingActionExecution();
        }
    }

    public static <TEntity, TEntityId> FindAssert.Executor<TEntity, TEntityId> assertFind(
            TestRestTemplate testRestTemplate,
            RestActions<?, FindAction<TEntity, TEntityId>, ?, ?, ?> restActions
    ) {
        return new FindAssert.Executor<>(testRestTemplate, restActions);
    }
}
