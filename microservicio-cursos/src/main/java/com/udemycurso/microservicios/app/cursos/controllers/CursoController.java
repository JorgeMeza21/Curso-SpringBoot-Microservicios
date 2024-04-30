package com.udemycurso.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.udemycurso.microservicios.app.cursos.models.entity.Curso;
import com.udemycurso.microservicios.app.cursos.models.entity.CursoAlumno;
import com.udemycurso.microservicios.app.cursos.services.CursoService;
import com.udemycurso.microservicios.commonalumnos.models.entity.Alumno;
import com.udemycurso.microservicios.commons.controllers.CommonController;
import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;

@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@Value("${config.test.loadbalancer}")
	private String balanceadorTest;
	
	@DeleteMapping("/eliminar-cursoalumno/{id}")
	public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
		service.deleteCursoAlumnoById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/balanceadorTest")
	public ResponseEntity<?> testLoadBalancer() {
		
		Map<String, Object> body = new HashMap<>();
		body.put("variableEntorno", balanceadorTest);
		body.put("Cursos", service.findAll());
		
		return ResponseEntity.ok(body);
	}
	
	@GetMapping
	@Override
	public ResponseEntity<?> listar(){
		
		List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(ca -> {
				Alumno a = new Alumno();
				a.setId(ca.getAlumnoId());
				c.addAlumno(a);
			});
			
			return c;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cursos);		
	}
	
	@GetMapping({"/pagina"})
	@Override
	public ResponseEntity<?> listar(Pageable p){
		
		Page<Curso> pc = service.findAll(p).map(c -> {
			c.getCursoAlumnos().forEach(ca -> {
				Alumno a = new Alumno();
				a.setId(ca.getAlumnoId());
				c.addAlumno(a);
			});
			
			return c;
		});
		
		return ResponseEntity.ok().body(pc);		
	}
	
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoBd = o.get();
		
		if (!cursoBd.getCursoAlumnos().isEmpty()) {
			List<Long> alumnosIds = cursoBd.getCursoAlumnos().stream().map(ca -> ca.getAlumnoId()).collect(Collectors.toList());
			cursoBd.setAlumnos((List<Alumno>) service.listarPorIds(alumnosIds));
		}
		
		return ResponseEntity.ok(cursoBd);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result,@PathVariable Long id){
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Curso> c = this.service.findById(id);
		if (c.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoMod = c.get();
		cursoMod.setNombre(curso.getNombre());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoMod));
	}
	
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
		Optional<Curso> c = this.service.findById(id);
		if (c.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoMod = c.get();
		
		alumnos.forEach(a -> {
			CursoAlumno ca = new CursoAlumno();
			ca.setAlumnoId(a.getId());
			ca.setCurso(cursoMod);
			cursoMod.addCursoAlumno(ca);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoMod));
	}
	
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Curso> c = this.service.findById(id);
		if (c.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoMod = c.get();
		
		CursoAlumno ca = new CursoAlumno();
		ca.setAlumnoId(alumno.getId());
		
		cursoMod.removeCursoAlumno(ca);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoMod));
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumno(@PathVariable Long id){
		Curso curso = service.findByAlumnoId(id);
		
		if (curso != null) {
			List<Long> examenesId = (List<Long>) service.obtenerExamenIdsConRespuestaAlumno(id);
			
			List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
				if (examenesId.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			
			curso.setExamenes(examenes);
		}
		
		return ResponseEntity.ok(curso);
	}
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id){
		Optional<Curso> c = this.service.findById(id);
		if (c.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoMod = c.get();
		
		examenes.forEach(e -> {
			cursoMod.addExamen(e);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoMod));
	}
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
		Optional<Curso> c = this.service.findById(id);
		if (c.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoMod = c.get();
		
		cursoMod.removeExamen(examen);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoMod));
	}
}
