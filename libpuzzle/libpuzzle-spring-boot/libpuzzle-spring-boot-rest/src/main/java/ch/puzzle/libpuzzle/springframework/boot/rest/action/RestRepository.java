package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RestRepository<TEntity, TEntityId> extends CrudRepository<TEntity, TEntityId>,
        JpaRepository<TEntity, TEntityId>,
        JpaSpecificationExecutor<TEntity> {
}
