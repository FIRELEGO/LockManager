-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.18-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for oldsuncoast
CREATE DATABASE IF NOT EXISTS `oldsuncoast` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `oldsuncoast`;


-- Dumping structure for table oldsuncoast.locks
CREATE TABLE IF NOT EXISTS `locks` (
  `lock_serial_number` int(11) NOT NULL,
  `combo` int(11) NOT NULL,
  PRIMARY KEY (`lock_serial_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table oldsuncoast.locks: ~0 rows (approximately)
/*!40000 ALTER TABLE `locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `locks` ENABLE KEYS */;


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
  `Password` char(32) NOT NULL,
  `isAwaitingApproval` bit(1) NOT NULL,
  `isApproved` bit(1) NOT NULL,
  `Question1` text,
  `Question2` text,
  `Question3` text,
  `Answer1` text,
  `Answer2` text,
  `Answer3` text,
  `PrivilegeLevel` int(1) NOT NULL,
  `Salt` char(20) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.account: ~5 rows (approximately)
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`FirstName`, `LastName`, `Email`, `JobTitle`, `Username`, `Password`, `isAwaitingApproval`, `isApproved`, `Question1`, `Question2`, `Question3`, `Answer1`, `Answer2`, `Answer3`, `PrivilegeLevel`, `Salt`) VALUES
	('cheyanne', 'manivong', 'chey@chey.com', 'Principal', 'chey', 'cheyanne', b'0', b'0', 'What was your childhood nickname?', 'What was your high school mascot?', 'What is your favorite web browser?', 'chey', 'charger', 'chrome', 3, ''),
	('Nick', 'Signori', 'nic=yholas.signori@gmail.com', '0', 'low', '3cdadf1d4eb19c16b8fa41cdcec06518', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL, 0, 'LFP/R,-z5Myx;^_M0|~C'),
	('Natascha', 'Kempfe', 'natkem@gmail.com', '0', 'nat', '3cdadf1d4eb19c16b8fa41cdcec06518', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL, 3, 'LFP/R,-z5Myx;^_M0|~C'),
	('Nick', 'Signori', 'nicholas.signori@gmail.com', '0', 'nick', '3cdadf1d4eb19c16b8fa41cdcec06518', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL, 3, 'LFP/R,-z5Myx;^_M0|~C'),
	('nat', 'kempfe', 'nlkempfe@gmail.com', 'Principal', 'nlkempfe', '2334NK98', b'0', b'1', 'What was your childhood nickname?', 'What is your favorite web browser?', 'In what town was your first job?', 'nat', 'chrome', 'pbg', 1, '');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


-- Dumping structure for table suncoast.exam
CREATE TABLE IF NOT EXISTS `exam` (
  `examName` varchar(50) NOT NULL,
  `examType` varchar(50) NOT NULL,
  `month` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `time` varchar(50) NOT NULL,
  `fee` double NOT NULL,
  PRIMARY KEY (`examName`,`examType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.exam: ~54 rows (approximately)
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` (`examName`, `examType`, `month`, `day`, `year`, `time`, `fee`) VALUES
	('AP Art History', 'REGULAR', 4, 2, 2017, '12:00 PM', 93),
	('AP Biology', 'ALT', 4, 19, 2017, '12:00 PM', 93),
	('AP Biology', 'REGULAR', 4, 8, 2017, '8:00 AM', 93),
	('AP Calculus AB', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP Calculus AB', 'REGULAR', 4, 9, 2017, '8:00 AM', 93),
	('AP Calculus BC', 'ALT', 4, 18, 2017, '8:00 AM', 93),
	('AP Calculus BC', 'REGULAR', 4, 9, 2017, '8:00 AM', 93),
	('AP Chemistry', 'REGULAR', 4, 1, 2017, '8:00 AM', 93),
	('AP Chinese Language and Culture', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP Comparative Government & Politics', 'ALT', 4, 17, 2017, '12:00 PM', 93),
	('AP Comparative Government & Politics', 'REGULAR', 4, 11, 2017, '8:00 AM', 93),
	('AP Computer Science A', 'ALT', 4, 18, 2017, '8:00 AM', 93),
	('AP Computer Science A', 'REGULAR', 4, 2, 2017, '8:00 AM', 93),
	('AP Computer Science Principles', 'REGULAR', 4, 5, 2017, '12:00 PM', 93),
	('AP English Language & Composition', 'ALT', 4, 17, 2017, '12:00 PM', 93),
	('AP English Language & Composition', 'REGULAR', 4, 10, 2017, '8:00 AM', 93),
	('AP English Literature & Composition', 'ALT', 4, 19, 2017, '8:00 AM', 93),
	('AP English Literature & Composition', 'REGULAR', 4, 3, 2017, '8:00 AM', 93),
	('AP Environmental Science', 'ALT', 4, 17, 2017, '12:00 PM', 93),
	('AP Environmental Science', 'REGULAR', 4, 1, 2017, '8:00 AM', 93),
	('AP European History', 'ALT', 4, 18, 2017, '12:00 PM', 93),
	('AP European History', 'REGULAR', 4, 12, 2017, '12:00 PM', 93),
	('AP French Language and Culture', 'ALT', 4, 17, 2017, '12:00 PM', 93),
	('AP French Language and Culture', 'REGULAR', 4, 9, 2017, '12:00 PM', 93),
	('AP Human Geography', 'ALT', 4, 18, 2017, '8:00 AM', 93),
	('AP Human Geography', 'REGULAR', 4, 12, 2017, '8:00 AM', 93),
	('AP Macroeconomics', 'ALT', 4, 17, 2017, '8:00 AM', 93),
	('AP Macroeconomics', 'REGULAR', 4, 10, 2017, '12:00 PM', 93),
	('AP Microeconomics', 'ALT', 4, 17, 2017, '12:00 PM', 93),
	('AP Microeconomics', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP Microeconomics', 'REGULAR', 4, 12, 2017, '8:00 AM', 93),
	('AP Music Theory', 'REGULAR', 4, 8, 2017, '8:00 AM', 93),
	('AP Physics 1', 'ALT', 4, 18, 2017, '12:00 PM', 93),
	('AP Physics 1', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP Physics 1', 'REGULAR', 4, 2, 2017, '12:00 PM', 93),
	('AP Physics 2', 'REGULAR', 4, 3, 2017, '12:00 PM', 93),
	('AP Physics C: Electricity and Magnetism', 'REGULAR', 4, 8, 2017, '2:00 PM', 93),
	('AP Physics C: Mechanics', 'REGULAR', 4, 8, 2017, '12:00 PM', 93),
	('AP Psychology', 'ALT', 4, 19, 2017, '8:00 AM', 93),
	('AP Psychology', 'REGULAR', 4, 1, 2017, '12:00 PM', 93),
	('AP Seminar', 'REGULAR', 4, 4, 2017, '12:00 PM', 93),
	('AP Spanish Language and Culture', 'ALT', 4, 18, 2017, '12:00 PM', 93),
	('AP Spanish Language and Culture', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP Spanish Language and Culture', 'REGULAR', 4, 2, 2017, '8:00 AM', 93),
	('AP Statistics', 'ALT', 4, 17, 2017, '8:00 AM', 93),
	('AP Statistics', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP Statistics', 'REGULAR', 4, 11, 2017, '12:00 PM', 93),
	('AP United States Government & Politics', 'ALT', 4, 17, 2017, '8:00 AM', 93),
	('AP United States Government & Politics', 'REGULAR', 4, 4, 2017, '8:00 AM', 93),
	('AP United States History', 'ALT', 4, 17, 2017, '8:00 AM', 93),
	('AP United States History', 'ALT ALT', 4, 26, 2017, '0:00AM', 93),
	('AP United States History', 'REGULAR', 4, 5, 2017, '8:00 AM', 93),
	('AP World History', 'ALT', 4, 18, 2017, '8:00 AM', 93),
	('AP World History', 'REGULAR', 4, 11, 2017, '8:00 AM', 93);
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

-- Dumping data for table suncoast.lock: ~3 rows (approximately)
/*!40000 ALTER TABLE `lock` DISABLE KEYS */;
INSERT INTO `lock` (`Serial`, `Combo`, `Barcode`, `YearAdded`, `YearLastUsed`, `TotalUses`) VALUES
	(1, '1-11-12', 5, 2016, 2017, 63),
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
	('3-242', 1),
	('2-457', 2),
	('2-451', 5);
/*!40000 ALTER TABLE `lockerassignment` ENABLE KEYS */;


-- Dumping structure for table suncoast.log
CREATE TABLE IF NOT EXISTS `log` (
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Username` varchar(50) NOT NULL,
  `Info` varchar(200) NOT NULL,
  KEY `FK_log_account` (`Username`),
  CONSTRAINT `FK_log_account` FOREIGN KEY (`Username`) REFERENCES `account` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.log: ~124 rows (approximately)
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` (`Date`, `Username`, `Info`) VALUES
	('2017-02-09 10:30:19', 'nlkempfe', 'sdfas'),
	('2017-02-10 09:23:24', 'nick', 'Login'),
	('2017-02-10 09:24:23', 'nick', 'Looked up lock serial # 2by serial.'),
	('2017-02-10 09:24:31', 'nick', 'Lock list viewed.'),
	('2017-02-10 09:25:02', 'nick', 'Login'),
	('2017-02-14 09:58:56', 'nick', 'Login'),
	('2017-02-14 09:59:15', 'nick', 'Login'),
	('2017-02-14 09:59:19', 'nick', 'Lock reports viewed.'),
	('2017-02-24 10:53:15', 'nick', 'Login'),
	('2017-02-24 10:53:28', 'nick', 'Logged off.'),
	('2017-02-24 10:57:21', 'nick', 'Login'),
	('2017-02-24 11:00:52', 'nick', 'Logged off.'),
	('2017-02-27 09:55:01', 'nick', 'Report filed.'),
	('2017-02-27 09:55:27', 'nick', 'Logged off.'),
	('2017-02-27 09:55:33', 'nick', 'Login'),
	('2017-02-27 09:55:37', 'nick', 'Lock reports viewed.'),
	('2017-02-27 09:55:54', 'nick', 'Report filed.'),
	('2017-02-27 09:56:51', 'nick', 'Logged off.'),
	('2017-02-27 09:57:05', 'nick', 'Login'),
	('2017-02-27 09:57:21', 'nick', 'Report filed.'),
	('2017-02-27 09:58:23', 'nick', 'Report filed.'),
	('2017-02-27 09:58:25', 'nick', 'Lock reports viewed.'),
	('2017-02-27 09:58:30', 'nick', 'Logged off.'),
	('2017-02-27 09:59:45', 'nick', 'Login'),
	('2017-02-27 10:00:41', 'nick', 'Lock reports viewed.'),
	('2017-02-27 10:00:55', 'nick', 'Lock reports viewed.'),
	('2017-02-27 10:01:57', 'nick', 'Login'),
	('2017-02-27 10:01:59', 'nick', 'Lock reports viewed.'),
	('2017-02-27 10:02:06', 'nick', 'Logged off.'),
	('2017-02-27 10:02:12', 'nick', 'Logged off.'),
	('2017-02-27 10:02:20', 'nick', 'Login'),
	('2017-02-27 10:04:28', 'nick', 'Report filed.'),
	('2017-02-27 10:04:31', 'nick', 'Lock reports viewed.'),
	('2017-02-27 10:04:42', 'nick', 'Logged off.'),
	('2017-03-01 09:33:00', 'nick', 'Login'),
	('2017-03-01 09:33:14', 'nick', 'Logged off.'),
	('2017-03-01 10:48:50', 'nick', 'Login'),
	('2017-03-01 10:49:45', 'nick', 'Lock reports viewed.'),
	('2017-03-01 10:56:21', 'nick', 'Logged off.'),
	('2017-03-03 09:40:06', 'nick', 'Login'),
	('2017-03-03 09:40:19', 'nick', 'Looked up all with the combo 12-12-12'),
	('2017-03-03 09:40:20', 'nick', 'Looked up all with the combo 12-12-12'),
	('2017-03-03 09:40:43', 'nick', 'Looked up all with the combo 22-22-23'),
	('2017-03-03 09:41:39', 'nick', 'Logged off.'),
	('2017-03-03 09:53:02', 'nick', 'Login'),
	('2017-03-03 09:54:34', 'nick', 'Looked up all with the combo 45-45-45'),
	('2017-03-03 09:54:39', 'nick', 'Logged off.'),
	('2017-03-07 13:20:55', 'nick', 'Login'),
	('2017-03-07 13:21:08', 'nick', 'Logged off.'),
	('2017-03-12 18:38:54', 'nick', 'Login'),
	('2017-03-12 18:43:42', 'nick', 'Login'),
	('2017-03-12 18:43:47', 'nick', 'Logged off.'),
	('2017-03-12 18:48:51', 'nick', 'Login'),
	('2017-03-12 18:49:06', 'nick', 'Lock reports viewed.'),
	('2017-03-12 18:50:48', 'nick', 'Lock reports viewed.'),
	('2017-03-12 18:50:57', 'nick', 'Logged off.'),
	('2017-03-12 18:52:59', 'nick', 'Login'),
	('2017-03-12 18:53:01', 'nick', 'Lock reports viewed.'),
	('2017-03-12 18:53:13', 'nick', 'Logged off.'),
	('2017-03-12 18:53:33', 'nick', 'Login'),
	('2017-03-12 18:53:34', 'nick', 'Lock reports viewed.'),
	('2017-03-12 18:53:41', 'nick', 'Logged off.'),
	('2017-03-12 18:53:46', 'nick', 'Logged off.'),
	('2017-03-12 18:53:55', 'nick', 'Login'),
	('2017-03-12 18:54:03', 'nick', 'Logged off.'),
	('2017-03-12 18:56:52', 'nick', 'Login'),
	('2017-03-12 18:57:01', 'nick', 'Lock reports viewed.'),
	('2017-03-12 18:57:48', 'nick', 'Logged off.'),
	('2017-03-12 19:14:54', 'low', 'Login'),
	('2017-03-12 19:15:19', 'low', 'Logged off.'),
	('2017-03-12 19:19:51', 'low', 'Login'),
	('2017-03-12 19:19:54', 'low', 'Lock reports viewed.'),
	('2017-03-12 19:20:03', 'low', 'Logged off.'),
	('2017-03-12 19:22:58', 'nick', 'Login'),
	('2017-03-12 19:23:01', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:23:23', 'nick', 'Logged off.'),
	('2017-03-12 19:26:42', 'nick', 'Login'),
	('2017-03-12 19:26:44', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:26:47', 'nick', 'Logged off.'),
	('2017-03-12 19:42:17', 'nick', 'Login'),
	('2017-03-12 19:42:20', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:43:32', 'nick', 'Logged off.'),
	('2017-03-12 19:43:42', 'nick', 'Login'),
	('2017-03-12 19:43:44', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:44:21', 'nick', 'Logged off.'),
	('2017-03-12 19:45:29', 'nick', 'Login'),
	('2017-03-12 19:45:31', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:45:39', 'nick', 'Logged off.'),
	('2017-03-12 19:51:57', 'nick', 'Login'),
	('2017-03-12 19:52:00', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:52:06', 'nick', 'Complaint id: 1 resolved.'),
	('2017-03-12 19:52:11', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:52:50', 'nick', 'Logged off.'),
	('2017-03-12 19:56:11', 'nick', 'Login'),
	('2017-03-12 19:56:14', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:56:18', 'nick', 'Complaint id: 2 resolved.'),
	('2017-03-12 19:56:22', 'nick', 'Logged off.'),
	('2017-03-12 19:57:07', 'nick', 'Login'),
	('2017-03-12 19:57:09', 'nick', 'Lock reports viewed.'),
	('2017-03-12 19:57:13', 'nick', 'Complaint id: 3 resolved.'),
	('2017-03-12 19:57:19', 'nick', 'Complaint id: 3 resolved.'),
	('2017-03-12 19:57:27', 'nick', 'Logged off.'),
	('2017-03-12 20:04:56', 'nick', 'Login'),
	('2017-03-12 20:05:29', 'nick', 'Logged off.'),
	('2017-03-12 20:07:00', 'nick', 'Login'),
	('2017-03-12 20:09:17', 'nick', 'Login'),
	('2017-03-12 20:09:44', 'nick', 'Logged off.'),
	('2017-03-12 20:13:13', 'nick', 'Logged off.'),
	('2017-03-12 21:39:45', 'nick', 'Login'),
	('2017-03-12 21:40:04', 'nick', 'Logged off.'),
	('2017-03-12 21:40:59', 'nick', 'Login'),
	('2017-03-12 21:41:18', 'nick', 'Logged off.'),
	('2017-03-12 21:44:05', 'nick', 'Login'),
	('2017-03-12 21:44:14', 'nick', 'Logged off.'),
	('2017-03-12 21:45:32', 'nick', 'Login'),
	('2017-03-12 21:46:12', 'nick', 'Logged off.'),
	('2017-03-12 21:48:09', 'nick', 'Login'),
	('2017-03-12 21:48:18', 'nick', 'Logged off.'),
	('2017-03-12 21:53:03', 'nick', 'Login'),
	('2017-03-12 21:54:06', 'nick', 'Logged off.'),
	('2017-03-12 21:55:43', 'nick', 'Login'),
	('2017-03-12 21:55:55', 'nick', 'Logged off.'),
	('2017-03-12 22:08:16', 'nick', 'Login'),
	('2017-03-12 22:08:20', 'nick', 'Logged off.'),
	('2017-03-12 23:38:13', 'nick', 'Login'),
	('2017-03-12 23:38:31', 'nick', 'Logged off.'),
	('2017-03-12 23:40:03', 'nick', 'Login'),
	('2017-03-12 23:40:13', 'nick', 'Logged off.'),
	('2017-03-12 23:40:46', 'nick', 'Login'),
	('2017-03-12 23:41:18', 'nick', 'Logged off.'),
	('2017-03-12 23:43:25', 'nick', 'Login'),
	('2017-03-12 23:43:36', 'nick', 'Logged off.'),
	('2017-03-12 23:44:17', 'nick', 'Login'),
	('2017-03-12 23:44:58', 'nick', 'Logged off.'),
	('2017-03-12 23:45:05', 'nick', 'Login'),
	('2017-03-12 23:47:19', 'nick', 'Logged off.'),
	('2017-03-12 23:47:25', 'nick', 'Login'),
	('2017-03-12 23:47:51', 'nick', 'Logged off.'),
	('2017-03-12 23:51:46', 'nick', 'Login'),
	('2017-03-12 23:57:16', 'nick', 'Logged off.'),
	('2017-03-12 23:57:26', 'nick', 'Login'),
	('2017-03-12 23:57:31', 'nick', 'Logged off.'),
	('2017-03-12 23:57:44', 'nick', 'Login'),
	('2017-03-12 23:59:34', 'nick', 'Logged off.');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;


-- Dumping structure for table suncoast.parkingdecal
CREATE TABLE IF NOT EXISTS `parkingdecal` (
  `studentNumber` int(11) NOT NULL,
  `decalNumber` char(3) NOT NULL,
  KEY `FK_parkingdecal_student` (`studentNumber`),
  CONSTRAINT `FK_parkingdecal_student` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.parkingdecal: ~0 rows (approximately)
/*!40000 ALTER TABLE `parkingdecal` DISABLE KEYS */;
INSERT INTO `parkingdecal` (`studentNumber`, `decalNumber`) VALUES
	(25382334, '22');
/*!40000 ALTER TABLE `parkingdecal` ENABLE KEYS */;


-- Dumping structure for table suncoast.reports
CREATE TABLE IF NOT EXISTS `reports` (
  `ReportID` int(11) NOT NULL AUTO_INCREMENT,
  `LockSerial` int(11) NOT NULL,
  `Priority` char(6) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Report` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ReportID`),
  KEY `FK_reports_lock` (`LockSerial`),
  CONSTRAINT `FK_reports_lock` FOREIGN KEY (`LockSerial`) REFERENCES `lock` (`Serial`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.reports: ~0 rows (approximately)
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;


-- Dumping structure for table suncoast.reservedlockers
CREATE TABLE IF NOT EXISTS `reservedlockers` (
  `lockerNum` char(5) NOT NULL,
  KEY `FK_lockerreservations_lockerassignment` (`lockerNum`),
  CONSTRAINT `FK_lockerreservations_lockerassignment` FOREIGN KEY (`lockerNum`) REFERENCES `lockerassignment` (`LockerNum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.reservedlockers: ~3 rows (approximately)
/*!40000 ALTER TABLE `reservedlockers` DISABLE KEYS */;
INSERT INTO `reservedlockers` (`lockerNum`) VALUES
	('2-451'),
	('2-451'),
	('2-457');
/*!40000 ALTER TABLE `reservedlockers` ENABLE KEYS */;


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
  `homePhone` varchar(50) NOT NULL,
  `dob` char(10) NOT NULL,
  `boughtLocker` bit(1) NOT NULL DEFAULT b'0',
  `boughtParkingDecal` bit(1) NOT NULL DEFAULT b'0',
  `englishTeacher` varchar(50) DEFAULT NULL,
  `englishPeriod` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.student: ~2 rows (approximately)
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`studentNumber`, `gradeLevel`, `firstName`, `middleName`, `lastName`, `address`, `city`, `state`, `zipcode`, `homePhone`, `dob`, `boughtLocker`, `boughtParkingDecal`, `englishTeacher`, `englishPeriod`) VALUES
	(25382334, 12, 'NATASCHA', 'LY ', 'KEMPFE', '14384 83RD LN N', 'LOXAHATCHEE', 'FL', 33470, '5617847559', '10/14/98', b'0', b'1', NULL, 0),
	(28349363, 12, 'Nicholas', 'James', 'Signori', '152 Harbourside Circle', 'Jupiter', 'FL', 33477, '5615755908', '12/17/98', b'0', b'1', 'Mosley', 3);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;


-- Dumping structure for table suncoast.studentexam
CREATE TABLE IF NOT EXISTS `studentexam` (
  `studentNumber` int(11) NOT NULL,
  `examName` varchar(50) NOT NULL,
  `examType` varchar(50) NOT NULL,
  `feePaid` bit(1) NOT NULL,
  PRIMARY KEY (`studentNumber`,`examName`,`examType`),
  KEY `examName` (`examName`,`examType`),
  CONSTRAINT `examName` FOREIGN KEY (`examName`, `examType`) REFERENCES `exam` (`examName`, `examType`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `studentNumber` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.studentexam: ~3 rows (approximately)
/*!40000 ALTER TABLE `studentexam` DISABLE KEYS */;
INSERT INTO `studentexam` (`studentNumber`, `examName`, `examType`, `feePaid`) VALUES
	(25382334, 'AP Environmental Science ', 'REGULAR', b'1'),
	(28349363, 'AP Environmental Science', 'REGULAR', b'1'),
	(28349363, 'AP Macroeconomics', 'REGULAR', b'1');
/*!40000 ALTER TABLE `studentexam` ENABLE KEYS */;


-- Dumping structure for table suncoast.studentlocker
CREATE TABLE IF NOT EXISTS `studentlocker` (
  `studentNumber` int(11) NOT NULL,
  `LockerNum` char(5) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `isArchived` bit(1) DEFAULT NULL,
  KEY `LockerNum` (`LockerNum`),
  KEY `FK_studentlocker_student` (`studentNumber`),
  CONSTRAINT `FK_studentlocker_student` FOREIGN KEY (`studentNumber`) REFERENCES `student` (`studentNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `LockerNum` FOREIGN KEY (`LockerNum`) REFERENCES `lockerassignment` (`LockerNum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table suncoast.studentlocker: ~2 rows (approximately)
/*!40000 ALTER TABLE `studentlocker` DISABLE KEYS */;
INSERT INTO `studentlocker` (`studentNumber`, `LockerNum`, `year`, `isArchived`) VALUES
	(28349363, '2-457', 2016, b'0'),
	(25382334, '3-242', 2016, b'0');
/*!40000 ALTER TABLE `studentlocker` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
