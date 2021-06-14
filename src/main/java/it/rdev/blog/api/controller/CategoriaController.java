package it.rdev.blog.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.service.BlogCategoriaDetailService;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	@Autowired
	private BlogCategoriaDetailService blogCategoria;
	
	@ResponseStatus(HttpStatus.OK)
	public Set<CategoriaDTO> get() {
		
		Set<CategoriaDTO> listaCategorie = blogCategoria.getAll();
		return listaCategorie;
	}
}
