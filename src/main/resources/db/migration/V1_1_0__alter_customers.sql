ALTER TABLE customer DROP COLUMN first_name;
ALTER TABLE customer DROP COLUMN last_name;
ALTER TABLE customer ADD category varchar(50) DEFAULT NULL;
ALTER TABLE customer ADD focus varchar(50) DEFAULT NULL;
ALTER TABLE customer ADD status varchar(50) DEFAULT NULL;