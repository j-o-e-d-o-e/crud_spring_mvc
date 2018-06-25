package net.joedoe.recipe.converters;

import net.joedoe.recipe.commands.CategoryCommand;
import net.joedoe.recipe.domains.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {
    private CategoryToCategoryCommand converter;

    @Before
    public void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void testConvert() {
        //given
        Long id = 1L;
        String description = "description";
        Category category = new Category();
        category.setId(id);
        category.setDescription(description);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertEquals(id, categoryCommand.getId());
        assertEquals(description, categoryCommand.getDescription());
    }
}