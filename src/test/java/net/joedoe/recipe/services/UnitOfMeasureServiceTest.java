package net.joedoe.recipe.services;

import net.joedoe.recipe.commands.UnitOfMeasureCommand;
import net.joedoe.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.joedoe.recipe.domains.UnitOfMeasure;
import net.joedoe.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceTest {
    private IUnitOfMeasureService service;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new UnitOfMeasureService(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testFindByDescription(){
        //given
        String description = "description";
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(description);
        Optional<UnitOfMeasure> uomOpt = Optional.of(uom);

        //when
        when(unitOfMeasureRepository.findByDescription(anyString())).thenReturn(uomOpt);
        Optional<UnitOfMeasure> returnUomOpt = unitOfMeasureRepository.findByDescription(anyString());

        //then
        assertEquals(description, returnUomOpt.get().getDescription());
        verify(unitOfMeasureRepository, times(1)).findByDescription(anyString());
    }

    @Test
    public void testListAllUoms() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}