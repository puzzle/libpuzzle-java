package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported.UnsupportedAction;
import org.springframework.http.ResponseEntity;

public interface ListAction<TFilter> {

    static <TFilter> ListAction<TFilter> unsupported() {
        return new UnsupportedAction<>();
    }

    <TResponseDto> ResponseEntity<Iterable<TResponseDto>> execute(ListActionParameters<TFilter, TResponseDto> params);

}
