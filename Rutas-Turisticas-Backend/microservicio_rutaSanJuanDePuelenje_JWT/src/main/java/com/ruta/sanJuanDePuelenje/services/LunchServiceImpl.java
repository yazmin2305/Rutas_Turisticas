package com.ruta.sanJuanDePuelenje.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.DTO.Command.LunchCommandDTO;
import com.ruta.sanJuanDePuelenje.DTO.Query.LunchQueryDTO;
import com.ruta.sanJuanDePuelenje.models.Lunch;
import com.ruta.sanJuanDePuelenje.repository.ILunchRepository;
import com.ruta.sanJuanDePuelenje.util.GenericPageableResponse;
import com.ruta.sanJuanDePuelenje.util.PageableUtils;

@Service
public class LunchServiceImpl implements ILunchService{

	@Autowired
	private ILunchRepository iLunchRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	public Response<List<LunchQueryDTO>> findAllLunch() {
		List<Lunch> lunchEntity = iLunchRepository.findAll();
		Response<List<LunchQueryDTO>> response = new Response<>();
		if(lunchEntity.isEmpty()) {
			response.setStatus(404);
			response.setUserMessage("Menú no encontrado");
			response.setMoreInfo("http://localhost:8080/lunch/ConsultAllLunch");
			response.setData(null);
		}else {
			List<LunchQueryDTO> lunchDTOs = lunchEntity.stream().map(lunch -> modelMapper.map(lunch, LunchQueryDTO.class)).collect(Collectors.toList());
			response.setStatus(200);
			response.setUserMessage("Menú encontrado con éxito");
			response.setMoreInfo("http://localhost:8080/lunch/ConsultAllLunch");
			response.setData(lunchDTOs);
		}
		return response;
	}


	@Override
	@Transactional(readOnly = true)
	public Response<LunchQueryDTO> findByLunchId(Integer lunchId) {
		Lunch lunch = iLunchRepository.findById(lunchId).orElse(null);
		Response<LunchQueryDTO> response = new Response<>();
		if(lunch == null) {
			response.setStatus(404);
			response.setUserMessage("Item de menú no encontrado");
			response.setMoreInfo("http://localhost:8080/lunch/ConsultById/{id}");
			response.setData(null);
		}else {
			LunchQueryDTO lunchDTO = modelMapper.map(lunch, LunchQueryDTO.class);
			response.setStatus(200);
			response.setUserMessage("Menú encontrado con éxito");
			response.setMoreInfo("http://localhost:8080/lunch/ConsultById/{id}");
			response.setData(lunchDTO);
		}
		return response;
	}

	@Override
	@Transactional
	public Response<LunchCommandDTO> saveLunch(LunchCommandDTO lunch) {
		Lunch lunchEntity  = this.modelMapper.map(lunch, Lunch.class);
		Response<LunchCommandDTO> response = new Response<>();
		if(lunch != null) {
			lunchEntity.setState(true);
			Lunch objLunch = this.iLunchRepository.save(lunchEntity);
			LunchCommandDTO lunchDTO = this.modelMapper.map(objLunch, LunchCommandDTO.class);
			response.setStatus(200);
			response.setUserMessage("Item del menú creado con éxito");
			response.setMoreInfo("http://localhost:8080/lunch/SaveLunch");
			response.setData(lunchDTO);
		}else {
			response.setStatus(500);
			response.setUserMessage("Error al crear nuevo item del menú");
			response.setMoreInfo("http://localhost:8080/lunch/SaveLunch");
			response.setData(null);
		}		
		return response;
	}

	@Override
	@Transactional
	public Response<LunchQueryDTO> updateLunch(Integer lunchId, LunchCommandDTO lunch) {
		Response<LunchQueryDTO> response = new Response<>();
		if(lunch != null && lunchId != null) {
			Lunch lunchEntity = this.modelMapper.map(lunch, Lunch.class);
			Lunch lunchEntity1 = this.iLunchRepository.findById(lunchId).get();
			lunchEntity1.setMenu(lunchEntity.getMenu());
			lunchEntity1.setUnitPrice(lunchEntity.getUnitPrice());
			lunchEntity1.setState(lunchEntity.getState());
			this.iLunchRepository.save(lunchEntity1);
			LunchQueryDTO lunchDTO = this.modelMapper.map(lunchEntity1, LunchQueryDTO.class);
			response.setStatus(200);
			response.setUserMessage("Menú actualizado con éxito");
			response.setMoreInfo("http://localhost:8080/lunch/UpdateLunch/{id}");
			response.setData(lunchDTO);
		}else {
			response.setStatus(500);
			response.setUserMessage("Error al actualizar item del menú");
			response.setMoreInfo("http://localhost:8080/lunch/UpdateLunch/{id}");
			response.setData(null);
		}
		return response;
	}

	@Override
	@Transactional
	public Response<Boolean> disableLunch(Integer lunchId) {
		Lunch lunchEntity = this.iLunchRepository.findById(lunchId).get();
		Response<Boolean> response = new Response<>();
		if(lunchEntity != null) {
			if(lunchEntity.getState() == true){
				lunchEntity.setState(false);
				this.iLunchRepository.save(lunchEntity);
				response.setStatus(200);
				response.setUserMessage("Menú deshabilitado con éxito");
				response.setMoreInfo("http://localhost:8080/lunch/DisableLunch/{id}");
				response.setData(true);
			}else {
				response.setStatus(500);
				response.setUserMessage("El menú ya esta deshabilitado");
				response.setMoreInfo("http://localhost:8080/lunch/DisableLunch/{id}");
				response.setData(false);
			}
		}
		return response;
	}
	
	@Override
	@Transactional
	public Response<Boolean> enableLunch(Integer lunchId) {
		Lunch lunchEntity = this.iLunchRepository.findById(lunchId).get();
		Response<Boolean> response = new Response<>();
		if(lunchEntity != null) {
			if(lunchEntity.getState() == false){
				lunchEntity.setState(true);
				this.iLunchRepository.save(lunchEntity);
				response.setStatus(200);
				response.setUserMessage("Menú habilitado con éxito");
				response.setMoreInfo("http://localhost:8080/lunch/EnableLunch/{id}");
				response.setData(true);
			}else {
				response.setStatus(500);
				response.setUserMessage("El menú ya esta deshabilitado");
				response.setMoreInfo("http://localhost:8080/lunch/EnableLunch/{id}");
				response.setData(false);
			}
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public GenericPageableResponse findAllLunchBytState(boolean state, Pageable pageable) {
		Page<Lunch> lunchPage = this.iLunchRepository.LstLunchByState(state, pageable);
		if (lunchPage.isEmpty())
			return GenericPageableResponse.emptyResponse("No se encuentran menus relacionados a este estado");
		return this.validatePageList(lunchPage);
	}
	
	private GenericPageableResponse validatePageList(Page<Lunch> lunchPage){
        List<LunchQueryDTO> lunchDTOS = lunchPage.stream().map(x->modelMapper.map(x, LunchQueryDTO.class)).collect(Collectors.toList());
        return PageableUtils.createPageableResponse(lunchPage, lunchDTOS);
	}

}
