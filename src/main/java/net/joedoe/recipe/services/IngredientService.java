package net.joedoe.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.IngredientCommand;
import net.joedoe.recipe.converters.IngredientCommandToIngredient;
import net.joedoe.recipe.converters.IngredientToIngredientCommand;
import net.joedoe.recipe.domains.Ingredient;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.RecipeRepository;
import net.joedoe.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientService implements IIngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientService(IngredientToIngredientCommand ingredientToIngredientCommand,
                             IngredientCommandToIngredient ingredientCommandToIngredient,
                             RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }
        Recipe recipe = recipeOptional.orElse(null);
        assert recipe != null;
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }
        return ingredientCommandOptional.orElse(null);
    }

    @Override
    @Transactional
    public IngredientCommand saveOrUpdateIngredient(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }
            //todo check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        log.debug("Deleting ingredient: " + recipeId + ":" + ingredientId);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
    }
}