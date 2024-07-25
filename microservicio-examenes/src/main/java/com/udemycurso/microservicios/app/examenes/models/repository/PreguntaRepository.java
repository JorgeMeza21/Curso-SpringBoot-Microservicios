package com.udemycurso.microservicios.app.examenes.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.udemycurso.microservicios.commonsexamenes.models.entity.Pregunta;

public interface PreguntaRepository extends CrudRepository<Pregunta, Long>{

	@Query("select e.id from Pregunta p join p.examen e where p.id in ?1 group by e.id")
	public Iterable<Long> findExamenIdsByPreguntaIds(Iterable<Long> preguntaIds);
}
