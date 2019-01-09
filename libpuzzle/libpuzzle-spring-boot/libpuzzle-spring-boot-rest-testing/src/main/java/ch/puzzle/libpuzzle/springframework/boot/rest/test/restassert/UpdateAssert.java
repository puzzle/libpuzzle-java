package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.UpdateAction;
import junit.framework.ComparisonFailure;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UpdateAssert<TEntity, TEntityId> {

    private Executor<TEntity, TEntityId> executor;

    private UpdateAssert(Executor<TEntity, TEntityId> executor) {
        this.executor = executor;
    }

    public static class Executor<TEntity, TEntityId> {

        private TestRestTemplate testRestTemplate;

        private RestActions<TEntity, TEntityId> restActions;

        private UpdateAction<TEntity, TEntityId, ?> updateAction;

        private HttpEntity<?> requestEntity;

        Executor(TestRestTemplate testRestTemplate, RestActions<TEntity, TEntityId> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            updateAction = mock(UpdateAction.class);
            doReturn(updateAction).when(restActions).update(any());
        }

        public <TDto> UpdateAssert<TEntity, TEntityId> using(String url, TDto dto, Class<?> responseClass) {
            requestEntity = new HttpEntity<>(dto);
            testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseClass);
            try {
                verify(restActions).update(eq(responseClass));
            } catch (ComparisonFailure e) {
                throw RestAssertionError.wrongResponseBody(e);
            }
            return new UpdateAssert<>(this);
        }
    }

    public UpdateAssert<TEntity, TEntityId> executedWith(TEntityId id) {
        try {
            verify(executor.updateAction).execute(eq(executor.requestEntity.getBody()), eq(id));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }
}
