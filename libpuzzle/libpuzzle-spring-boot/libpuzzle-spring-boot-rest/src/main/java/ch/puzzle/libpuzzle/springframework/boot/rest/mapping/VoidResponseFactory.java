package ch.puzzle.libpuzzle.springframework.boot.rest.mapping;

public class VoidResponseFactory implements ResponseFactory<Object, Void, Object> {
    @Override
    public Void create(final Object input, final Object actionParameters, final ActionContext actionContext) {
        return null;
    }
}
