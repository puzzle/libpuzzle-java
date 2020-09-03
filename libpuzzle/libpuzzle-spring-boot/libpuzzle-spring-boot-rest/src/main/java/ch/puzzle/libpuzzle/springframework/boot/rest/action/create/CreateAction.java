package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface CreateAction<TEntity> {

    static <TEntity> CreateAction<TEntity> unsupported() {
        return new UnsupportedAction<>();
    }

    TEntity execute(CreateActionParameters<TEntity, ?> actionExecution);

}
