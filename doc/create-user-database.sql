drop user if exists 'naivejudge'@'localhost';
drop database if exists naivejudge;
create database naivejudge;
create user 'naivejudge'@'localhost' IDENTIFIED BY 'naivejudge';
GRANT ALL PRIVILEGES ON naivejudge. * TO 'naivejudge'@'localhost';
