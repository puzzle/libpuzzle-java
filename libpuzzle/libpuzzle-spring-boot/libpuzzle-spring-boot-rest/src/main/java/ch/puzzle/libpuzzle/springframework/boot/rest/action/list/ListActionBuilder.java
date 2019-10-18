package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import org.springframework.http.ResponseEntity;

public interface ListActionBuilder<TFilter, TResponseDto> {

    ListActionBuilder<TFilter, TResponseDto> matching(TFilter filter);

    ListActionBuilder<TFilter, TResponseDto> skip(int offset);

    ListActionBuilder<TFilter, TResponseDto> limit(int limit);

    <TNewResponseDto> ResponseEntity<Iterable<TNewResponseDto>> execute(Class<TNewResponseDto> responseDtoClass);

}
