package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ActionParameter<TParameter> {

    private final Supplier<TParameter> parameterSupplier;

    public static <TParameter> ActionParameter<TParameter> empty(Class<?> builderClass, String paramMethod) {
        assert Arrays.stream(builderClass.getMethods())
                .anyMatch(method -> paramMethod.equals(method.getName())): "Method does not exist";
        return new ActionParameter<>(() -> {
            throw new ParameterNotSetException(builderClass, paramMethod);
        });
    }

    public static <TParameter> ActionParameter<TParameter> holding(TParameter parameter) {
        return new ActionParameter<>(() -> parameter);
    }

    public final TParameter orDefault(TParameter defaultValue) {
        try {
            return get();
        } catch (ParameterNotSetException e) {
            return defaultValue;
        }
    }

    public final TParameter get() {
        return parameterSupplier.get();
    }

}
