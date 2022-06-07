DROP SCHEMA IF EXISTS psinder;

CREATE SCHEMA psinder;

CREATE TABLE psinder.user (
	id INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(40),
    lastname VARCHAR(40),
    username VARCHAR(69),
    password CHAR(69),
    email VARCHAR(320),
    verified BOOLEAN,
    PRIMARY KEY (id)
    );

CREATE TABLE psinder.pet (
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(40),
    race VARCHAR(40),
    size ENUM('small', 'medium', 'big', 'chonker'),
    description VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES psinder.user(id)
    );
    
CREATE TABLE psinder.petaddress (
	id INT NOT NULL AUTO_INCREMENT,
    pet_id INT NOT NULL,
    city VARCHAR(40),
    street VARCHAR(69),
    PRIMARY KEY(id),
    FOREIGN KEY (pet_id) REFERENCES psinder.pet(id)
    );
    
CREATE TABLE psinder.petavailability (
	id INT NOT NULL AUTO_INCREMENT,
    pet_id INT NOT NULL,
    day ENUM('pn', 'wt', 'sr', 'cz', 'pt', 'so', 'nd'),
    from_hour VARCHAR(5),
    to_hour VARCHAR(5),
    PRIMARY KEY(id),
    FOREIGN KEY (pet_id) REFERENCES psinder.pet(id)
    );

CREATE TABLE psinder.connection (
	id INT NOT NULL AUTO_INCREMENT,
    pet_id INT NOT NULL,
    owner_id INT NOT NULL,
    walker_id INT NOT NULL,
    status ENUM('waiting', 'accepted', 'canceled'),
    PRIMARY KEY(id),
    FOREIGN KEY (pet_id) REFERENCES psinder.pet(id),
    FOREIGN KEY (owner_id) REFERENCES psinder.user(id),
    FOREIGN KEY (walker_id) REFERENCES psinder.user(id)
    );
    
CREATE TABLE psinder.chat (
    id INT NOT NULL AUTO_INCREMENT,
	connection_id INT NOT NULL,
    user_id INT NOT NULL,
    text VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (connection_id) REFERENCES psinder.connection(id),
	FOREIGN KEY (user_id) REFERENCES psinder.user(id)
    );

CREATE TABLE psinder.rating (
	id INT NOT NULL AUTO_INCREMENT,
    rating TINYINT(2),
    comment VARCHAR(255),
    from_user_id INT NOT NULL,
    to_user_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (from_user_id) REFERENCES psinder.user(id),
    FOREIGN KEY (to_user_id) REFERENCES psinder.user(id)
    );
    