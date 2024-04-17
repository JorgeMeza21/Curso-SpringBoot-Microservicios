package com.udemycurso.microservicios.app.cursos.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemycurso.microservicios.app.cursos.models.entity.Curso;
import com.udemycurso.microservicios.commonalumnos.models.entity.Alumno;
import com.udemycurso.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso>{

	public Curso findByAlumnoId(Long id);
	public Iterable<Long> obtenerExamenIdsConRespuestaAlumno(Long idAlumno);
	public Iterable<Alumno> listarPorIds(Iterable<Long> ids);
}
