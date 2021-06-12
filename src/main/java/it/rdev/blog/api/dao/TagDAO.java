package it.rdev.blog.api.dao;

import java.util.Set;

import it.rdev.blog.api.dao.entity.Articolo;

public interface TagDAO {
	//cerca tramite un tag
		Set<Articolo> findByTags(String tags);
}
