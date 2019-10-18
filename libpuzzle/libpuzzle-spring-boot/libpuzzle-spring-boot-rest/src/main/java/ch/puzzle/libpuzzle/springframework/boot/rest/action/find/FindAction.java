package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface FindAction<TIdentifier> {

    static <TIdentifier> FindAction<TIdentifier> unsupported() {
        return new UnsupportedAction<>();
    }

    <TResponseDto> ResponseEntity<TResponseDto> execute(FindActionParameters<TIdentifier, TResponseDto> params);

}
