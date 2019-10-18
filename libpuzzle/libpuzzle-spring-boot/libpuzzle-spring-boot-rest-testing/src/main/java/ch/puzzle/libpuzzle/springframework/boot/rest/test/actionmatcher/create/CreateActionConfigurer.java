package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class CreateActionConfigurer extends CrudActionConfigurer<CreateActionBuilder<?, ?, ?>> {

    private CreateActionConfigurer(CrudActions<?, ?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static CreateActionConfigurer mockedCreateAction(CrudActions<?, ?, ?, ?> crudActions) {
        return new CreateActionConfigurer(crudActions);
    }

    @Override
    protected CreateActionBuilder<?, ?, ?> createActionBuilderMock() {
        var action = (CreateActionBuilder<?, ?, ?>) mock(CreateActionBuilder.class);
        doReturn(action).when(action).using(any());
        doReturn(action).when(action).with(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected CreateActionBuilder<?, ?, ?> createActionBuilderSpy() {
        return spy(crudActions.create());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, ?, ?, ?> crudActions, CreateActionBuilder<?, ?, ?> action) {
        doReturn(action).when(crudActions).create();
    }
}
