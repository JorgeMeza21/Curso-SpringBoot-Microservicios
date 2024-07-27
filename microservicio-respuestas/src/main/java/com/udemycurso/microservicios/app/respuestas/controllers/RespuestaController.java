package com.udemycurso.microservicios.app.respuestas.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.udemycurso.microservicios.app.respuestas.models.entity.Respuesta;
import com.udemycurso.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {

	@Autowired 
	private RespuestaService respuestaService;
	
	@PostMapping
	public ResponseEntity<?> saveAll(@RequestBody Iterable<Respuesta> respuestas){
		respuestas = ((List<Respuesta>) respuestas).stream().map(r -> {
			r.setAlumnoId(r.getAlumno().getId());
			r.setPreguntaId(r.getPregunta().getId());
			return r;
		}).collect(Collectors.toList());
		
		Iterable<Respuesta> resp = respuestaService.saveAll(respuestas);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}
	
	@GetMapping("/buscar/alumno/{idAlumno}/examen/{idExamen}")
	public ResponseEntity<?> buscarPorAlumnoPorExamen(@PathVariable Long idAlumno, @PathVariable Long idExamen){
		return ResponseEntity.ok(respuestaService.findByAlumnoByExamen(idAlumno, idExamen));
	}
	
	@GetMapping("/alumno/{idAlumno}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenIdsConRespuestaAlumno(@PathVariable Long idAlumno){
		Iterable<Long> examenesId = respuestaService.findExamenIdsConRespuestaByAlumno(idAlumno);
		return ResponseEntity.ok(examenesId);
	}
	
}
