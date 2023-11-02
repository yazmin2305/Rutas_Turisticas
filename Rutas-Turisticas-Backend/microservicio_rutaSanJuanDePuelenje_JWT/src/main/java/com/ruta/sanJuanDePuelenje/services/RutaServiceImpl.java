package com.ruta.sanJuanDePuelenje.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.DTO.Command.RutaCommandDTO;
import com.ruta.sanJuanDePuelenje.DTO.Query.RutaQueryDTO;
import com.ruta.sanJuanDePuelenje.models.Ruta;
import com.ruta.sanJuanDePuelenje.repository.IRutaRepository;

@Service
public class RutaServiceImpl implements IRutaService{

	@Autowired
	private IRutaRepository iRutaRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	public Response<List<RutaQueryDTO>> findAllRutas() {
		List<Ruta> rutaEntity = iRutaRepository.findAll();
		Response<List<RutaQueryDTO>> response = new Response<>();
		if(rutaEntity.isEmpty()) {
			response.setStatus(404);
			response.setUserMessage("Rutas no encontradas");
			response.setMoreInfo("http://localhost:8080/ruta/ConsultAllRutas");
			response.setData(null);
		}else {
			List<RutaQueryDTO> rutaDTOs = rutaEntity.stream().map(ruta -> modelMapper.map(ruta, RutaQueryDTO.class)).collect(Collectors.toList());
			response.setStatus(200);
			response.setUserMessage("Rutas encontradas con éxito");
			response.setMoreInfo("http://localhost:8080/ruta/ConsultAllRutas");
			response.setData(rutaDTOs);
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public Response<RutaQueryDTO> findByRutaId(Integer RutaId) {
		Ruta ruta = iRutaRepository.findById(RutaId).orElse(null);
		Response<RutaQueryDTO> response = new Response<>();
		if(ruta == null) {
			response.setStatus(404);
			response.setUserMessage("Ruta no encontrada");
			response.setMoreInfo("http://localhost:8080/ruta/ConsultById/{id}");
			response.setData(null);
		}else {
			RutaQueryDTO rutaDTO = modelMapper.map(ruta, RutaQueryDTO.class);
			response.setStatus(200);
			response.setUserMessage("Ruta encontrada con éxito");
			response.setMoreInfo("http://localhost:8080/ruta/ConsultById/{id}");
			response.setData(rutaDTO);
		}
		return response;
	}

	@Override
	@Transactional
	public Response<RutaCommandDTO> saveRuta(RutaCommandDTO ruta) {
		Response<RutaCommandDTO> response = new Response<>();
		if(ruta != null) {
			Ruta rutaEntity  = this.modelMapper.map(ruta, Ruta.class);
			rutaEntity.setState(true);
			Ruta objRuta = this.iRutaRepository.save(rutaEntity);
			RutaCommandDTO rutaDTO = this.modelMapper.map(objRuta, RutaCommandDTO.class);
			response.setStatus(200);
			response.setUserMessage("Ruta creada con éxito");
			response.setMoreInfo("http://localhost:8080/ruta/SaveRuta");
			response.setData(rutaDTO);
		}else {
			response.setStatus(500);
			response.setUserMessage("Error al crear la ruta");
			response.setMoreInfo("http://localhost:8080/ruta/SaveRuta");
			response.setData(null);
		}
		return response;
	}

	@Override
	@Transactional
	public Response<RutaCommandDTO> updateRuta(Integer rutaId, RutaCommandDTO ruta) {
		Response<RutaCommandDTO> response = new Response<>();
		if(ruta != null && rutaId != null) {
			Ruta rutaEntity = this.modelMapper.map(ruta, Ruta.class);
			Ruta rutaEntity1 = this.iRutaRepository.findById(rutaId).get();
			rutaEntity1.setName(rutaEntity.getName());
			rutaEntity1.setState(rutaEntity.getState());
//			rutaEntity1.setLstTalking(rutaEntity.getLstTalking());
//			rutaEntity1.setLstWorkshop(rutaEntity.getLstWorkshop());
//			rutaEntity1.setLstRecreation(rutaEntity.getLstRecreation());
//			rutaEntity1.setLstLodging(rutaEntity.getLstLodging());
//			rutaEntity1.setLstFestival(rutaEntity.getLstFestival());
//			rutaEntity1.setLstFinca(rutaEntity.getLstFinca());
//			rutaEntity1.setLstLunch(rutaEntity.getLstLunch());
//			rutaEntity1.setLstReserve(rutaEntity.getLstReserve());
//			rutaEntity1.setLstUser(rutaEntity.getLstUser());
			this.iRutaRepository.save(rutaEntity1);
			RutaCommandDTO rutaDTO = this.modelMapper.map(rutaEntity1, RutaCommandDTO.class);
			response.setStatus(200);
			response.setUserMessage("Ruta actualizada con éxito");
			response.setMoreInfo("http://localhost:8080/ruta/UpdateRuta/{id}");
			response.setData(rutaDTO);		
		}else {
			response.setStatus(500);
			response.setUserMessage("Error al actualizar la ruta");
			response.setMoreInfo("http://localhost:8080/ruta/UpdateRuta/{id}");
			response.setData(null);		
		}
		return response;
	}

	@Override
	@Transactional
	public Response<Boolean> disableRuta(Integer rutaId) {
		Ruta rutaEntity = this.iRutaRepository.findById(rutaId).get();
		Response<Boolean> response = new Response<>();
		if (rutaEntity != null) {
			if(rutaEntity.getState() == true){
				rutaEntity.setState(false);
				this.iRutaRepository.save(rutaEntity);
				response.setStatus(200);
				response.setUserMessage("Ruta deshabilitada con éxito");
				response.setMoreInfo("http://localhost:8080/ruta/DisableRuta/{id}");
				response.setData(true);
			}else {
				response.setStatus(500);
				response.setUserMessage("La ruta ya está deshabilitada");
				response.setMoreInfo("http://localhost:8080/ruta/DisableRuta/{id}");
				response.setData(false);
			}
		}
		return response;
	}

	@Override
	public Response<List<RutaQueryDTO>> findAllRutasBytState(boolean state) {
		List<Ruta> rutaEntity = this.iRutaRepository.LstRutasByState(state);
		Response<List<RutaQueryDTO>> response = new Response<>();
		if(!rutaEntity.isEmpty()) {
			List<RutaQueryDTO> rutaDTO = rutaEntity.stream().map(ruta -> modelMapper.map(ruta, RutaQueryDTO.class)).collect(Collectors.toList());
			response.setStatus(200);
			response.setUserMessage("Rutas encontrados con éxito");
			response.setMoreInfo("http://localhost:8080/ruta/ConsultAllRutasByState");
			response.setData(rutaDTO);
		}else {
			response.setStatus(404);
			response.setUserMessage("No existen rutas relacionadas a este estado");
			response.setMoreInfo("http://localhost:8080/ruta/ConsultAllRutasByState");
			response.setData(null);
		}
		return response;
	}


}