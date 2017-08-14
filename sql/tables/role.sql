use `arepa`;

CREATE TABLE `Role` (
  `idRole` int(11) NOT NULL AUTO_INCREMENT DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `customerRef` int(11) DEFAULT NULL,
  PRIMARY KEY (`idRole`),
  KEY `fk_Role_1_idx` (`customerRef`),
  CONSTRAINT `fk_Role_1` FOREIGN KEY (`customerRef`) REFERENCES `Customer` (`idCustomer`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;