package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.service.BlogArticoloDetailService;

class ArticoloTest {
	@Autowired
	private BlogArticoloDetailService blogArticolo;
	@Autowired
	private ArticoloDAO aDAO;
	
	@Test
	void findArticoloByAutore() {
		String autore="test";
		Set<ArticoloDTO> articoli= blogArticolo.findByAutore(autore);
		assertAll(
				() -> assertNotNull(articoli, "Articoli details shouldn't be null"),
				() -> assertEquals(articoli.toString() , autore, "the username field should be equals to " + autore)
				);
		
	}
	
	
	@Test
	void findPerWord() {
		String s="cpu";
		Set<ArticoloDTO> articoli= blogArticolo.findXword(s);
		assertAll(
				() -> assertNotNull(articoli, "Articoli details shouldn't be null"),
				() -> assertEquals(articoli.toString() , s, "the KEYWORD field should be equals to " + s)
				);
		
	}
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
