package ch.jalu.configme.properties.types;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SetPropertyType<E> extends CollectionPropertyType<E, Set<E>> {

    public SetPropertyType(@NotNull PropertyType<E> entryType) {
        super(entryType);
    }

    @Override
    protected @NotNull Collector<E, ?, Set<E>> resultCollector() {
        return Collectors.toCollection(LinkedHashSet::new);
    }
}
