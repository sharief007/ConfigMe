package ch.jalu.configme.properties.types;

import ch.jalu.configme.properties.ArrayProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.builder.ArrayPropertyBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

class ArrayPropertyTypeTest {

    @Test
    void shouldCreateArrayProperty() {
        // given / when
        Property<Long[]> property = ArrayPropertyBuilder.arrayBuilder(NumberType.LONG, Long[]::new)
            .path("given.path")
            .defaultValue(5L, 11L, 23L)
            .build();

        // then
        assertThat(property, instanceOf(ArrayProperty.class));
        assertThat(property.getPath(), equalTo("given.path"));
        assertThat(property.getDefaultValue(), arrayContaining(5L, 11L, 23L));
    }

}
