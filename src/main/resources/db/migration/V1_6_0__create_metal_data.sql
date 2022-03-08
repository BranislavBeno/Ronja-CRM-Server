CREATE TABLE metal_data (
  `id` int NOT NULL AUTO_INCREMENT,
  `fetched` date DEFAULT NULL,
  `currency` varchar(50) DEFAULT NULL,
  `lme_aluminium` decimal(14,12) DEFAULT NULL,
  `lme_copper` decimal(13,12) DEFAULT NULL,
  `lme_lead` decimal(14,12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
