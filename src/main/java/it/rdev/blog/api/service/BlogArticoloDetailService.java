package it.rdev.blog.api.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.controller.dto.StatoDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Stato;
import it.rdev.blog.api.dao.entity.Tag;
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
			
			articoloDTO.setId(a.getId());
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
			
			aDTO.add(articoloDTO);
		}
		
		return aDTO;
	}
	
	public Set<ArticoloDTO> findAll(){
		Set<Articolo> lista = aDao.findAll();
		Set<ArticoloDTO> listaDto = new HashSet<>();
			
		listaDto= entityToDTO(lista);
					
		
		return listaDto;
	}
	
	//svolge la conversione
	private Articolo dtoToEntity(ArticoloDTO aDTO) {
		
		
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
	public boolean deleteByUser(long idArticolo, long idAutore) {
		User autore= new User();
		autore.setId(idAutore);
		return aDao.deleteByIdAndUser(idArticolo, autore);
	}
	
	//cerca tramite autore
	public Set<ArticoloDTO> findByAutore(String user){
		Set<ArticoloDTO> listaDto = new HashSet<>();
		
		listaDto= entityToDTO(aDao.findByAutore(user));
		return listaDto;
		//return null;
		
	}
	
	/*
	//cerca tramite autore gli articoli pubblicati e non
	public Set<ArticoloDTO> findByAutoreNotPublish(String user, Stato stato){
		Set<ArticoloDTO> listaDto = new HashSet<>();
			
		listaDto= entityToDTO(aDao.findByAutoreNotPublish(user, stato ));
		return listaDto;
						
	}
	*/
	
	public ArticoloDTO findById(long id) {
		Set<ArticoloDTO> listaDto = new HashSet<>();
		Set<Articolo> a= new HashSet<>();
		a.add(aDao.findById(id));
		//System.out.println("" +a.toString());
		listaDto= entityToDTO(a);
		
		//Iterator<ArticoloDTO> i= listaDto.iterator();
		//art=listaDto.iterator().next();
		//return i.next();
		System.out.println("LISTA DTO Piena? "+listaDto.iterator().hasNext());
		return listaDto.iterator().next();
		
	}
	
	public Set<ArticoloDTO> findByCategoria(Categoria categoria) {
		Set<ArticoloDTO> listaDto = new HashSet<>();
		
		listaDto= entityToDTO(aDao.findByCategory(categoria.getNome() ));
		return listaDto;
		
	}
	public Set<ArticoloDTO> findByTag(Tag tag) {
		Set<ArticoloDTO> listaDto = new HashSet<>();
		
		listaDto= entityToDTO(aDao.findByTag(tag.getTag()));
		return listaDto;
		
	}
	
	public Articolo save(ArticoloDTO articolo) {
		
		return aDao.save(dtoToEntity(articolo) );
	}

	
	
}
