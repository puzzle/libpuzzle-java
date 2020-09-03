package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;

public interface FindActionBuilder<TIdentifier, TEntity> {

    FindActionBuilder<TIdentifier, TEntity> by(TIdentifier identifier);

    <TNewResponseDto> TNewResponseDto execute(ResponseFactory<TEntity, TNewResponseDto, FindActionParameters<TIdentifier>> responseFactory);

    <TNewResponseDto> TNewResponseDto execute(Class<TNewResponseDto> responseDtoClass);
}
