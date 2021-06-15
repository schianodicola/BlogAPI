package it.rdev.blog.api.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Stato;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.dao.entity.User;

@Repository
public interface ArticoloDAO extends CrudRepository<Articolo, Integer>{
	
	@Query("SELECT a FROM Articolo a WHERE a.titolo like :titolo OR a.sottotitolo like :titolo OR a.testo like :titolo")
	Set<Articolo> findPerWord(@Param("titolo") String word);
	
	@Query("SELECT a FROM Articolo a")
	Set<Articolo> findAll();
	
	//cerca tramite autore
	@Query("SELECT a FROM Articolo a WHERE a.autore= :autore")
	Set<Articolo> findByAutore(@Param("autore")String autore);
	
	@Query("SELECT a FROM Articolo a WHERE a.autore= :autore AND a.stato= :stato")
	Set<Articolo> findByAutoreNotPublish(@Param("autore")String autore, @Param("stato") Stato stato);
	
	//Cerca gli articoli di una determinata categoria
	@Query("SELECT a FROM Articolo a JOIN a.categoria c WHERE c.nome= :categoria")
	Set<Articolo> findByCategory(@Param("categoria")Categoria categoria);
	
	//Cerca gli articoli di un determinato tag
	@Query("SELECT a FROM Articolo a JOIN a.tags t WHERE t.tag= :tag")
	Set<Articolo> findByTag(@Param("tag")Tag tag);
		
	//cerca tramite id
	@Query("Select a from Articolo a where id = :id")
	Articolo findById(@Param("id")long id);
	
	//elimina tramite id
	@Query("DELETE FROM Articolo a WHERE a.id = :idArticolo")
	boolean deleteById(@Param("idArticolo")long idArticolo);
	
	//elimina tramite id
	@Query("DELETE FROM Articolo a WHERE a.id = :idArticolo AND a.autore= :user")
	boolean deleteByIdAndUser(@Param("idArticolo")long idArticolo, @Param("user")User user);
	 
}
