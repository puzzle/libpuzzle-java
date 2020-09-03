package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionExecution;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ListActionConfigurer extends CrudActionConfigurer<ListActionExecution<?, ?>> {

    private ListActionConfigurer(CrudActions<?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static ListActionConfigurer mockedListAction(CrudActions<?, ?, ?> crudActions) {
        return new ListActionConfigurer(crudActions);
    }

    @Override
    protected ListActionExecution<?, ?> createActionBuilderMock() {
        var action = (ListActionExecution<?, ?>) mock(ListActionExecution.class);
        doReturn(action).when(action).skip(anyInt());
        doReturn(action).when(action).limit(anyInt());
        doReturn(action).when(action).matching(any());
        doReturn(null).when(action).execute(any(Class.class)); // FIXME: Handle ResponseFactory
        return action;
    }

    @Override
    protected ListActionExecution<?, ?> createActionBuilderSpy() {
        return spy(crudActions.list());
    }

    @Override
    protected void mockCrudActions(final CrudActions<?, ?, ?> crudActions, final ListActionExecution<?, ?> action) {
        doReturn(action).when(crudActions).list();
    }
}
