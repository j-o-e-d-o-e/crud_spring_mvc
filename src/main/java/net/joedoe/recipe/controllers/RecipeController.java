package net.joedoe.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.services.CategoryService;
import net.joedoe.recipe.services.IRecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {
    private IRecipeService<Recipe> recipeService;
    private CategoryService categoryService;

    private static final String RECIPE_FORM_URL = "recipe/recipe-form";

    public RecipeController(IRecipeService<Recipe> recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("RecipeController: newRecipe()");
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.findAll());
        return RECIPE_FORM_URL;
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
        return RECIPE_FORM_URL;
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @PostMapping("/recipe") //@ModelAttribute needs to be named "recipe" according to obj in template "recipe-form"
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));
            return RECIPE_FORM_URL;
        }
        log.debug("RecipeController: saveOrUpdateIngredient()");
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
