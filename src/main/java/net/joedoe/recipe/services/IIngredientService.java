package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.IngredientCommand;

public interface IIngredientService {

    IngredientCommand findIngredient(Long recipeId, Long ingredientId);

    IngredientCommand saveOrUpdateIngredient(IngredientCommand command);

    void deleteIngredient(Long recipeId, Long idToDelete);
}
