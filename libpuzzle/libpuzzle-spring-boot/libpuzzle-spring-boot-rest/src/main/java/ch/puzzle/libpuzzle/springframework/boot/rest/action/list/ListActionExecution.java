package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.IterableResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.MappingResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;

public class ListActionExecution<TFilter, TEntity>
        extends AbstractListActionExecution<TFilter, TEntity, ListActionExecution<TFilter, TEntity>> {

    private final ListAction<TEntity, TFilter> action;

    private final ActionContext actionContext;

    public ListActionExecution(final ListAction<TEntity, TFilter> action, final ListActionParameters<TFilter> params, final ActionContext actionContext) {
        super(params);
        this.action = action;
        this.actionContext = actionContext;
    }

    public ListActionExecution(final ListAction<TEntity, TFilter> action, ActionContext actionContext, final int defaultOffset, final int defaultLimit) {
        this(action, new ListActionParameters<>(defaultOffset, defaultLimit), actionContext);
    }

    @Override
    protected ListActionExecution<TFilter, TEntity> withParams(final ListActionParameters<TFilter> params) {
        return new ListActionExecution<>(action, params, actionContext);
    }

    @Override
    public <TNewResponseDto> TNewResponseDto execute(final ResponseFactory<Iterable<TEntity>, TNewResponseDto, ListActionParameters<TFilter>> responseFactory) {
        return responseFactory.create(action.execute(params), params, actionContext);
    }

    public <TNewResponseDto> Iterable<TNewResponseDto> execute(final Class<TNewResponseDto> responseDtoClass) {
        return execute(IterableResponseFactory.forEach(MappingResponseFactory.mapTo(responseDtoClass)));
    }
}
