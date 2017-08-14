use `arepa`;

CREATE TABLE `DeviceGroup` (
  `idDeviceGroup` int(11) NOT NULL AUTO_INCREMENT,
  `ownerCustomerRef` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `certificateCA` blob,
  `isDeactivated` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idDeviceGroup`),
  KEY `fk_DeviceGroup_1_idx` (`ownerCustomerRef`),
  CONSTRAINT `fk_DeviceGroup_1` FOREIGN KEY (`ownerCustomerRef`) REFERENCES `Customer` (`idCustomer`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;