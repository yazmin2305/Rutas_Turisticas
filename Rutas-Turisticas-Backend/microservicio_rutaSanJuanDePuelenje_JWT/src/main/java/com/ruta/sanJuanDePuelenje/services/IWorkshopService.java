package com.ruta.sanJuanDePuelenje.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.DTO.Command.WorkshopCommandDTO;
import com.ruta.sanJuanDePuelenje.DTO.Query.WorkshopQueryDTO;
import com.ruta.sanJuanDePuelenje.util.GenericPageableResponse;

public interface IWorkshopService {
	
	public Response<List<WorkshopCommandDTO>> findAllWorkshop();
	
	public Response<WorkshopCommandDTO> findByWorkshopId(Integer workshopId);
	
	public Response<WorkshopCommandDTO> saveWorkshop(WorkshopCommandDTO workshop);
	
	public Response<WorkshopQueryDTO> updateWorkshop(Integer workshopId, WorkshopCommandDTO workshop);
	
	public Response<Boolean> disableWorkshop(Integer workshopId);
	
	public Response<Boolean> enableWorkshop(Integer workshopId);
	
	public GenericPageableResponse findAllWorkshopBytState(boolean state, Pageable pageable);
	
	public Response<List<WorkshopQueryDTO>> findWorkshopByStateByRuta(boolean state, Integer rutaId);

	public Response<List<WorkshopQueryDTO>> findAllWorkshopBytRuta(Integer rutaId);
}
