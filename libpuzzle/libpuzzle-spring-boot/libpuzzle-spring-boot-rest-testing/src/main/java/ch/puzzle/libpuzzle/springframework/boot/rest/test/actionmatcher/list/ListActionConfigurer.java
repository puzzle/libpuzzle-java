package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.RestActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ListActionConfigurer<TEntity> extends RestActionConfigurer<RestActions<ListAction<TEntity>, ?, ?, ?, ?>, ListAction<TEntity>> {

    private ListActionConfigurer(RestActions<ListAction<TEntity>, ?, ?, ?, ?> restActions) {
        super(restActions);
    }

    public static <TEntity> ListActionConfigurer<TEntity> mockedListAction(RestActions<ListAction<TEntity>, ?, ?, ?, ?> restActions) {
        return new ListActionConfigurer<>(restActions);
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
        return spy(restActions.list());
    }

    @Override
    protected void mockRestActions(RestActions<ListAction<TEntity>, ?, ?, ?, ?> restActions, ListAction<TEntity> action) {
        doReturn(action).when(restActions).list();
    }
}
