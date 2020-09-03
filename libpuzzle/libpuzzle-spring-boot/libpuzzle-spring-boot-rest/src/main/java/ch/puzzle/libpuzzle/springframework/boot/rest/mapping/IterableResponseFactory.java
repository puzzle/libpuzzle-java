package ch.puzzle.libpuzzle.springframework.boot.rest.mapping;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IterableResponseFactory<I, O, P> implements ResponseFactory<Iterable<I>, Iterable<O>, P> {

    private final ResponseFactory<I, O, P> responseFactory;

    private IterableResponseFactory(final ResponseFactory<I, O, P> responseFactory) {
        this.responseFactory = responseFactory;
    }

    public static <I, O, P> IterableResponseFactory<I, O, P> forEach(ResponseFactory<I, O, P> responseFactory) {
        return new IterableResponseFactory<>(responseFactory);
    }

    @Override
    public Iterable<O> create(final Iterable<I> input, final P actionParameters, final ActionContext actionContext) {
        return StreamSupport.stream(input.spliterator(), true)
                .map(i -> responseFactory.create(i, actionParameters, actionContext))
                .collect(Collectors.toList());
    }
}
