DROP DATABASE IF EXISTS `find_path`;
CREATE DATABASE  IF NOT EXISTS `find_path` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `find_path`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: find_path
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bus_routes`
--

DROP TABLE IF EXISTS `bus_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bus_routes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stops` varchar(500) NOT NULL,
  `fk_buses_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_buses_id_idx` (`fk_buses_id`),
  CONSTRAINT `fk_buses_id` FOREIGN KEY (`fk_buses_id`) REFERENCES `buses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus_routes`
--

--
-- Table structure for table `buses`
--

DROP TABLE IF EXISTS `buses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buses` (
  `id` int(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `fk_routes_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_routes_id_idx` (`fk_routes_id`),
  CONSTRAINT `fk_routes_id` FOREIGN KEY (`fk_routes_id`) REFERENCES `routes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buses`
--

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routes` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

--
-- Table structure for table `trafic_stops`
--

DROP TABLE IF EXISTS `trafic_stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trafic_stops` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trafic_stops`
--

DELIMITER $$
USE `find_path`$$

CREATE PROCEDURE `find_path`.`get_stops` ()
BEGIN
	SELECT id, name FROM trafic_stops;
END $$

CREATE PROCEDURE `find_path`.`get_stop_by_id` (anId INT)
BEGIN
	SELECT name FROM trafic_stops WHERE id = anId;
END $$

CREATE PROCEDURE `find_path`.`get_routes` ()
BEGIN
	SELECT id, name FROM routes;
END $$

CREATE PROCEDURE `find_path`.`get_rout_by_id` (anId INT)
BEGIN
	SELECT name FROM routes WHERE id = anId;
END $$

CREATE PROCEDURE `find_path`.`get_buses` ()
BEGIN
	SELECT id, name, fk_routes_id FROM buses;
END $$


CREATE PROCEDURE `find_path`.`get_bus_by_id` (anId INT)
BEGIN
	SELECT name, fk_routes_id FROM buses WHERE id = anId;
END $$

CREATE PROCEDURE `find_path`.`get_buses_routes` ()
BEGIN
	SELECT id, stops, fk_buses_id FROM bus_routes;
END $$

CREATE PROCEDURE `find_path`.`get_buses_route_by_bus_id` (anBusId INT)
BEGIN
	SELECT id, stops FROM bus_routes WHERE fk_buses_id = anBusId ;
END $$

DELIMITER ;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-29 15:11:15