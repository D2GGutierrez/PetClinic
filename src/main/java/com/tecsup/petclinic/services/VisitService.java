package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface VisitService {

	/**
	 * Registra una nueva visita.
	 *
	 * @param visitDTO
	 * @return la visita creada (con id generado)
	 */
	VisitDTO create(VisitDTO visitDTO);

	/**
	 * Actualiza una visita existente.
	 *
	 * @param visitDTO
	 * @return la visita actualizada
	 */
	VisitDTO update(VisitDTO visitDTO);

	/**
	 * Busca una visita por su id.
	 *
	 * @param id
	 * @return la visita encontrada
	 * @throws VisitNotFoundException si no existe
	 */
	VisitDTO findById(Integer id) throws VisitNotFoundException;

	List<VisitDTO> findByPetId(int petId);

	List<VisitDTO> findByVetId(Integer vetId);

	void delete(Integer id) throws VisitNotFoundException;

	BigDecimal calculateTotalCostByPet(int petId);
}
