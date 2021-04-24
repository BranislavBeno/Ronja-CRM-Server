ALTER TABLE ronja.customer DROP COLUMN first_name;
ALTER TABLE ronja.customer DROP COLUMN last_name;
ALTER TABLE ronja.customer ADD category varchar(50) DEFAULT NULL;
ALTER TABLE ronja.customer ADD focus varchar(50) DEFAULT NULL;
ALTER TABLE ronja.customer ADD status varchar(50) DEFAULT NULL;