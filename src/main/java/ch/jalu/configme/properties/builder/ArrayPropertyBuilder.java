package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.ArrayProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.types.ArrayPropertyType;
import ch.jalu.configme.properties.types.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class ArrayPropertyBuilder<E, P extends Property<E[]>> {

    private String path;
    private final List<E> defaultValue = new ArrayList<>();
    private final BiFunction<String, E[], P> createPropertyFunction;
    private final IntFunction<E[]> arrayProducer;

    public ArrayPropertyBuilder(@NotNull BiFunction<String, E[], P> createPropertyFunction,
                                @NotNull IntFunction<E[]> arrayProducer) {
        this.createPropertyFunction = createPropertyFunction;
        this.arrayProducer = arrayProducer;
    }

    public static <E> @NotNull ArrayPropertyBuilder<E, ArrayProperty<E>> arrayBuilder(
                                                                              @NotNull PropertyType<E> entryType,
                                                                              @NotNull IntFunction<E[]> arrayProducer) {
        return new ArrayPropertyBuilder<>(
            (path, defVal) -> new ArrayProperty<>(path, defVal, entryType, arrayProducer),
            arrayProducer);
    }

    public static <E> @NotNull ArrayPropertyBuilder<E, ArrayProperty<E>> arrayBuilder(
                                                                              @NotNull ArrayPropertyType<E> arrayType) {
        return new ArrayPropertyBuilder<>(
            (path, defVal) -> new ArrayProperty<>(path, arrayType, defVal),
            arrayType.getArrayProducer());
    }

    public @NotNull ArrayPropertyBuilder<E, P> path(@NotNull String path) {
        this.path = path;
        return this;
    }

    @SafeVarargs
    public final @NotNull ArrayPropertyBuilder<E, P> defaultValue(@NotNull E @NotNull ... entries) {
        defaultValue.clear();
        defaultValue.addAll(Arrays.asList(entries));
        return this;
    }

    public @NotNull ArrayPropertyBuilder<E, P> addToDefaultValue(@NotNull E entry) {
        defaultValue.add(entry);
        return this;
    }

    public @NotNull P build() {
        PropertyBuilderUtils.requireNonNullPath(path);
        E[] defaultValueArray = defaultValue.stream().toArray(arrayProducer);
        return createPropertyFunction.apply(path, defaultValueArray);
    }
}
