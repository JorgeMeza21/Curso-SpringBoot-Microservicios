package com.udemycurso.microservicios.app.respuestas.services;

import com.udemycurso.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {

	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	public Iterable<Respuesta> findByAlumnoByExamen(Long idAlumno, Long idExamen);
	public Iterable<Long> findExamenIdsConRespuestaByAlumno(Long idAlumno);
}
