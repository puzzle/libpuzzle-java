package ch.puzzle.libpuzzle.modelmapper.jpa;

import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ReferenceProviderFactory {

    private ModelMapper mapper;

    private EntityManager entityManager;

    public ReferenceProviderFactory(
            ModelMapper mapper,
            EntityManager entityManager
    ) {
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    public <TDto, TEntity> ReferenceProvider<TDto, TEntity> single(
            Class<TEntity> entityClass, Function<TDto,
            Object> idProvider,
            boolean update

    ) {
        return new ReferenceProvider<>(mapper, entityManager, entityClass, idProvider, update);
    }

    public <TDto, TEntity, TCollection extends Collection<TEntity>> ListOfReferencesProvider<TDto, TEntity, TCollection> collection(
            Class<TEntity> entityClass, Function<TDto,
            Object> idProvider,
            boolean update,
            Collector<TEntity, ?, TCollection> collector,
            Supplier<TCollection> emptyCollectionSupplier
    ) {
        return new ListOfReferencesProvider<>(single(entityClass, idProvider, update), collector, emptyCollectionSupplier);
    }

    public <TDto, TEntity> ListOfReferencesProvider<TDto, TEntity, List<TEntity>> list(
            Class<TEntity> entityClass, Function<TDto,
            Object> idProvider,
            boolean update
    ) {
        return collection(entityClass, idProvider, update, Collectors.toList(), Collections::emptyList);
    }

    public <TDto, TEntity> ListOfReferencesProvider<TDto, TEntity, Set<TEntity>> set(
            Class<TEntity> entityClass, Function<TDto,
            Object> idProvider,
            boolean update
    ) {
        return collection(entityClass, idProvider, update, Collectors.toSet(), Collections::emptySet);
    }

}
