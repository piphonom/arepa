use `arepa`;

CREATE TABLE `Customer` (
  `idCustomer` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `passhash` varchar(256) DEFAULT NULL,
  `passsalt` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idCustomer`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
