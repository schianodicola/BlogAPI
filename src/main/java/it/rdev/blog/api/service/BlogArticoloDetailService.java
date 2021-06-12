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
			articoloDTO.setDataPubblicazione(a.getStato().getDataPubblicazione());
			
		}
		
		return aDTO;
	}
	
	public Set<ArticoloDTO> findAll(String token){
		Set<Articolo> lista = aDao.findAll();
		Set<ArticoloDTO> listaDto = new HashSet<>();
		
		
			//for(Articolo a: lista) {
		listaDto= entityToDTO(lista);
					
			//}
		
			
		return listaDto;
	}
	
	private Articolo dtoToEntity(ArticoloDTO aDTO) {
		
		//svolgere la conversione
		return null;
	}
		
	public void pubblica(ArticoloDTO articolo) {
		aDao.save(dtoToEntity(articolo));
	}
	
	//elimina il post  tramite autore
	public boolean elimina(long id, long idAutore) {
		User utente = new User();
		utente.setId(idAutore);
		//return aDao.deleteBy;
		return false;
		
	}
	
	
}
