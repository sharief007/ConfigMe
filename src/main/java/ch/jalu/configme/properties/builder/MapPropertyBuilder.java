package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.MapProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.types.PropertyType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MapPropertyBuilder<V, M extends Map<String, V>, P extends Property<M>> {

    private String path;
    private final M defaultValue;
    private final BiFunction<String, M, P> createPropertyFunction;

    public MapPropertyBuilder(BiFunction<String, M, P> createPropertyFunction, M defaultValue) {
        this.createPropertyFunction = createPropertyFunction;
        this.defaultValue = defaultValue;
    }

    public static <V> MapPropertyBuilder<V, Map<String, V>, MapProperty<V>> listBuilder(PropertyType<V> entryType) {
        return new MapPropertyBuilder<>(
            (path, defVal) -> new MapProperty<>(path, defVal, entryType),
            new LinkedHashMap<>());
    }

    public static <V, M extends Map<String, V>, P extends Property<M>> MapPropertyBuilder<V, M, P> collectionBuilder(
                                                                                BiFunction<String, M, P> createFunction,
                                                                                M defaultValue) {
        return new MapPropertyBuilder<>(createFunction, defaultValue);
    }

    public MapPropertyBuilder<V, M, P> path(String path) {
        this.path = path;
        return this;
    }

    public MapPropertyBuilder<V, M, P> addToDefaultValue(String key, V value) {
        defaultValue.put(key, value);
        return this;
    }

    public P build() {
        return createPropertyFunction.apply(path, defaultValue);
    }
}
