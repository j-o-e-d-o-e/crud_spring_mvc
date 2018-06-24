package net.joedoe.recipe.domains;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
    private Category category;

    @Before
    public void setUp(){
        category = new Category();
    }
    @Test
    public void getId() {
        Long id = 4l;
        category.setId(id);
        assertEquals(id, category.getId());
    }
}