package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;
import java.util.stream.Collectors;

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create.CreateActionConfigurer.mockedCreateAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete.DeleteActionConfigurer.mockedDeleteAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find.FindActionConfigurer.mockedFindAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list.ListActionConfigurer.mockedListAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update.UpdateActionConfigurer.mockedUpdateAction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompositeActionConfigurer implements MockMvcConfigurer {

    private final Set<MockMvcConfigurer> configurers;

    @Override
    public RequestPostProcessor beforeMockMvcCreated(
            ConfigurableMockMvcBuilder<?> builder, WebApplicationContext context
    ) {
        var processors = configurers.stream()
                .map(configurer -> configurer.beforeMockMvcCreated(builder, context))
                .collect(Collectors.toSet());
        return request -> {
            for (final RequestPostProcessor processor : processors) {
                request = processor.postProcessRequest(request);
            }
            return request;
        };
    }

    public static MockMvcConfigurer mockedReadActions(CrudActions<?, ?, ?, ?> crudActions) {
        return new CompositeActionConfigurer(Set.of(
                mockedFindAction(crudActions),
                mockedListAction(crudActions)
        ));
    }

    public static MockMvcConfigurer mockedWriteActions(CrudActions<?, ?, ?, ?> crudActions) {
        return new CompositeActionConfigurer(Set.of(
                mockedCreateAction(crudActions),
                mockedUpdateAction(crudActions),
                mockedDeleteAction(crudActions)
        ));
    }

    public static MockMvcConfigurer mockedResourceActions(CrudActions<?, ?, ?, ?> crudActions) {
        return new CompositeActionConfigurer(Set.of(
                mockedCreateAction(crudActions),
                mockedFindAction(crudActions),
                mockedUpdateAction(crudActions),
                mockedDeleteAction(crudActions)
        ));
    }

    public static MockMvcConfigurer mockedActions(CrudActions<?, ?, ?, ?> crudActions) {
        return new CompositeActionConfigurer(Set.of(
                mockedCreateAction(crudActions),
                mockedFindAction(crudActions),
                mockedListAction(crudActions),
                mockedUpdateAction(crudActions),
                mockedDeleteAction(crudActions)
        ));
    }
}
