package net.joedoe.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.converters.RecipeCommandToRecipe;
import net.joedoe.recipe.converters.RecipeToRecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeService implements IRecipeService<Recipe> {
    private final RecipeRepository repository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeService(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.repository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> findAll() {
        log.debug("RecipeRecipeService: findAll()");
        Set<Recipe> recipes = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional //makes sure hibernate still works even if it uses lazy
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void save(Recipe recipe) {
        repository.save(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe savedRecipe = repository.save(recipeCommandToRecipe.convert(command));
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
