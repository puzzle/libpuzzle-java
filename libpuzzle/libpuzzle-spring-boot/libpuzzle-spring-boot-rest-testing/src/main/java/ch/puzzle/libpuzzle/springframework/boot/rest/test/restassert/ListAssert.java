package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror.RestAssertionError;
import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;
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

        private RestActions<ListAction<TEntity>, ?, ?, ?, ?> restActions;

        private ListAction<TEntity> listAction;

        Executor(TestRestTemplate testRestTemplate, RestActions<ListAction<TEntity>, ?, ?, ?, ?> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            listAction = mock(ListAction.class);
            doReturn(listAction).when(restActions).list();
        }

        public ListAssert<TEntity> using(String url, Class<?> responseClass) {
            testRestTemplate.exchange(url, HttpMethod.GET, null, responseClass);
            try {
                verify(restActions).list();
            } catch (WantedButNotInvoked e) {
                throw RestAssertionError.missingActionInitialization();
            }
            return new ListAssert<>(this);
        }
    }

    public void executed(Class<?> responseDtoClass) {
        try {
            verify(executor.listAction).execute(eq(responseDtoClass));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
    }

    public static <TEntity> ListAssert.Executor<TEntity> assertList(
            TestRestTemplate testRestTemplate,
            RestActions<ListAction<TEntity>, ?, ?, ?, ?> restActions
    ) {
        return new ListAssert.Executor<>(testRestTemplate, restActions);
    }
}
