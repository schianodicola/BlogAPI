package it.rdev.blog.api.dao.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tag")
public class Tag {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String tag;
	
	@ManyToMany(mappedBy = "tags")
	@JsonIgnore
	private Set<Articolo> articoli= new HashSet<>();
	/*
	@ManyToMany
	@JoinTable(name="articolotag",
				joinColumns = @JoinColumn(name="id_articolo"),
				inverseJoinColumns =  @JoinColumn(name="id_tag") )
    private Set<Articolo> articoli = new HashSet<>();
	*/
	
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
