package net.joedoe.recipe.converters;

import net.joedoe.recipe.commands.UnitOfMeasureCommand;
import net.joedoe.recipe.domains.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        Long id = 1L;
        String description = "description";
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(id);
        uom.setDescription(description);

        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(id, uomc.getId());
        assertEquals(description, uomc.getDescription());
    }
}