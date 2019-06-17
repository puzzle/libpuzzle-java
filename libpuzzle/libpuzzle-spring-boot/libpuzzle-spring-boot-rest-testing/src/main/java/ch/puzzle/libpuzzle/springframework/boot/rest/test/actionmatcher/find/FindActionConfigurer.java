package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.RestActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class FindActionConfigurer<TEntity, TId> extends RestActionConfigurer<RestActions<?, FindAction<TEntity, TId>, ?, ?, ?>, FindAction<TEntity, TId>> {

    private FindActionConfigurer(RestActions<?, FindAction<TEntity, TId>, ?, ?, ?> restActions) {
        super(restActions);
    }

    public static <TEntity, TId> FindActionConfigurer<TEntity, TId> mockedFindAction(RestActions<?, FindAction<TEntity, TId>, ?, ?, ?> restActions) {
        return new FindActionConfigurer<>(restActions);
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
        return spy(restActions.find());
    }

    @Override
    protected void mockRestActions(RestActions<?, FindAction<TEntity, TId>, ?, ?, ?> restActions, FindAction<TEntity, TId> action) {
        doReturn(action).when(restActions).find();
    }
}
