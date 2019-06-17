package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.reset;

public abstract class RestActionConfigurer<TRestActions extends RestActions, TAction> implements MockMvcConfigurer {

    protected final TRestActions restActions;

    private final TAction action;

    protected RestActionConfigurer(TRestActions restActions) {
        this.restActions = restActions;
        this.action = createAction(restActions);
    }

    @Override
    public RequestPostProcessor beforeMockMvcCreated(
            ConfigurableMockMvcBuilder<?> builder, WebApplicationContext context
    ) {
        mockRestActions(restActions, action);
        return (request) -> {
            request.setAttribute(getClass().getName(), action);
            return request;
        };
    }

    private TAction createAction(TRestActions restActions) {
        return mockingDetails(restActions).isSpy() ? createActionSpy(restActions) : createActionMock(restActions);
    }

    private TAction createActionSpy(TRestActions restActions) {
        var action = createActionSpy();
        reset(restActions);
        return action;
    }

    private TAction createActionMock(TRestActions restActions) {
        var action = createActionMock();
        reset(restActions);
        return action;
    }

    abstract protected TAction createActionMock();

    abstract protected TAction createActionSpy();

    abstract protected void mockRestActions(TRestActions restActions, TAction action);
}
