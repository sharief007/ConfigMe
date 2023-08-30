package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.MapProperty;
import ch.jalu.configme.properties.types.NumberType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link MapPropertyBuilder}.
 */
class MapPropertyBuilderTest {

    @Test
    void shouldCreateMapProperty() {
        // given / when
        MapProperty<Double> result = MapPropertyBuilder.mapBuilder(NumberType.DOUBLE)
            .path("the.path")
            .addToDefaultValue("leet", 1337.0)
            .addToDefaultValue("all", 411.411)
            .build();

        // then
        assertThat(result.getPath(), equalTo("the.path"));
        Map<String, Double> defaultValue = result.getDefaultValue();
        assertThat(defaultValue.keySet(), contains("leet", "all"));
        assertThat(defaultValue.get("leet"), equalTo(1337.0));
        assertThat(defaultValue.get("all"), equalTo(411.411));
    }

    @Test
    void shouldCreateMapPropertyWithEmptyMapAsDefault() {
        // given / when
        MapProperty<Double> result = MapPropertyBuilder.mapBuilder(NumberType.DOUBLE)
            .path("some.path")
            .build();

        // then
        assertThat(result.getDefaultValue(), anEmptyMap());
    }

    @Test
    void shouldThrowForMissingPathInMapBuilder() {
        // given / when
        Exception ex = assertThrows(IllegalStateException.class,
            () -> MapPropertyBuilder.mapBuilder(NumberType.DOUBLE).build());

        // then
        assertThat(ex.getMessage(), equalTo("The path was not defined"));
    }
}
