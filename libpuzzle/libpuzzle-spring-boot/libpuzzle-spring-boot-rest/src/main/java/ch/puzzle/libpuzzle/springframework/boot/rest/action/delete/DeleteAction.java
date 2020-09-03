package ch.puzzle.libpuzzle.springframework.boot.rest.action.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface DeleteAction<TIdentifier> {

    static <TIdentifier> DeleteAction<TIdentifier> unsupported() {
        return new UnsupportedAction<>();
    }

    Void execute(DeleteActionParameters<TIdentifier> params);

}
