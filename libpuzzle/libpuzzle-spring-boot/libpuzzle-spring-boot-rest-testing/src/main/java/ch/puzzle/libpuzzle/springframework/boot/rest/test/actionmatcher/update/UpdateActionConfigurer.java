package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ResponseFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class UpdateActionConfigurer extends CrudActionConfigurer<UpdateActionBuilder<?, ?, ?>> {

    private UpdateActionConfigurer(CrudActions<?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static UpdateActionConfigurer mockedUpdateAction(CrudActions<?, ?, ?> crudActions) {
        return new UpdateActionConfigurer(crudActions);
    }

    @Override
    protected UpdateActionBuilder<?, ?, ?> createActionBuilderMock() {
        var action = (UpdateActionBuilder<?, ?, ?>) mock(UpdateActionBuilder.class);
        doReturn(action).when(action).by(any());
        doReturn(action).when(action).with(any());
        doReturn(null).when(action).execute(any(Class.class));
        doReturn(null).when(action).execute(any(ResponseFactory.class));
        return action;
    }

    @Override
    protected UpdateActionBuilder<?, ?, ?> createActionBuilderSpy() {
        return spy(crudActions.update());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, ?, ?> crudActions, UpdateActionBuilder<?, ?, ?> action) {
        doReturn(action).when(crudActions).update();
    }
}
