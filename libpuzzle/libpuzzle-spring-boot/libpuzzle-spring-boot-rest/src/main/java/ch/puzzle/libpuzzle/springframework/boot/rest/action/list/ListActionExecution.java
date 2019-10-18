package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class ListActionExecution<TFilter, TResponseDto> implements
        ListActionBuilder<TFilter, TResponseDto>,
        ListActionParameters<TFilter, TResponseDto> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<Integer> offset;

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<Integer> limit;

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TFilter> filter;

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<Class<TResponseDto>> responseDtoClass;

    private final ListAction<TFilter> action;

    public ListActionExecution(final ListAction<TFilter> action, int defaultOffset, int defaultLimit) {
        this(
                ActionParameter.holding(defaultOffset),
                ActionParameter.holding(defaultLimit),
                ActionParameter.empty(ListActionBuilder.class, "matching"),
                ActionParameter.empty(ListActionBuilder.class, "execute"),
                action
        );
    }

    @Override
    public ActionParameter<TFilter> filter() {
        return filter;
    }

    @Override
    public ActionParameter<Integer> offset() {
        return offset;
    }

    @Override
    public ActionParameter<Integer> limit() {
        return limit;
    }

    @Override
    public ActionParameter<Class<TResponseDto>> responseDtoClass() {
        return responseDtoClass;
    }

    @Override
    public ListActionBuilder<TFilter, TResponseDto> skip(final int offset) {
        return withOffset(ActionParameter.holding(offset));
    }

    @Override
    public ListActionBuilder<TFilter, TResponseDto> limit(final int limit) {
        return withLimit(ActionParameter.holding(limit));
    }

    @Override
    public ListActionBuilder<TFilter, TResponseDto> matching(final TFilter filter) {
        return withFilter(ActionParameter.holding(filter));
    }

    @Override
    public <TNewResponseDto> ResponseEntity<Iterable<TNewResponseDto>> execute(final Class<TNewResponseDto> responseDtoClass) {
        return action.execute(
                new ListActionExecution<>(
                offset,
                limit,
                filter,
                ActionParameter.holding(responseDtoClass),
                action
        ));
    }
}
