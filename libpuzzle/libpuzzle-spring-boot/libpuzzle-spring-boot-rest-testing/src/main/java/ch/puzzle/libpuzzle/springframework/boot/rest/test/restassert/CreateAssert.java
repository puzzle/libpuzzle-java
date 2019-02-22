package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror.RestAssertionError;
import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CreateAssert<TEntity> {

    private Executor<TEntity> executor;

    private CreateAssert(Executor<TEntity> executor) {
        this.executor = executor;
    }

    public static class Executor<TEntity> {

        private TestRestTemplate testRestTemplate;

        private RestActions<?, ?, CreateAction<TEntity>, ?, ?> restActions;

        private CreateAction<TEntity> createAction;

        private HttpEntity<?> requestEntity;

        Executor(TestRestTemplate testRestTemplate, RestActions<?, ?, CreateAction<TEntity>, ?, ?> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            createAction = mock(CreateAction.class);
            doReturn(createAction).when(restActions).create();
            doReturn(createAction).when(createAction).from(any());
            doReturn(createAction).when(createAction).with(any());
        }

        public <TDto> CreateAssert<TEntity> using(String url, TDto dto, Class<?> responseClass) {
            requestEntity = new HttpEntity<>(dto);
            testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, responseClass);
            try {
                verify(restActions).create();
            } catch (WantedButNotInvoked e) {
                throw RestAssertionError.missingActionInitialization();
            }
            return new CreateAssert<>(this);
        }
    }

    public <TDto> CreateAssert<TEntity> from(TDto dto) {
        try {
            verify(executor.createAction).from(eq(dto));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }

    public CreateAssert<TEntity> with(TEntity initialEntity) {
        try {
            verify(executor.createAction).with(eq(initialEntity));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }

    public void withResponse(Class<?> responseDtoClass) {
        try {
            verify(executor.createAction).execute(eq(responseDtoClass));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
    }

    public static <TEntity> CreateAssert.Executor<TEntity> assertCreate(
            TestRestTemplate testRestTemplate,
            RestActions<?, ?, CreateAction<TEntity>, ?, ?> restActions
    ) {
        return new CreateAssert.Executor<>(testRestTemplate, restActions);
    }
}
