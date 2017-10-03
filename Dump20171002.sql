-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: safe
-- ------------------------------------------------------
-- Server version	5.6.23-log

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
-- Table structure for table `experiment_table`
--

DROP TABLE IF EXISTS `experiment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experiment_table` (
  `id` varchar(25) NOT NULL DEFAULT '',
  `name` varchar(100) DEFAULT NULL,
  `content` longtext CHARACTER SET ascii,
  `module_order` varchar(10000) DEFAULT NULL,
  `experiment_status` varchar(10) DEFAULT NULL,
  `exp_duedate` varchar(15) DEFAULT NULL,
  `order_list` varchar(1000) DEFAULT NULL,
  `module_name_list` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file_table`
--

DROP TABLE IF EXISTS `file_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_table` (
  `file_Id` int(11) NOT NULL AUTO_INCREMENT,
  `question_Id` varchar(25) DEFAULT NULL,
  `file_type` varchar(15) DEFAULT NULL,
  `file_extension` varchar(60) DEFAULT NULL,
  `file_name` varchar(40) DEFAULT NULL,
  `content` longtext CHARACTER SET ascii,
  PRIMARY KEY (`file_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module_table`
--

DROP TABLE IF EXISTS `module_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_table` (
  `module_Id` varchar(25) NOT NULL DEFAULT '',
  `experiment_Id` varchar(25) DEFAULT NULL,
  `module_name` varchar(100) DEFAULT NULL,
  `question_order` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`module_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question_table`
--

DROP TABLE IF EXISTS `question_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_table` (
  `question_Id` varchar(25) NOT NULL DEFAULT '',
  `module_Id` varchar(25) DEFAULT NULL,
  `type` char(5) DEFAULT NULL,
  `question_data` longtext CHARACTER SET ascii,
  PRIMARY KEY (`question_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registration_slot`
--

DROP TABLE IF EXISTS `registration_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration_slot` (
  `slot_Id` int(11) NOT NULL AUTO_INCREMENT,
  `semester` varchar(10) DEFAULT NULL,
  `semesterYear` varchar(10) DEFAULT NULL,
  `weekday` varchar(10) DEFAULT NULL,
  `daytime` char(10) DEFAULT NULL,
  `classno` char(10) DEFAULT NULL,
  `room` char(10) DEFAULT NULL,
  `taname` varchar(25) DEFAULT NULL,
  `tanetid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`slot_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registration_slot_json`
--

DROP TABLE IF EXISTS `registration_slot_json`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration_slot_json` (
  `reg_Id` int(11) NOT NULL AUTO_INCREMENT,
  `semester` varchar(10) DEFAULT NULL,
  `semesterYear` int(11) DEFAULT NULL,
  `setPoint` int(11) DEFAULT NULL,
  `modified` longtext CHARACTER SET ascii,
  `notmodified` longtext CHARACTER SET ascii,
  PRIMARY KEY (`reg_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_assessment_table`
--

DROP TABLE IF EXISTS `student_assessment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_assessment_table` (
  `netid` varchar(10) NOT NULL DEFAULT '',
  `expid` varchar(25) NOT NULL DEFAULT '',
  `moduleid` varchar(25) NOT NULL DEFAULT '',
  `data` longtext CHARACTER SET ascii,
  `ques_data` longtext CHARACTER SET ascii,
  `mixed_data` longtext CHARACTER SET ascii,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`netid`,`expid`,`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_prelab_table`
--

DROP TABLE IF EXISTS `student_prelab_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_prelab_table` (
  `netid` varchar(10) NOT NULL DEFAULT '',
  `expid` varchar(25) NOT NULL DEFAULT '',
  `moduleid` varchar(25) NOT NULL DEFAULT '',
  `data` longtext CHARACTER SET ascii,
  `pdf` longtext CHARACTER SET ascii,
  `ta_comments` mediumtext CHARACTER SET ascii,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`netid`,`expid`,`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_tacomments_table`
--

DROP TABLE IF EXISTS `student_tacomments_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_tacomments_table` (
  `netid` varchar(10) NOT NULL DEFAULT '',
  `expid` varchar(25) NOT NULL DEFAULT '',
  `ta_comments` longtext CHARACTER SET ascii,
  PRIMARY KEY (`netid`,`expid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `netid` varchar(10) NOT NULL DEFAULT '',
  `pswd` varchar(20) DEFAULT NULL,
  `accountType` varchar(10) DEFAULT NULL,
  `firstname` varchar(20) DEFAULT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  `slot` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`netid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-02 19:59:30
