package com.ruta.sanJuanDePuelenje.DTO.Query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LunchQueryDTO {
	private String lunchMenu;
	private Double lunchUnitPrice;
	private Boolean lunchState;
}
