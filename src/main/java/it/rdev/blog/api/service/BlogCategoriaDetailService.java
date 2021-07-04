package it.rdev.blog.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Tag;

@Service
public class BlogCategoriaDetailService {
	
	@Autowired 
	private CategoriaDAO cDao;
	
	//Converte l'entità in dto
	public Set<CategoriaDTO> entityToDTO(Set<Categoria> categoria){
		
		Set<CategoriaDTO> cDTO = new HashSet<>();
		for (Categoria c : categoria) {
			
			CategoriaDTO catDTO = new CategoriaDTO();
			catDTO.setNome(c.getNome());
			catDTO.setId(c.getId());
			cDTO.add(catDTO);
		} 
		return cDTO;
	}
	
	//Pesca tutte le categorie dal db
	public Set<CategoriaDTO> getAll(){
		Set<Categoria> categorie= cDao.getAllCategories();
		Set<CategoriaDTO> listaCategorie= new HashSet<>();
		
		listaCategorie = entityToDTO(categorie);
		return listaCategorie;
		
	}
	
	public CategoriaDTO getCategoria(String nome) {
		Set<Categoria> c= new HashSet<>();
		c.add( cDao.getCategoria(nome) );
		if(c.isEmpty()) return null;
		CategoriaDTO cDTO= entityToDTO(c).iterator().next();
		
		return cDTO;
	}
	
}
