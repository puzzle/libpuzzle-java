package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class RepositoryUpdateAction<TEntity, TIdentifier> implements UpdateAction<TIdentifier> {

    private final CrudRepository<TEntity, TIdentifier> repository;

    private final DtoMapper mapper;

    @Override
    public <TResponseDto> ResponseEntity<TResponseDto> execute(final UpdateActionParameters<TIdentifier, ?, TResponseDto> params) {
        return repository.findById(params.identifier().get())
                .map(entity -> mapAndSave(params.requestDto().get(), entity))
                .map(entity -> mapper.map(entity, params.responseDtoClass().get()))
                .map(responseDto -> new ResponseEntity<>(responseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private <TDto> TEntity mapAndSave(TDto dto, TEntity entity) {
        mapper.map(dto, entity);
        return repository.save(entity);
    }
}
