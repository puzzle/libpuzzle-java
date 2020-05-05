package ch.puzzle.libpuzzle.springframework.boot.rest.newaction.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
public class CreateActionParameters<TEntity, TRequestData> {

    @With
    private final ActionParameter<TEntity> entity;

    @With
    private final ActionParameter<TRequestData> requestData;

    public ActionParameter<TRequestData> requestData() {
        return requestData;
    }

    public ActionParameter<TEntity> entity() {
        return entity;
    }
}
