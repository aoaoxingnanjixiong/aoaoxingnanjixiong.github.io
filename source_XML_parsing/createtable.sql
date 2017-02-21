CREATE TABLE movies(
id integer NOT NULL AUTO_INCREMENT,
title varchar(100) NOT NULL,
year integer NOT NULL,
director varchar(100) NOT NULL,
banner_url varchar(200),
trailer_url varchar(200),
PRIMARY KEY (id)
);

CREATE TABLE stars(
id integer NOT NULL AUTO_INCREMENT,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
dob varchar(20),
photo_url varchar(200), 
PRIMARY KEY (id)
);

CREATE TABLE stars_in_movies(
star_id integer NOT NULL REFERENCEs stars(id),
movie_id integer NOT NULL REFERENCES movies(id)
);

CREATE TABLE genres(
id integer NOT NULL AUTO_INCREMENT,
name varchar(32) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE genres_in_movies(
genre_id integer NOT NULL REFERENCES genres(id),
movie_id integer NOT NULL REFERENCES movies(id)
);

CREATE TABLE creditcards(
id varchar(20) NOT NULL,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
expiration date NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE customers(
id integer NOT NULL AUTO_INCREMENT,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
cc_id varchar(20) NOT NULL REFERENCES creditcards(id), 
address varchar(200) NOT NULL,
email varchar(50) NOT NULL,
password varchar(20) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE sales(
id integer  NOT NULL AUTO_INCREMENT,
customer_id integer NOT NULL REFERENCES customers(id), 
movie_id integer NOT NULL REFERENCES movies(id),
sale_date date NOT NULL, 
PRIMARY KEY (id)
);
