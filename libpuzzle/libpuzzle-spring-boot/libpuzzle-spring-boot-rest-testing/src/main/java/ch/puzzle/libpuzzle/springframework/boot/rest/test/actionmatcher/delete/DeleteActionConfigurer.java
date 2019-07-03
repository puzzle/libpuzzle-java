package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class DeleteActionConfigurer<TId> extends CrudActionConfigurer<CrudActions<?, ?, ?, ?, DeleteAction<TId>>, DeleteAction<TId>> {

    private DeleteActionConfigurer(CrudActions<?, ?, ?, ?, DeleteAction<TId>> crudActions) {
        super(crudActions);
    }

    public static <TId> DeleteActionConfigurer<TId> mockedDeleteAction(CrudActions<?, ?, ?, ?, DeleteAction<TId>> crudActions) {
        return new DeleteActionConfigurer<>(crudActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected DeleteAction<TId> createActionMock() {
        var action = (DeleteAction<TId>) mock(DeleteAction.class);
        doReturn(action).when(action).by(any());
        doReturn(null).when(action).execute();
        return action;
    }

    @Override
    protected DeleteAction<TId> createActionSpy() {
        return spy(crudActions.delete());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, ?, ?, ?, DeleteAction<TId>> crudActions, DeleteAction<TId> action) {
        doReturn(action).when(crudActions).delete();
    }
}
