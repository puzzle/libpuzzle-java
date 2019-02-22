package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;

public interface RestActionsConfiguration<TListAction, TFindAction, TCreateAction, TUpdateAction, TDeleteAction> {

    void apply(
            RestActions<TListAction, TFindAction, TCreateAction, TUpdateAction, TDeleteAction>.Config config
    );


}
