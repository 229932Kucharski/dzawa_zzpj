DROP SCHEMA IF EXISTS psinder;

CREATE SCHEMA psinder;

CREATE TABLE psinder.user
(
    id        INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(40),
    lastname  VARCHAR(40),
    username  VARCHAR(69),
    password  CHAR(69),
    email     VARCHAR(320),
    verified  BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE psinder.pet
(
    id          INT NOT NULL AUTO_INCREMENT,
    user_id     INT NOT NULL,
    name        VARCHAR(40),
    race        VARCHAR(40),
    size        ENUM('small', 'medium', 'big', 'chonker'),
    description VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES psinder.user (id)
);

CREATE TABLE psinder.petaddress
(
    id     INT NOT NULL AUTO_INCREMENT,
    pet_id INT NOT NULL,
    city   VARCHAR(40),
    street VARCHAR(69),
    PRIMARY KEY (id),
    FOREIGN KEY (pet_id) REFERENCES psinder.pet (id)
);

CREATE TABLE psinder.petavailability
(
    id        INT NOT NULL AUTO_INCREMENT,
    pet_id    INT NOT NULL,
    day       ENUM('pn', 'wt', 'sr', 'cz', 'pt', 'so', 'nd'),
    from_hour VARCHAR(5),
    to_hour   VARCHAR(5),
    PRIMARY KEY (id),
    FOREIGN KEY (pet_id) REFERENCES psinder.pet (id)
);

CREATE TABLE psinder.connection
(
    id        INT NOT NULL AUTO_INCREMENT,
    pet_id    INT NOT NULL,
    owner_id  INT NOT NULL,
    walker_id INT NOT NULL,
    status    ENUM('waiting', 'accepted', 'canceled'),
    PRIMARY KEY (id),
    FOREIGN KEY (pet_id) REFERENCES psinder.pet (id),
    FOREIGN KEY (owner_id) REFERENCES psinder.user (id),
    FOREIGN KEY (walker_id) REFERENCES psinder.user (id)
);

CREATE TABLE psinder.chat
(
    id            INT NOT NULL AUTO_INCREMENT,
    connection_id INT NOT NULL,
    user_id       INT NOT NULL,
    text          VARCHAR(255),
    date_created  DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (connection_id) REFERENCES psinder.connection (id),
    FOREIGN KEY (user_id) REFERENCES psinder.user (id)
);

CREATE TABLE psinder.rating
(
    id           INT NOT NULL AUTO_INCREMENT,
    rating       TINYINT(2),
    comment      VARCHAR(255),
    from_user_id INT NOT NULL,
    to_user_id   INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (from_user_id) REFERENCES psinder.user (id),
    FOREIGN KEY (to_user_id) REFERENCES psinder.user (id)
);

USE
psinder;

INSERT INTO user
VALUES (1, 'Drzysztow', 'Kwużnik', 'Krzysiek', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy',
        'krzysiek.dwuznik@gmail.com', false);
INSERT INTO user
VALUES (2, 'Kojciech', 'Wowner', 'Nafai', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy',
        'wojciech.kowner@gmail.com', false);
INSERT INTO user
VALUES (3, 'Krzegorz', 'Gucharski', 'Gelo', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy',
        'grzegorz.kucharski@gmail.com', false);
INSERT INTO user
VALUES (4, 'Partek', 'Bietrzyba', 'Bartek', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy',
        'bartek.pietrzyba@gmail.com', false);
INSERT INTO user
VALUES (5, 'Paulina', 'Papiernik', 'Paulina', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy',
        'paulina.papiernik@gmail.com', false);
INSERT INTO user
VALUES (6, 'Sabriela', 'Gzkilondz', 'Gabrysia', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy',
        'gabriela.szkilondz@gmail.com', false);


INSERT INTO pet
VALUES (1, 1, 'Pimpek', 'Owczarek niemiecki', 'big', 'Fajny pies, bardzo fajny, miły, dzień dobry na klatce mówi');
INSERT INTO pet
VALUES (2, 1, 'Ciapek', 'Mieszaniec', 'medium', 'Moze i troche mieszany, ale za to jaki puszysty.');
INSERT INTO pet
VALUES (3, 2, 'Stefan', 'Szpic miniaturowy', 'small', 'Pochodzi z pomorza');

INSERT INTO petaddress
VALUES (1, 1, 'Lódź', 'Piotrkowska');
INSERT INTO petaddress
VALUES (2, 2, 'Lódź', 'Piotrkowska');
INSERT INTO petaddress
VALUES (3, 3, 'Warszawa', 'Narutowicza');

INSERT INTO petavailability
VALUES (1, 1, 'pn', '13:30', '16:00');
INSERT INTO petavailability
VALUES (2, 1, 'wt', '9:00', '12:00');
INSERT INTO petavailability
VALUES (3, 1, 'wt', '18:30', '20:00');
INSERT INTO petavailability
VALUES (4, 2, 'pn', '12:00', '16:00');
INSERT INTO petavailability
VALUES (5, 2, 'cz', '15:00', '21:37');
INSERT INTO petavailability
VALUES (6, 3, 'pn', '10:30', '14:50');
INSERT INTO petavailability
VALUES (7, 3, 'sr', '10:00', '11:00');
INSERT INTO petavailability
VALUES (8, 3, 'sr', '16:00', '19:00');
INSERT INTO petavailability
VALUES (9, 3, 'so', '12:00', '15:00');

INSERT INTO rating
VALUES (1, 8, 'Bardzo ładny piesek, polecam', 3, 2);
INSERT INTO rating
VALUES (2, 8, 'Super zabawa, super spacery', 3, 1);
INSERT INTO rating
VALUES (3, 8, 'Pies ciągle szczekał...', 4, 1);
INSERT INTO rating
VALUES (4, 8, 'Nie polecam tego właściciela', 4, 2);
INSERT INTO rating
VALUES (5, 8, 'Spóźnialska osoba', 1, 4);

INSERT INTO connection
VALUES (1, 1, 1, 3, 'waiting');
INSERT INTO connection
VALUES (2, 2, 1, 3, 'accepted');
INSERT INTO connection
VALUES (3, 3, 2, 4, 'canceled');