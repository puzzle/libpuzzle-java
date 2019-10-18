package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import org.springframework.http.ResponseEntity;

public interface FindActionBuilder<TIdentifier, TResponseDto> {

    FindActionBuilder<TIdentifier, TResponseDto> by(TIdentifier identifier);

    <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(Class<TNewResponseDto> responseDtoClass);
}
