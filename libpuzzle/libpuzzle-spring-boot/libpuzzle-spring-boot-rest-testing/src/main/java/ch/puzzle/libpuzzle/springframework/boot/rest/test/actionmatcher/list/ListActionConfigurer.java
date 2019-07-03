package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ListActionConfigurer<TEntity> extends CrudActionConfigurer<CrudActions<ListAction<TEntity>, ?, ?, ?, ?>, ListAction<TEntity>> {

    private ListActionConfigurer(CrudActions<ListAction<TEntity>, ?, ?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static <TEntity> ListActionConfigurer<TEntity> mockedListAction(CrudActions<ListAction<TEntity>, ?, ?, ?, ?> crudActions) {
        return new ListActionConfigurer<>(crudActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ListAction<TEntity> createActionMock() {
        var action = (ListAction<TEntity>) mock(ListAction.class);
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected ListAction<TEntity> createActionSpy() {
        return spy(crudActions.list());
    }

    @Override
    protected void mockCrudActions(CrudActions<ListAction<TEntity>, ?, ?, ?, ?> crudActions, ListAction<TEntity> action) {
        doReturn(action).when(crudActions).list();
    }
}
