package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import org.springframework.http.ResponseEntity;

public interface UpdateActionBuilder<TEntity, TIdentifier, TRequestDto, TResponseDto> {

    UpdateActionBuilder<TEntity, TIdentifier, TRequestDto, TResponseDto> by(TIdentifier identifier);

    <TNewRequestDto> UpdateActionBuilder<TEntity, TIdentifier, TNewRequestDto, TResponseDto> with(TNewRequestDto requestDto);

    <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(Class<TNewResponseDto> responseDtoClass);

}
