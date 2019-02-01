DROP DATABASE IF EXISTS `scgj_pmts`;
CREATE DATABASE  IF NOT EXISTS `scgj_pmts` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scgj_pmts`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: scgj_pmts
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `audit_table`
--

DROP TABLE IF EXISTS `audit_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` varchar(150) DEFAULT NULL,
  `sender_name` varchar(250) DEFAULT NULL,
  `subject` varchar(250) DEFAULT NULL,
  `priority` varchar(150) DEFAULT NULL,
  `assigned_to` int(11) NOT NULL,
  `eta` date DEFAULT NULL,
  `document_remarks` varchar(250) DEFAULT NULL,
  `document_path` varchar(250) DEFAULT NULL,
  `date_status_update` date DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `document_type` varchar(150) DEFAULT NULL,
  `assigned_by` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `assigned_to_idx` (`assigned_to`),
  CONSTRAINT `assigned_to` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doc_status`
--

DROP TABLE IF EXISTS `doc_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `status` enum('Not Started','Assigned','On Hold','In Action','Closed','Delayed') DEFAULT NULL,
  `eta` date DEFAULT NULL,
  `doc_id` int(11) DEFAULT NULL,
  `date_assigned` date DEFAULT NULL,
  `date_started` date DEFAULT NULL,
  `date_closed` date DEFAULT NULL,
  `additional_comment` text,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`owner_id`),
  KEY `doc_id_idx` (`doc_id`),
  CONSTRAINT `doc_id` FOREIGN KEY (`doc_id`) REFERENCES `document_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `owner_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document_details`
--

DROP TABLE IF EXISTS `document_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` varchar(250) NOT NULL,
  `sender_name` varchar(150) DEFAULT NULL,
  `sender_poc` varchar(150) DEFAULT NULL,
  `sender_contact` bigint(12) DEFAULT NULL,
  `date_received` date DEFAULT NULL,
  `date_of_entry` date DEFAULT NULL,
  `priority` varchar(50) DEFAULT NULL,
  `subject` varchar(250) DEFAULT NULL,
  `document_type` varchar(250) DEFAULT NULL,
  `document_path` varchar(250) DEFAULT NULL,
  `document_remarks` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `application_id_UNIQUE` (`application_id`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `department` enum('Assessment and Assurance','Finance','Marketing','Standard and Research','Data Entry Operator') DEFAULT NULL,
  `role_type` enum('DEO','DH','DE','CEO') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-01 17:35:40
