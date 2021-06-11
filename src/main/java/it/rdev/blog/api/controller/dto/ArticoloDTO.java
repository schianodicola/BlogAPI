package it.rdev.blog.api.controller.dto;

import java.time.LocalDateTime;
import java.util.Set;


public class ArticoloDTO {
	
	private String titolo;
	private String sottotiolo;
	private String testo;
	private String autore;
	private LocalDateTime dataPubblicazione;
	private LocalDateTime dataUltimaModifica;
	private LocalDateTime dataCreazione;
	
	private Set<CategoriaDTO> categorie;
	private Set<TagDTO> tags;
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getSottotiolo() {
		return sottotiolo;
	}
	public void setSottotiolo(String sottotiolo) {
		this.sottotiolo = sottotiolo;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public LocalDateTime getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(LocalDateTime dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	public LocalDateTime getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(LocalDateTime dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Set<CategoriaDTO> getCategorie() {
		return categorie;
	}
	public void setCategorie(Set<CategoriaDTO> categorie) {
		this.categorie = categorie;
	}
	public Set<TagDTO> getTags() {
		return tags;
	}
	public void setTags(Set<TagDTO> tags) {
		this.tags = tags;
	}
	
	
}
