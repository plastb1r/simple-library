CREATE schema
if not exists librarydb;

DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS
(
  user_name VARCHAR(255) PRIMARY KEY,
  user_password VARCHAR(255)
);

DROP TABLE IF EXISTS BOOKS;

CREATE TABLE BOOKS
(
  book_isn VARCHAR(255) PRIMARY KEY,
  book_author VARCHAR(255) NOT NULL,
  book_name VARCHAR(255) NOT NULL,
  book_owner VARCHAR(255) DEFAULT NULL,
  foreign key (book_owner) references USERS(user_name)
);


INSERT INTO USERS
  (user_name, user_password)
VALUES
  ('Tom', '1'),
  ('Bob', '2'),
  ('Nancy', '3'),
  ('Kate', '4');

INSERT INTO BOOKS
  (book_isn, book_author,book_name, book_owner)
VALUES
  ('isn1', 'mom', 'How to Live', 'Tom'),
  ('isn2', 'dad', 'How to Survive', 'Bob'),
  ('isn3', 'grandma', 'How to Cook', 'Nancy'),
  ('isn4', 'grandpa', 'How to Build', 'Kate'),
  ('isn5', 'Mortimer J. Adler', 'How to Read a Book', 'Tom'),
  ('isn6', 'Dale Carnegie', 'How to Win Friends and Influence People', NULL),
  ('isn7', 'Margareta Magnusson', 'How to Free Yourself and your Family from a Lifetime of Clutter ', NULL),
  ('isn8', 'Kelly Williams Brown', 'How to Become a Grown-up in 468 Easy(ish) Steps', NULL),
  ('isn9', 'Jen Sincero', ' How to Stop Doubting Your Greatness and Start Living an Awesome Life ', NULL),
  ('isn10', 'Atul Gawande', 'How to Get Things Right ', NULL),
  ('isn11', 'Gabriel Wyner', 'How to Learn Any Language Fast and Never Forget It', NULL),
  ('isn12', 'Janey Fraser', 'How to Write Romantic Fiction', NULL);
