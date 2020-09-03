package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RepositoryCreateAction<TEntity> implements CreateAction<TEntity> {

    private CrudRepository<TEntity, ?> repository;

    private DtoMapper mapper;

    public RepositoryCreateAction(CrudRepository<TEntity, ?> repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TEntity execute(CreateActionParameters<TEntity, ?> params) {
        var entity = params.entity().get();
        mapper.map(params.requestDto().get(), entity);
        return repository.save(entity);
    }
}
