package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.entity.Categoria;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("<= Categoria Controller Integration Test =>")
class CategoriaControllerIntegrationTests extends TestDbInit{

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private CategoriaDAO cDAO;

	@Test
	@DisplayName("Chiama URL .../api/categoria - tabella vuota")
	void getCategorieTest1() {
		client.get().uri("/api/categoria")
		.exchange()
		.expectStatus().isNotFound();		
	}
	
	@Test
	@DisplayName("Chiama URL .../api/categoria - tabella piena")
	void getCategorieTest2() {
		
		Categoria c = new Categoria();
		c.setNome("Hardware");
		cDAO.save(c);
		
		client.get().uri("/api/categoria")
		.exchange()
		.expectStatus().isOk();	
		
		cDAO.delete(c);
		
	}
}
