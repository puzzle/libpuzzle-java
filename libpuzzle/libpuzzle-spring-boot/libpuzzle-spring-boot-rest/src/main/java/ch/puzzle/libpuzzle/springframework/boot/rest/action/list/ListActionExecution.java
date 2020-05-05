package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import org.springframework.http.ResponseEntity;

public class ListActionExecution<TFilter, TResponseDto>
        extends AbstractListActionExecution<TFilter, TResponseDto, ListActionExecution<TFilter, TResponseDto>> {

    private final ListAction<TFilter> action;

    public ListActionExecution(final ListAction<TFilter> action, final ListActionParameters<TFilter, TResponseDto> params) {
        super(params);
        this.action = action;
    }

    public ListActionExecution(final ListAction<TFilter> action, final int defaultOffset, final int defaultLimit) {
        this(action, new ListActionParameters<>(defaultOffset, defaultLimit));
    }

    @Override
    protected ListActionExecution<TFilter, TResponseDto> withParams(final ListActionParameters<TFilter, TResponseDto> params) {
        return new ListActionExecution<>(action, params);
    }

    public <TNewResponseDto> ResponseEntity<Iterable<TNewResponseDto>> execute(final Class<TNewResponseDto> responseDtoClass) {
        return action.execute(params.withResponseDtoClass(ActionParameter.holding(responseDtoClass)));
    }
}
