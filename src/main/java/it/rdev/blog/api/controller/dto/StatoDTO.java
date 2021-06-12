package it.rdev.blog.api.controller.dto;

import java.time.LocalDateTime;

import it.rdev.blog.api.dao.entity.Articolo;

public class StatoDTO {
	private Articolo articolo;
	private LocalDateTime data_pubblicazione;
	
	public Articolo getArticolo() {
		return articolo;
	}
	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}
	public LocalDateTime getData_pubblicazione() {
		return data_pubblicazione;
	}
	public void setData_pubblicazione(LocalDateTime data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}
	
	
}
