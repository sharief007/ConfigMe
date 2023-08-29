package ch.jalu.configme.properties.types;

import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EnumSetPropertyType<E extends Enum<E>> extends CollectionPropertyType<E, EnumSet<E>> {

    public EnumSetPropertyType(@NotNull EnumPropertyType<E> entryType) {
        super(entryType);
    }

    @Override
    public @NotNull EnumPropertyType<E> getEntryType() {
        return (EnumPropertyType<E>) super.getEntryType();
    }

    @Override
    protected @NotNull Collector<E, ?, EnumSet<E>> resultCollector() {
        Class<E> enumType = getEntryType().getEnumType();
        return Collectors.toCollection(() -> EnumSet.noneOf(enumType));
    }
}
