package com.ruta.sanJuanDePuelenje.DTO.Command;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCommandDTO {
	private Integer reserveId;
	private Integer reserveAmountPersons;
	private Double reserveTotalPrice;
	private Double lodgingTotalPrice;
	private Double lunchTotalPrice;
	private Double talkingTotalPrice;
	private Double recreationTotalPrice;
	private Double workshopTotalPrice;
	private Boolean reserveState;
	private Date reserveDate;
	private UserCommandDTO user;
	private List<WorkshopCommandDTO> LstWorkshop;
	private List<TalkingCommandDTO> LstTalking;
	private List<RecreationCommandDTO> LstRecreation;
	private List<LodgingCommandDTO> LstLodging;
	private List<FestivalCommandDTO> LstFestival;
	private List<LunchCommandDTO> ListLunch;
	private RutaCommandDTO ruta;
}