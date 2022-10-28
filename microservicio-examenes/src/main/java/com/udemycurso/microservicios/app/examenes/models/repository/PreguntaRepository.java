package com.udemycurso.microservicios.app.examenes.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.udemycurso.microservicios.commonsexamenes.models.entity.Pregunta;

public interface PreguntaRepository extends CrudRepository<Pregunta, Long>{

}
