package com.udemycurso.microservicios.app.usuarios.services;

import java.util.List;


import com.udemycurso.microservicios.commonalumnos.models.entity.Alumno;
import com.udemycurso.microservicios.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{

	public List<Alumno> findbyNombreOrApellido(String term);
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	public void eliminarCursoAlumnoPorId(Long id);

}
