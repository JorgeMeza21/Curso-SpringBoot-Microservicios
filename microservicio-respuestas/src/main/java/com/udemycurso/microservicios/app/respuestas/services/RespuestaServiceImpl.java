package com.udemycurso.microservicios.app.respuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemycurso.microservicios.app.respuestas.models.entity.Respuesta;
import com.udemycurso.microservicios.app.respuestas.models.repository.RespuestaRepository;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository respuestaRepo;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepo.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> findByAlumnoByExamen(Long idAlumno, Long idExamen) {
		return respuestaRepo.findByAlumnoByExamen(idAlumno, idExamen);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenIdsConRespuestaByAlumno(Long idAlumno) {
		return respuestaRepo.findExamenIdsConRespuestaByAlumno(idAlumno);
	}

}
