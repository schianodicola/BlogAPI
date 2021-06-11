package it.rdev.blog.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.dao.CategoriaDAO;

@Service
public class BlogCategoriaDetailService {
	
	@Autowired 
	private CategoriaDAO caDao;
}
