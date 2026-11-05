package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

	public static Pet getPet() {
		return new Pet(1,"Leo",1,1, null);
	}

	public static Pet newPet() {
		Pet newEntity = new Pet();
		newEntity.setName("Punky");
		newEntity.setTypeId(1);
		newEntity.setOwnerId(1);
		return newEntity;
	}

	public static Pet newPetCreated() {
		Pet pet = newPet();
		pet.setId(1000);
		return pet;
	}

	public static PetDTO newPetDTO() {
		PetDTO newDTO = new PetDTO();
		newDTO.setName("Punky");
		newDTO.setTypeId(1);
		newDTO.setOwnerId(1);
		return newDTO;
	}

	public static PetDTO newPetDTOCreated() {
		PetDTO petDTO = newPetDTO();
		petDTO.setId(1000);
		return petDTO;
	}


	public static Pet newPetForUpdate() {
		return new Pet(0,"Bear",1,1,null);
	}

	public static Pet newPetCreatedForUpdate() {
		Pet pet = newPetForUpdate();
		pet.setId(4000);
		return pet;
	}

	public static Pet newPetForDelete() {
		return new Pet(0,"Bird",1,1, null);
	}

	public static Pet newPetCreatedForDelete() {
		Pet pet = newPetForDelete();
		pet.setId(2000);
		return pet;
	}

	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(1,"Leo",1,1, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(9,"Lucky",5,7, null));
		pets.add(new Pet(11,"Freddy",5,9, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(12,"Lucky",2,10, null));
		pets.add(new Pet(13,"Sly",1,10, null));
		return pets;
	}

	// ===================== VISIT =====================

	/**
	 * Visita existente: replica el registro id=1 de data.sql.
	 */
	public static Visit getVisit() {
		return new Visit(1, 7, 2, LocalDate.of(2010, 3, 4), "rabies shot", new BigDecimal("45.00"));
	}

	/**
	 * Visita nueva, todavia sin id (tal como llega antes de guardarse).
	 */
	public static Visit newVisit() {
		Visit newEntity = new Visit();
		newEntity.setPetId(1);
		newEntity.setVetId(1);
		newEntity.setVisitDate(LocalDate.of(2024, 5, 10));
		newEntity.setDescription("general checkup");
		newEntity.setCost(new BigDecimal("65.00"));
		return newEntity;
	}

	/**
	 * La misma visita, pero con el id que le asignaria la base de datos.
	 */
	public static Visit newVisitCreated() {
		Visit visit = newVisit();
		visit.setId(1000);
		return visit;
	}

	public static Visit newVisitForUpdate() {
		return new Visit(null, 1, 1, LocalDate.of(2024, 6, 1), "vaccination", new BigDecimal("80.00"));
	}

	public static Visit newVisitCreatedForUpdate() {
		Visit visit = newVisitForUpdate();
		visit.setId(4000);
		return visit;
	}
}
