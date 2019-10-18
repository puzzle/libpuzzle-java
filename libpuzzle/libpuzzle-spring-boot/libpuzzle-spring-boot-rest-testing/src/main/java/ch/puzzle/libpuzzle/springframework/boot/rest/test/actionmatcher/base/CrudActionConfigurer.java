package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.reset;

public abstract class CrudActionConfigurer<TActionBuilder> implements MockMvcConfigurer {

    protected final CrudActions<?, ?, ?, ?> crudActions;

    private final TActionBuilder actionBuilder;

    protected CrudActionConfigurer(CrudActions<?, ?, ?, ?> crudActions) {
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

    private TActionBuilder createActionBuilder(CrudActions<?, ?, ?, ?> crudActions) {
        return mockingDetails(crudActions).isSpy() ? createActionBuilderSpy(crudActions) : createActionBuilderMock(crudActions);
    }

    private TActionBuilder createActionBuilderSpy(CrudActions<?, ?, ?, ?> crudActions) {
        var action = createActionBuilderSpy();
        reset(crudActions);
        return action;
    }

    private TActionBuilder createActionBuilderMock(CrudActions<?, ?, ?, ?> crudActions) {
        var action = createActionBuilderMock();
        reset(crudActions);
        return action;
    }

    abstract protected TActionBuilder createActionBuilderMock();

    abstract protected TActionBuilder createActionBuilderSpy();

    abstract protected void mockCrudActions(CrudActions<?, ?, ?, ?> crudActions, TActionBuilder action);
}
