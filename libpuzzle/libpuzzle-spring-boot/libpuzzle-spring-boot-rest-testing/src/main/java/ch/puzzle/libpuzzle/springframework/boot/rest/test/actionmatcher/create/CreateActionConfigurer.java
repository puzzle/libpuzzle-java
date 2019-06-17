package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.RestActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CreateActionConfigurer<TEntity> extends RestActionConfigurer<RestActions<?, ?, CreateAction<TEntity>, ?, ?>, CreateAction<TEntity>> {

    private CreateActionConfigurer(RestActions<?, ?, CreateAction<TEntity>, ?, ?> restActions) {
        super(restActions);
    }

    public static <TEntity> CreateActionConfigurer<TEntity> mockedCreateAction(RestActions<?, ?, CreateAction<TEntity>, ?, ?> restActions) {
        return new CreateActionConfigurer<>(restActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CreateAction<TEntity> createActionMock() {
        var action = (CreateAction<TEntity>) mock(CreateAction.class);
        doReturn(action).when(action).with(any());
        doReturn(action).when(action).from(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected CreateAction<TEntity> createActionSpy() {
        return spy(restActions.create());
    }

    @Override
    protected void mockRestActions(RestActions<?, ?, CreateAction<TEntity>, ?, ?> restActions, CreateAction<TEntity> action) {
        doReturn(action).when(restActions).create();
    }
}
