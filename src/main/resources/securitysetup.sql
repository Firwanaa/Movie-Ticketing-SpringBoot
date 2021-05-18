create table SEC_USER
(
  userId           BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName         VARCHAR(36) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL
) ;

---- ======================================================
---- Create Movie Table
---- ======================================================
--create table movie
--(
--  movieId           BIGINT NOT NULL Primary Key AUTO_INCREMENT,
--  movieName         VARCHAR(36) NOT NULL UNIQUE,
--  moviePrice        int,
--  availableSeats    int
--
--) ;
--
---- ======================================================
---- Create Movie_USER Table
---- ======================================================
--create table movie_user
--(
-- ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
--userId BIGINT NOT NULL,
--movieId BIGINT NOT NULL,
-- FOREIGN KEY (userId)
--	REFERENCES SEC_USER (userId),
--	FOREIGN KEY (movieId)
--    	REFERENCES movie (movieId),
--)
--
---- ======================================================
---- Insert Movie Table
---- ======================================================
--insert into movie (movieName, moviePrice, availableSeats)
--values ('movie1', 15, 100);









create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;


create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);

alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('qq', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

--insert into SEC_User (userName, encryptedPassword, ENABLED)
--values ('admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
--
--insert into SEC_User (userName, encryptedPassword, ENABLED)
--values ('PROG3275', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);



insert into sec_role (roleName)
values ('ROLE_ADMIN');

insert into sec_role (roleName)
values ('ROLE_USER');

--insert into sec_role (roleName)
--values ('ROLE_STUDENT');



insert into user_role (userId, roleId)
values (1, 1);

insert into user_role (userId, roleId)
values (1, 2);

CREATE TABLE movie_info (
    mpk INTEGER NOT NULL Primary Key AUTO_INCREMENT,
    movieId INTEGER,
    movieName VARCHAR(50),
    discountCat INTEGER,
    visitor_name VARCHAR(50),
    seatTaken INTEGER,
    mTime VARCHAR(25),
    moviePrice DECIMAL(7,2),
    aviSeats INTEGER
);


COMMIT;

