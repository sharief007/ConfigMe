package ch.jalu.configme.properties.builder;

public abstract class PropertyBuilder<T> {

    private String path;

    private T defaultValue;

    public PropertyBuilder<T> defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    protected T getDefaultValue() {
        return defaultValue;
    }
}
