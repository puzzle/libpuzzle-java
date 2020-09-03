package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class DeleteActionConfigurer extends CrudActionConfigurer<DeleteActionBuilder<?>> {

    private DeleteActionConfigurer(CrudActions<?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static DeleteActionConfigurer mockedDeleteAction(CrudActions<?, ?, ?> crudActions) {
        return new DeleteActionConfigurer(crudActions);
    }

    @Override
    protected DeleteActionBuilder<?> createActionBuilderMock() {
        var action = (DeleteActionBuilder<?>) mock(DeleteActionBuilder.class);
        doReturn(action).when(action).by(any());
        doNothing().when(action).execute();
        return action;
    }

    @Override
    protected DeleteActionBuilder<?> createActionBuilderSpy() {
        return spy(crudActions.delete());
    }

    @Override
    protected void mockCrudActions(CrudActions<?, ?, ?> crudActions, DeleteActionBuilder<?> action) {
        doReturn(action).when(crudActions).delete();
    }
}
