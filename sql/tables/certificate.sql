use `arepa`;

CREATE TABLE `Certificate` (
  `idCertificate` int(11) NOT NULL AUTO_INCREMENT,
  `value` blob,
  `serialNumber` bigint(20) DEFAULT NULL,
  `deviceGroupRef` int(11) DEFAULT NULL,
  `isRevoked` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idCertificate`),
  KEY `fk_Certificate_1_idx` (`deviceGroupRef`),
  CONSTRAINT `fk_Certificate_1` FOREIGN KEY (`deviceGroupRef`) REFERENCES `DeviceGroup` (`idDeviceGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;