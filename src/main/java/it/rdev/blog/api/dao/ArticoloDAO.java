package it.rdev.blog.api.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Articolo;

@Repository
public interface ArticoloDAO extends CrudRepository<Articolo, Integer>{
	Set<Articolo> findByAutore(String user);
	Set<Articolo> findByCategoria(String categoria);
	
	@Query("SELECT a FROM articolo a WHERE a.titolo like :titolo OR a.sottotitolo like :titolo OR a.testo like :titolo")
	Set<Articolo> findXword(@Param("titolo") String word);
	
	@Query("SELECT a FROM articolo a")
	Set<Articolo> findAll();
	
	//cerca tramite un tag
	Set<Articolo> findByTags(Set<Articolo> tags);
	//cerca tramite id
	@Query("Select a from Articolo a where id = :id")
	Articolo findById(long id);
	
	//cerca tramite autore
	//elimina tramite id
}
