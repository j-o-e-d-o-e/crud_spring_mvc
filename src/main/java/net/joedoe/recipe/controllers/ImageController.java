package net.joedoe.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.services.IImageService;
import net.joedoe.recipe.services.IRecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {
    private final IImageService imageService;
    private final IRecipeService recipeService;

    public ImageController(IImageService imageService, IRecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/image-upload-form";
    }

    @PostMapping("recipe/{id}/image")
    public String saveImageFile(@PathVariable String id, @RequestParam("image-file") MultipartFile file) {
        Recipe recipe = (Recipe) recipeService.findById(Long.valueOf(id));
        imageService.saveImageToDB(recipe, file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipe-image")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        log.debug("Render img from db");
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        if (recipeCommand.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(imageService.loadImageFromDB(recipeCommand));
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
