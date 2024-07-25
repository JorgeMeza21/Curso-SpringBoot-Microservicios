package com.udemycurso.microservicios.app.respuestas.models.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.udemycurso.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

	@Query("{'alumnoId': ?0, 'preguntaId': { $in: ?1}}")
	public Iterable<Respuesta> findRespuestaByAlumnoByPregunta(Long idAlumno, Iterable<Long> preguntaIds);
	
	@Query("{'alumnoId': ?0}")
	public Iterable<Respuesta> findByAlumno(Long idAlumno);

	//@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumnoId = ?1 and e.id = ?2")
	//public Iterable<Respuesta> findByAlumnoByExamen(Long idAlumno, Long idExamen);
	
	//@Query("select e.id from Respuesta r join r.pregunta p join p.examen e where r.alumnoId = ?1 group by e.id")
	//public Iterable<Long> findExamenIdsConRespuestaByAlumno(Long idAlumno);
	
}
