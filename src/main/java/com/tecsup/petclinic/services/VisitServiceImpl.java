package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mappers.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

	private final VisitRepository visitRepository;
	private final VisitMapper visitMapper;

	@Override
	public VisitDTO create(VisitDTO visitDTO) {

		Visit newVisit = visitRepository.save(visitMapper.mapToEntity(visitDTO));

		return visitMapper.mapToDto(newVisit);
	}

	@Override
	public VisitDTO update(VisitDTO visitDTO) {

		Visit updatedVisit = visitRepository.save(visitMapper.mapToEntity(visitDTO));

		return visitMapper.mapToDto(updatedVisit);
	}

	@Override
	public VisitDTO findById(Integer id) throws VisitNotFoundException {

		Optional<Visit> visit = visitRepository.findById(id);

		if (!visit.isPresent())
			throw new VisitNotFoundException("Record not found...!");

		return this.visitMapper.mapToDto(visit.get());
	}

}
