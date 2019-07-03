package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.IllegalActionParam;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class UpdateAction<TEntity, TEntityId> {

    private CrudRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;

    private Supplier<Object> dtoSupplier = IllegalActionParam.missingParam(UpdateAction.class, "with");

    private Supplier<TEntityId> idSupplier = IllegalActionParam.missingParam(UpdateAction.class, "by");

    public UpdateAction(
            CrudRepository<TEntity, TEntityId> repository,
            DtoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UpdateAction<TEntity, TEntityId> by(TEntityId id) {
        this.idSupplier = () -> id;
        return this;
    }

    public <TDto> UpdateAction<TEntity, TEntityId> with(TDto requestDto) {
        this.dtoSupplier = () -> requestDto;
        return this;
    }

    public <TResponseDto> ResponseEntity<TResponseDto> execute(Class<TResponseDto> responseDtoClass) {
        return repository.findById(idSupplier.get())
                .map(entity -> mapAndSave(dtoSupplier.get(), entity))
                .map(entity -> mapper.map(entity, responseDtoClass))
                .map(responseDto -> new ResponseEntity<>(responseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private <TDto> TEntity mapAndSave(TDto dto, TEntity entity) {
        mapper.map(dto, entity);
        return repository.save(entity);
    }

}
