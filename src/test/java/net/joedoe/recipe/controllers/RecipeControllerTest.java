package net.joedoe.recipe.controllers;

import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.services.CategoryService;
import net.joedoe.recipe.services.IRecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    private IRecipeService recipeService;
    @Mock
    private CategoryService categoryService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RecipeController controller = new RecipeController(recipeService, categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testNewRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void testShowRecipe() throws Exception {
        //given
        Long id = 1L;
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(id);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipe);

        //then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
        assertEquals(id, recipe.getId());
    }

    @Test
    public void testGetUpdateViewRecipe() throws Exception {
        //given
        Long id = 2L;
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //then
        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"));
        assertEquals(id, command.getId());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteById(anyLong());
    }

    @Test
    public void testSaveOrUpdateRecipe() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        //when
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "").param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }
}
