package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;

public interface FindActionParameters<TIdentifier, TResponseDto> {

    ActionParameter<TIdentifier> identifier();

    ActionParameter<Class<TResponseDto>> responseDtoClass();
}
