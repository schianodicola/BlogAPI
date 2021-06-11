CREATE TABLE IF NOT EXISTS users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);
--
--
-- ddinuzzo/password02
INSERT INTO users
(username, password)
VALUES('ddinuzzo', '$2a$10$vj3PqvSqQSsLhknZpxU2oOIUOdmm6cpPu1shwcyXHVzba.xBWLe4K');

CREATE TABLE IF NOT EXISTS articolo (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  titolo varchar(50) NOT NULL,
  sottotitolo varchar(50),
  testo text,
  data_ultima_modifica datetime,
  data_creazione datetime NOT NULL,
  
  id_autore bigint unsigned NOT NULL,
  id_categoria bigint unsigned,
  
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS tag (
  nome varchar(20) NOT NULL,
  
  PRIMARY KEY (nome)
);

CREATE TABLE IF NOT EXISTS articolo_tags (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  
  id_tag varchar(20) NOT NULL,
  id_articolo bigint unsigned NOT NULL,
  
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS stato (
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	data_pubblicazione datetime NOT NULL,
    
    id_articolo bigint unsigned NOT NULL UNIQUE,
    
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categoria (
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	nome varchar(20) NOT NULL,
    
    PRIMARY KEY (id)
);

ALTER TABLE articolo
ADD CONSTRAINT articolo_fk1 FOREIGN KEY (id_autore) REFERENCES users(id);

ALTER TABLE articolo
ADD CONSTRAINT articolo_fk2 FOREIGN KEY (id_categoria) REFERENCES categoria(id);

ALTER TABLE articolo_tags
ADD CONSTRAINT tag_fk1 FOREIGN KEY(id_articolo) REFERENCES articolo(id);

ALTER TABLE articolo_tags
ADD CONSTRAINT tag_fk2 FOREIGN KEY(id_tag) REFERENCES tag(nome);

ALTER TABLE stato
ADD CONSTRAINT stato_fk1 FOREIGN KEY(id_articolo) REFERENCES articolo(id);

