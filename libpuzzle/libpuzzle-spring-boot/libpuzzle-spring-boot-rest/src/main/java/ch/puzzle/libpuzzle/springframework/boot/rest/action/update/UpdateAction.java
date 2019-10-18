package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface UpdateAction<TIdentifier> {

    static <TIdentifier> UpdateAction<TIdentifier> unsupported() {
        return new UnsupportedAction<>();
    }

    <TResponseDto> ResponseEntity<TResponseDto> execute(UpdateActionParameters<TIdentifier, ?, TResponseDto> params);

}
