package net.joedoe.recipe.services;

import net.joedoe.recipe.converters.RecipeCommandToRecipe;
import net.joedoe.recipe.converters.RecipeToRecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {
    private IRecipeService<Recipe> service;
    @Mock
    private RecipeRepository repository;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new RecipeService(repository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void testFindAll() {
        //given
        HashSet recipesData = new HashSet();
        recipesData.add(new Recipe());

        //when
        when(service.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = service.findAll();

        //then
        assertEquals(1, recipes.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(repository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe recipeReturned = service.findById(1L);

        //then
        assertNotNull("Null recipe returned", recipeReturned);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).findAll();
    }

    @Test
    public void testDeleteById() {
        //when
        service.deleteById(anyLong());

        //then
        verify(repository, times(1)).deleteById(anyLong());
    }
}