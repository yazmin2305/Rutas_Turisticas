package com.ruta.sanJuanDePuelenje.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.DTO.Command.FestivalCommandDTO;
import com.ruta.sanJuanDePuelenje.DTO.Query.FestivalQueryDTO;
import com.ruta.sanJuanDePuelenje.services.IFestivalService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/festival")
@CrossOrigin("*")
public class FestivalController {

	@Autowired
	private IFestivalService iFestivalService;

	// Consultar todos los festivales
	@PermitAll
	@GetMapping("/ConsultAllFestivales")
	public Response<List<FestivalQueryDTO>> ConsultAllFestival() {
		return this.iFestivalService.findAllFestival();
	}

	// Consultar festival por id
	//las consultas por id para que rol son?
	@Secured({"ADMIN", "USER"})
	@GetMapping("/ConsultById/{id}")
	public Response<FestivalQueryDTO> ConsultFestivalById(@PathVariable Integer id) {
		return this.iFestivalService.findByFestivalId(id);
	}

	// Guardar festival
	@Secured("ADMIN")
	@PostMapping("/SaveFestival")
	public Response<FestivalCommandDTO> SaveFestival(@RequestBody FestivalCommandDTO festival) {
		return this.iFestivalService.saveFestival(festival);
	}

	// Actualizar festival
	@Secured("ADMIN")
	@PutMapping("/UpdateFestival/{id}")
	public Response<FestivalQueryDTO> UpdateFestival(@RequestBody FestivalCommandDTO festival, @PathVariable Integer id) {
		return this.iFestivalService.updateFestival(id, festival);
	}

	// Desabilitar un festival registrado en el sistema
	@Secured("ADMIN")
	@PutMapping("/DisableFestival/{id}")
	public Response<Boolean> DisableFestival(@PathVariable Integer id) {
		return this.iFestivalService.disableFestival(id);
	}

	// Consultar los festivales dependiento su estado: activado - desactivado
	@Secured({"ADMIN", "USER"})
	@GetMapping("ConsultAllFestivalByState/{state}")
	public Response<List<FestivalQueryDTO>> ConsultAllFestivalByState(@PathVariable Boolean state) {
		return this.iFestivalService.findAllFestivalBytState(state);
	}
}
