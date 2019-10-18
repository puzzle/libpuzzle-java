package ch.puzzle.libpuzzle.springframework.boot.rest.action.delete;

import org.springframework.http.ResponseEntity;

public interface DeleteActionBuilder<TIdentifier> {

    DeleteActionBuilder<TIdentifier> by(TIdentifier identifier);

    ResponseEntity<Void> execute();
}
