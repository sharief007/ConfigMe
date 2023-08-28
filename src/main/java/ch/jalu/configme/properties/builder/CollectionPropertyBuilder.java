package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.SetProperty;
import ch.jalu.configme.properties.types.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class CollectionPropertyBuilder<E, C extends Collection<E>, P extends Property<C>> {

    private String path;
    private final C defaultValue;
    private final BiFunction<String, C, P> createPropertyFunction;

    public CollectionPropertyBuilder(@NotNull BiFunction<String, C, P> createPropertyFunction,
                                     @NotNull C defaultValue) {
        this.createPropertyFunction = createPropertyFunction;
        this.defaultValue = defaultValue;
    }

    public static <E> @NotNull CollectionPropertyBuilder<E, List<E>, ListProperty<E>> listBuilder(
                                                                                   @NotNull PropertyType<E> entryType) {
        return new CollectionPropertyBuilder<>(
            (path, defVal) -> new ListProperty<>(path, entryType, defVal),
            new ArrayList<>());
    }

    public static <E> @NotNull CollectionPropertyBuilder<E, Set<E>, SetProperty<E>> setBuilder(
                                                                                   @NotNull PropertyType<E> entryType) {
        return new CollectionPropertyBuilder<>(
            (path, defVal) -> new SetProperty<>(path, entryType, defVal),
            new HashSet<>());
    }

    public static <E, C extends Collection<E>, P extends Property<C>>
         @NotNull CollectionPropertyBuilder<E, C, P> collectionBuilder(@NotNull BiFunction<String, C, P> createFunction,
                                                                       @NotNull C defaultValue) {
        return new CollectionPropertyBuilder<>(createFunction, defaultValue);
    }

    public @NotNull CollectionPropertyBuilder<E, C, P> path(@NotNull String path) {
        this.path = path;
        return this;
    }

    public @NotNull CollectionPropertyBuilder<E, C, P> addToDefaultValue(@NotNull E entry) {
        defaultValue.add(entry);
        return this;
    }

    public @NotNull P build() {
        return createPropertyFunction.apply(path, defaultValue);
    }
}
