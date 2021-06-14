package it.rdev.blog.api.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Tag;

public interface TagDAO extends CrudRepository<Tag, Integer>{
	//cerca tramite un tag
	@Query("SELECT a FROM Articolo a WHERE a.tags= :tag")
	Set<Articolo> findByTags(String tag);
}
