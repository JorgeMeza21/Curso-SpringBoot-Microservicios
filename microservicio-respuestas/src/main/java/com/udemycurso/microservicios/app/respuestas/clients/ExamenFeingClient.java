package com.udemycurso.microservicios.app.respuestas.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeingClient {

	@GetMapping("/{id}")
	public Examen obtenerExamenPorId(@PathVariable Long id);
	
	@GetMapping("/examenesIds-por-preguntaIds")
	public Iterable<Long> buscarExamenesIdsPorPreguntaIds(@RequestParam List<Long> pregIds);

}
