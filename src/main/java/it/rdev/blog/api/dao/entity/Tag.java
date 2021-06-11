package it.rdev.blog.api.dao.entity;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String tag;
	
	@ManyToMany(mappedBy = "tags")
	private Set<Articolo> articoli;
	
}
