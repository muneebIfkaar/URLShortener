-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.14 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table urls.urltable
CREATE TABLE IF NOT EXISTS `urltable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `urlKey` varchar(100) NOT NULL,
  `url` varchar(200) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `creationTime` bigint(20) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- Dumping data for table urls.urltable: 5 rows
/*!40000 ALTER TABLE `urltable` DISABLE KEYS */;
INSERT INTO `urltable` (`id`, `urlKey`, `url`, `status`, `creationTime`, `expireTime`) VALUES
	(14, '14', 'http://www.facebook.com', 1, 1533018379039, 1564554379039),
	(13, '13', 'http://www.google.com', 1, 1533018167499, 1564554167499);
/*!40000 ALTER TABLE `urltable` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
