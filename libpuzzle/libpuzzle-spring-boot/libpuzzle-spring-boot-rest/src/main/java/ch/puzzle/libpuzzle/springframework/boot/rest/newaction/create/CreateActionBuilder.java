package ch.puzzle.libpuzzle.springframework.boot.rest.newaction.create;

public interface CreateActionBuilder<TEntity, TRequestData> {

    CreateActionBuilder<TEntity, TRequestData> using(TEntity initialEntity);

    <TNewRequestData> CreateActionBuilder<TEntity, TNewRequestData> with(TNewRequestData requestData);

}
