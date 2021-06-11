package it.rdev.blog.api.controller.dto;

import java.util.Set;

import it.rdev.blog.api.dao.entity.Articolo;


public class TagDTO {
	private String tag;
	private Set<Articolo> articoli;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Set<Articolo> getArticoli() {
		return articoli;
	}
	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}
	
	
}
