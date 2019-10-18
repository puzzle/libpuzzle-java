package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class CreateActionExecution<TEntity, TDto, TResponseDto> implements
        CreateActionBuilder<TEntity, TDto, TResponseDto>, CreateActionParameters<TEntity, TDto, TResponseDto> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TEntity> entity;

    private final ActionParameter<TDto> requestDto;

    private final ActionParameter<Class<TResponseDto>> responseDtoClass;

    private final CreateAction<TEntity> action;

    public CreateActionExecution(final CreateAction<TEntity> action) {
        this(
                ActionParameter.empty(CreateActionBuilder.class, "using"),
                ActionParameter.empty(CreateActionBuilder.class, "with"),
                ActionParameter.empty(CreateActionBuilder.class, "execute"),
                action
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
    public ActionParameter<Class<TResponseDto>> responseDtoClass() {
        return responseDtoClass;
    }

    @Override
    public CreateActionBuilder<TEntity, TDto, TResponseDto> using(final TEntity initialEntity) {
        return withEntity(ActionParameter.holding(initialEntity));
    }

    @Override
    public <TNewRequestDto> CreateActionBuilder<TEntity, TNewRequestDto, TResponseDto> with(
            final TNewRequestDto requestDto
    ) {
        return new CreateActionExecution<>(
                entity,
                ActionParameter.holding(requestDto),
                responseDtoClass,
                action
        );
    }

    @Override
    public <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(final Class<TNewResponseDto> responseDtoClass) {
        return action.execute(new CreateActionExecution<>(
                entity,
                requestDto,
                ActionParameter.holding(responseDtoClass),
                action
        ));
    }
}
