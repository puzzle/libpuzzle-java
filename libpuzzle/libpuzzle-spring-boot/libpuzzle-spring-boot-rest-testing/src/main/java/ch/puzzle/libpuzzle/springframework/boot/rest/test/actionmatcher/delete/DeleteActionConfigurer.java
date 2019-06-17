package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.RestActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class DeleteActionConfigurer<TId> extends RestActionConfigurer<RestActions<?, ?, ?, ?, DeleteAction<TId>>, DeleteAction<TId>> {

    private DeleteActionConfigurer(RestActions<?, ?, ?, ?, DeleteAction<TId>> restActions) {
        super(restActions);
    }

    public static <TId> DeleteActionConfigurer<TId> mockedDeleteAction(RestActions<?, ?, ?, ?, DeleteAction<TId>> restActions) {
        return new DeleteActionConfigurer<>(restActions);
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
        return spy(restActions.delete());
    }

    @Override
    protected void mockRestActions(RestActions<?, ?, ?, ?, DeleteAction<TId>> restActions, DeleteAction<TId> action) {
        doReturn(action).when(restActions).delete();
    }
}
