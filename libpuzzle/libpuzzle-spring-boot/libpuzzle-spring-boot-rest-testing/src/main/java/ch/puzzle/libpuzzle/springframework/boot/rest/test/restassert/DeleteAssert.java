package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import junit.framework.ComparisonFailure;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteAssert<TEntity, TEntityId> {

    private Executor<TEntity, TEntityId> executor;

    private DeleteAssert(Executor<TEntity, TEntityId> executor) {
        this.executor = executor;
    }

    public static class Executor<TEntity, TEntityId> {

        private TestRestTemplate testRestTemplate;

        private RestActions<TEntity, TEntityId> restActions;

        private DeleteAction<TEntity, TEntityId> deleteAction;

        Executor(TestRestTemplate testRestTemplate, RestActions<TEntity, TEntityId> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            deleteAction = mock(DeleteAction.class);
            doReturn(deleteAction).when(restActions).delete();
        }

        public DeleteAssert<TEntity, TEntityId> using(String url) {
            testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
            try {
                verify(restActions).delete();
            } catch (ComparisonFailure e) {
                throw RestAssertionError.wrongResponseBody(e);
            }
            return new DeleteAssert<>(this);
        }
    }

    public DeleteAssert<TEntity, TEntityId> executedWith(TEntityId id) {
        try {
            verify(executor.deleteAction).execute(id);
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }
}
