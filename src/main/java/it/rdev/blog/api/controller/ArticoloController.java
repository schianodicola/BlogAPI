package it.rdev.blog.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.service.BlogArticoloDetailService;

@RestController
@RequestMapping(value= "/api/articolo")
public class ArticoloController {

		@Autowired
		private BlogArticoloDetailService blogArticolo;
		@Autowired
		private JwtTokenUtil jwtUtil;
		
		
		@RequestMapping(path = "/{id:\\d+}", method = RequestMethod.GET)
		@ResponseStatus(HttpStatus.OK)
		public ArticoloDTO getById(@PathVariable Integer id) {
			ArticoloDTO articolo = blogArticolo.findById(id);
			return null;
			
		}
		
		// restituisce gli articoli di tutti gli utenti e i propri notpublish (se loggati) to fix
		@RequestMapping(path = "", method = RequestMethod.GET)
		@ResponseStatus(HttpStatus.OK)
		public List<ArticoloDTO> getArticoli(@RequestHeader(name = "Authorization") String token) {
			List<ArticoloDTO> lArticoli= new ArrayList<>();
			
			if(token != null && token.startsWith("Bearer")) {
				Long userId = jwtUtil.getUserIdFromToken(token);
				
				//modificare: dovrà restituire i propri articoli (pubblicati e non)
				return (List<ArticoloDTO>) blogArticolo.findByAutore(token);
				
			}
			return (List<ArticoloDTO>) blogArticolo.findAll();
		
		}
		
		
		
}
