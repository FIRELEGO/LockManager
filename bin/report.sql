CREATE TABLE `reports` (
	`ReportID` INT(11) NOT NULL,
	`LockSerial` INT(11) NOT NULL,
	`Priority` CHAR(6) NOT NULL,
	`Progress` CHAR(8) NOT NULL,
	`Date` CHAR(8) NOT NULL,
	PRIMARY KEY (`ReportID`),
	INDEX `FK_reports_lock` (`LockSerial`),
	CONSTRAINT `FK_reports_lock` FOREIGN KEY (`LockSerial`) REFERENCES `lock` (`Serial`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
