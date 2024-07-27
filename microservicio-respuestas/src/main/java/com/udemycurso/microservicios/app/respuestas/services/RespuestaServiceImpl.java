package com.udemycurso.microservicios.app.respuestas.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemycurso.microservicios.app.respuestas.clients.ExamenFeingClient;
import com.udemycurso.microservicios.app.respuestas.models.entity.Respuesta;
import com.udemycurso.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository respuestaRepo;
	
	@Autowired
	private ExamenFeingClient examenClient;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepo.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findByAlumnoByExamen(Long idAlumno, Long idExamen) {
		//return respuestaRepo.findByAlumnoByExamen(idAlumno, idExamen);
		/*Examen e = examenClient.obtenerExamenPorId(idExamen);
		
		List<Pregunta> preguntas = e.getPreguntas();
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		
		List<Respuesta> respuestas = (List<Respuesta>) respuestaRepo.findRespuestaByAlumnoByPregunta(idAlumno, preguntaIds);
		respuestas.stream().map(r -> {
			preguntas.forEach(p -> {
				if (r.getPreguntaId() == p.getId()) r.setPregunta(p); 
			});
			
			return r;
		}).collect(Collectors.toList());*/
		
		return respuestaRepo.findByAlumnoByExamen(idAlumno, idExamen);
	}

	@Override
	public Iterable<Long> findExamenIdsConRespuestaByAlumno(Long idAlumno) {
		//return respuestaRepo.findExamenIdsConRespuestaByAlumno(idAlumno);
		
		/*List<Respuesta> respuestas = (List<Respuesta>) respuestaRepo.findByAlumno(idAlumno);
		List<Long> examenesIds = Collections.emptyList();
		
		if (respuestas.size() > 0) {
			List<Long> preguntaIds = respuestas.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
			examenesIds = (List<Long>) examenClient.buscarExamenesIdsPorPreguntaIds(preguntaIds);
		}*/
		
		List<Respuesta> respuestas = (List<Respuesta>) respuestaRepo.findExamenIdsConRespuestaByAlumno(idAlumno);
		List<Long> exaxmenIds = respuestas
				.stream()
				.map(r -> r.getPregunta().getExamen().getId())
				.collect(Collectors.toList());
		
		return exaxmenIds;
	}

}
