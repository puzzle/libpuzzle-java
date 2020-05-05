package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
public class ListActionParameters<TFilter, TResponseDto> {

    @With
    private final ActionParameter<Integer> offset;

    @With
    private final ActionParameter<Integer> limit;

    @With
    private final ActionParameter<TFilter> filter;

    private final ActionParameter<Class<TResponseDto>> responseDtoClass;

    public ListActionParameters(final int defaultOffset, final int defaultLimit) {
        offset = ActionParameter.holding(defaultOffset);
        limit = ActionParameter.holding(defaultLimit);
        filter = ActionParameter.empty(ListActionBuilder.class, "matching");
        responseDtoClass = ActionParameter.empty(ListActionBuilder.class, "execute");
    }

    public ActionParameter<TFilter> filter() {
        return filter;
    }

    public ActionParameter<Integer> offset() {
        return offset;
    }

    public ActionParameter<Integer> limit() {
        return limit;
    }

    public ActionParameter<Class<TResponseDto>> responseDtoClass() {
        return responseDtoClass;
    }

    public <TNewResponseDto> ListActionParameters<TFilter, TNewResponseDto> withResponseDtoClass(
            ActionParameter<Class<TNewResponseDto>> responseDtoClass
    ) {
        return new ListActionParameters<>(offset, limit, filter, responseDtoClass);
    }
}
