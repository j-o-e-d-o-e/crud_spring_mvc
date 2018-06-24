package net.joedoe.recipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

    void saveImageFile(Long recipeId, MultipartFile file);
}
