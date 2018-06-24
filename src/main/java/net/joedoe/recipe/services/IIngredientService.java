package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.IngredientCommand;

public interface IIngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recipeId, Long idToDelete);
}
