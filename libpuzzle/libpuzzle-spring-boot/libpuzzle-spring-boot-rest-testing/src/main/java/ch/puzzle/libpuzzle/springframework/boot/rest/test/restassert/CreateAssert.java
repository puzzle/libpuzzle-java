package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CreateAction;
import junit.framework.ComparisonFailure;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
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

        private RestActions<TEntity, ?> restActions;

        private CreateAction<TEntity, ?> createAction;

        private HttpEntity<?> requestEntity;

        Executor(TestRestTemplate testRestTemplate, RestActions<TEntity, ?> restActions) {
            this.testRestTemplate = testRestTemplate;
            this.restActions = restActions;
            createAction = mock(CreateAction.class);
            doReturn(createAction).when(restActions).create(any());
        }

        public <TDto> CreateAssert<TEntity> using(String url, TDto dto, Class<?> responseClass) {
            requestEntity = new HttpEntity<>(dto);
            testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, responseClass);
            try {
                verify(restActions).create(eq(responseClass));
            } catch (ComparisonFailure e) {
                throw RestAssertionError.wrongResponseBody(e);
            }
            return new CreateAssert<>(this);
        }
    }

    public CreateAssert<TEntity> executedWith(TEntity entity) {
        try {
            verify(executor.createAction).execute(eq(executor.requestEntity.getBody()), eq(entity));
        } catch (ComparisonFailure e) {
            throw RestAssertionError.wrongActionParams(e);
        }
        return this;
    }
}
