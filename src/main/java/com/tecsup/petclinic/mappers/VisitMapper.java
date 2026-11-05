package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {

	Visit mapToEntity(VisitDTO dto);

	VisitDTO mapToDto(Visit entity);
}
