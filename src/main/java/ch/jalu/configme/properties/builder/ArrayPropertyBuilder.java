package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.ArrayProperty;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.SetProperty;
import ch.jalu.configme.properties.types.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class ArrayPropertyBuilder<E, P extends Property<E[]>> {

    private String path;
    private final List<E> defaultValue;
    private final BiFunction<String, E[], P> createPropertyFunction;

    public ArrayPropertyBuilder(@NotNull BiFunction<String, E[], P> createPropertyFunction,
                                @NotNull List<E> defaultValue) {
        this.createPropertyFunction = createPropertyFunction;
        this.defaultValue = defaultValue;
    }

    public static <E> @NotNull ArrayPropertyBuilder<E, ArrayProperty<E>> arrayBuilder(
                                                                               @NotNull PropertyType<E> entryType,
                                                                               @NotNull IntFunction<E[]> arrayCreator) {
        return new ArrayPropertyBuilder<>(
            (path, defVal) -> new ArrayProperty<>(path, entryType, defVal),
            new ArrayList<>());
    }

    public static <E> @NotNull ArrayPropertyBuilder<E, ArrayProperty<E>> setBuilder(
        @NotNull PropertyType<E> entryType) {
        return new ArrayPropertyBuilder<>(
            (path, defVal) -> new SetProperty<>(path, entryType, defVal),
            new HashSet<>());
    }

    public @NotNull ArrayPropertyBuilder<E, C, P> path(@NotNull String path) {
        this.path = path;
        return this;
    }

    public @NotNull ArrayPropertyBuilder<E, C, P> addToDefaultValue(@NotNull E entry) {
        defaultValue.add(entry);
        return this;
    }

    public @NotNull P build() {
        return createPropertyFunction.apply(path, defaultValue);
    }
}
