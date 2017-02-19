-- --------------------------------------------------------
-- Host:                         0151T3105C47541
-- Server version:               10.1.18-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for suncoast
CREATE DATABASE IF NOT EXISTS `suncoast` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `suncoast`;


-- Dumping structure for table suncoast.account
CREATE TABLE IF NOT EXISTS `account` (
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `JobTitle` varchar(50) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `isAwaitingApproval` bit(1) NOT NULL,
  `isApproved` bit(1) NOT NULL,
  `Question1` text,
  `Question2` text,
  `Question3` text,
  `Answer1` text,
  `Answer2` text,
  `Answer3` text,
  `PrivilegeLevel` int(1) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.account: ~3 rows (approximately)
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`FirstName`, `LastName`, `Email`, `JobTitle`, `Username`, `Password`, `isAwaitingApproval`, `isApproved`, `Question1`, `Question2`, `Question3`, `Answer1`, `Answer2`, `Answer3`, `PrivilegeLevel`) VALUES
	('cheyanne', 'manivong', 'chey@chey.com', 'Principal', 'chey', 'cheyanne', b'0', b'0', 'What was your childhood nickname?', 'What was your high school mascot?', 'What is your favorite web browser?', 'chey', 'charger', 'chrome', 3),
	('Natascha', 'Kempfe', 'natkem@gmail.com', '0', 'nat', 'pass', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL, 3),
	('nat', 'kempfe', 'nlkempfe@gmail.com', 'Principal', 'nlkempfe', '2334NK98', b'0', b'1', 'What was your childhood nickname?', 'What is your favorite web browser?', 'In what town was your first job?', 'nat', 'chrome', 'pbg', 1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


-- Dumping structure for table suncoast.account_copy
CREATE TABLE IF NOT EXISTS `account_copy` (
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `JobTitle` varchar(50) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` char(32) NOT NULL,
  `Salt` char(20) NOT NULL,
  `isAwaitingApproval` bit(1) NOT NULL,
  `isApproved` bit(1) NOT NULL,
  `Question1` text,
  `Question2` text,
  `Question3` text,
  `Answer1` text,
  `Answer2` text,
  `Answer3` text,
  `PrivilegeLevel` int(1) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table suncoast.account_copy: ~3 rows (approximately)
/*!40000 ALTER TABLE `account_copy` DISABLE KEYS */;
INSERT INTO `account_copy` (`FirstName`, `LastName`, `Email`, `JobTitle`, `Username`, `Password`, `Salt`, `isAwaitingApproval`, `isApproved`, `Question1`, `Question2`, `Question3`, `Answer1`, `Answer2`, `Answer3`, `PrivilegeLevel`) VALUES
	('cheyanne', 'manivong', 'chey@chey.com', 'Principal', 'chey', 'cheyanne', '', b'0', b'0', 'What was your childhood nickname?', 'What was your high school mascot?', 'What is your favorite web browser?', 'chey', 'charger', 'chrome', 3),
	('Natascha', 'Kempfe', 'natkem@gmail.com', '0', 'nat', 'pass', '', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL, 3),
	('Nick', 'Signori', 'nicholas.signori@gmail.com', 'lock', 'nick', '3add4ee00119642a167be7f1a00ed680', 'sdfsdfsdafasdfsdfadf', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL, 0),
	('nat', 'kempfe', 'nlkempfe@gmail.com', 'Principal', 'nlkempfe', '2334NK98', '', b'0', b'1', 'What was your childhood nickname?', 'What is your favorite web browser?', 'In what town was your first job?', 'nat', 'chrome', 'pbg', 1);
/*!40000 ALTER TABLE `account_copy` ENABLE KEYS */;


-- Dumping structure for table suncoast.course
CREATE TABLE IF NOT EXISTS `course` (
  `courseCode` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`courseCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.course: ~0 rows (approximately)
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;


-- Dumping structure for table suncoast.exam
CREATE TABLE IF NOT EXISTS `exam` (
  `examName` varchar(50) NOT NULL,
  `examType` varchar(50) NOT NULL,
  `month` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `time` double NOT NULL,
  `fee` double NOT NULL,
  `courseCode` int(11) NOT NULL,
  PRIMARY KEY (`examName`,`examType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.exam: ~2 rows (approximately)
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` (`examName`, `examType`, `month`, `day`, `year`, `time`, `fee`, `courseCode`) VALUES
	('AP Environmental Science', 'Alt', 5, 17, 2017, 1200, 90, 1),
	('AP Environmental Science', 'Regular', 5, 1, 2017, 800, 90, 1);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;


-- Dumping structure for table suncoast.form
CREATE TABLE IF NOT EXISTS `form` (
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.form: ~0 rows (approximately)
/*!40000 ALTER TABLE `form` DISABLE KEYS */;
/*!40000 ALTER TABLE `form` ENABLE KEYS */;


-- Dumping structure for table suncoast.lock
CREATE TABLE IF NOT EXISTS `lock` (
  `Serial` int(11) NOT NULL,
  `Combo` char(8) NOT NULL,
  `Barcode` int(11) NOT NULL,
  `YearAdded` int(11) NOT NULL,
  `YearLastUsed` int(11) NOT NULL,
  `TotalUses` int(11) NOT NULL,
  PRIMARY KEY (`Serial`),
  UNIQUE KEY `Barcode` (`Barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.lock: ~2 rows (approximately)
/*!40000 ALTER TABLE `lock` DISABLE KEYS */;
INSERT INTO `lock` (`Serial`, `Combo`, `Barcode`, `YearAdded`, `YearLastUsed`, `TotalUses`) VALUES
	(2, '22-22-23', 3, 2016, 2017, 4),
	(6, '12-45-32', 4, 2016, 2017, 1);
/*!40000 ALTER TABLE `lock` ENABLE KEYS */;


-- Dumping structure for table suncoast.lockerassignment
CREATE TABLE IF NOT EXISTS `lockerassignment` (
  `LockerNum` char(5) NOT NULL,
  `Serial` int(11) NOT NULL,
  PRIMARY KEY (`LockerNum`),
  UNIQUE KEY `Serial` (`Serial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.lockerassignment: ~3 rows (approximately)
/*!40000 ALTER TABLE `lockerassignment` DISABLE KEYS */;
INSERT INTO `lockerassignment` (`LockerNum`, `Serial`) VALUES
	('25', 1),
	('212', 2),
	('55', 5);
/*!40000 ALTER TABLE `lockerassignment` ENABLE KEYS */;


-- Dumping structure for table suncoast.log
CREATE TABLE IF NOT EXISTS `log` (
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Username` varchar(50) NOT NULL,
  `Info` varchar(200) NOT NULL,
  KEY `FK_log_account_copy` (`Username`),
  CONSTRAINT `FK_log_account_copy` FOREIGN KEY (`Username`) REFERENCES `account_copy` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.log: ~8 rows (approximately)
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` (`Date`, `Username`, `Info`) VALUES
	('2017-02-09 10:30:19', 'nlkempfe', 'sdfas'),
	('2017-02-10 09:23:24', 'nick', 'Login'),
	('2017-02-10 09:24:23', 'nick', 'Looked up lock serial # 2by serial.'),
	('2017-02-10 09:24:31', 'nick', 'Lock list viewed.'),
	('2017-02-10 09:25:02', 'nick', 'Login'),
	('2017-02-14 09:58:56', 'nick', 'Login'),
	('2017-02-14 09:59:15', 'nick', 'Login'),
	('2017-02-14 09:59:19', 'nick', 'Lock reports viewed.');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;


-- Dumping structure for table suncoast.obligation
CREATE TABLE IF NOT EXISTS `obligation` (
  `studentNumber` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `paid` binary(50) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`studentNumber`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.obligation: ~0 rows (approximately)
/*!40000 ALTER TABLE `obligation` DISABLE KEYS */;
/*!40000 ALTER TABLE `obligation` ENABLE KEYS */;


-- Dumping structure for table suncoast.reports
CREATE TABLE IF NOT EXISTS `reports` (
  `ReportID` int(11) NOT NULL AUTO_INCREMENT,
  `LockSerial` int(11) NOT NULL,
  `Priority` char(6) NOT NULL,
  `Progress` char(8) NOT NULL,
  `Date` char(8) NOT NULL,
  `Report` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ReportID`),
  KEY `FK_reports_lock` (`LockSerial`),
  CONSTRAINT `FK_reports_lock` FOREIGN KEY (`LockSerial`) REFERENCES `lock` (`Serial`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.reports: ~0 rows (approximately)
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` (`ReportID`, `LockSerial`, `Priority`, `Progress`, `Date`, `Report`) VALUES
	(1, 1, 'High', 'SUBMITED', '12-02-16', NULL);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;


-- Dumping structure for table suncoast.student
CREATE TABLE IF NOT EXISTS `student` (
  `studentNumber` int(11) NOT NULL,
  `gradeLevel` tinyint(1) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `middleName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zipcode` int(11) NOT NULL,
  `homePhone` varchar(50) DEFAULT NULL,
  `workPhone` varchar(50) DEFAULT NULL,
  `program` varchar(50) NOT NULL,
  `decalNumber` int(11) DEFAULT NULL,
  `boughtLocker` bit(1) NOT NULL,
  `boughtParkingDecal` bit(1) NOT NULL,
  `englishTeacher` varchar(50) NOT NULL,
  `englishPeriod` int(11) NOT NULL,
  PRIMARY KEY (`studentNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.student: ~3 rows (approximately)
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`studentNumber`, `gradeLevel`, `firstName`, `middleName`, `lastName`, `address`, `city`, `state`, `zipcode`, `homePhone`, `workPhone`, `program`, `decalNumber`, `boughtLocker`, `boughtParkingDecal`, `englishTeacher`, `englishPeriod`) VALUES
	(25382334, 12, 'Natascha', 'L', 'Kempfe', '14384 83rd LN N', 'Loxahatchee', 'FL', 33470, '17847559', '5632944', 'CS', 25, b'1', b'1', 'Mosley', 3),
	(28349363, 12, 'Nicholas', 'James', 'Signori', '152 Harbourside Circle', 'Jupiter', 'FL', 33477, '5615755908', '000000000', 'CS', 0, b'0', b'0', 'Mosley', 3);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;


-- Dumping structure for table suncoast.studentexam
CREATE TABLE IF NOT EXISTS `studentexam` (
  `studentNumber` int(11) NOT NULL,
  `examName` varchar(50) NOT NULL,
  `examType` varchar(50) NOT NULL,
  `feePaid` bit(1) NOT NULL,
  KEY `studentNumber` (`studentNumber`),
  KEY `examName` (`examName`,`examType`),
  CONSTRAINT `examName` FOREIGN KEY (`examName`, `examType`) REFERENCES `exam` (`examName`, `examType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `studentNumber` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.studentexam: ~0 rows (approximately)
/*!40000 ALTER TABLE `studentexam` DISABLE KEYS */;
INSERT INTO `studentexam` (`studentNumber`, `examName`, `examType`, `feePaid`) VALUES
	(25382334, 'AP Environmental Science', 'Regular', b'0');
/*!40000 ALTER TABLE `studentexam` ENABLE KEYS */;


-- Dumping structure for table suncoast.studentlocker
CREATE TABLE IF NOT EXISTS `studentlocker` (
  `studentNumber` int(11) NOT NULL,
  `LockerNum` char(5) DEFAULT NULL,
  KEY `LockerNum` (`LockerNum`),
  KEY `FK_studentlocker_student` (`studentNumber`),
  CONSTRAINT `LockerNum` FOREIGN KEY (`LockerNum`) REFERENCES `lockerassignment` (`LockerNum`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.studentlocker: ~0 rows (approximately)
/*!40000 ALTER TABLE `studentlocker` DISABLE KEYS */;
INSERT INTO `studentlocker` (`studentNumber`, `LockerNum`) VALUES
	(25382334, '25');
/*!40000 ALTER TABLE `studentlocker` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
