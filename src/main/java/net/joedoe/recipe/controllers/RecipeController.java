package net.joedoe.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.domains.Recipe;
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
    private IRecipeService<Recipe> service;

    public RecipeController(IRecipeService<Recipe> service) {
        this.service = service;
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("RecipeController: newRecipe()");
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipe-form";
    }

    @GetMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", service.findCommandById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String getUpdateViewRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", service.findCommandById(Long.valueOf(id)));
        return "recipe/recipe-form";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting id: " + id);
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @PostMapping("/recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command) {
        log.debug("RecipeController: saveOrUpdate()");
        RecipeCommand savedCommand = service.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
