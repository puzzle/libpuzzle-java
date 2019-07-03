package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.reset;

public abstract class CrudActionConfigurer<TCrudActions extends CrudActions, TAction> implements MockMvcConfigurer {

    protected final TCrudActions crudActions;

    private final TAction action;

    protected CrudActionConfigurer(TCrudActions crudActions) {
        this.crudActions = crudActions;
        this.action = createAction(crudActions);
    }

    @Override
    public RequestPostProcessor beforeMockMvcCreated(
            ConfigurableMockMvcBuilder<?> builder, WebApplicationContext context
    ) {
        mockCrudActions(crudActions, action);
        return (request) -> {
            request.setAttribute(getClass().getName(), action);
            return request;
        };
    }

    private TAction createAction(TCrudActions crudActions) {
        return mockingDetails(crudActions).isSpy() ? createActionSpy(crudActions) : createActionMock(crudActions);
    }

    private TAction createActionSpy(TCrudActions crudActions) {
        var action = createActionSpy();
        reset(crudActions);
        return action;
    }

    private TAction createActionMock(TCrudActions crudActions) {
        var action = createActionMock();
        reset(crudActions);
        return action;
    }

    abstract protected TAction createActionMock();

    abstract protected TAction createActionSpy();

    abstract protected void mockCrudActions(TCrudActions crudActions, TAction action);
}
