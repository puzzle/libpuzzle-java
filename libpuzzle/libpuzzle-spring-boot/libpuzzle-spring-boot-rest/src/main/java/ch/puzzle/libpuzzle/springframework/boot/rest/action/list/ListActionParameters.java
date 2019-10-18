package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;

public interface ListActionParameters<TFilter, TResponseDto> {

    ActionParameter<TFilter> filter();

    ActionParameter<Integer> offset();

    ActionParameter<Integer> limit();

    ActionParameter<Class<TResponseDto>> responseDtoClass();
}
