package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;

public interface ListActionBuilder<TFilter, TEntity, TBuilder
        extends ListActionBuilder<TFilter, TEntity, TBuilder>> {

    TBuilder matching(TFilter filter);

    TBuilder skip(int offset);

    TBuilder limit(int limit);

    <TNewResponseDto> TNewResponseDto execute(final ResponseFactory<Iterable<TEntity>, TNewResponseDto, ListActionParameters<TFilter>> responseFactory);

    <TNewResponseDto> Iterable<TNewResponseDto> execute(final Class<TNewResponseDto> responseDtoClass);

}
