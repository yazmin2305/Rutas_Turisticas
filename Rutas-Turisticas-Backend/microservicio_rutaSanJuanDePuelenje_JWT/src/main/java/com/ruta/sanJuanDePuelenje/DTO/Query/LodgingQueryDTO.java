package com.ruta.sanJuanDePuelenje.DTO.Query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LodgingQueryDTO {
	private String lodgingName;
	private String lodgingDescription;
	private Integer lodgingQuantityAvailable;
	private Integer lodgingMaxAmountPerson;
	private Double lodgingUnitPrice;
	private Boolean lodgingState;
}
