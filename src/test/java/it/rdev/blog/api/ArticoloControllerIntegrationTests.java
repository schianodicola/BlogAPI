package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import it.rdev.blog.api.dao.ArticoloDAO;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("<= ArticoloControllerIntegration Test =>")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = {"/database_init.sql"})
@Transactional
class ArticoloControllerIntegrationTests {

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private ArticoloDAO aDAO;
	
	private String token1;
	private boolean inizializzato=false;
	
	@BeforeEach
	public void inizialize() {
		
		if(!inizializzato) {
			
			register("test", "test");
			token1 = authenticate("test", "test");
		
			//Categoria categoria= new Categoria();
			
			
			for (int i = 1; i<4; i++) {
				client.post().uri("/api/articolo")
				.header("Authorization", "Bearer " + token1)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"titolo\": \"articolo"+ i +"\", "
						+ "\"sottotitolo\": \"sottotitolo"+ i +"\", "
						+ "\"testo\": \"testo dell'articolo numero " + i + "\", "
						+ "\"categoria\": \"Hardware\"}")
				.exchange().expectStatus().isNoContent();
			}
			
			
		}
		inizializzato=true;
		
		
	}
	
	private void register(String username, String password) {
		
		client.post().uri("/register")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue("{ \"username\": \"" + username + 
				"\", \"password\": \""+ password +"\" }")
		.exchange().expectStatus().isOk();
	}
	
	private String authenticate(String username, String password) {
		
		byte[] response = client.post().uri("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"username\": \"" + username + 
						"\", \"password\": \""+ password +"\" }")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.token").exists()
				.returnResult().getResponseBodyContent();
		
		String token=null;
		String res= new String(response);
		int i=res.lastIndexOf("\"");
		if(i >=0) {
			res= res.substring(0, i);
			i=res.lastIndexOf("\"");
			if(i>=0) token= res.substring(i+1);
			
		}
			
		return token;
	}
	
	@Test
	@DisplayName("Richiede gli articoli pubblicati")
	void testGETArticoliPublici() {
		client.get().uri("/api/articolo")
		.exchange()
		.expectStatus()
		.isNotFound();		
	}
	
	@Test
	@DisplayName("Richiede gli articoli pubblicati e privati")
	void testGETArticoloAutenticazione() {
		
		client.get().uri("/api/articolo")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + token1)
		.exchange()
		.expectStatus().isOk();		
	}
	
	@Test
	@DisplayName("Richiede l'articolo tramite ID (anche privato)")
	void testGETById() {
		
		client.get().uri("/api/articolo/1")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + token1)
		.exchange()
		.expectStatus().isOk();		
	}
}
