package com.udemycurso.microservicios.app.examenes.services;

import java.util.List;

import com.udemycurso.microservicios.commons.services.CommonService;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Asignatura;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;

public interface ExamenService extends CommonService<Examen>{

	public List<Examen> findbyName(String term);
	public Iterable<Asignatura> findAllAsignatura();
	public List<Long> findExamenIdsByPreguntaIds(List<Long> preguntaIds);
}
