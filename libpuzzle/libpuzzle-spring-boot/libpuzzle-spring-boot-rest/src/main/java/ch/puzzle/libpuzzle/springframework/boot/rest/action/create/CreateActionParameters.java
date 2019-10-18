package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;

public interface CreateActionParameters<TEntity, TDto, TResponseDto> {

    ActionParameter<TDto> requestDto();

    ActionParameter<TEntity> entity();

    ActionParameter<Class<TResponseDto>> responseDtoClass();
}
