package com.ruta.sanJuanDePuelenje.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.ruta.sanJuanDePuelenje.DTO.Response;
import com.ruta.sanJuanDePuelenje.DTO.Command.FincaCommandDTO;
import com.ruta.sanJuanDePuelenje.DTO.Query.FincaQueryDTO;
import com.ruta.sanJuanDePuelenje.services.IFincaService;
import com.ruta.sanJuanDePuelenje.util.GenericPageableResponse;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/finca")
@CrossOrigin("*")
public class FincaController {

	@Autowired
	private IFincaService iFincaService;

	// Consultar todas las fincas
	@PermitAll
	@GetMapping("/ConsultAllFincas")
	public Response<List<FincaCommandDTO>> ConsultAllFincas() {
		return this.iFincaService.findAllFincas();
	}

	// Consultar todas las fincas por ruta
	@Secured({ "ADMIN", "SUPER" })
	@GetMapping("ConsultAllFincaByRuta/{rutaId}")
	public Response<List<FincaQueryDTO>> ConsultAllFincaByRuta(@PathVariable Integer rutaId) {
		return this.iFincaService.findAllFincasBytRuta(rutaId);
	}

	// Consultar finca por id
	@Secured({ "ADMIN", "SUPER" })
	@GetMapping("/ConsultById/{id}")
	public Response<FincaCommandDTO> ConsultFincaById(@PathVariable Integer id) {
		return this.iFincaService.findByFincaId(id);
	}

	// Guardar finca
	@Secured("ADMIN")
	@PostMapping("/SaveFinca")
	public Response<FincaCommandDTO> SaveFinca(@RequestBody FincaCommandDTO finca) {
		return this.iFincaService.saveFinca(finca);
	}

	// Actualizar finca
	@Secured("ADMIN")
	@PatchMapping("/UpdateFinca/{id}")
	public Response<FincaQueryDTO> UpdateFinca(@RequestBody FincaCommandDTO finca, @PathVariable Integer id) {
		return this.iFincaService.updateFinca(id, finca);
	}

	// Desabilitar una finca registrada en el sistema
	@Secured("ADMIN")
	@PatchMapping("/DisableFinca/{id}")
	public Response<Boolean> DisableFinca(@PathVariable Integer id) {
		return this.iFincaService.disableFinca(id);
	}

	// Habilitar una finca registrada en el sistema
	@Secured("ADMIN")
	@PatchMapping("/EnableFinca/{id}")
	public Response<Boolean> EnableFinca(@PathVariable Integer id) {
		return this.iFincaService.enableFinca(id);
	}

	// Consultar las fincas dependiento su estado: activado - desactivado
	@Secured({ "SUPER" })
	@GetMapping("ConsultAllFincaByState/{state}")
	public ResponseEntity<GenericPageableResponse> ConsultAllFincaByState(@RequestParam Integer page,
			@RequestParam Integer size, @RequestParam String sort, @RequestParam String order,
			@PathVariable Boolean state) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
		return ResponseEntity.status(HttpStatus.OK).body(this.iFincaService.findAllFincaBytState(state, pageable));
	}

	// Consultar las fincas dependiento su estado: activado - desactivado y dependiendo la ruta con la que esten relacionadas
	@Secured({ "ADMIN", "SUPER" })
	@GetMapping("ConsultAllFincaByStateByRuta/{state}/{rutaId}")
	public Response<List<FincaQueryDTO>> ConsultAllFincaByStateByRuta(@PathVariable Boolean state, @PathVariable Integer rutaId) {
		return this.iFincaService.findAllFincaBytStateByRuta(state, rutaId);
	}
}
