package ch.puzzle.libpuzzle.springframework.boot.rest.action.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;

public interface DeleteActionBuilder<TIdentifier> {

    DeleteActionBuilder<TIdentifier> by(TIdentifier identifier);

    <TNewResponseDto> TNewResponseDto execute(ResponseFactory<Void, TNewResponseDto, DeleteActionParameters<TIdentifier>> responseFactory);

    Void execute();
}
