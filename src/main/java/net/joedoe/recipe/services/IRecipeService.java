package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.RecipeCommand;

import java.util.Set;

public interface IRecipeService<T> {
    Set<T> findAll();

    void save(T t);

    T findById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand testRecipeCommand);

    void deleteById(Long id);
}
