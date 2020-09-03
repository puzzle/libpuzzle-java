package ch.puzzle.libpuzzle.springframework.boot.rest.mapping;

public interface ResponseFactory<I, O, P> {

    O create(I input, P actionParameters, ActionContext actionContext);



}
