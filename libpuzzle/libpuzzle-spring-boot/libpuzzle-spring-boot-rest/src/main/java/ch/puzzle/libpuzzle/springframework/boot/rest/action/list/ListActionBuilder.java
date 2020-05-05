package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

public interface ListActionBuilder<TFilter, TResponseDto, TBuilder
        extends ListActionBuilder<TFilter, TResponseDto, TBuilder>> {

    TBuilder matching(TFilter filter);

    TBuilder skip(int offset);

    TBuilder limit(int limit);

    <TNewResponseDto> Object execute(final Class<TNewResponseDto> responseDtoClass);

}
