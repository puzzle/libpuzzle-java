package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.IllegalActionParam;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class CreateAction<TEntity> {

    private CrudRepository<TEntity, ?> repository;

    private DtoMapper mapper;

    private Supplier<Object> dtoSupplier = IllegalActionParam.missingParam(CreateAction.class, "from");

    private Supplier<TEntity> entitySupplier = IllegalActionParam.missingParam(CreateAction.class, "with");

    public CreateAction(CrudRepository<TEntity, ?> repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public <TDto> CreateAction<TEntity> from(TDto requestDto) {
        dtoSupplier = () -> requestDto;
        return this;
    }

    public CreateAction<TEntity> with(TEntity initialEntity) {
        entitySupplier = () -> initialEntity;
        return this;
    }

    public <TResponseDto> ResponseEntity<TResponseDto> execute(Class<TResponseDto> responseDtoClass) {
        var entity = entitySupplier.get();
        mapper.map(dtoSupplier.get(), entity);
        var persistedEntity = repository.save(entity);
        TResponseDto responseDto = mapper.map(persistedEntity, responseDtoClass);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
