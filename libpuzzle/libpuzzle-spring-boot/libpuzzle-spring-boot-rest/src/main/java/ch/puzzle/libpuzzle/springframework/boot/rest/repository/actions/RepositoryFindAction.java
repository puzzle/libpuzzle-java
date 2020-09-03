package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class RepositoryFindAction<TEntity, TIdentifier> implements FindAction<TIdentifier, TEntity> {

    private final CrudRepository<TEntity, TIdentifier> repository;

    private final DtoMapper mapper;

    @Override
    public TEntity execute(
            final FindActionParameters<TIdentifier> params
    ) {
        Optional<TEntity> entity = repository.findById(params.identifier().get());
        if (entity.isEmpty()) {
            throw new RuntimeException("404"); // FIXME
        }
        return entity.get();
    }
}
