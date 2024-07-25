package com.udemycurso.microservicios.app.examenes.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udemycurso.microservicios.app.examenes.services.ExamenService;
import com.udemycurso.microservicios.commons.controllers.CommonController;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService>{

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Examen examenDB = o.get();
		examenDB.setNombre(examen.getNombre());
		
		/*List<Pregunta> eliminadas = new ArrayList<>();
		examenDB.getPreguntas().forEach(p -> {
			if (!examen.getPreguntas().contains(p)) {
				eliminadas.add(p);
			}
		});*/
		examenDB.getPreguntas()
		.stream()
		.filter(p -> !examen.getPreguntas().contains(p))
		.forEach(examenDB::removePregunta);
		
		/*eliminadas.forEach(p -> {
			examenDB.removePregunta(p);
		});*/
		
		examenDB.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDB));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrarPorNombre(@PathVariable String term){
		return ResponseEntity.ok(service.findbyName(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignatura());
	}
	
	@GetMapping("/examenesIds-por-preguntaIds")
	public ResponseEntity<?> buscarExamenesIdsPorPreguntaIds(@RequestParam List<Long> pregIds){
		return ResponseEntity.ok(service.findExamenIdsByPreguntaIds(pregIds));
	}
}
