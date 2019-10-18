package ch.puzzle.libpuzzle.springframework.boot.rest.action.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;

public interface DeleteActionParameters<TIdentifier> {

    ActionParameter<TIdentifier> identifier();
}
