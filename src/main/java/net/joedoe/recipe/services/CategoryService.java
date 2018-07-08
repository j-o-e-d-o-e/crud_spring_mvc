package net.joedoe.recipe.services;

import lombok.extern.slf4j.Slf4j;
import net.joedoe.recipe.domains.Category;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> findByDescription(String description) {
        return repository.findByDescription(description);
    }

    public Set<Category> findAll() {
        log.debug("CategoryService: findAll()");
        Set<Category> categories = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(categories::add);
        return categories;
    }

    public Long count() {
        return repository.count();
    }

    public void save(Category cat) {
        repository.save(cat);
    }
}
