package it.rdev.blog.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.service.BlogTagDetailService;

@RestController
@RequestMapping(value= "/api/tag")
public class TagController {
	@Autowired
	private BlogTagDetailService blogTag;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	//@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAllTag() {
		
		Set<TagDTO> listaTag = blogTag.getAll();
		if(listaTag != null) return new ResponseEntity<>(listaTag, HttpStatus.OK);
		else return new ResponseEntity<>("Non esistono tag", HttpStatus.NOT_FOUND);
	}
	
	/*
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public void exce() {
		System.err.println("Tag non presenti - error query");
	}
	*/
}
