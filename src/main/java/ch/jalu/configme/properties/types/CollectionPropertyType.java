package ch.jalu.configme.properties.types;

import ch.jalu.configme.properties.convertresult.ConvertErrorRecorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class CollectionPropertyType<E, C extends Collection<E>> implements PropertyType<C> {

    private final PropertyType<E> entryType;

    public CollectionPropertyType(@NotNull PropertyType<E> entryType) {
        this.entryType = entryType;
    }

    @Override
    public @Nullable C convert(@Nullable Object object, @NotNull ConvertErrorRecorder errorRecorder) {
        if (object instanceof Collection<?>) {
            Collection<?> coll = (Collection<?>) object;
            return coll.stream()
                .map(elem -> entryType.convert(elem, errorRecorder))
                .filter(Objects::nonNull)
                .collect(resultCollector());
        }
        return null;
    }

    @Override
    public @Nullable Object toExportValue(@NotNull C value) {
        return value.stream()
            .map(entryType::toExportValue)
            .collect(Collectors.toList());
    }

    public @NotNull PropertyType<E> getEntryType() {
        return entryType;
    }

    protected abstract @NotNull Collector<E, ?, C> resultCollector();

}
