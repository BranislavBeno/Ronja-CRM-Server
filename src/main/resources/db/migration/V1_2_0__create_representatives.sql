CREATE TABLE representative (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `region` varchar(50) DEFAULT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `last_visit` date DEFAULT NULL,
  `scheduled_visit` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;