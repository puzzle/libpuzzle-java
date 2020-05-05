package ch.puzzle.libpuzzle.springframework.boot.rest.newaction;

public interface DataApplier<TSource, TDestination> {

    void apply(TSource source, TDestination destination);

}
