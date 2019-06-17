package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.RestActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class UpdateActionConfigurer<TEntity, TId> extends RestActionConfigurer<RestActions<?, ?, ?, UpdateAction<TEntity, TId>, ?>, UpdateAction<TEntity, TId>> {

    private UpdateActionConfigurer(RestActions<?, ?, ?, UpdateAction<TEntity, TId>, ?> restActions) {
        super(restActions);
    }

    public static <TEntity, TId> UpdateActionConfigurer<TEntity, TId> mockedUpdateAction(RestActions<?, ?, ?, UpdateAction<TEntity, TId>, ?> restActions) {
        return new UpdateActionConfigurer<>(restActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected UpdateAction<TEntity, TId> createActionMock() {
        var action = (UpdateAction<TEntity, TId>) mock(UpdateAction.class);
        doReturn(action).when(action).by(any());
        doReturn(action).when(action).dto(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected UpdateAction<TEntity, TId> createActionSpy() {
        return spy(restActions.update());
    }

    @Override
    protected void mockRestActions(RestActions<?, ?, ?, UpdateAction<TEntity, TId>, ?> restActions, UpdateAction<TEntity, TId> action) {
        doReturn(action).when(restActions).update();
    }
}
