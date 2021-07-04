package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.UserDao;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.service.BlogArticoloDetailService;

//@Sql(scripts = {"/database_init.sql"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ArticoloTest extends TestDbInit{
	@Autowired
	private static BlogArticoloDetailService blogArticolo;
	//@Autowired
	
	private ArticoloDAO aDAO;
	//@Mock UserDao userDao;
	
	@BeforeEach
	public void init() {
		/*
		User user = new User();
		user.setId(100);
		user.setUsername("test");
		user.setPassword("password_100");
		*/
		ArticoloDTO a= new ArticoloDTO();
		a.getAutore().setUsername("test");
		/*
		blogArticolo.save(a);
		blogArticolo = new BlogArticoloDetailService();
		*/
	}
	
	@Test
	void findArticoloByAutore() {
		String autore="test";
		
		Set<ArticoloDTO> articoli= blogArticolo.findByAutore(autore);
		assertAll(
				() -> assertEquals(articoli.iterator().next().getAutore().getUsername() , autore),
				() -> assertNotNull(articoli, "gli articoli non devono essere nulli")
				);
		//if(articoli !=null) {
		/*
		for(ArticoloDTO a: articoli) {
			assertAll(
				
				() -> assertEquals(a.getAutore().getUsername() , autore, "the username field should be equals to " + autore)
				);
		}
		*/
		//}
		
	}
	
	
	@Test
	void findPerWord() {
		String s="cpu";
		Set<ArticoloDTO> articoli= blogArticolo.findXword(s);
		assertAll(
				() -> assertNotNull(articoli, "Articoli details shouldn't be null"),
				() -> assertEquals(articoli.iterator().next().getTitolo() , s, "the KEYWORD field should be equals to " + s)
				);
		
	}
	
	
	@Test
	void test() {
		fail("Test di Prova");
	}

}
