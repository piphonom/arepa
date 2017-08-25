use `arepa`;

CREATE TABLE `Device` (
  `idDevice` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `pubId` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `deviceGroupRef` int(11) DEFAULT NULL,
  `certificateRef` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDevice`),
  KEY `fk_Device_1_idx` (`deviceGroupRef`),
  KEY `fk_Device_2_idx` (`certificateRef`),
  CONSTRAINT `fk_Device_1` FOREIGN KEY (`deviceGroupRef`) REFERENCES `DeviceGroup` (`idDeviceGroup`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_Device_2` FOREIGN KEY (`certificateRef`) REFERENCES `Certificate` (`idCertificate`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
