package it.rdev.blog.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.dao.TagDAO;
import it.rdev.blog.api.dao.entity.Tag;

@Service
public class BlogTagDetailService {
	
	@Autowired
	private TagDAO tDao;
	
	//Converte l'entità in dto
	public Set<TagDTO> entityToDTO(Set<Tag> tag){
		
		Set<TagDTO> tDTO = new HashSet<>();
		for (Tag t : tag) {
			
			TagDTO tagDTO = new TagDTO();
			tagDTO.setTag(t.getTag());
			tDTO.add(tagDTO);
		} 
		return tDTO;
	}
	
	public Set<TagDTO> getAll() {
		
		Set<Tag> tag = tDao.getAllTag();
		Set<TagDTO> listaTag = new HashSet<>();
		
		listaTag= entityToDTO(tag);
		return listaTag;
		
	}
	
	
}
