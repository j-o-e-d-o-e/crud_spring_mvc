package net.joedoe.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.services.IRecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {
    private IRecipeService<Recipe> service;

    public IndexController(IRecipeService<Recipe> service) {
        this.service = service;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("IndexController: getIndexPage(Model model)");
        model.addAttribute("recipes", service.findAll());
        return "index";
    }
}
