package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public final class UpdateActionExecution<TEntity, TIdentifier, TRequestDto, TResponseDto> implements
        UpdateActionBuilder<TEntity, TIdentifier, TRequestDto, TResponseDto>,
        UpdateActionParameters<TIdentifier, TRequestDto, TResponseDto> {

    @With(AccessLevel.PRIVATE)
    private final ActionParameter<TIdentifier> identifier;

    private final ActionParameter<TRequestDto> requestDto;

    private final ActionParameter<Class<TResponseDto>> responseDtoClass;

    private final UpdateAction<TIdentifier> action;

    public UpdateActionExecution(final UpdateAction<TIdentifier> action) {
        this(
                ActionParameter.empty(UpdateActionBuilder.class, "by"),
                ActionParameter.empty(UpdateActionBuilder.class, "with"),
                ActionParameter.empty(UpdateActionBuilder.class, "execute"),
                action
        );
    }

    @Override
    public ActionParameter<TRequestDto> requestDto() {
        return requestDto;
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
    public UpdateActionBuilder<TEntity, TIdentifier, TRequestDto, TResponseDto> by(final TIdentifier identifier) {
        return withIdentifier(ActionParameter.holding(identifier));
    }

    @Override
    public <TNewRequestDto> UpdateActionBuilder<TEntity, TIdentifier, TNewRequestDto, TResponseDto> with(
            final TNewRequestDto requestDto
    ) {
        return new UpdateActionExecution<>(
                identifier,
                ActionParameter.holding(requestDto),
                responseDtoClass,
                action
        );
    }

    @Override
    public <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(final Class<TNewResponseDto> responseDtoClass) {
        return action.execute(new UpdateActionExecution<>(
                identifier,
                requestDto,
                ActionParameter.holding(responseDtoClass),
                action
        ));
    }
}
