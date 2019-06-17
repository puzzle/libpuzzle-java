package ch.puzzle.libpuzzle.modelmapper.jpa;

import java.util.Collection;
import java.util.function.Consumer;

public class PersistentCollectionUtil {

    public static <TCollection extends Collection<TEntity>, TEntity> TCollection set(
            TCollection currentValues,
            TCollection newValues
    ) {
        return set(currentValues, newValues, null);
    }

    public static <TCollection extends Collection<TEntity>, TEntity> TCollection set(
            TCollection currentValues,
            TCollection newValues,
            Consumer<TEntity> elementCallback
    ) {
        if (elementCallback != null) {
            newValues.forEach(elementCallback);
        }
        if (currentValues == null) {
            return newValues;
        }
        currentValues.clear();
        currentValues.addAll(newValues);
        return currentValues;
    }
}
