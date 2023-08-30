package ch.jalu.configme.properties.types;

import ch.jalu.configme.properties.convertresult.ConvertErrorRecorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Array property type that stores its values as a string with a separator in the property resource.
 *
 * @param <T> the array type
 */
public class InlineArrayPropertyType<T> implements PropertyType<T[]> {

    public static final InlineArrayPropertyType<Boolean> BOOLEAN =
        new InlineArrayPropertyType<>(BooleanType.BOOLEAN, ",", true, Boolean[]::new);

    public static final InlineArrayPropertyType<Byte> BYTE =
        new InlineArrayPropertyType<>(NumberType.BYTE, ",", true, Byte[]::new);

    public static final InlineArrayPropertyType<Short> SHORT =
        new InlineArrayPropertyType<>(NumberType.SHORT, ",", true, Short[]::new);

    public static final InlineArrayPropertyType<Integer> INTEGER =
        new InlineArrayPropertyType<>(NumberType.INTEGER, ",", true, Integer[]::new);

    public static final InlineArrayPropertyType<Long> LONG =
        new InlineArrayPropertyType<>(NumberType.LONG, ",", true, Long[]::new);

    public static final InlineArrayPropertyType<Float> FLOAT =
        new InlineArrayPropertyType<>(NumberType.FLOAT, ",", true, Float[]::new);

    public static final InlineArrayPropertyType<Double> DOUBLE =
        new InlineArrayPropertyType<>(NumberType.DOUBLE, ",", true, Double[]::new);

    public static final InlineArrayPropertyType<String> STRING =
        new InlineArrayPropertyType<>(StringType.STRING, "\n", false, String[]::new);


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
                .map(entry -> convertOrRegisterError(entry, errorRecorder))
                .filter(Objects::nonNull)
                .toArray(arrayProducer);
        }
        return null;
    }

    protected @Nullable T convertOrRegisterError(@NotNull String entry, @NotNull ConvertErrorRecorder errorRecorder) {
        T value = useTrimAndSpaces
            ? entryType.convert(entry.trim(), errorRecorder)
            : entryType.convert(entry, errorRecorder);
        if (value == null) {
            errorRecorder.setHasError("Could not convert '" + entry + "'");
        }
        return value;
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
