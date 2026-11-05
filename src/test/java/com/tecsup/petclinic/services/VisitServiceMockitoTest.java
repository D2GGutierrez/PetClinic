package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mappers.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import com.tecsup.petclinic.util.TObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del VisitService (Integrante A - CRUD basico).
 *
 * El repositorio es un MOCK (@MockitoBean): ninguna prueba toca la base de
 * datos. Asi se valida unicamente la logica del servicio (mapeo DTO <-> entidad
 * y llamadas al repositorio), con datos totalmente controlados.
 */
@Slf4j
@SpringBootTest
public class VisitServiceMockitoTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private VisitMapper visitMapper;

    // Reemplaza al VisitRepository real dentro del contexto de Spring.
    @MockitoBean
    private VisitRepository repository;

    /**
     * Registrar una visita.
     *
     * El servicio recibe un DTO sin id, lo convierte a entidad y llama a
     * repository.save(...). En la vida real la base de datos asigna el id;
     * aqui el mock simula esa respuesta devolviendo la misma visita con id 1000.
     */
    @Test
    public void testCreateVisit() {

        // ------------ Preparar datos ---------------

        Visit newVisit = TObjectCreator.newVisit();               // sin id
        Visit newVisitCreated = TObjectCreator.newVisitCreated(); // con id 1000

        VisitDTO newVisitDTO = this.visitMapper.mapToDto(newVisit);
        VisitDTO hopeVisitDTOCreated = this.visitMapper.mapToDto(newVisitCreated);

        Mockito.when(this.repository.save(newVisit))
                .thenReturn(newVisitCreated);

        // ------------ Ejecutar ---------------

        VisitDTO newVisitDTOCreated = this.visitService.create(newVisitDTO);

        log.info("Visit created : {}", newVisitDTOCreated);

        // ------------ Validar ---------------
        assertNotNull(newVisitDTOCreated.getId());
        assertEquals(hopeVisitDTOCreated.getId(), newVisitDTOCreated.getId());
        assertEquals(hopeVisitDTOCreated.getPetId(), newVisitDTOCreated.getPetId());
        assertEquals(hopeVisitDTOCreated.getVetId(), newVisitDTOCreated.getVetId());
        assertEquals(hopeVisitDTOCreated.getVisitDate(), newVisitDTOCreated.getVisitDate());
        assertEquals(hopeVisitDTOCreated.getDescription(), newVisitDTOCreated.getDescription());
        assertEquals(hopeVisitDTOCreated.getCost(), newVisitDTOCreated.getCost());

        // El servicio debio guardar exactamente 1 vez la visita recibida.
        Mockito.verify(this.repository, Mockito.times(1)).save(newVisit);
    }

    /**
     * Actualizar una visita.
     *
     * Flujo: (1) se crea la visita, (2) se modifican sus datos en el DTO y
     * (3) se llama a update(). El update del servicio tambien usa save(...):
     * como la entidad ya trae id, JPA hace un UPDATE en lugar de un INSERT.
     */
    @Test
    public void testUpdateVisit() {

        LocalDate UP_VISIT_DATE = LocalDate.of(2024, 7, 15);
        String UP_DESCRIPTION = "vaccination + deworming";
        BigDecimal UP_COST = new BigDecimal("95.50");
        Integer UP_VET_ID = 2;

        Visit newVisit = TObjectCreator.newVisitForUpdate();               // sin id
        Visit newVisitCreate = TObjectCreator.newVisitCreatedForUpdate();  // con id 4000

        VisitDTO newVisitDTO = this.visitMapper.mapToDto(newVisit);

        // ------------ Create ---------------

        Mockito.when(this.repository.save(newVisit))
                .thenReturn(newVisitCreate);

        VisitDTO newVisitDTOCreate = this.visitService.create(newVisitDTO);
        log.info("{}", newVisitDTOCreate);

        // ------------ Update ---------------

        // Preparar los datos nuevos (el id 4000 se mantiene)
        newVisitDTOCreate.setVetId(UP_VET_ID);
        newVisitDTOCreate.setVisitDate(UP_VISIT_DATE);
        newVisitDTOCreate.setDescription(UP_DESCRIPTION);
        newVisitDTOCreate.setCost(UP_COST);

        // La entidad que el servicio enviara a save(...) al actualizar
        Visit newVisitUpdate = this.visitMapper.mapToEntity(newVisitDTOCreate);

        // El mock devuelve la entidad tal como fue modificada
        Mockito.when(this.repository.save(newVisitUpdate))
                .thenReturn(newVisitUpdate);

        // Ejecutar el update
        VisitDTO visitDTOUpdate = this.visitService.update(newVisitDTOCreate);
        log.info("{}", visitDTOUpdate);

        // ------------ Validar ---------------

        //            EXPECTED                    ACTUAL
        assertEquals(newVisitCreate.getId(), visitDTOUpdate.getId());   // sigue siendo la misma visita
        assertEquals(UP_VET_ID, visitDTOUpdate.getVetId());
        assertEquals(UP_VISIT_DATE, visitDTOUpdate.getVisitDate());
        assertEquals(UP_DESCRIPTION, visitDTOUpdate.getDescription());
        assertEquals(UP_COST, visitDTOUpdate.getCost());

        // Los datos que NO se modificaron deben permanecer igual.
        assertEquals(newVisitCreate.getPetId(), visitDTOUpdate.getPetId());

        // save() se invoco con la visita ya modificada.
        Mockito.verify(this.repository, Mockito.times(1)).save(newVisitUpdate);
    }

    /**
     * Buscar una visita existente por id.
     *
     * El mock simula que la base de datos tiene la visita con id 1
     * ("rabies shot", mismo registro que data.sql) y se comprueba que el
     * servicio la devuelve correctamente convertida a DTO.
     */
    @Test
    public void testFindVisitById() {

        Integer ID = 1;

        Visit visitExpected = TObjectCreator.getVisit();

        // Cuando el servicio consulte el id 1, el mock responde con la visita.
        Mockito.when(this.repository.findById(ID))
                .thenReturn(Optional.of(visitExpected));

        VisitDTO visit = null;

        try {
            visit = this.visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("{}", visitExpected);
        log.info("{}", visit);

        //            EXPECTED                          ACTUAL
        assertNotNull(visit);
        assertEquals(visitExpected.getId(), visit.getId());
        assertEquals(visitExpected.getPetId(), visit.getPetId());
        assertEquals(visitExpected.getVetId(), visit.getVetId());
        assertEquals(visitExpected.getVisitDate(), visit.getVisitDate());
        assertEquals(visitExpected.getDescription(), visit.getDescription());
        assertEquals(visitExpected.getCost(), visit.getCost());

        // Se consulto al repositorio exactamente una vez con el id pedido.
        Mockito.verify(this.repository, Mockito.times(1)).findById(ID);
    }

    @Test
    public void testFindByPetId() {

        int PET_ID = 7;

        Visit visit1 = new Visit(1, 7, 2, LocalDate.of(2010, 3, 4), "rabies shot", new BigDecimal("45.00"));
        Visit visit4 = new Visit(4, 7, 3, LocalDate.of(2008, 9, 4), "spayed", new BigDecimal("275.00"));

        Mockito.when(this.repository.findByPetId(PET_ID))
                .thenReturn(List.of(visit1, visit4));

        List<VisitDTO> visits = this.visitService.findByPetId(PET_ID);

        log.info("{}", visits);

        assertEquals(2, visits.size());
        assertEquals(1, visits.get(0).getId());
        assertEquals(4, visits.get(1).getId());
        visits.forEach(v -> assertEquals(PET_ID, v.getPetId()));

        Mockito.verify(this.repository, Mockito.times(1)).findByPetId(PET_ID);
    }

    @Test
    public void testFindByVetId() {

        Integer VET_ID = 2;

        Visit visit1 = new Visit(1, 7, 2, LocalDate.of(2010, 3, 4), "rabies shot", new BigDecimal("45.00"));
        Visit visit2 = new Visit(2, 8, 2, LocalDate.of(2011, 3, 4), "rabies shot", new BigDecimal("45.00"));

        Mockito.when(this.repository.findByVetId(VET_ID))
                .thenReturn(List.of(visit1, visit2));

        List<VisitDTO> visits = this.visitService.findByVetId(VET_ID);

        log.info("{}", visits);

        assertEquals(2, visits.size());
        assertEquals(1, visits.get(0).getId());
        assertEquals(2, visits.get(1).getId());
        visits.forEach(v -> assertEquals(VET_ID, v.getVetId()));

        Mockito.verify(this.repository, Mockito.times(1)).findByVetId(VET_ID);
    }

    @Test
    public void testDeleteVisit() {

        Integer ID = 1;

        Visit visit = new Visit(1, 7, 2, LocalDate.of(2010, 3, 4), "rabies shot", new BigDecimal("45.00"));

        Mockito.when(this.repository.findById(ID))
                .thenReturn(Optional.of(visit));

        try {
            this.visitService.delete(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        Mockito.verify(this.repository, Mockito.times(1)).findById(ID);
        Mockito.verify(this.repository, Mockito.times(1)).deleteById(ID);
    }


    @Test
    public void testCalculateTotalCostByPet() {

        int PET_ID = 7;

        Visit visit1 = new Visit(1, 7, 2, LocalDate.of(2010, 3, 4), "rabies shot", new BigDecimal("45.00"));
        Visit visit4 = new Visit(4, 7, 3, LocalDate.of(2008, 9, 4), "spayed", new BigDecimal("275.00"));

        Mockito.when(this.repository.findByPetId(PET_ID))
                .thenReturn(List.of(visit1, visit4));

        BigDecimal total = this.visitService.calculateTotalCostByPet(PET_ID);

        log.info("Total cost: {}", total);

        assertEquals(new BigDecimal("320.00"), total);

        Mockito.verify(this.repository, Mockito.times(1)).findByPetId(PET_ID);
    }

    @Test
    public void testCalculateTotalCostByPet_NoVisits() {

        int PET_ID = 99;

        Mockito.when(this.repository.findByPetId(PET_ID))
                .thenReturn(List.of());

        BigDecimal total = this.visitService.calculateTotalCostByPet(PET_ID);

        log.info("Total cost: {}", total);

        assertEquals(BigDecimal.ZERO, total);

        Mockito.verify(this.repository, Mockito.times(1)).findByPetId(PET_ID);
    }

    @Test
    public void testFindVisitById_NotFound() {

        Integer ID = 9999;

        Mockito.when(this.repository.findById(ID))
                .thenReturn(Optional.empty());

        assertThrows(VisitNotFoundException.class, () -> this.visitService.findById(ID));

        Mockito.verify(this.repository, Mockito.times(1)).findById(ID);
    }

}
