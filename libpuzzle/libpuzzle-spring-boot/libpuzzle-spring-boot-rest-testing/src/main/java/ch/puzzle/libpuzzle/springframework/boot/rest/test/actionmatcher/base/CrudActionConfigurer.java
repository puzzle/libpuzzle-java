package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.reset;

public abstract class CrudActionConfigurer<TActionExecution> implements MockMvcConfigurer {

    protected final CrudActions<?, ?, ?> crudActions;

    private final TActionExecution actionBuilder;

    protected CrudActionConfigurer(CrudActions<?, ?, ?> crudActions) {
        this.crudActions = crudActions;
        this.actionBuilder = createActionBuilder(crudActions);
    }

    @Override
    public RequestPostProcessor beforeMockMvcCreated(
            ConfigurableMockMvcBuilder<?> builder, WebApplicationContext context
    ) {
        mockCrudActions(crudActions, actionBuilder);
        return (request) -> {
            request.setAttribute(getClass().getName(), actionBuilder);
            return request;
        };
    }

    private TActionExecution createActionBuilder(CrudActions<?, ?, ?> crudActions) {
        return mockingDetails(crudActions).isSpy() ? createActionBuilderSpy(crudActions) : createActionBuilderMock(crudActions);
    }

    private TActionExecution createActionBuilderSpy(CrudActions<?, ?, ?> crudActions) {
        var action = createActionBuilderSpy();
        reset(crudActions);
        return action;
    }

    private TActionExecution createActionBuilderMock(CrudActions<?, ?, ?> crudActions) {
        var action = createActionBuilderMock();
        reset(crudActions);
        return action;
    }

    abstract protected TActionExecution createActionBuilderMock();

    abstract protected TActionExecution createActionBuilderSpy();

    abstract protected void mockCrudActions(CrudActions<?, ?, ?> crudActions, TActionExecution action);
}
