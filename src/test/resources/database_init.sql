CREATE TABLE IF NOT EXISTS users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

--
-- ddinuzzo/password02
INSERT INTO users
(username, password)
VALUES('ddinuzzo', '$2a$10$vj3PqvSqQSsLhknZpxU2oOIUOdmm6cpPu1shwcyXHVzba.xBWLe4K');
-- user: test/ pass: test
INSERT INTO users
(username, password)
VALUES('test', '$2a$10$0ACdD9GIb.YpIxVrAyjJru0hPuHPicx0Jp.w/WARq8LbH8./xNYxi');

CREATE TABLE IF NOT EXISTS categoria (
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	nome varchar(20) NOT NULL,
    
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS articolo (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  titolo varchar(50) NOT NULL,
  sottotitolo varchar(50),
  testo text,
  data_ultima_modifica datetime,
  data_creazione datetime DEFAULT CURRENT_TIMESTAMP,
  
  id_autore bigint unsigned NOT NULL,
  id_categoria bigint unsigned,
  
  PRIMARY KEY (id),
  CONSTRAINT articolo_fk1 FOREIGN KEY (id_autore) REFERENCES users(id),
  CONSTRAINT articolo_fk2 FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);


CREATE TABLE IF NOT EXISTS tag (
  tag varchar(20) NOT NULL,
  
  PRIMARY KEY (tag)
);

CREATE TABLE IF NOT EXISTS articolo_tags (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  
  id_tag varchar(20) NOT NULL,
  id_articolo bigint unsigned NOT NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT tag_fk1 FOREIGN KEY(id_articolo) REFERENCES articolo(id),
  CONSTRAINT tag_fk2 FOREIGN KEY(id_tag) REFERENCES tag(tag)
);

CREATE TABLE IF NOT EXISTS stato (
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	data_pubblicazione datetime NOT NULL,
    
    id_articolo bigint unsigned NOT NULL UNIQUE,
    
    PRIMARY KEY (id),
    CONSTRAINT stato_fk1 FOREIGN KEY(id_articolo) REFERENCES articolo(id)
);



-- ALTER TABLE articolo
-- ADD CONSTRAINT articolo_fk1 FOREIGN KEY (id_autore) REFERENCES users(id);

-- ALTER TABLE articolo
-- ADD CONSTRAINT articolo_fk2 FOREIGN KEY (id_categoria) REFERENCES categoria(id);

-- ALTER TABLE articolo_tags
-- ADD CONSTRAINT tag_fk1 FOREIGN KEY(id_articolo) REFERENCES articolo(id);

-- ALTER TABLE articolo_tags
-- ADD CONSTRAINT tag_fk2 FOREIGN KEY(id_tag) REFERENCES tag(tag);

-- ALTER TABLE stato
-- ADD CONSTRAINT stato_fk1 FOREIGN KEY(id_articolo) REFERENCES articolo(id);


-- INSERT FOR TEST
-- INSERT INTO categoria (nome) VALUES('Hardware');
-- INSERT INTO articolo (titolo,sottotitolo,testo, data_ultima_modifica,data_creazione, id_autore) VALUES('cpu','ciao','ciao','2021-06-21T13:30:00','2021-06-21T13:30:00',2);
-- INSERT INTO stato (data_pubblicazione, id_articolo) VALUES('2021-06-21T13:30:00',1);



