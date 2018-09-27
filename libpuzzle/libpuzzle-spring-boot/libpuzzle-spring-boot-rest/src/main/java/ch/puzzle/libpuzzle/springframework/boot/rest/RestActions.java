package ch.puzzle.libpuzzle.springframework.boot.rest;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class RestActions {

    public <TEntity, TDto> CreateAction<TEntity, TDto> create(CrudRepository<TEntity, ?> repository) {
        return new CreateAction<>(repository, null);
    }

    public <TEntity, TDto> ListAction<TEntity, TDto> list(CrudRepository<TEntity, ?> repository, Class<TDto> dtoClass) {
        return new ListAction<>(repository, null, dtoClass);
    }

    public <TEntity, TDto> PageListAction<TEntity, TDto> list(JpaRepository<TEntity, ?> repository, Class<TDto> dtoClass) {
        return new PageListAction<>(repository, null, dtoClass);
    }

    public <TEntity, TDto> FilteredListAction<TEntity, TDto> filter(JpaSpecificationExecutor<TEntity> repository, Class<TDto> dtoClass) {
        return new FilteredListAction<>(repository, null, dtoClass);
    }

    public <TEntity, TEntityId, TDto> FindAction<TEntity, TEntityId, TDto> find(
            CrudRepository<TEntity, TEntityId> repository,
            Class<TDto> dtoClass
    ) {
        return new FindAction<>(repository, null, dtoClass);
    }

    public <TEntity, TEntityId, TDto> UpdateAction<TEntity, TEntityId, TDto> update(
            CrudRepository<TEntity, TEntityId> repository
    ) {
        return new UpdateAction<>(repository, null);
    }

    public <TEntity, TEntityId, TDto> DeleteAction<TEntity, TEntityId, TDto> delete(
            CrudRepository<TEntity, TEntityId> repository
    ) {
        return new DeleteAction<>(repository);
    }

}
