package com.udemycurso.microservicios.app.cursos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemycurso.microservicios.app.cursos.clients.RespuestaFeignClient;
import com.udemycurso.microservicios.app.cursos.models.entity.Curso;
import com.udemycurso.microservicios.app.cursos.models.repository.CursoRepository;
import com.udemycurso.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService{

	@Autowired
	private RespuestaFeignClient client;
	
	@Override
	@Transactional(readOnly = true)
	public Curso findByAlumnoId(Long id) {
		return repository.findByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenIdsConRespuestaAlumno(Long idAlumno) {
		return client.obtenerExamenIdsConRespuestaAlumno(idAlumno);
	}

	
}
