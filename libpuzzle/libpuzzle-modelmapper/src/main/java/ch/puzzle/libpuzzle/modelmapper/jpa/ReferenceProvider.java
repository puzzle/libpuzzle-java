package ch.puzzle.libpuzzle.modelmapper.jpa;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;

import javax.persistence.EntityManager;
import java.util.function.Function;

public class ReferenceProvider<TDto, TEntity> implements Provider<TEntity> {

    private final ModelMapper mapper;

    private final EntityManager entityManager;

    private final Function<TDto, Object> idProvider;

    private final Class<TEntity> entityClass;

    private boolean update;

    public ReferenceProvider(
            ModelMapper mapper,
            EntityManager entityManager,
            Class<TEntity> entityClass,
            Function<TDto, Object> idProvider,
            boolean update
    ) {
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.idProvider = idProvider;
        this.update = update;
    }

    @Override
    public TEntity get(ProvisionRequest<TEntity> request) {
        var source = request.getSource();
        return get((TDto) source);
    }

    public TEntity get(TDto dto) {
        if (dto == null) {
            return null;
        }
        var id = idProvider.apply(dto);
        if (id == null) {
            return mapper.map(dto, entityClass);
        }
        var entity = entityManager.getReference(entityClass, id);
        if (update) {
            mapper.map(dto, entity);
        }
        return entity;
    }
}
