package net.joedoe.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageService implements IImageService {
    private final RecipeRepository recipeRepository;

    public ImageService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
            Byte[] bytes = new Byte[file.getBytes().length]; //hibernate prefers obj to primitives
            int i = 0;
            for (byte b : file.getBytes()) {
                bytes[i++] = b;
            }
            assert recipe != null;
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occured", e);
            e.printStackTrace();
        }
    }
}
