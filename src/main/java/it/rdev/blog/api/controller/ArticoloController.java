package it.rdev.blog.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Stato;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.service.BlogArticoloDetailService;
import it.rdev.blog.api.service.BlogCategoriaDetailService;



@RestController
@RequestMapping(value= "/api/articolo")
public class ArticoloController {

		@Autowired
		private BlogArticoloDetailService blogArticolo;
		//@Autowired
		//private BlogCategoriaDetailService blogCategoria;
		@Autowired
		private JwtTokenUtil jwtUtil;
		
		//Restituisce l'articolo di un determinato id
		//se è not publish, restituisce error 404, almeno che non sia l'autore dell'articolo
		@RequestMapping(path = "{id:\\d+}", method = RequestMethod.GET)
		public ResponseEntity<?> getById(@PathVariable long id, @RequestHeader(required = false, value = "Authorization") String token) {
			
			System.out.println("getById - id: "+ id);
			
			ArticoloDTO articolo = blogArticolo.findById(id);
			//System.out.println("ARTICOLO: "+articolo.toString());
			ResponseEntity<?> status = null;
			if(articolo!= null ) {
				//ADD TRY
				System.out.println("TOKEN: "+ token); 
				if(token != null && token.startsWith("Bearer")) {
					token = token.replaceAll("Bearer ", "");
					Long userId = jwtUtil.getUserIdFromToken(token);
					
					//System.out.println("USERid: "+ userId);
					if(articolo.getStato()!= null) status = new ResponseEntity<>(articolo, HttpStatus.OK);
					else if(articolo.getStato()== null && articolo.getAutore().getId()== userId) status = new ResponseEntity<>(articolo, HttpStatus.OK);
					else status = new ResponseEntity<>("[getById] Non hai i diritti di accesso", HttpStatus.NOT_FOUND);
				}
				
			}
			else {
				status = new ResponseEntity<>("[getById] Articolo non trovato/pubblicato ", HttpStatus.NOT_FOUND);
			}
			return status;
			
		}
		
		
		//TODO: Ottimizzare - troppo codice duplicato
		// restituisce gli articoli di tutti gli utenti e i propri notpublish (se loggati)
		@RequestMapping(path = "", method = RequestMethod.GET)
		public ResponseEntity<?> getArticoli(@RequestHeader(required = false, value = "Authorization") String token, @RequestParam(required = false) Map<String, String> parametri) {
			
			Set<ArticoloDTO> lArticoli= new HashSet<>();
			ArticoloDTO a=null;
			System.out.println("Sono in [getArticoli()] ");
			
			boolean trovato=false;
			Long userId = 0L;
			if(parametri !=null && !parametri.isEmpty()) {
				System.out.println("PARAMETRI NON NULLI ");
				for(String p: parametri.keySet()) {
					System.out.println("Param " + parametri.keySet());
					if(p.equals("id")) {
						//System.out.println("PARAMETRO = ID ");
						a= blogArticolo.findById(Long.parseLong( parametri.get(p) ));
						//System.out.println("ARTICOLO :" + a.toString());
						lArticoli.add(a);
						if(a!= null) trovato=true;
	
					}
					if(p.equals("categoria")) {
						Categoria c= new Categoria();
						c.setNome(parametri.get(p));
						
						lArticoli.addAll( blogArticolo.findByCategoria(c) );
						//System.out.println("ARTICOLO :" + c.toString());
						if(lArticoli!= null) trovato=true;
						
					}
					if(p.equals("tag")) {
						Tag t= new Tag();
						t.setTag(parametri.get(p));
						
						lArticoli.addAll( blogArticolo.findByTag(t));
						if(lArticoli!= null) trovato=true;
						
					}
					if(p.equals("autore")) {
						
						lArticoli.addAll( blogArticolo.findByAutore(parametri.get(p)) );
						if(lArticoli!= null) trovato=true;
						
					}
					if(p.equals("testo")) {
						if(parametri.get(p).length()>=3) {
							
							lArticoli.addAll( blogArticolo.findXword(parametri.get(p)) );
							if(!lArticoli.isEmpty()) trovato=true;
							
						}
						else return new ResponseEntity<>("Errore - Immetti almeno 3 caratteri. ", HttpStatus.BAD_REQUEST);
					}
					
				}
				
			
				
				//Long userId = 0L;
				if(token != null && token.startsWith("Bearer")) {
					
					token = token.replaceAll("Bearer ", "");
					try {
						 userId = jwtUtil.getUserIdFromToken(token);
						 
					}catch (ExpiredJwtException e) {
						System.err.println("TOKEN Scaduto! ");
					}
					
					long uId = userId.longValue();
					lArticoli.removeIf(s -> s.getStato()== null && s.getAutore().getId()!=uId);
					System.out.println("RIMUOVO ALCUNE BOZZE: " + uId);
				}
				else{
					lArticoli.removeIf(s -> s.getStato()== null );
					System.out.println("RIMUOVO LE BOZZE ");
				}
				
				if(trovato == true) return new ResponseEntity<>(lArticoli, HttpStatus.OK);
				else return new ResponseEntity<>("[getArticoli] Non ho trovato articoli", HttpStatus.NOT_FOUND);
			}
			
			
			
			
			
			lArticoli= blogArticolo.findAll();
			if(lArticoli == null) return new ResponseEntity<>("[getArticoli] Non ho trovato articoli", HttpStatus.NOT_FOUND);
			System.out.println("USERID: " + userId);
			
			if(token != null && token.startsWith("Bearer")) {
				token = token.replaceAll("Bearer ", "");
				userId = jwtUtil.getUserIdFromToken(token);
				
			}
			//if(userId != 0L) {
			long uId= userId.longValue();
			lArticoli.removeIf(s -> s.getStato()== null && s.getAutore().getId()!=uId);
			System.out.println("RIMUOVO ALCUNE BOZZE2: " + uId);
			//}
			return new ResponseEntity<>(lArticoli, HttpStatus.OK);
		
		}
		
		
		//Salva l'articolo in stato di bozza
		@RequestMapping(path = "", method = RequestMethod.POST)
		public ResponseEntity<?> post (
				@RequestHeader(required = true, value = "Authorization") String token,
				@RequestBody final ArticoloDTO articolo) {
			
			if(articolo==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			if(token != null && token.startsWith("Bearer" ) ) {
				token = token.replaceAll("Bearer ", "");
				try {
					Long userId = jwtUtil.getUserIdFromToken(token);
					articolo.getAutore().setId(userId);
					System.out.println("UserID: "+ userId);
				}catch (ExpiredJwtException e) {
					System.err.println("TOKEN Scaduto! ");
				}
				//articolo.getAutore().setId(userId);
				
				//salvo articolo - in caso contrario, lancio l'errore
				if(blogArticolo.save(articolo) != null) return new ResponseEntity<>("Articolo Salvato!", HttpStatus.NO_CONTENT);
				//else return new ResponseEntity<>("Articolo non inserito", HttpStatus.METHOD_FAILURE);
				else return new ResponseEntity<>("Articolo non inserito", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
			
			
		}
		
		//Modifica l'articolo
		@RequestMapping(path = "/{idArticolo:\\d+}", method = RequestMethod.PUT)
		public ResponseEntity<?> put (
						@RequestHeader(required = true, value = "Authorization") String token,
						@PathVariable Integer idAutore,
						@RequestBody final ArticoloDTO articolo) {
			
			if(idAutore!=null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			if(blogArticolo.findById(articolo.getId()) == null) return new ResponseEntity<>("L'articolo che vuoi modificare non presente nel db", HttpStatus.NOT_FOUND);
			
			if(token != null && token.startsWith("Bearer" ) ) {
				token = token.replaceAll("Bearer ", "");
				Long userId = jwtUtil.getUserIdFromToken(token);
				
				if(articolo.getAutore().getId() != idAutore) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
				
				articolo.getStato().setData_pubblicazione(LocalDateTime.now());
				//salvo articolo
				if(blogArticolo.save(articolo) == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				else return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
			
		}
		
		//cancella l'articolo tramite un id
		@RequestMapping(path = "/{id:\\d+}", method = RequestMethod.DELETE)
		public ResponseEntity<?> delete (
				@RequestHeader(required = true, value = "Authorization") String token,
				@PathVariable Integer id) {
			
			//controllo se l'articolo è presente
			if(blogArticolo.findById(id) == null) return new ResponseEntity<>("articolo non presente", HttpStatus.NOT_FOUND);
				
			if(token != null && token.startsWith("Bearer" ) ) {
				token = token.replaceAll("Bearer ", "");
				Long userId = jwtUtil.getUserIdFromToken(token);
				
				if(blogArticolo.deleteByUser(id, userId)) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
				else return new ResponseEntity<>("errore query -oppure- non sei l'autore dell'articolo" , HttpStatus.FORBIDDEN);
				
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			
			
		}
		
		
}
