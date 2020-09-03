package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface UpdateAction<TEntity, TIdentifier> {

    static <TEntity, TIdentifier> UpdateAction<TEntity, TIdentifier> unsupported() {
        return new UnsupportedAction<>();
    }

    TEntity execute(UpdateActionParameters<TIdentifier, ?> params);

}
