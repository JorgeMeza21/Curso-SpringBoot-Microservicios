package com.udemycurso.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.udemycurso.microservicios.commonsexamenes.models.entity.Examen;

public interface ExamenRepository extends PagingAndSortingRepository<Examen, Long>{

	@Query("select e from Examen e where e.nombre like %?1%")
	public List<Examen> findbyName(String term);
}
