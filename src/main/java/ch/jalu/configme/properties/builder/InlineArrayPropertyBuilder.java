package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.InlineArrayProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.inlinearray.InlineArrayConverter;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class InlineArrayPropertyBuilder<E, P extends Property<E[]>> {

    private String path;
    private E[] defaultValue;
    private final BiFunction<String, E[], P> createPropertyFunction;


    public InlineArrayPropertyBuilder(@NotNull BiFunction<String, E[], P> createPropertyFunction) {
        this.createPropertyFunction = createPropertyFunction;
    }

    public static <E> @NotNull InlineArrayPropertyBuilder<E, InlineArrayProperty<E>> inlineArrayBuilder(
                                                                    @NotNull InlineArrayConverter<E> arrayElementType) {
        return new InlineArrayPropertyBuilder<>(
            (path, defVal) -> new InlineArrayProperty<>(path, defVal, arrayElementType));
    }

    public @NotNull InlineArrayPropertyBuilder<E, P> path(@NotNull String path) {
        this.path = path;
        return this;
    }

    @SafeVarargs
    public final @NotNull InlineArrayPropertyBuilder<E, P> defaultValue(@NotNull E @NotNull ... defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public @NotNull P build() {
        return createPropertyFunction.apply(path, defaultValue);
    }
}
