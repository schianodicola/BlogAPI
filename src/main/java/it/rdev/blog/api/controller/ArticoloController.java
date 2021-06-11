package it.rdev.blog.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.service.BlogArticoloDetailService;

@RestController
@RequestMapping(value= "/api/articolo")
public class ArticoloController {

		@Autowired
		private BlogArticoloDetailService blog;
}
