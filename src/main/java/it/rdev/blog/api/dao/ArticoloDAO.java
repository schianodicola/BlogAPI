package it.rdev.blog.api.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Articolo;

@Repository
public interface ArticoloDAO extends CrudRepository<Articolo, Integer>{
	
	@Query("SELECT a FROM Articolo a WHERE a.titolo like :titolo OR a.sottotitolo like :titolo OR a.testo like :titolo")
	Set<Articolo> findPerWord(@Param("titolo") String word);
	
	@Query("SELECT a FROM Articolo a")
	Set<Articolo> findAll();
	
	//cerca tramite autore
	Set<Articolo> findByAutore(String user);
	
	//cerca tramite id
	@Query("Select a from Articolo a where id = :id")
	Articolo findById(long id);
	
	//elimina tramite id
	@Query("DELETE FROM Articolo a WHERE a.id = :idArticolo")
	boolean deleteById(long idArticolo);
}
