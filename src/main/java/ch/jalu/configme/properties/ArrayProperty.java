package ch.jalu.configme.properties;

import ch.jalu.configme.properties.types.ArrayPropertyType;
import ch.jalu.configme.properties.types.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public class ArrayProperty<T> extends TypeBasedProperty<T[]> {

    /**
     * Constructor.
     *
     * @param path the path of the property
     * @param defaultValue the default value of the property
     * @param type the property type
     * @param arrayProducer array constructor (desired array size as argument)
     */
    public ArrayProperty(@NotNull String path, T @NotNull [] defaultValue, @NotNull PropertyType<T> type,
                         @NotNull IntFunction<T[]> arrayProducer) {
        this(path, new ArrayPropertyType<T>(type, arrayProducer), defaultValue);
    }

    public ArrayProperty(@NotNull String path, @NotNull PropertyType<T[]> type, T @NotNull ... defaultValue) {
        super(path, defaultValue, type);
    }
}
