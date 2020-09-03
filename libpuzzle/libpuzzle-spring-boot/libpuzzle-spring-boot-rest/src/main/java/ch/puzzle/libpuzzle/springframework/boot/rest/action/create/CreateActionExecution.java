package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.MappingResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
public final class CreateActionExecution<TEntity, TDto> implements
        CreateActionBuilder<TEntity, TDto>, CreateActionParameters<TEntity, TDto> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TEntity> entity;

    private final ActionParameter<TDto> requestDto;

    private final CreateAction<TEntity> action;

    private final ActionContext actionContext;

    public CreateActionExecution(final CreateAction<TEntity> action, ActionContext actionContext) {
        this(
                ActionParameter.empty(CreateActionBuilder.class, "using"),
                ActionParameter.empty(CreateActionBuilder.class, "with"),
                action,
                actionContext
        );
    }

    @Override
    public ActionParameter<TDto> requestDto() {
        return requestDto;
    }

    @Override
    public ActionParameter<TEntity> entity() {
        return entity;
    }

    @Override
    public CreateActionBuilder<TEntity, TDto> using(final TEntity initialEntity) {
        return withEntity(ActionParameter.holding(initialEntity));
    }

    @Override
    public <TNewRequestDto> CreateActionBuilder<TEntity, TNewRequestDto> with(
            final TNewRequestDto requestDto
    ) {
        return new CreateActionExecution<>(
                entity,
                ActionParameter.holding(requestDto),
                action,
                actionContext
        );
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final ResponseFactory<TEntity, TNewResponseDto, CreateActionParameters<TEntity, TDto>> responseFactory) {
        return responseFactory.create(action.execute(this), this, actionContext);
    }

    public <TNewResponseDto> TNewResponseDto execute(final Class<TNewResponseDto> responseDtoClass) {
        return execute(MappingResponseFactory.mapTo(responseDtoClass));
    }
}
