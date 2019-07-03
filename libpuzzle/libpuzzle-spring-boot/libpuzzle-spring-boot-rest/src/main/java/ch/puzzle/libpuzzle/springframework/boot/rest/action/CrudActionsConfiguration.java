package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;

public interface CrudActionsConfiguration<TListAction, TFindAction, TCreateAction, TUpdateAction, TDeleteAction> {

    void apply(
            CrudActions<TListAction, TFindAction, TCreateAction, TUpdateAction, TDeleteAction>.Config config
    );


}
