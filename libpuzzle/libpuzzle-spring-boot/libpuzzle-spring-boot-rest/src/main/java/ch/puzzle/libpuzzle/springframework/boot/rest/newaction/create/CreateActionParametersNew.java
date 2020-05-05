package ch.puzzle.libpuzzle.springframework.boot.rest.newaction.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
public class CreateActionParametersNew<TEntity, TRequestData> implements CreateActionBuilder<TEntity, TRequestData> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TEntity> entity;

    private final ActionParameter<TRequestData> requestData;

    private CreateActionParametersNew() {
        // TODO: Ensure default error message is understandable ("actions.list()...filter()....execute(...)"
        entity = ActionParameter.empty(CreateActionParametersNew.class, "filter");
        requestData = ActionParameter.empty(CreateActionParametersNew.class, "filter");
    }

    public static <TEntity, TRequestData> CreateActionParametersNew<TEntity, TRequestData> create() {
        return new CreateActionParametersNew<>();
    }

    @Override
    public CreateActionBuilder<TEntity, TRequestData> using(final TEntity entity) {
        return new CreateActionParametersNew<>(ActionParameter.holding(entity), requestData);
    }

    @Override
    public <TNewRequestData> CreateActionBuilder<TEntity, TNewRequestData> with(final TNewRequestData requestData) {
        return new CreateActionParametersNew<>(entity, ActionParameter.holding(requestData));
    }
}
