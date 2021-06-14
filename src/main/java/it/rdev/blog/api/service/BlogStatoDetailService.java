package it.rdev.blog.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.StatoDTO;
import it.rdev.blog.api.dao.StatoDAO;
import it.rdev.blog.api.dao.entity.Stato;

@Service
public class BlogStatoDetailService {
	
	@Autowired
	private StatoDAO sDao;
	
	public StatoDTO entityToDTO(Stato stato) {
		StatoDTO sDTO= new StatoDTO();
		sDTO.setArticolo(stato.getArticolo());
		sDTO.setData_pubblicazione(stato.getDataPubblicazione());
		
		return sDTO;
		
	}
	
	//Pesca gli articoli con stato pubblicato dal db
	
}
