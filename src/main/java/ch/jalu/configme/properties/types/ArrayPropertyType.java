package ch.jalu.configme.properties.types;

import ch.jalu.configme.properties.convertresult.ConvertErrorRecorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class ArrayPropertyType<T> implements PropertyType<T[]> {

    private final PropertyType<T> entryType;
    private final IntFunction<T[]> arrayProducer;

    public ArrayPropertyType(@NotNull PropertyType<T> entryType, @NotNull IntFunction<T[]> arrayProducer) {
        Objects.requireNonNull(entryType, "entryType");
        Objects.requireNonNull(arrayProducer, "arrayProducer");
        this.entryType = entryType;
        this.arrayProducer = arrayProducer;
    }

    @Override
    public T @Nullable [] convert(@Nullable Object object, @NotNull ConvertErrorRecorder errorRecorder) {
        if (object instanceof Collection<?>) {
            Collection<?> coll = (Collection<?>) object;
            return coll.stream()
                .map(entry -> entryType.convert(entry, errorRecorder))
                .filter(Objects::nonNull)
                .toArray(arrayProducer);
        }

        return null;
    }

    @Override
    public @Nullable List<?> toExportValue(T @NotNull [] value) {
        return Arrays.stream(value)
            .map(entryType::toExportValue)
            .collect(Collectors.toList());
    }
}
