package ch.puzzle.libpuzzle.springframework.boot.rest.mapper;

public interface StaticDtoMapper<TSource, TDestination> {

    void map(TSource source, TDestination destination);

    TDestination map(TSource source, Class<TDestination> destinationClass);

}
