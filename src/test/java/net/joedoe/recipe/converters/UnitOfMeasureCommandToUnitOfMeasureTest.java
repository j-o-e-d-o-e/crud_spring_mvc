package net.joedoe.recipe.converters;

import net.joedoe.recipe.commands.UnitOfMeasureCommand;
import net.joedoe.recipe.domains.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        Long id = 1L;
        String description = "description";
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(id);
        command.setDescription(description);

        //when
        UnitOfMeasure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(id, uom.getId());
        assertEquals(description, uom.getDescription());
    }
}