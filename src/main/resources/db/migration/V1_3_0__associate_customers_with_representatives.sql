ALTER TABLE representative DROP COLUMN company_name;
ALTER TABLE representative ADD customer_id int(11) DEFAULT NULL;
ALTER TABLE representative ADD KEY `FK_CUSTOMER_idx` (`customer_id`);
ALTER TABLE representative ADD CONSTRAINT `FK_CUSTOMER` FOREIGN KEY (`customer_id`)
REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
