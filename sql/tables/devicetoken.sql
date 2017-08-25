use `arepa`;

CREATE TABLE `DeviceToken` (
  `idDeviceToken` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `deviceGroupRef` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDeviceToken`),
  KEY `fk_DeviceToken_1_idx` (`deviceGroupRef`),
  CONSTRAINT `fk_DeviceToken_1` FOREIGN KEY (`deviceGroupRef`) REFERENCES `DeviceGroup` (`idDeviceGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;