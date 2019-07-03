package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class FindActionConfigurer<TEntity, TId> extends CrudActionConfigurer<CrudActions<?, FindAction<TEntity, TId>, ?, ?, ?>, FindAction<TEntity, TId>> {

    private FindActionConfigurer(CrudActions<?, FindAction<TEntity, TId>, ?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static <TEntity, TId> FindActionConfigurer<TEntity, TId> mockedFindAction(CrudActions<?, FindAction<TEntity, TId>, ?, ?, ?> crudActions) {
        return new FindActionConfigurer<>(crudActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected FindAction<TEntity, TId> createActionMock() {
        var action = (FindAction<TEntity, TId>) mock(FindAction.class);
        doReturn(action).when(action).by(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected FindAction<TEntity, TId> createActionSpy() {
        return spy(crudActions.find());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, FindAction<TEntity, TId>, ?, ?, ?> crudActions, FindAction<TEntity, TId> action) {
        doReturn(action).when(crudActions).find();
    }
}
