package ch.puzzle.libpuzzle.modelmapper.jpa;

import org.modelmapper.Converter;
import org.modelmapper.Provider;
import org.modelmapper.spi.MappingContext;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListOfReferencesProvider<TDto, TEntity, TCollection extends Collection<TEntity>> implements Converter<Collection<TDto>, TCollection>, Provider<Collection<TEntity>> {

    private final ReferenceProvider<TDto, TEntity> referenceProvider;
    private final Supplier<TCollection> emptyCollectionSupplier;

    private final Collector<TEntity, ?, TCollection> collector;

    public ListOfReferencesProvider(
            ReferenceProvider<TDto, TEntity> referenceProvider,
            Collector<TEntity, ?, TCollection> collector,
            Supplier<TCollection> emptyCollectionSupplier
    ) {
        this.referenceProvider = referenceProvider;
        this.collector = collector;
        this.emptyCollectionSupplier = emptyCollectionSupplier;
    }

    @Override
    public TCollection get(ProvisionRequest<Collection<TEntity>> request) {
        var source = request.getSource();
        if (!(source instanceof Collection)) {
            throw new IllegalArgumentException(String.format(
                    "Trying to provide Collection<TEntity> out of '%s'",
                    source.getClass()
            ));
        }
        return get((Collection<TDto>) source);

    }

    // Since ModelMapper 2.3, a converter must be used as provided objects get always mapped automatically
    @Override
    public TCollection convert(MappingContext<Collection<TDto>, TCollection> context) {
        return get(context.getSource());
    }

    private TCollection get(Collection<TDto> source) {
        if (source == null) {
            return emptyCollectionSupplier.get();
        }
        return source.stream()
                .map(referenceProvider::get)
                .collect(collector);
    }
}

