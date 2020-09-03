package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractListActionExecution<TFilter, TResponseDto, TExecutor
        extends AbstractListActionExecution<TFilter, TResponseDto, TExecutor>>
        implements ListActionBuilder<TFilter, TResponseDto, TExecutor> {

    protected final ListActionParameters<TFilter> params;

    @Override
    public TExecutor skip(final int offset) {
        return withParams(params.withOffset(ActionParameter.holding(offset)));
    }

    @Override
    public TExecutor limit(final int limit) {
        return withParams(params.withLimit(ActionParameter.holding(limit)));
    }

    @Override
    public TExecutor matching(final TFilter filter) {
        return withParams(params.withFilter(ActionParameter.holding(filter)));
    }

    abstract protected TExecutor withParams(ListActionParameters<TFilter> params);
}
