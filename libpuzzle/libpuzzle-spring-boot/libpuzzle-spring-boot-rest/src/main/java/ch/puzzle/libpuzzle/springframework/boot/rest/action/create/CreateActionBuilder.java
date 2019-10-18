package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import org.springframework.http.ResponseEntity;

public interface CreateActionBuilder<TEntity, TDto, TResponseDto> {

    CreateActionBuilder<TEntity, TDto, TResponseDto> using(TEntity initialEntity);

    <TNewRequestDto> CreateActionBuilder<TEntity, TNewRequestDto, TResponseDto> with(TNewRequestDto requestDto);

    <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(Class<TNewResponseDto> responseDtoClass);

}
