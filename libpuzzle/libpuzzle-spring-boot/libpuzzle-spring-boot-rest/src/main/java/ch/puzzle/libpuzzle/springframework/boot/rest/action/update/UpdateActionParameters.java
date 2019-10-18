package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;

public interface UpdateActionParameters<TIdentifier, TDto, TResponseDto> {

    ActionParameter<TIdentifier> identifier();

    ActionParameter<TDto> requestDto();

    ActionParameter<Class<TResponseDto>> responseDtoClass();
}
