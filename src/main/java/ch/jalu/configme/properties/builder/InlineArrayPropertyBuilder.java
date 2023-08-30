package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.InlineArrayProperty;
import ch.jalu.configme.properties.types.InlineArrayPropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class InlineArrayPropertyBuilder<E> {

    private String path;
    private E[] defaultValue;
    private final BiFunction<String, E[], InlineArrayProperty<E>> createPropertyFunction;


    public InlineArrayPropertyBuilder(@NotNull BiFunction<String, E[], InlineArrayProperty<E>> createPropertyFunction) {
        this.createPropertyFunction = createPropertyFunction;
    }

    public static <E> @NotNull InlineArrayPropertyBuilder<E> inlineArrayBuilder(
                                                                  @NotNull InlineArrayPropertyType<E> inlineArrayType) {
        return new InlineArrayPropertyBuilder<>(
            (path, defVal) -> new InlineArrayProperty<>(path, defVal, inlineArrayType));
    }

    public @NotNull InlineArrayPropertyBuilder<E> path(@NotNull String path) {
        this.path = path;
        return this;
    }

    @SafeVarargs
    public final @NotNull InlineArrayPropertyBuilder<E> defaultValue(@NotNull E @NotNull ... defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    // TODO: Add entry to default value method?

    public @NotNull InlineArrayProperty<E> build() {
        return createPropertyFunction.apply(path, defaultValue);
    }
}
