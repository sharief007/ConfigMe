package ch.jalu.configme.properties.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class PropertyBuilderUtils {

    private PropertyBuilderUtils() {
    }

    static <T> @NotNull T requireNonNullDefaultValue(@Nullable T defaultValue) {
        if (defaultValue == null) {
            throw new IllegalStateException("The default value was not defined");
        }
        return defaultValue;
    }

    static @NotNull String requireNonNullPath(@Nullable String path) {
        if (path == null) {
            throw new IllegalStateException("The path was not defined");
        }
        return path;
    }
}
