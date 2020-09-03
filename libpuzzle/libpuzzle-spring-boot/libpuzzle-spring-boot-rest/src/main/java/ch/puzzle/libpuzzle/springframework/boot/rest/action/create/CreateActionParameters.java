package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionWithRequestBodyParameters;

public interface CreateActionParameters<TEntity, TDto> extends
        ActionWithRequestBodyParameters<TDto> {

    ActionParameter<TEntity> entity();

}
