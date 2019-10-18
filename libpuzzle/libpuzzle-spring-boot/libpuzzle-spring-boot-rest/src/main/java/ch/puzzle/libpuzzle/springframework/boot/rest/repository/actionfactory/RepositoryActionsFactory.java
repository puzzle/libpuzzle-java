package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actionfactory;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryActionsFactory {

    private final DtoMapper dtoMapper;

    public <TEntity, TIdentifier> RepositoryActions<TEntity, TIdentifier> forRepository(CrudRepository<TEntity, TIdentifier> repository) {
        return new RepositoryActions<>(repository, dtoMapper);
    }

}
