package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror.RestAssertionError;
import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;
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

        private RestActions<?, ?, ?, ?, DeleteAction<TEntity, TEntityId>> restActions;

        private DeleteAction<TEntity, TEntityId> deleteAction;

        Executor(TestRestTemplate testRestTemplate, RestActions<?, ?, ?, ?, DeleteAction<TEntity, TEntityId>> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            deleteAction = mock(DeleteAction.class);
            doReturn(deleteAction).when(restActions).delete();
            doReturn(deleteAction).when(deleteAction).by(any());
        }

        public DeleteAssert<TEntity, TEntityId> using(String url) {
            testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
            try {
                verify(restActions).delete();
            } catch (WantedButNotInvoked e) {
                throw RestAssertionError.missingActionInitialization();
            }
            return new DeleteAssert<>(this);
        }
    }

    public DeleteAssert<TEntity, TEntityId> by(TEntityId id) {
        try {
            verify(executor.deleteAction).by(eq(id));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        } catch (WantedButNotInvoked e) {
            throw RestAssertionError.missingActionParam(DeleteAction.class, "by", String.valueOf(id));
        }
        return this;
    }

    public void executed() {
        try {
            verify(executor.deleteAction).execute();
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
    }

    public static <TEntity, TEntityId> DeleteAssert.Executor<TEntity, TEntityId> assertDelete(
            TestRestTemplate testRestTemplate,
            RestActions<?, ?, ?, ?, DeleteAction<TEntity, TEntityId>> restActions
    ) {
        return new DeleteAssert.Executor<>(testRestTemplate, restActions);
    }
}
