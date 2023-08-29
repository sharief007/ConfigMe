package ch.jalu.configme.properties.types;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListPropertyType<E> extends CollectionPropertyType<E, List<E>> {

    public ListPropertyType(PropertyType<E> entryType) {
        super(entryType);
    }

    @Override
    protected @NotNull Collector<E, ?, List<E>> resultCollector() {
        return Collectors.toList();
    }
}
