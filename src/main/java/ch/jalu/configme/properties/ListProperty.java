package ch.jalu.configme.properties;

import ch.jalu.configme.properties.types.ListPropertyType;
import ch.jalu.configme.properties.types.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * List property of a configurable type. The lists are immutable. TODO "immutable" comment not really true
 *
 * @param <E> the entry type
 */
public class ListProperty<E> extends TypeBasedProperty<List<E>> {

    /**
     * Constructor.
     *
     * @param path the path of the property
     * @param type the property type
     * @param defaultValue the entries in the list of the default value
     */
    @SafeVarargs
    public ListProperty(@NotNull String path, @NotNull PropertyType<E> type, @NotNull E @NotNull ... defaultValue) {
        this(path, type, Arrays.asList(defaultValue));
    }

    /**
     * Constructor.
     *
     * @param path the path of the property
     * @param type the entry type
     * @param defaultValue the default value of the property
     */
    public ListProperty(@NotNull String path, @NotNull PropertyType<E> type, @NotNull List<E> defaultValue) {
        super(path, Collections.unmodifiableList(defaultValue), new ListPropertyType<>(type));
    }

    /**
     * Constructor.
     *
     * @param path the path of the property
     * @param type the entry type
     * @param defaultValue the default value of the property
     */
    public ListProperty(@NotNull String path, @NotNull ListPropertyType<E> type, @NotNull List<E> defaultValue) {
        super(path, Collections.unmodifiableList(defaultValue), type);
    }
}
