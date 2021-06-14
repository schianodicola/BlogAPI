package it.rdev.blog.api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Stato;


@Repository
public interface StatoDAO extends CrudRepository<Stato, Integer>{
	
		//cerca tramite stato
		@Query("Select a from Articolo a left join Stato s where a.id = s.id")
		Articolo findByStato(long id);
}
