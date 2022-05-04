USE psinder;

INSERT INTO User VALUES (1, 'Karol', 'Wojtyła', 'Jp2', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy', 'papiezpolak@o2.pl');
INSERT INTO User VALUES (2, 'Kojciech', 'Wowner', 'Nafai', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy', 'wojciech.kowner@gmail.pl');
INSERT INTO User VALUES (3, 'Krzegorz', 'Gucharski', 'Gelo', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy', 'grzegorz.kucharski@gmail.pl');
INSERT INTO User VALUES (4, 'Partek', 'Bietrzyba', 'Bartek', '$2a$10$Fn5ReBxSA7m5lsZEIQ/JyOmIKQYIm5iVUx3ZMYIspzOWSI88h7Noy', 'bartek.pietrzyba@gmail.pl');

INSERT INTO Pet VALUES (1, 1, 'Pimpek', 'Owczarek niemiecki', 'big', 'Fajny pies, bardzo fajny, miły, dzień dobry na klatce mówi');
INSERT INTO Pet VALUES (2, 1, 'Ciapek', 'Mieszaniec', 'medium', 'Państwo Izrael bezprawnie okupuje tereny należące do Palestyny ale opinia publiczna ma to w dupie i przyzwala na czystki etniczne');
INSERT INTO Pet VALUES (3, 2, 'Stefan', 'Szpic miniaturowy', 'small', 'Pochodzi z pomorza');

INSERT INTO PetAddress VALUES (1, 1, 'Lódź', 'Piotrkowska');
INSERT INTO PetAddress VALUES (2, 2, 'Lódź', 'Piotrkowska');
INSERT INTO PetAddress VALUES (3, 3, 'Warszawa', 'Narutowicza');

INSERT INTO PetAvailability VALUES (1, 1, 'pn', '13:30', '16:00');
INSERT INTO PetAvailability VALUES (2, 1, 'wt', '9:00', '12:00');
INSERT INTO PetAvailability VALUES (3, 1, 'wt', '18:30', '20:00');
INSERT INTO PetAvailability VALUES (4, 2, 'pn', '12:00', '16:00');
INSERT INTO PetAvailability VALUES (5, 2, 'cz', '15:00', '21:37');
INSERT INTO PetAvailability VALUES (6, 3, 'pn', '10:30', '14:50');
INSERT INTO PetAvailability VALUES (7, 3, 'sr', '10:00', '11:00');
INSERT INTO PetAvailability VALUES (8, 3, 'sr', '16:00', '19:00');
INSERT INTO PetAvailability VALUES (9, 3, 'so', '12:00', '15:00');

INSERT INTO Rating VALUES (1, 8, 'Bardzo ładny piesek, polecam', 3, 2);
INSERT INTO Rating VALUES (2, 8, 'Super zabawa, super spacery', 3, 1);
INSERT INTO Rating VALUES (3, 8, 'Pies ciągle szczekał...', 4, 1);
INSERT INTO Rating VALUES (4, 8, 'Nie polecam tego właściciela', 4, 2);
INSERT INTO Rating VALUES (5, 8, 'Spóźnialska osoba', 1, 4);

INSERT INTO Connection VALUES (1, 1, 1, 3, 'waiting');
INSERT INTO Connection VALUES (2, 2, 1, 3, 'accepted');
INSERT INTO Connection VALUES (3, 3, 2, 4, 'canceled');

INSERT INTO Chat VALUES (1, 2, 1, "Czy ten pies gryzie?");
INSERT INTO Chat VALUES (2, 2, 3, "Tak, gryzie");
INSERT INTO Chat VALUES (3, 2, 1, "A to nie chce go.");