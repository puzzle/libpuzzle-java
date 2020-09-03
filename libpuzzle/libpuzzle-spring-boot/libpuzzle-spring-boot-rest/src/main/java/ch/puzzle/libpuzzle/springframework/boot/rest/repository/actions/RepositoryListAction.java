package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class RepositoryListAction<TEntity> implements ListAction<TEntity, Predicate<TEntity>> {

    private final Predicate<TEntity> DEFAULT_FILTER = (entity) -> true;

    private final CrudRepository<TEntity, ?> repository;

    private final DtoMapper mapper;

    public static <TEntity> Predicate<TEntity> allFilter() {
        return (entity) -> true;
    }

    @Override
    public Iterable<TEntity> execute(final ListActionParameters<Predicate<TEntity>> params) {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .filter(params.filter().orDefault(DEFAULT_FILTER))
                .skip(params.offset().get())
                .limit(params.limit().get())
                .collect(Collectors.toList());
    }
}
