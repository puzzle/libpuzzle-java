package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;

public interface CreateActionBuilder<TEntity, TDto> {

    CreateActionBuilder<TEntity, TDto> using(TEntity initialEntity);

    <TNewRequestDto> CreateActionBuilder<TEntity, TNewRequestDto> with(TNewRequestDto requestDto);

    <TNewResponseDto> TNewResponseDto execute(ResponseFactory<TEntity, TNewResponseDto, CreateActionParameters<TEntity, TDto>> responseFactory);

    <TNewResponseDto> TNewResponseDto execute(Class<TNewResponseDto> responseDtoClass);

}
