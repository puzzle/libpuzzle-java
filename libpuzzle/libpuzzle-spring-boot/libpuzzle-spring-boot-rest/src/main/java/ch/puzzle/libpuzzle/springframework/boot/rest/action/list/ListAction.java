package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface ListAction<TEntity, TFilter> {

    static <TEntity, TFilter> ListAction<TEntity, TFilter> unsupported() {
        return new UnsupportedAction<>();
    }

    Iterable<TEntity> execute(ListActionParameters<TFilter> params);

}
