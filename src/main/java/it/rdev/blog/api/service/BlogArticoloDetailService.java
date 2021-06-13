package it.rdev.blog.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.controller.dto.StatoDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Stato;
import it.rdev.blog.api.dao.entity.User;

@Service
public class BlogArticoloDetailService{
	
	@Autowired
	private ArticoloDAO aDao;
	
	public Set<ArticoloDTO> findXword(String s){
		Set<Articolo> a= aDao.findPerWord(s);
		Set<ArticoloDTO> aDTO= entityToDTO(a);
		return aDTO;
		
	}
	
	public Set<ArticoloDTO> entityToDTO(Set<Articolo> art){
		
		Set<ArticoloDTO> aDTO= new HashSet<>();
		
		for(Articolo a: art) {
			ArticoloDTO articoloDTO = new ArticoloDTO();
			
			articoloDTO.setTitolo(a.getTitolo());
			articoloDTO.setSottotitolo(a.getSottotitolo());
			articoloDTO.setTesto(a.getTesto());
			articoloDTO.setAutore(a.getAutore());
			articoloDTO.setCategoria(a.getCategoria());
			articoloDTO.setTags(a.getTags());
			articoloDTO.setDataCreazione(a.getDataCreazione());
			articoloDTO.setDataUltimaModifica(a.getDataUltimaModifica());
			//articoloDTO.setDataPubblicazione(a.getStato().getDataPubblicazione());
			articoloDTO.setStato(a.getStato());
			
		}
		
		return aDTO;
	}
	
	public Set<ArticoloDTO> findAll(String token){
		Set<Articolo> lista = aDao.findAll();
		Set<ArticoloDTO> listaDto = new HashSet<>();
			
		listaDto= entityToDTO(lista);
					
		
		return listaDto;
	}
	
	private Articolo dtoToEntity(ArticoloDTO aDTO) {
		
		//svolge la conversione
		Articolo a = new Articolo();
		
		a.setId(aDTO.getId());
		a.setTitolo(aDTO.getTitolo());
		a.setSottotitolo(aDTO.getSottotitolo());
		a.setTesto(aDTO.getTesto());
		//a.setDataPubblicazione(aDTO.getStato().getDataPubblicazione());
		a.setDataUltimaModifica(aDTO.getDataUltimaModifica());
		a.setDataCreazione(aDTO.getDataCreazione());
		a.setStato(aDTO.getStato());
		
		return a;
	}
		
	public void pubblica(ArticoloDTO articolo) {
		aDao.save(dtoToEntity(articolo));
	}
	
	//elimina il post tramite id post
	public boolean elimina(long id) {
		
		return aDao.deleteById(id);
		
	}
	
	
}
