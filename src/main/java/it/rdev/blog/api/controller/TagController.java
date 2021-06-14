package it.rdev.blog.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.service.BlogTagDetailService;

@RestController
@RequestMapping(value= "/api/tag")
public class TagController {
	@Autowired
	private BlogTagDetailService blogTag;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Set<TagDTO> getAllTag() {
		
		Set<TagDTO> listaTag = blogTag.getAll();
		return listaTag;
	}
}
