package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.exception.InvalidMatcherConfigDetected;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ActionResult implements MvcResult {

    private final MvcResult inner;

    public ActionResult(MvcResult inner) {
        this.inner = inner;
    }

    @SuppressWarnings("unchecked")
    public <TAction> TAction action(ActionMatchers<TAction> matcher) {
        var action = getRequest().getAttribute(matcher.configurer().getName());
        if (null == action) {
            throw InvalidMatcherConfigDetected.actionNotFound(matcher.configurer());
        }
        try {
            return (TAction) action;
        } catch (ClassCastException e) {
            throw InvalidMatcherConfigDetected.actionNotSupported(action, e, matcher.configurer());
        }
    }

    @Override
    public MockHttpServletRequest getRequest() {
        return inner.getRequest();
    }

    @Override
    public MockHttpServletResponse getResponse() {
        return inner.getResponse();
    }

    @Override
    public Object getHandler() {
        return inner.getHandler();
    }

    @Override
    public HandlerInterceptor[] getInterceptors() {
        return inner.getInterceptors();
    }

    @Override
    public ModelAndView getModelAndView() {
        return inner.getModelAndView();
    }

    @Override
    public Exception getResolvedException() {
        return inner.getResolvedException();
    }

    @Override
    public FlashMap getFlashMap() {
        return inner.getFlashMap();
    }

    @Override
    public Object getAsyncResult() {
        return inner.getAsyncResult();
    }

    @Override
    public Object getAsyncResult(long timeToWait) {
        return inner.getAsyncResult(timeToWait);
    }
}
