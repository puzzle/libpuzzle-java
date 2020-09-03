package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class RepositoryUpdateAction<TEntity, TIdentifier> implements UpdateAction<TEntity, TIdentifier> {

    private final CrudRepository<TEntity, TIdentifier> repository;

    private final DtoMapper mapper;

    @Override
    public TEntity execute(final UpdateActionParameters<TIdentifier, ?> params) {
        return repository.findById(params.identifier().get())
                .map(entity -> mapAndSave(params.requestDto().get(), entity))
                .orElseThrow(() -> new RuntimeException("404")); // FIXME
    }

    private <TDto> TEntity mapAndSave(TDto dto, TEntity entity) {
        mapper.map(dto, entity);
        return repository.save(entity);
    }
}
