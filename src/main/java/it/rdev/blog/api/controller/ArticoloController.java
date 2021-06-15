package it.rdev.blog.api.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.service.BlogArticoloDetailService;
import it.rdev.blog.api.service.BlogCategoriaDetailService;

@RestController
@RequestMapping(value= "/api/articolo")
public class ArticoloController {

		@Autowired
		private BlogArticoloDetailService blogArticolo;
		@Autowired
		private BlogCategoriaDetailService blogCategoria;
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
		
		//TO FIX: with Map Collection in request param
		//TODO: add other controlls
		// restituisce gli articoli di tutti gli utenti e i propri notpublish (se loggati)
		@RequestMapping(path = "", method = RequestMethod.GET)
		public ResponseEntity<?> getArticoli(@RequestHeader(required = false, value = "Authorization") String token, @RequestParam(required = false) Set<String> parametri) {
			Set<ArticoloDTO> lArticoli= new HashSet<>();
			
			
			
			Iterator<String> p= parametri.iterator();
			int cont=0;
			//DA OTTIMIZZARE: funziona solo in poche casistiche
			while(p.hasNext()) {
				if(p.equals("id")) {
					
					ArticoloDTO a= blogArticolo.findById(Integer.parseInt( p.next() ));
					return new ResponseEntity<>(a, HttpStatus.OK);
				}
				if(p.equals("categoria")) {
					Categoria c= new Categoria();
					c.setNome(p.next());
					lArticoli= blogArticolo.findByCategoria(c);
					return new ResponseEntity<>(lArticoli, HttpStatus.OK);
				}
				if(p.equals("tag")) {
					Tag t= new Tag();
					t.setTag(p.next());
					lArticoli= blogArticolo.findByTag(t);
					return new ResponseEntity<>(lArticoli, HttpStatus.OK);
				}
				if(p.equals("autore")) {
					lArticoli= blogArticolo.findByAutore(p.next());
					return new ResponseEntity<>(lArticoli, HttpStatus.OK);
				}
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
			
			/*
			if(token != null && token.startsWith("Bearer")) {
				Long userId = jwtUtil.getUserIdFromToken(token);
				
				//modificare: dovrà restituire i propri articoli (pubblicati e non)
				lArticoli= blogArticolo.findByAutore(token);
				if(lArticoli == null) exce();
				return lArticoli;
				
			}
			*/
			lArticoli= blogArticolo.findAll();
			if(lArticoli == null) exce();
			return new ResponseEntity<>(lArticoli, HttpStatus.OK);
		
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
