package ch.puzzle.libpuzzle.springframework.boot.rest.action;

public interface ActionWithIdentifierParameters<TIdentifier> {

    ActionParameter<TIdentifier> identifier();
}
