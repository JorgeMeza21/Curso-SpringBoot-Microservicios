package com.udemycurso.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemycurso.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.udemycurso.microservicios.app.examenes.models.repository.ExamenRepository;
import com.udemycurso.microservicios.commons.services.CommonServiceImpl;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Asignatura;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService{
	
	@Autowired
	private AsignaturaRepository asigRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Examen> findbyName(String term) {
		return repository.findbyName(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignatura() {
		return asigRepository.findAll();
	}

	
}
