package net.joedoe.recipe.repositories;

import net.joedoe.recipe.domains.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {
    @Autowired
    private UnitOfMeasureRepository repository;

    @Test
    public void findByDescription() {
        UnitOfMeasure uom = repository.findByDescription("Teaspoon").orElse(null);
        assertEquals("Teaspoon", uom.getDescription());
    }
}