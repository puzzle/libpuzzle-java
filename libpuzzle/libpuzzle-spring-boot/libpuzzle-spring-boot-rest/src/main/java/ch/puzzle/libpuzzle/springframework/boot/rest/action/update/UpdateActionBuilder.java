package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;

public interface UpdateActionBuilder<TEntity, TIdentifier, TRequestDto> {

    UpdateActionBuilder<TEntity, TIdentifier, TRequestDto> by(TIdentifier identifier);

    <TNewRequestDto> UpdateActionBuilder<TEntity, TIdentifier, TNewRequestDto> with(TNewRequestDto requestDto);

    <TNewResponseDto> TNewResponseDto execute(ResponseFactory<TEntity, TNewResponseDto, UpdateActionParameters<TIdentifier, TRequestDto>> responseFactory);

    <TNewResponseDto> TNewResponseDto execute(Class<TNewResponseDto> responseDtoClass);

}
