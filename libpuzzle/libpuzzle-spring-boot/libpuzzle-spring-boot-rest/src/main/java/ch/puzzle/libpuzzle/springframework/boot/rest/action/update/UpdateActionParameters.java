package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionWithIdentifierParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionWithRequestBodyParameters;

public interface UpdateActionParameters<TIdentifier, TDto> extends
        ActionWithRequestBodyParameters<TDto>,
        ActionWithIdentifierParameters<TIdentifier> {
}
