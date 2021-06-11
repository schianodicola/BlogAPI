package it.rdev.blog.api.controller.dto;

import java.time.LocalDateTime;

import it.rdev.blog.api.dao.entity.Articolo;

public class StatoDTO {
	private Articolo articolo;
	private LocalDateTime data_pubblicazione;
}
