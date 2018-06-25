package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.IngredientCommand;
import net.joedoe.recipe.converters.IngredientCommandToIngredient;
import net.joedoe.recipe.converters.IngredientToIngredientCommand;
import net.joedoe.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import net.joedoe.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.joedoe.recipe.domains.Ingredient;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.RecipeRepository;
import net.joedoe.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private IngredientService ingredientService;

    public IngredientServiceTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientService(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void testFindIngredient() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        recipe.addIngredient(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);
        recipe.addIngredient(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        IngredientCommand ingredientCommand = ingredientService.findIngredient(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveOrUpdate() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        IngredientCommand savedCommand = ingredientService.saveOrUpdateIngredient(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDelete() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteIngredient(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}