package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.UnitOfMeasureCommand;
import net.joedoe.recipe.domains.UnitOfMeasure;

import java.util.Optional;
import java.util.Set;

public interface IUnitOfMeasureService {

    Optional<UnitOfMeasure> findByDescription(String description);

    Set<UnitOfMeasureCommand> listAllUoms();

    Long count();

    void save(UnitOfMeasure uom);
}
