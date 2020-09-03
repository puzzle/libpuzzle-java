package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
public class ListActionParameters<TFilter> {

    @With
    private final ActionParameter<Integer> offset;

    @With
    private final ActionParameter<Integer> limit;

    @With
    private final ActionParameter<TFilter> filter;

    public ListActionParameters(final int defaultOffset, final int defaultLimit) {
        offset = ActionParameter.holding(defaultOffset);
        limit = ActionParameter.holding(defaultLimit);
        filter = ActionParameter.empty(ListActionBuilder.class, "matching");
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
}
