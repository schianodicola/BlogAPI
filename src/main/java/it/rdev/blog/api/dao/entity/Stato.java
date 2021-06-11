package it.rdev.blog.api.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "stato")
public class Stato {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_articolo", nullable=true)
	private Articolo articolo;
	
	@Column
	private LocalDateTime data_pubblicazione;
	
	
}
