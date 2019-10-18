package ch.puzzle.libpuzzle.springframework.boot.rest.action.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
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

    public DeleteActionExecution(final DeleteAction<TIdentifier> action) {
        this(
                ActionParameter.empty(DeleteActionBuilder.class, "by"),
                action
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
    public ResponseEntity<Void> execute() {
        return action.execute(this);
    }
}
