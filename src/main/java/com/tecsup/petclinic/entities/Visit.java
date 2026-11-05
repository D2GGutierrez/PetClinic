package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Visita veterinaria (tabla "visits").
 *
 * pet_id es obligatorio; vet_id puede ser null (la FK es ON DELETE SET NULL),
 * por eso vetId es Integer y no int.
 */
@Entity(name = "visits")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "pet_id")
	private int petId;

	@Column(name = "vet_id")
	private Integer vetId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "visit_date")
	private LocalDate visitDate;

	private String description;

	private BigDecimal cost;

}
