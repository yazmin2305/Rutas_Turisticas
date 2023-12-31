package com.ruta.sanJuanDePuelenje.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.DTO.Query.FestivalQueryDTO;
import com.ruta.sanJuanDePuelenje.util.GenericPageableResponse;
import com.ruta.sanJuanDePuelenje.DTO.Command.FestivalCommandDTO;

public interface IFestivalService {
	
	public Response<List<FestivalCommandDTO>> findAllFestival();
	
	public Response<FestivalCommandDTO> findByFestivalId(Integer festivalId);
	
	public Response<FestivalCommandDTO> saveFestival(FestivalCommandDTO festival);
	
	public Response<FestivalQueryDTO> updateFestival(Integer festivalId, FestivalCommandDTO festival);
	
	public Response<Boolean> disableFestival(Integer festivalId);
	
	public Response<Boolean> enableFestival(Integer festivalId);
	
	public GenericPageableResponse findAllFestivalByState(boolean state, Pageable pageable);
	
	public Response<List<FestivalQueryDTO>> findFestivalByStateByRuta(boolean state, Integer rutaId);
	
	public Response<List<FestivalQueryDTO>> findAllFestivalBytRuta(Integer rutaId);
}
