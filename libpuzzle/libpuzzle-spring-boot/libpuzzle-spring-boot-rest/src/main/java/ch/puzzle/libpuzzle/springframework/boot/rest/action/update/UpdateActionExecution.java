package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.MappingResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class UpdateActionExecution<TEntity, TIdentifier, TRequestDto> implements
        UpdateActionBuilder<TEntity, TIdentifier, TRequestDto>,
        UpdateActionParameters<TIdentifier, TRequestDto> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TIdentifier> identifier;

    private final ActionParameter<TRequestDto> requestDto;

    private final UpdateAction<TEntity, TIdentifier> action;

    private final ActionContext actionContext;

    public UpdateActionExecution(final UpdateAction<TEntity, TIdentifier> action, final ActionContext actionContext) {
        this(
                ActionParameter.empty(UpdateActionBuilder.class, "by"),
                ActionParameter.empty(UpdateActionBuilder.class, "with"),
                action,
                actionContext
        );
    }

    @Override
    public ActionParameter<TRequestDto> requestDto() {
        return requestDto;
    }

    @Override
    public ActionParameter<TIdentifier> identifier() {
        return identifier;
    }

    @Override
    public UpdateActionBuilder<TEntity, TIdentifier, TRequestDto> by(final TIdentifier identifier) {
        return withIdentifier(ActionParameter.holding(identifier));
    }

    @Override
    public <TNewRequestDto> UpdateActionBuilder<TEntity, TIdentifier, TNewRequestDto> with(
            final TNewRequestDto requestDto
    ) {
        return new UpdateActionExecution<>(
                identifier,
                ActionParameter.holding(requestDto),
                action,
                actionContext
        );
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final ResponseFactory<TEntity, TNewResponseDto, UpdateActionParameters<TIdentifier, TRequestDto>> responseFactory) {
        return responseFactory.create(action.execute(this), this, actionContext);
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final Class<TNewResponseDto> responseDtoClass) {
        return execute(MappingResponseFactory.mapTo(responseDtoClass));
    }
}
