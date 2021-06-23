package it.rdev.blog.api.dao.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name= "articolo")
public class Articolo {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	@Column(name="titolo", length= 50, nullable=false)
	private String titolo;
	@Column(name="sottotitolo", length= 50)
	private String sottotitolo;
	@Column(name="testo", nullable=false)
	private String testo;
	@ManyToOne
	@JoinColumn(name="id_categoria", referencedColumnName = "id")
	private Categoria categoria;
	@ManyToOne
	@JoinColumn(name="id_autore", referencedColumnName = "id", nullable=false)
	private User autore;
	@ManyToMany
	@JoinTable(name="articolo_tags",
				joinColumns = @JoinColumn(name="id_articolo"),
				inverseJoinColumns =  @JoinColumn(name="id_tag") )
	private Set<Tag> tags;
	
	@OneToOne(mappedBy="articolo")
	private Stato stato;
	//private LocalDateTime dataPubblicazione;
	
	@Column
	private LocalDateTime dataUltimaModifica;
	@Column
	private LocalDateTime dataCreazione;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getSottotitolo() {
		return sottotitolo;
	}
	public void setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public User getAutore() {
		return autore;
	}
	public void setAutore(User autore) {
		this.autore = autore;
	}
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
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
	@Override
	public String toString() {
		return "Articolo [id=" + id + ", titolo=" + titolo + ", sottotitolo=" + sottotitolo + ", testo=" + testo
				+ ", categoria=" + categoria + ", autore=" + autore + ", tags=" + tags + ", stato=" + stato
				+ ", dataUltimaModifica=" + dataUltimaModifica + ", dataCreazione=" + dataCreazione + "]";
	}
	
	
	
}
