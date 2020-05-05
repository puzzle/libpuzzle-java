//package ch.puzzle.libpuzzle.springframework.boot.rest.newaction.create;
//
//import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
//import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionBuilder;
//import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionParameters;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public abstract class AbstractCreateActionProcessor<TEntity, TRequestData, TProcessor extends AbstractCreateActionProcessor<TEntity, TRequestData, TProcessor>> implements CreateActionBuilder<TEntity, TRequestData> {
//
//    protected final CreateActionParameters<TEntity, TRequestData> params;
//
//    protected abstract TProcessor withParams(final CreateActionParameters<TEntity, TRequestData> params);
//
//    @Override
//    public CreateActionBuilder<TEntity, TRequestData> using(final TEntity initialEntity) {
//        return withParams(params.withEntity(ActionParameter.holding(initialEntity)));
//    }
//
//    @Override
//    public <TNewRequestData> CreateActionBuilder<TEntity, TNewRequestData> with(final TNewRequestData requestData) {
//        return withParams(params.withRequestData(ActionParameter.holding(requestData)));
//    }
//}
