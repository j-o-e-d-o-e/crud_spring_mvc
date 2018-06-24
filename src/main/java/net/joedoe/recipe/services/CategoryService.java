package net.joedoe.recipe.services;

import net.joedoe.recipe.domains.Category;
import net.joedoe.recipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> findByDescription(String description) {
        return repository.findByDescription(description);
    }
}
