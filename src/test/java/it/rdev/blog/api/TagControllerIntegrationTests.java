package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.dao.TagDAO;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Tag;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("<= Tag Controller Integration Test =>")
class TagControllerIntegrationTests extends TestDbInit{

	@Autowired
	private WebTestClient client;
	
	//@Autowired
	@Mock
	private TagDAO tDAO;
	
	@Test
	@DisplayName("Chiama URL .../api/tag - tabella vuota")
	void getTagTest1() {
		client.get().uri("/api/tag")
		.exchange()
		.expectStatus().isNotFound();
				
	}
	
	@Test
	@DisplayName("Chiama URL .../api/tag - tabella pieno")
	void getTagTest2() {
		
		Tag t = new Tag();
		t.setTag("panini");
		tDAO.save(t);
		
		client.get().uri("/api/tag")
		.exchange()
		.expectStatus().isOk();
	}
	
	

}
