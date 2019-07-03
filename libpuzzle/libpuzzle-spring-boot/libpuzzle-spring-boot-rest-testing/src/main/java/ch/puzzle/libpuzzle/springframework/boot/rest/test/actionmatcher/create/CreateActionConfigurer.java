package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CreateActionConfigurer<TEntity> extends CrudActionConfigurer<CrudActions<?, ?, CreateAction<TEntity>, ?, ?>, CreateAction<TEntity>> {

    private CreateActionConfigurer(CrudActions<?, ?, CreateAction<TEntity>, ?, ?> crudActions) {
        super(crudActions);
    }

    public static <TEntity> CreateActionConfigurer<TEntity> mockedCreateAction(CrudActions<?, ?, CreateAction<TEntity>, ?, ?> crudActions) {
        return new CreateActionConfigurer<>(crudActions);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CreateAction<TEntity> createActionMock() {
        var action = (CreateAction<TEntity>) mock(CreateAction.class);
        doReturn(action).when(action).using(any());
        doReturn(action).when(action).with(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected CreateAction<TEntity> createActionSpy() {
        return spy(crudActions.create());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, ?, CreateAction<TEntity>, ?, ?> crudActions, CreateAction<TEntity> action) {
        doReturn(action).when(crudActions).create();
    }
}
