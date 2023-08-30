package ch.jalu.configme.properties.builder;

import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.SetProperty;
import ch.jalu.configme.properties.types.EnumPropertyType;
import ch.jalu.configme.properties.types.NumberType;
import ch.jalu.configme.samples.TestEnum;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static ch.jalu.configme.TestUtils.getExceptionTypeForNullArg;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link CollectionPropertyBuilder}.
 */
class CollectionPropertyBuilderTest {

    @Test
    void shouldCreateListProperty() {
        // given / when
        Property<List<TestEnum>> property = CollectionPropertyBuilder.listBuilder(EnumPropertyType.of(TestEnum.class))
            .path("enum.path")
            .defaultValue(TestEnum.FOURTH, TestEnum.SECOND)
            .build();

        // then
        assertThat(property, instanceOf(ListProperty.class));
        assertThat(property.getPath(), equalTo("enum.path"));
        assertThat(property.getDefaultValue(), contains(TestEnum.FOURTH, TestEnum.SECOND));
    }

    @Test
    void shouldCreateSetProperty() {
        // given / when
        Property<Set<Integer>> property = CollectionPropertyBuilder.setBuilder(NumberType.INTEGER)
            .path("path.to.set")
            .defaultValue(1, 4, 2, 5)
            .build();

        // then
        assertThat(property, instanceOf(SetProperty.class));
        assertThat(property.getPath(), equalTo("path.to.set"));
        assertThat(property.getDefaultValue(), contains(1, 4, 2, 5));
    }

    @Test
    @Disabled // TODO: Throw, or allow for empty list?
    void shouldThrowForMissingDefaultValue() {
        // given / when / then
        assertThrows(getExceptionTypeForNullArg(),
            () -> CollectionPropertyBuilder.listBuilder(NumberType.DOUBLE)
                .path("defined.path")
                .build());
    }
}
