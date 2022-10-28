package com.udemycurso.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas")
public interface RespuestaFeignClient {

	@GetMapping("alumno/{idAlumno}/examenes-respondidos")
	public Iterable<Long> obtenerExamenIdsConRespuestaAlumno(@PathVariable Long idAlumno);
}
