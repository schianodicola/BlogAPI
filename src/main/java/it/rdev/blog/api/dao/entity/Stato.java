package it.rdev.blog.api.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stato")
public class Stato {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_articolo", nullable=true)
	@JsonIgnore
	private Articolo articolo;
	
	@Column
	private LocalDateTime dataPubblicazione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public LocalDateTime getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setData_pubblicazione(LocalDateTime dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	
	
}
