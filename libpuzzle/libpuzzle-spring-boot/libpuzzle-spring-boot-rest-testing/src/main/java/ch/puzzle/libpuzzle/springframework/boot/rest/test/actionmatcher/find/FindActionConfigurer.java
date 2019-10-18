package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.CrudActionConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class FindActionConfigurer extends CrudActionConfigurer<FindActionBuilder<?, ?>> {

    private FindActionConfigurer(CrudActions<?, ?, ?, ?> crudActions) {
        super(crudActions);
    }

    public static FindActionConfigurer mockedFindAction(CrudActions<?, ?, ?, ?> crudActions) {
        return new FindActionConfigurer(crudActions);
    }

    @Override
    protected FindActionBuilder<?, ?> createActionBuilderMock() {
        var action = (FindActionBuilder<?, ?>) mock(FindActionBuilder.class);
        doReturn(action).when(action).by(any());
        doReturn(null).when(action).execute(any());
        return action;
    }

    @Override
    protected FindActionBuilder<?, ?> createActionBuilderSpy() {
        return spy(crudActions.find());
    }

    @Override
    protected void mockCrudActions(final CrudActions<?, ?, ?, ?> crudActions, final FindActionBuilder<?, ?> action) {
        doReturn(action).when(crudActions).find();
    }
}
