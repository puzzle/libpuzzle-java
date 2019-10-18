package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actionfactory;

import ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory.CrudActionFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions.RepositoryCreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions.RepositoryDeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions.RepositoryFindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions.RepositoryListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions.RepositoryUpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class RepositoryActions<TEntity, TIdentifier> extends CrudActionFactory<TEntity, TIdentifier, Predicate<TEntity>> {

    private final CrudRepository<TEntity, TIdentifier> repository;

    private final DtoMapper dtoMapper;

    public CreateAction<TEntity> create() {
        return new RepositoryCreateAction<>(repository, dtoMapper);
    }

    public FindAction<TIdentifier> find() {
        return new RepositoryFindAction<>(repository, dtoMapper);
    }

    public ListAction<Predicate<TEntity>> list() {
        return new RepositoryListAction<>(repository, dtoMapper);
    }

    public UpdateAction<TIdentifier> update() {
        return new RepositoryUpdateAction<>(repository, dtoMapper);
    }

    public DeleteAction<TIdentifier> delete() {
        return new RepositoryDeleteAction<>(repository);
    }
}
