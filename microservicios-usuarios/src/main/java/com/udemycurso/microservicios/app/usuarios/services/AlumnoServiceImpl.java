package com.udemycurso.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemycurso.microservicios.app.usuarios.clients.CursoFeignClient;
import com.udemycurso.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.udemycurso.microservicios.commonalumnos.models.entity.Alumno;
import com.udemycurso.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	@Autowired
	CursoFeignClient cursoClient;
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findbyNombreOrApellido(String term) {
		return repository.findbyNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}
	
	@Override
	public void eliminarCursoAlumnoPorId(Long id) {
		cursoClient.eliminarCursoAlumnoPorId(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}
	
	

}
