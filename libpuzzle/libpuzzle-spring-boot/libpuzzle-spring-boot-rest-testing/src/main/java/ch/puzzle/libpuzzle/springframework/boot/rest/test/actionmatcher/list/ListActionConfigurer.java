package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ListActionConfigurer extends CrudActionConfigurer<ListActionBuilder<?, ?>> {

    private ListActionConfigurer(CrudActions<?, ?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static ListActionConfigurer mockedListAction(CrudActions<?, ?, ?, ?> crudActions) {
        return new ListActionConfigurer(crudActions);
    }

    @Override
    protected ListActionBuilder<?, ?> createActionBuilderMock() {
        var action = (ListActionBuilder<?, ?>) mock(ListActionBuilder.class);
        doReturn(action).when(action).skip(anyInt());
        doReturn(action).when(action).limit(anyInt());
        doReturn(action).when(action).matching(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected ListActionBuilder<?, ?> createActionBuilderSpy() {
        return spy(crudActions.list());
    }

    @Override
    protected void mockCrudActions(final CrudActions<?, ?, ?, ?> crudActions, final ListActionBuilder<?, ?> action) {
        doReturn(action).when(crudActions).list();
    }
}
