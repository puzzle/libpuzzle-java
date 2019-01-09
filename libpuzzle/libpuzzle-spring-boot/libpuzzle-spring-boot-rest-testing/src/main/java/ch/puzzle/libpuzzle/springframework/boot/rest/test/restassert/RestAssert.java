package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class RestAssert {

    public static <TEntity, TEntityId> ListAssert.Executor<TEntity> assertList(
            TestRestTemplate testRestTemplate,
            RestActions<TEntity, TEntityId> restActions
    ) {
        return new ListAssert.Executor<>(testRestTemplate, restActions);
    }

    public static <TEntity, TEntityId> CreateAssert.Executor<TEntity> assertCreate(
            TestRestTemplate testRestTemplate,
            RestActions<TEntity, TEntityId> restActions
    ) {
        return new CreateAssert.Executor<>(testRestTemplate, restActions);
    }

    public static <TEntity, TEntityId> FindAssert.Executor<TEntity, TEntityId> assertFind(
            TestRestTemplate testRestTemplate,
            RestActions<TEntity, TEntityId> restActions
    ) {
        return new FindAssert.Executor<>(testRestTemplate, restActions);
    }

    public static <TEntity, TEntityId> UpdateAssert.Executor<TEntity, TEntityId> assertUpdate(
            TestRestTemplate testRestTemplate,
            RestActions<TEntity, TEntityId> restActions
    ) {
        return new UpdateAssert.Executor<>(testRestTemplate, restActions);
    }

    public static <TEntity, TEntityId> DeleteAssert.Executor<TEntity, TEntityId> assertDelete(
            TestRestTemplate testRestTemplate,
            RestActions<TEntity, TEntityId> restActions
    ) {
        return new DeleteAssert.Executor<>(testRestTemplate, restActions);
    }

}
