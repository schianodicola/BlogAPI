package it.rdev.blog.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import it.rdev.blog.api.controller.dto.UserDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.UserDao;

import it.rdev.blog.api.dao.entity.User;

@Sql({"/database_init.sql"})
public class TestDbInit {
	
	@AfterAll
	public static void destroy(@Autowired UserDao userDao) {
		userDao.deleteAll();
	}
	
	@BeforeEach
	public void init(@Autowired UserDao uDao, @Autowired ArticoloDAO aDao) {
		User u= new User();
		u.setUsername("Test");
		u.setPassword("Test");
		
		
		uDao.save(u);
		
	}

}
