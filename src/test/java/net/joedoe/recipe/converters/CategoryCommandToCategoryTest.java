package net.joedoe.recipe.converters;

import net.joedoe.recipe.commands.CategoryCommand;
import net.joedoe.recipe.domains.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {
    private CategoryCommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void testConvert() {
        //given
        Long id = 1L;
        String description = "description";
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(id);
        categoryCommand.setDescription(description);

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(id, category.getId());
        assertEquals(description, category.getDescription());
    }
}