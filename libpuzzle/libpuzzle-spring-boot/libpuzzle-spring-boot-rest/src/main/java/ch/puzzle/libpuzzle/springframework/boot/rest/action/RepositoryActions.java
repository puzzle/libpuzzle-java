package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class RepositoryActions {

    private final DtoMapper dtoMapper;

    public RepositoryActions(DtoMapper dtoMapper) {
        this.dtoMapper = dtoMapper;
    }

    public <TEntity, TEntityId> Configurer<TEntity, TEntityId> forRepository(CrudRepository<TEntity, TEntityId> repository) {
        return new Configurer<>(repository, dtoMapper);
    }

    private static class Configurer<TEntity, TEntityId> implements RestActionsConfiguration<
            ListAction<TEntity>,
            FindAction<TEntity, TEntityId>,
            CreateAction<TEntity>,
            UpdateAction<TEntity, TEntityId>,
            DeleteAction<TEntityId>
            > {

        private CrudRepository<TEntity, TEntityId> repository;

        private DtoMapper dtoMapper;

        private Configurer(CrudRepository<TEntity, TEntityId> repository, DtoMapper dtoMapper) {
            this.repository = repository;
            this.dtoMapper = dtoMapper;
        }

        @Override
        public void apply(RestActions<
                ListAction<TEntity>,
                FindAction<TEntity, TEntityId>,
                CreateAction<TEntity>,
                UpdateAction<TEntity, TEntityId>,
                DeleteAction<TEntityId>
                >.Config config) {
            config
                    .useListAction(() -> new ListAction<>(repository, dtoMapper))
                    .useFindAction(() -> new FindAction<>(repository, dtoMapper))
                    .useCreateAction(() -> new CreateAction<>(repository, dtoMapper))
                    .useUpdateAction(() -> new UpdateAction<>(repository, dtoMapper))
                    .useDeleteAction(() -> new DeleteAction<>(repository));
        }
    }
}
