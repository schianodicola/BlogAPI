package it.rdev.blog.api.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
		
		//Restituisce l'articolo di un determinato id
		//se è not publish, restituisce error 404, almeno che non sia l'autore dell'articolo
		@RequestMapping(path = "/{id:\\d+}", method = RequestMethod.GET)
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<ArticoloDTO> getById(@PathVariable Integer id, @RequestHeader(required = false, value = "Authorization") String token) {
			
			ArticoloDTO articolo = blogArticolo.findById(id);
			ResponseEntity<ArticoloDTO> status = null;
			if(articolo!= null && articolo.getStato().getDataPubblicazione()== null) {
				if(token != null && token.startsWith("Bearer")) {
					Long userId = jwtUtil.getUserIdFromToken(token);
					
					if(articolo.getAutore().getId()== userId) status = new ResponseEntity<>(articolo, HttpStatus.OK);
					else status = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
				
			}
			return status;
			
		}
		
		// restituisce gli articoli di tutti gli utenti e i propri notpublish (se loggati) to fix
		@RequestMapping(path = "", method = RequestMethod.GET)
		@ResponseStatus(HttpStatus.OK)
		public Set<ArticoloDTO> getArticoli(@RequestHeader(required = false, value = "Authorization") String token) {
			Set<ArticoloDTO> lArticoli= new HashSet<>();
			
			if(token != null && token.startsWith("Bearer")) {
				Long userId = jwtUtil.getUserIdFromToken(token);
				
				//modificare: dovrà restituire i propri articoli (pubblicati e non)
				lArticoli= blogArticolo.findByAutore(token);
				if(lArticoli == null) exce();
				return lArticoli;
				
			}
			lArticoli= blogArticolo.findAll();
			if(lArticoli == null) exce();
			return lArticoli;
		
		}
		
		//ricordati di fare altri controlli nel metodo
		@RequestMapping(path = "/{idArticolo:\\d+}", method = RequestMethod.PUT)
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void put (
				@RequestHeader(required = true, value = "Authorization") String token,
				@PathVariable Integer idArticolo,
				@RequestBody final ArticoloDTO articolo) {
			
			//salvo articolo
			if(blogArticolo.save(articolo) == null) exce2();
			
		}
		
		@RequestMapping(path = "/{id:\\d+}", method = RequestMethod.DELETE)
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void delete (
				@RequestHeader(required = true, value = "Authorization") String token,
				@PathVariable Integer id) {
			
			if(blogArticolo.deleteByUser(id, 0)) exce2();
			
		}
		
		
		@ResponseStatus(code = HttpStatus.NOT_FOUND)
		public void exce() {
			System.err.println("Articoli non presenti - error query");
		}
		
		@ResponseStatus(code = HttpStatus.NOT_FOUND)
		public void exce2() {
			System.err.println("L'ID passato non è presente nel DB - error query");
		}
		
}
