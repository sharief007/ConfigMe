package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.InlineArrayProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.types.InlineArrayPropertyType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Test for {@link InlineArrayPropertyBuilder}.
 */
class InlineArrayPropertyBuilderTest {

    @Test
    void shouldCreateInlineArrayProperty() {
        // given / when
        Property<Float[]> property = InlineArrayPropertyBuilder.inlineArrayBuilder(InlineArrayPropertyType.FLOAT)
            .path("one.path")
            .defaultValue(-1.23f)
            .build();

        // then
        assertThat(property, instanceOf(InlineArrayProperty.class));
        assertThat(property.getPath(), equalTo("one.path"));
        assertThat(property.getDefaultValue(), arrayContaining(-1.23f));
    }

}
