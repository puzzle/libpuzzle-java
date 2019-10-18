package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class FindActionExecution<TIdentifier, TResponseDto> implements
        FindActionBuilder<TIdentifier, TResponseDto>,
        FindActionParameters<TIdentifier, TResponseDto> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TIdentifier> identifier;

    private final ActionParameter<Class<TResponseDto>> responseDtoClass;

    private final FindAction<TIdentifier> action;

    public FindActionExecution(final FindAction<TIdentifier> action) {
        this(
                ActionParameter.empty(FindActionBuilder.class, "by"),
                ActionParameter.empty(FindActionBuilder.class, "execute"),
                action
        );
    }

    @Override
    public ActionParameter<TIdentifier> identifier() {
        return identifier;
    }

    @Override
    public ActionParameter<Class<TResponseDto>> responseDtoClass() {
        return responseDtoClass;
    }

    @Override
    public FindActionBuilder<TIdentifier, TResponseDto> by(final TIdentifier identifier) {
        return withIdentifier(ActionParameter.holding(identifier));
    }

    @Override
    public <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(final Class<TNewResponseDto> responseDtoClass) {
        return action.execute(new FindActionExecution<>(
                identifier,
                ActionParameter.holding(responseDtoClass),
                action
        ));
    }
}
