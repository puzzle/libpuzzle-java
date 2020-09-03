package ch.puzzle.libpuzzle.springframework.boot.rest.mapping;

public class MappingResponseFactory<I, O, P> implements ResponseFactory<I, O, P> {

    private final Class<O> outputClass;

    public MappingResponseFactory(final Class<O> outputClass) {
        this.outputClass = outputClass;
    }

    public static <I, O, P> MappingResponseFactory<I, O, P> mapTo(Class<O> outputClass){
        return new MappingResponseFactory<>(outputClass);
    }

    @Override
    public O create(final I input, final P actionParameters, final ActionContext actionContext) {
        return actionContext.getMapper().map(input, outputClass);
    }
}
