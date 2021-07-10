CREATE DATABASE musala;

CREATE TABLE `musala`.`Gateway` ( `SERIAL_NUM` VARCHAR(100) NOT NULL , `NAME` VARCHAR(100) NOT NULL , `IP_ADDRESS` VARCHAR(100) NOT NULL , PRIMARY KEY (`SERIAL_NUM`)) ;
CREATE TABLE `musala`.`PERIPHERAL_DEVICE` ( `UID` INT NOT NULL , `VENDOR` VARCHAR(100) NOT NULL ,
 `CREATION_DATE` DATETIME NOT NULL , `STATUS` VARCHAR(100) NOT NULL , `GETWAY_SERIAL_NUM` VARCHAR(100) NOT NULL , PRIMARY KEY (`UID`) ) ;
 ALTER TABLE `peripheral_device` CHANGE `UID` `UID` INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `peripheral_device` ADD FOREIGN KEY (`GETWAY_SERIAL_NUM`) REFERENCES `gateway`(`SERIAL_NUM`) ON DELETE RESTRICT ON UPDATE RESTRICT;