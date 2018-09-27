package ch.puzzle.libpuzzle.springframework.boot.rest.mapper;

public interface DtoMapper {

    <TDestination> void map(Object source, TDestination destination);

    <TDestination> TDestination map(Object source, Class<TDestination> destinationClass);

}
