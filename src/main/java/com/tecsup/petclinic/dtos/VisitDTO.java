package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitDTO {

	private Integer id;
	private int petId;
	private Integer vetId;
	private LocalDate visitDate;
	private String description;
	private BigDecimal cost;
}
