package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.converters.RecipeToRecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
    private static final String NEW_DESCRIPTION = "New Description";
    @Autowired
    private RecipeService service;
    @Autowired
    private RecipeRepository repository;
    @Autowired
    private RecipeToRecipeCommand converter;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = repository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        testRecipe.setDescription(NEW_DESCRIPTION);

        //when: user input via form with command obj as input for service to save
        RecipeCommand savedRecipeCommand = service.saveRecipeCommand(converter.convert(testRecipe));

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
