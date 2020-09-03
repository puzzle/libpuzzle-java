package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface FindAction<TIdentifier, TEntity> {

    static <TIdentifier, TEntity> FindAction<TIdentifier, TEntity> unsupported() {
        return new UnsupportedAction<>();
    }

    TEntity execute(FindActionParameters<TIdentifier> params);

}
