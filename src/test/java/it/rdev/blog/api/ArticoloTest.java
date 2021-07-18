package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.UserDao;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.service.BlogArticoloDetailService;
import it.rdev.blog.api.service.BlogUserDetailsService;

//@Sql(scripts = {"/database_init.sql"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@Transactional
class ArticoloTest extends TestDbInit{
	//@InjectMocks
	@Autowired
	BlogArticoloDetailService blogArticolo;
	
	@Autowired
	//@Mock
	ArticoloDAO aDAO;
	
	
	@BeforeEach
	public void init() {
		
		User user = new User();
		user.setId(2);
		user.setUsername("test");
		user.setPassword("test");
		//user= (User) userService.loadUserByUsername("test");
		
		ArticoloDTO a= new ArticoloDTO();
		a.setTitolo("cpu");
		a.setSottotitolo("ciao");
		a.setTesto("ciao");
		a.setDataCreazione(LocalDateTime.now());
		a.setDataUltimaModifica(LocalDateTime.now());
		a.setAutore(user);
		//a.getAutore().setUsername("test");
		
		blogArticolo.save(a);
		a.setTitolo("ram");
		//blogArticolo.save(a);
		
		
	}
	
	@Test
	void findArticoloByAutore() {
		String autore="test";
		String au="cia";
		
		
		Set<ArticoloDTO> articoli= blogArticolo.findByAutore(autore);
		//System.out.println("AUTORE ARTICOLO:::: "+  articoli.iterator().next().getAutore().getUsername());
		//if(articoli.iterator().hasNext()) u= articoli.iterator().next().getAutore().getUsername();
		//assertAll(
				//() -> assertEquals(autore, "test"),
				
				//() -> assertNotNull(articoli, "gli articoli non devono essere nulli" );
				assertNotNull(articoli, "gli articoli non devono essere nulli" );
				//);
		//if(articoli !=null) {
		
		//for(ArticoloDTO a: articoli) {
				//ArticoloDTO a= articoli.iterator().next();
				while(articoli.iterator().hasNext())
				 assertEquals(autore, articoli.iterator().next().getAutore().getUsername() , "L'autore deve essere lo stesso");
				
				//);
		//}
		
		//}
		
	}
	
	
	@Test
	void findPerWord() {
		String s="cpu";
		Set<ArticoloDTO> articoli= blogArticolo.findXword(s);
		assertAll(
				() -> assertNotNull(articoli, "Articoli details shouldn't be null"),
				() -> assertEquals(s, articoli.iterator().next().getTitolo() , "the KEYWORD field should be equals to " + s)
				);
		
	}
	
	
	

}
