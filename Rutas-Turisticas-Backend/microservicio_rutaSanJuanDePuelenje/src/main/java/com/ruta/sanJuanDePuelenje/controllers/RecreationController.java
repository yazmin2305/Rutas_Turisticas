package com.ruta.sanJuanDePuelenje.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.ruta.sanJuanDePuelenje.DTO.RecreationDTO;
import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.services.IRecreationService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/recreation")
@CrossOrigin("*")
public class RecreationController {

	@Autowired
	private IRecreationService iRecreationService;

	// Consultar todos las actividades de recreación
	@PermitAll
	@GetMapping("/ConsultAllRecreation")
	public Response<List<RecreationDTO>> ConsultAllRecreation() {
		return this.iRecreationService.findAllRecreation();
	}

	// Consultar una actividad recreativa por su id
	@Secured({"ADMIN", "USER"})
	@GetMapping("/ConsultById/{id}")
	public Response<RecreationDTO> ConsultRecreationById(@PathVariable Integer id) {
		return this.iRecreationService.findByRecreationId(id);
	}

	// Guardar una actividad recreativa
	@Secured("ADMIN")
	@PostMapping("/SaveRecreation")
	public Response<RecreationDTO> SaveRecreation(@RequestBody RecreationDTO recreation) {
		return this.iRecreationService.saveRecreation(recreation);
	}

	// Actualizar una actividad recreativa
	@Secured("ADMIN")
	@PutMapping("/UpdateRecreation/{id}")
	public Response<RecreationDTO> UpdateRecreation(@RequestBody RecreationDTO recreation, @PathVariable Integer id) {
		return this.iRecreationService.updateRecreation(id, recreation);
	}

	// Desabilitar una actividad recreativa registrada en el sistema
	@Secured("ADMIN")
	@PutMapping("/DisableRecreation/{id}")
	public Response<Boolean> DisableRecreation(@PathVariable Integer id) {
		return this.iRecreationService.disableRecreation(id);
	}

	// Consultar las actividades de recreacion dependiento su estado: activado - desactivado
	@Secured({"ADMIN", "USER"})
	@GetMapping("ConsultAllRecreationByState/{state}")
	public Response<List<RecreationDTO>> ConsultAllRecreationByState(@PathVariable Boolean state) {
		return this.iRecreationService.findAllRecreationBytState(state);
	}
}
