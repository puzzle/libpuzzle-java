package ch.puzzle.libpuzzle.springframework.boot.rest;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.*;
import ch.puzzle.libpuzzle.springframework.boot.rest.filter.FilterSpecificationFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.stereotype.Service;

@Service
public class RestActions<TEntity, TEntityId> {

    private RestRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;

    public RestActions(RestRepository<TEntity, TEntityId> repository, DtoMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    public <TResponseDto> CreateAction<TEntity, TResponseDto> create(Class<TResponseDto> responseDtoClass) {
        return new CreateAction<>(repository, mapper, responseDtoClass);
    }

    public ListAction<TEntity> list() {
        return new ListAction<>(repository, mapper);
    }

    public PageListAction<TEntity> paginate() {
        return new PageListAction<>(repository, mapper);
    }

    public <TDto, TFilter> FilteredListAction<TEntity, TDto, TFilter> filter(
            Class<TDto> dtoClass,
            FilterSpecificationFactory<TFilter> filterSpecificationFactory
    ) {
        return new FilteredListAction<>(repository, mapper, dtoClass, filterSpecificationFactory);
    }

    public <TResponseDto> FindAction<TEntity, TEntityId, TResponseDto> find(Class<TResponseDto> responseDtoClass) {
        return new FindAction<>(repository, mapper, responseDtoClass);
    }

    public <TResponseDto> UpdateAction<TEntity, TEntityId, TResponseDto> update(Class<TResponseDto> responseDtoClass) {
        return new UpdateAction<>(repository, mapper, responseDtoClass);
    }

    public <TResponseDto> DeleteAction<TEntity, TEntityId> delete() {
        return new DeleteAction<>(repository);
    }

}
