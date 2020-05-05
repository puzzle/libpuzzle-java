//package ch.puzzle.libpuzzle.springframework.boot.rest.newaction.create;
//
//import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
//import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.With;
//import org.springframework.http.ResponseEntity;
//
//@RequiredArgsConstructor
//public final class DtoCreateActionProcessor<TEntity, TDto> implements
//        CreateActionBuilder<TEntity, TDto>, CreateActionParameters<TEntity> {
//
//    DtoMapper mapper;
//
//    @With(AccessLevel.PRIVATE)
//    private final ActionParameter<TEntity> entity;
//
//    private final ActionParameter<TDto> requestDto;
//
//    private final CreateAction<TEntity> action;
//
////    public DtoCreateActionProcessor(final CreateAction<TEntity> action) {
////        this(
////                ActionParameter.empty(CreateActionBuilder.class, "using"),
////                ActionParameter.empty(CreateActionBuilder.class, "with"),
////                ActionParameter.empty(CreateActionBuilder.class, "execute"),
////                action
////        );
////    }
//
//    @Override
//    public ActionParameter<TEntity> requestData() {
//        return requestDto;
//    }
//
//    @Override
//    public ActionParameter<TEntity> entity() {
//        return entity;
//    }
//
//    @Override
//    public ActionParameter<Class<TResponseDto>> responseDtoClass() {
//        return responseDtoClass;
//    }
//
//    @Override
//    public CreateActionBuilder<TEntity, TDto, TResponseDto> using(final TEntity initialEntity) {
//        return withEntity(ActionParameter.holding(initialEntity));
//    }
//
//    @Override
//    public <TNewRequestDto> CreateActionBuilder<TEntity, TNewRequestDto, TResponseDto> with(
//            final TNewRequestDto requestData
//    ) {
//        return new DtoCreateActionProcessor<>(
//                entity,
//                ActionParameter.holding(requestData),
//                responseDtoClass,
//                action
//        );
//    }
//
//    public <TNewResponseDto> ResponseEntity<TNewResponseDto> execute(final Class<TNewResponseDto> responseDtoClass) {
//        mapper.map(requestData().get(), );
//
//
//        return action.execute(new DtoCreateActionProcessor<>(
//                entity,
//                requestDto,
//                ActionParameter.holding(responseDtoClass),
//                action
//        ));
//    }
//}
