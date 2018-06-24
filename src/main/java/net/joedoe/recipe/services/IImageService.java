package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

    void saveImageToDB(Recipe recipe, MultipartFile file);

    byte[] loadImageFromDB(RecipeCommand recipe);
}
