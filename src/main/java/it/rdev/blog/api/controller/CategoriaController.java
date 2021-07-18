package it.rdev.blog.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.service.BlogCategoriaDetailService;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	@Autowired
	private BlogCategoriaDetailService blogCategoria;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	//@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> get() {
		
		Set<CategoriaDTO> listaCategorie = blogCategoria.getAll();
		//if(listaCategorie != null) return new ResponseEntity<>(listaCategorie, HttpStatus.OK);
		if(!listaCategorie.isEmpty()) return new ResponseEntity<>(listaCategorie, HttpStatus.OK);
		else return new ResponseEntity<>("Non è presente nessuna categoria", HttpStatus.NOT_FOUND);
		
	}
	
	/*
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public void exce() {
		System.err.println("Categorie non presenti - error query");
	}
	*/
}
