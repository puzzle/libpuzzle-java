package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror.RestAssertionError;
import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;
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

        private RestActions<?, ?, ?, UpdateAction<TEntity, TEntityId>, ?> restActions;

        private UpdateAction<TEntity, TEntityId> updateAction;

        private HttpEntity<?> requestEntity;

        Executor(
                TestRestTemplate testRestTemplate,
                RestActions<?, ?, ?, UpdateAction<TEntity, TEntityId>, ?> restActions
        ) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            updateAction = mock(UpdateAction.class);
            doReturn(updateAction).when(restActions).update();
            doReturn(updateAction).when(updateAction).by(any());
            doReturn(updateAction).when(updateAction).dto(any());
        }

        public <TDto> UpdateAssert<TEntity, TEntityId> using(String url, TDto dto, Class<?> responseClass) {
            requestEntity = new HttpEntity<>(dto);
            testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseClass);
            try {
                verify(restActions).update();
            } catch (WantedButNotInvoked e) {
                throw RestAssertionError.missingActionInitialization();
            }
            return new UpdateAssert<>(this);
        }
    }

    public UpdateAssert<TEntity, TEntityId> by(TEntityId id) {
        try {
            verify(executor.updateAction).by(eq(id));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }

    public <TDto> UpdateAssert<TEntity, TEntityId> dto(TDto requestDto) {
        try {
            verify(executor.updateAction).dto(requestDto);
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }

    public void withResponse(Class<?> responseDtoClass) {
        try {
            verify(executor.updateAction).execute(eq(responseDtoClass));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
    }

    public static <TEntity, TEntityId> UpdateAssert.Executor<TEntity, TEntityId> assertUpdate(
            TestRestTemplate testRestTemplate,
            RestActions<?, ?, ?, UpdateAction<TEntity, TEntityId>, ?> restActions
    ) {
        return new UpdateAssert.Executor<>(testRestTemplate, restActions);
    }
}
