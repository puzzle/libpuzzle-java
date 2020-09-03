package ch.puzzle.libpuzzle.springframework.boot.rest.action.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.MappingResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class DeleteActionExecution<TIdentifier> implements
        DeleteActionBuilder<TIdentifier>,
        DeleteActionParameters<TIdentifier> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TIdentifier> identifier;

    private final DeleteAction<TIdentifier> action;

    private final ActionContext actionContext;

    public DeleteActionExecution(final DeleteAction<TIdentifier> action, ActionContext actionContext) {
        this(
                ActionParameter.empty(DeleteActionBuilder.class, "by"),
                action,
                actionContext
        );
    }

    @Override
    public ActionParameter<TIdentifier> identifier() {
        return identifier;
    }

    @Override
    public DeleteActionBuilder<TIdentifier> by(final TIdentifier identifier) {
        return withIdentifier(ActionParameter.holding(identifier));
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final ResponseFactory<Void, TNewResponseDto, DeleteActionParameters<TIdentifier>> responseFactory) {
        return responseFactory.create(action.execute(this), this, actionContext);
    }

    @Override
    public Void execute() {
        return execute(MappingResponseFactory.mapTo(Void.class));
    }
}
