package it.rdev.blog.api.dao;

import org.springframework.data.jpa.repository.Query;

import it.rdev.blog.api.dao.entity.Articolo;



public interface StatoDAO {
	
		//cerca tramite stato
		@Query("Select a from Articolo a left join Stato s where a.id = s.id")
		Articolo findByStato(long id);
}
