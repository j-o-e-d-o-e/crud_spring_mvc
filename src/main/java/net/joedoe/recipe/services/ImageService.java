package net.joedoe.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.commands.RecipeCommand;
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
    public void saveImageToDB(Recipe recipe, MultipartFile file) {
        try {
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

    @Override
    @Transactional
    public byte[] loadImageFromDB(RecipeCommand recipeCommand) {
        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (Byte wrappedByte : recipeCommand.getImage()) {
            byteArray[i++] = wrappedByte; //auto unboxing
        }
        return byteArray;
    }
}