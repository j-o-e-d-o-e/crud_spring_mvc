package net.joedoe.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.services.CategoryService;
import net.joedoe.recipe.services.IRecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RecipeController {
    private IRecipeService<Recipe> recipeService;
    private CategoryService categoryService;

    public RecipeController(IRecipeService<Recipe> recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("RecipeController: newRecipe()");
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.findAll());
        return "recipe/recipe-form";
    }

    @GetMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String getUpdateViewRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        model.addAttribute("categories", categoryService.findAll());
        return "recipe/recipe-form";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @PostMapping("/recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command) {
        log.debug("RecipeController: saveOrUpdateIngredient()");
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
