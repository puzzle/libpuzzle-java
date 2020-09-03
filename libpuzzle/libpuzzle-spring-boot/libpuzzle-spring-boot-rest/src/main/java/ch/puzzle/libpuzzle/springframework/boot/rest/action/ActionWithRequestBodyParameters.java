package ch.puzzle.libpuzzle.springframework.boot.rest.action;

public interface ActionWithRequestBodyParameters<TDto> {

    ActionParameter<TDto> requestDto();
}
