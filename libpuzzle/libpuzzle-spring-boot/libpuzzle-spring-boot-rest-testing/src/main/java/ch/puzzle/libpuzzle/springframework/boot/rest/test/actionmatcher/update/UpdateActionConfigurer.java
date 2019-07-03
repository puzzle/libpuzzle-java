package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class UpdateActionConfigurer<TEntity, TId> extends CrudActionConfigurer<CrudActions<?, ?, ?, UpdateAction<TEntity, TId>, ?>, UpdateAction<TEntity, TId>> {

    private UpdateActionConfigurer(CrudActions<?, ?, ?, UpdateAction<TEntity, TId>, ?> crudActions) {
        super(crudActions);
    }

    public static <TEntity, TId> UpdateActionConfigurer<TEntity, TId> mockedUpdateAction(CrudActions<?, ?, ?, UpdateAction<TEntity, TId>, ?> crudActions) {
        return new UpdateActionConfigurer<>(crudActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UpdateAction<TEntity, TId> createActionMock() {
        var action = (UpdateAction<TEntity, TId>) mock(UpdateAction.class);
        doReturn(action).when(action).by(any());
        doReturn(action).when(action).with(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected UpdateAction<TEntity, TId> createActionSpy() {
        return spy(crudActions.update());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, ?, ?, UpdateAction<TEntity, TId>, ?> crudActions, UpdateAction<TEntity, TId> action) {
        doReturn(action).when(crudActions).update();
    }
}
