package ch.jalu.configme.properties.types;

import ch.jalu.configme.properties.convertresult.ConvertErrorRecorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InlineArrayPropertyType<T> implements PropertyType<T[]> {

    private final PropertyType<T> entryType;
    private final String separator;
    private final boolean useTrimAndSpaces;
    private final IntFunction<T[]> arrayProducer;

    public InlineArrayPropertyType(@NotNull PropertyType<T> entryType, @NotNull String separator,
                                   boolean useTrimAndSpaces, @NotNull IntFunction<T[]> arrayProducer) {
        this.entryType = entryType;
        this.separator = separator;
        this.useTrimAndSpaces = useTrimAndSpaces;
        this.arrayProducer = arrayProducer;
    }

    @Override
    public T @Nullable [] convert(@Nullable Object object, @NotNull ConvertErrorRecorder errorRecorder) {
        if (object != null) {
            String strValue = (String) object;

            return Arrays.stream(strValue.split(Pattern.quote(separator)))
                .map(entry -> entryType.convert(entry, errorRecorder))
                .filter(Objects::nonNull)
                .toArray(arrayProducer);
        }
        return null;
    }

    @Override
    public @NotNull String toExportValue(T @NotNull [] value) {
        String delimiter = useTrimAndSpaces ? (separator + " ") : separator;
        return Arrays.stream(value)
            .map(entryType::toExportValue)
            .filter(Objects::nonNull)
            .map(Object::toString)
            .collect(Collectors.joining(delimiter));
    }
}
