package ch.puzzle.libpuzzle.springframework.boot.rest.mapping;

public abstract class DelegatingResponseFactory<I, T, O, P> implements ResponseFactory<I, O, P> {

    protected final ResponseFactory<I, T, P> responseFactory;

    protected DelegatingResponseFactory(final ResponseFactory<I, T, P> responseFactory) {
        this.responseFactory = responseFactory;
    }

    @Override
    public O create(final I input, final P actionParameters, final ActionContext actionContext) {
        return create(responseFactory.create(input, actionParameters, actionContext));
    }

    abstract protected O create(T input);
}
