package it.rdev.blog.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
				lArticoli= (List<ArticoloDTO>) blogArticolo.findByAutore(token);
				if(lArticoli == null) exce();
				return lArticoli;
				
			}
			lArticoli= (List<ArticoloDTO>) blogArticolo.findAll();
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
