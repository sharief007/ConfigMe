package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.MapProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.types.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MapPropertyBuilder<V, M extends Map<String, V>, P extends Property<M>> {

    private String path;
    private final M defaultValue;
    private final BiFunction<String, M, P> createPropertyFunction;

    public MapPropertyBuilder(@NotNull BiFunction<String, M, P> createPropertyFunction, @NotNull M defaultValue) {
        this.createPropertyFunction = createPropertyFunction;
        this.defaultValue = defaultValue;
    }

    public static <V> @NotNull MapPropertyBuilder<V, Map<String, V>, MapProperty<V>> mapBuilder(
                                                                                   @NotNull PropertyType<V> entryType) {
        return new MapPropertyBuilder<>(
            (path, defVal) -> new MapProperty<>(path, defVal, entryType),
            new LinkedHashMap<>());
    }

    public @NotNull MapPropertyBuilder<V, M, P> path(@NotNull String path) {
        this.path = path;
        return this;
    }

    // TODO: "add" or "put"?
    public @NotNull MapPropertyBuilder<V, M, P> addToDefaultValue(@NotNull String key, @NotNull V value) {
        defaultValue.put(key, value);
        return this;
    }

    // TODO: Here and in other builders, disallow both types of default value setting to be used in same builder?
    public @NotNull MapPropertyBuilder<V, M, P> defaultValue(@NotNull Map<String, V> defaultValue) {
        this.defaultValue.clear();
        this.defaultValue.putAll(defaultValue);
        return this;
    }

    public @NotNull P build() {
        PropertyBuilderUtils.requireNonNullPath(path);
        return createPropertyFunction.apply(path, defaultValue);
    }
}
