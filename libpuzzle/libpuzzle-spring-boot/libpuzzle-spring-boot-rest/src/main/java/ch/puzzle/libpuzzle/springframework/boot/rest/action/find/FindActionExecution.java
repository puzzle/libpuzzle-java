package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.MappingResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class FindActionExecution<TIdentifier, TEntity> implements
        FindActionBuilder<TIdentifier, TEntity>,
        FindActionParameters<TIdentifier> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TIdentifier> identifier;

    private final FindAction<TIdentifier, TEntity> action;

    private final ActionContext actionContext;

    public FindActionExecution(final FindAction<TIdentifier, TEntity> action, ActionContext actionContext) {
        this(
                ActionParameter.empty(FindActionBuilder.class, "by"),
                action,
                actionContext
        );
    }

    @Override
    public ActionParameter<TIdentifier> identifier() {
        return identifier;
    }

    @Override
    public FindActionBuilder<TIdentifier, TEntity> by(final TIdentifier identifier) {
        return withIdentifier(ActionParameter.holding(identifier));
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final ResponseFactory<TEntity, TNewResponseDto, FindActionParameters<TIdentifier>> responseFactory) {
        return responseFactory.create(action.execute(this), this, actionContext);
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final Class<TNewResponseDto> responseDtoClass) {
        return execute(MappingResponseFactory.mapTo(responseDtoClass));
    }
}
