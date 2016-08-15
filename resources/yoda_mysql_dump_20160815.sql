CREATE DATABASE  IF NOT EXISTS `yoda` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `yoda`;
-- MySQL dump 10.13  Distrib 5.7.13, for Win64 (x86_64)
--
-- Host: localhost    Database: yoda
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `activityId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`activityId`),
  UNIQUE KEY `activityId_UNIQUE` (`activityId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (0,'Делать'),(1,'Читать'),(2,'Смотреть'),(3,'Купить/Продать'),(4,'Учить');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `categoryId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `categoryId_UNIQUE` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (0,'Неразобранное'),(1,'Работа'),(2,'Здоровье'),(3,'Отдых'),(4,'Английский'),(5,'Идеи'),(6,'Быт'),(11,'test');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `statusId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`statusId`),
  UNIQUE KEY `statusId_UNIQUE` (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (0,'Открыта'),(1,'Закрыта');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `taskId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` tinytext,
  `content` text,
  `deadline` datetime DEFAULT NULL,
  `categoryId` int(10) unsigned DEFAULT NULL,
  `activityId` int(10) unsigned DEFAULT NULL,
  `priority` int(10) unsigned NOT NULL,
  `statusId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  UNIQUE KEY `idTask_UNIQUE` (`taskId`),
  KEY `fkActivity_idx` (`activityId`),
  KEY `fkCategory_idx` (`categoryId`),
  KEY `fkStatus_idx` (`statusId`),
  CONSTRAINT `fkActivity` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`) ON UPDATE CASCADE,
  CONSTRAINT `fkCategory` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`) ON UPDATE CASCADE,
  CONSTRAINT `fkStatus` FOREIGN KEY (`statusId`) REFERENCES `status` (`statusId`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Сделать проект EPAM','Система управления задачами и база знаний\r\n                                ','2016-07-06 00:00:00',1,0,1,0),(2,'Сдать экзамен EPAM','15 июля','2016-07-15 00:00:00',1,0,1,0),(3,'Купить ноутбук','ZenBook UX305CA \r\n                                ','2016-07-20 00:00:00',6,3,5,1),(4,'купить телефон','meizu Ð¸Ð»Ð¸ lenovo  asdfsd\r\n11111\r\n22222                           \r\n                          \r\n                                             \r\n                                ','2016-06-16 00:00:00',6,3,2,0),(5,'Съездить на Шацкие озера','                                ','2016-06-24 00:00:00',3,0,2,1),(6,'Купить обратный билет',NULL,'2016-06-24 01:42:16',3,0,2,1),(7,'Учить английский','                                ','1970-01-01 00:00:00',4,4,1,0),(8,'Учить аудиотексты английского',NULL,'2016-06-24 01:42:18',4,4,1,0),(10,'подготовка к интервью на английском','                    \r\n                    ','2016-06-24 00:00:00',4,0,1,1),(11,'Гулять по 3 часа в день','Полезно.','2016-06-24 00:00:00',2,0,1,0),(12,'Найти сейф',NULL,'2016-06-24 01:42:03',6,0,1,0),(13,'Сделать страховку на машину','                                ','2016-07-22 00:00:00',6,0,1,0),(14,'Продать кроссовки','                                ','2016-07-22 00:00:00',6,3,1,0),(15,'Почистить файлы на ноуте и сделать бэкап','                                ','2016-07-15 00:00:00',6,0,1,0),(16,'Подготовка к тестам',NULL,NULL,1,0,1,0),(17,'Поехать на море в июле',NULL,NULL,3,0,1,0),(18,'Разобрать и выбросить лишние вещи дома',NULL,NULL,6,0,1,0),(19,'Самостоятельно реализовать подключение к базе через фабрику','                                ','2016-07-22 00:00:00',1,0,1,1),(20,'Yoda. Разобраться с классом Query. У меня в DAO PreparedStatement, а там только CreateStatement. Можно ли сделать, чтобы там было все?','                                ','2016-07-22 00:00:00',1,0,1,0),(21,'Yoda. Максим. Как релазизовать категории и активности. Либо в классе обернутом на enum, либо тянуть из базы при запуске, и запихивать в map?','Создаем обычный ентити класс и работаем через ДАО\r\n                                ','2016-07-22 00:00:00',1,0,1,1),(22,'Сдать лишний билет. Проверить возврат','Узнать в банке почему возврат не доступен на счету.                          ','2016-07-22 00:00:00',3,0,1,0),(23,'Yoda. Добавить возможность фильтра по субъекту (например вопросы конкретному человеку). Возможно тэгами.','                    \r\n                    ','2016-08-31 00:00:00',0,0,1,0),(24,'Yoda. Максим. Уточнить насчет тэгов (многие ко многим, или добавить 5 отдельных полей в таблицу задачи)','                                ','2016-07-22 00:00:00',1,0,1,1),(25,'Сергей Пономарев, созвониться в скайпе','','2016-07-05 00:00:00',1,0,1,1),(26,'Yoda. Максим. 1. Конфигурацию базы хранить в XML.Это хмл томката. Пока храним в пропертиес',NULL,NULL,1,0,1,0),(27,'Yoda. Добавить тэги','                    ','2016-08-11 00:00:00',1,0,1,1),(28,'Yoda.Фильтр сделать enum-ами',NULL,NULL,1,0,1,1),(29,'Yoda.Каждая таблица - отдельное ДАО. Перенести из енумов.',NULL,NULL,1,0,1,1),(30,'Yoda. Добавить ответственных. USER, USERTYPE','                    ','2016-08-08 00:00:00',1,0,1,1),(31,'Максим. Как в jsp передавать фильтры как массив, а не каждое значение статуса отдельным параметром?',NULL,NULL,1,0,1,1),(32,'Максим. Может реализовать статусы как синглтон, один раз скачали в начале список, а дальше работаем с ним. И сразу загонять в класс Status','Не стоит. Лучше через ДАО.',NULL,1,0,1,1),(33,'Максим. Как сделать чтобы несколько кнопок Set Filters работали едино','                                ','2016-07-20 00:00:00',1,0,1,1),(34,'Максим. Как реализовать апдейт тасков? можно ли чтобы исправлять сразу в строке не открывая новую форму?',NULL,NULL,1,0,1,0),(35,'Максим. Делать ли перевод интерфейса? Статусов категорий и т.д.','Сделана локализация','2016-07-11 00:00:00',1,0,1,1),(36,'Yoda. доделать фильтр по activity   ','','2016-06-22 17:13:36',1,0,1,1),(37,'Yoda. сделать красивые приоритеты (разноцветные а не текстом)',NULL,NULL,1,0,1,0),(38,'Готовиться к тестам по XML','',NULL,1,0,1,0),(39,'Yoda. Добавить кнопку сбросить все фильтры',NULL,NULL,1,0,1,0),(40,'Yoda. Как сделать чтобы при клацании на задачу не слетали фильтры?','Реализовал через js. ','2016-08-01 00:00:00',1,0,1,1),(41,'записки юного врача bbcYoung Doctor’s Notebook (BBC)','                                ','2016-07-20 00:00:00',3,2,2,1),(42,'Yoda. Переделать везде get на post','','2016-07-10 00:00:00',1,0,1,1),(43,'Yoda. Переделать id=0 на id=1?',NULL,NULL,1,0,1,0),(44,'Yoda. Сделать фабрику для ДАО','                                ','2016-07-20 00:00:00',1,0,1,1),(45,'Скинуть фото от Питляк ВК. Почистить дропбокс. Переписать на внешний винт.',NULL,NULL,1,0,1,0),(46,'Yoda. taskDAO.getAsList(new Filter()) не можем получить, потому что в интерфейсе DAO этот медод бер параметра Фильтр        // подумать как исправить','','2016-07-05 00:00:00',1,0,1,1),(47,'Yoda. Сделать все через сервлет и через единую страницу.','','2016-07-07 00:00:00',1,0,1,1),(48,'Yoda. Максим. В какой момент закрывать стейтмент, если мы выносим работу с запросами в базовый ДАО Класс?','                                ','2016-07-22 00:00:00',1,0,1,1),(49,'Сходить к стоматологу',NULL,NULL,2,0,1,0),(50,'Yoda.сделать ConnectionPool','                                \r\n                                ','2016-07-20 00:00:00',1,0,1,0),(63,'Yoda. Сделать страницу с login-ом.','                                \r\n                                ','2016-07-20 00:00:00',1,0,1,1),(64,'Yoda. добавить в правую панель все аттрибуты задачи, и возможность их редактировать. ','В том числе менять статусы и категории\r\n                                \r\n                                ','2016-07-20 00:00:00',1,0,1,1),(65,'Yoda. при добавлении новой задачи taskID должен установливаться в новое значение','                                ','2016-07-20 00:00:00',0,0,1,0),(66,'Сергей Пономорев. Сделать Unit Tests','см скриншоты.\r\n                                ','2016-07-20 00:00:00',0,0,1,0),(67,'Yoda. AbandonedConnectionCleanupThread','                                ','1970-01-01 00:00:00',1,0,1,0),(68,'Yoda. добавить СЕССИИ','                                ','1970-01-01 00:00:00',1,0,1,1),(69,'Yoda. Добавить ФИЛЬТРЫ','                                \r\n                                ','2016-07-22 00:00:00',1,0,1,1),(73,'Yoda. проверить текст убрать все зашитые строки в КОНСТАНТЫ','                                \r\n                                \r\n                                ','2016-07-20 00:00:00',1,0,1,1),(76,'yoda. сделать LOCALIZATION','                                \r\n                                ','2016-07-20 00:00:00',1,0,1,1),(80,'yoda. //todo add, update, delete должно возвращать успешно или нет','                                ','2016-07-15 00:00:00',0,0,1,1),(108,'Тест ЕПАМ http://javastudy.ru/interview/jee-servlet-api-questions/','','2016-07-19 00:00:00',1,4,1,0),(112,'Тест ЕПАМ http://javastudy.ru/interview/jee-servlet-api-questions/','                                ','2016-07-19 00:00:00',1,4,1,0),(122,'Съездить на Шацкие озера','222222                                \r\n                                \r\n                                ','2016-06-24 00:00:00',3,0,2,1),(124,'Съездить на Шацкие озера','222222                                \r\n                                \r\n                                \r\n                                ','2016-06-24 00:00:00',3,0,2,1),(125,'Yoda. Сделать кнопку, чтобы отображала задачи на сегодня (фильтр по дате)','Сделать удобный перенос задач на завтра и т.д.                                \r\n                                \r\n                                ','2016-08-01 00:00:00',1,0,1,0),(126,'Yoda. Сделать чтобы совмещались фильтры поиска по слову и статусу.','                  \r\n                                \r\n                                \r\n                    ','2016-08-01 00:00:00',1,0,1,1),(127,'yoda. узнать сделать чтобы текст задачи редактировать сразу в списке а не на отдельной форме','                                ','2016-08-01 00:00:00',1,0,1,0),(133,'33337777ааппп','fghfgh dsfh dfh dfh dfhfdh df dfhfdhdfgghryut7sd l;n ;lkalsgsd[slsdlmasdbnfdh  oo[sdofdgfi j wkej kd;flksdf','2016-08-05 00:00:00',0,0,1,0),(134,'33337777аапппфф888','ффф               \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                    \r\n                    ','2016-08-05 00:00:00',0,0,1,0),(144,'аааббббббб11','фывфыва00\r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    ','2016-08-16 00:00:00',0,1,5,1),(146,'kiss fm deep chanel','                    ','2016-08-15 00:00:00',3,2,1,1),(147,'test аааппп778','test аааппп66\r\n                    \r\n                    \r\n                    \r\n                    \r\n                    ','2016-08-16 00:00:00',0,2,3,1),(150,'','                    ','2016-08-16 00:00:00',0,0,1,0),(151,'fgh','fgh\r\n                    \r\n                    ','2016-08-16 00:00:00',11,0,1,0),(152,'fgh','fgh\r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    ','2016-08-16 00:00:00',11,0,1,0),(162,'fgh','fgh\r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    \r\n                    ','2016-08-16 00:00:00',11,0,1,0);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `usertypeId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  KEY `fkUsertypeId_idx` (`usertypeId`),
  CONSTRAINT `fkUsertypeId` FOREIGN KEY (`usertypeId`) REFERENCES `usertype` (`usertypeId`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin',1),(2,'user','user',2),(4,'user11','user11',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertype`
--

DROP TABLE IF EXISTS `usertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertype` (
  `usertypeId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`usertypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertype`
--

LOCK TABLES `usertype` WRITE;
/*!40000 ALTER TABLE `usertype` DISABLE KEYS */;
INSERT INTO `usertype` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `usertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `view_task`
--

DROP TABLE IF EXISTS `view_task`;
/*!50001 DROP VIEW IF EXISTS `view_task`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_task` AS SELECT 
 1 AS `task_name`,
 1 AS `task_category_id`,
 1 AS `category_id`,
 1 AS `category_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `view_task`
--

/*!50001 DROP VIEW IF EXISTS `view_task`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_task` AS select `task`.`name` AS `task_name`,`task`.`categoryId` AS `task_category_id`,`category`.`categoryId` AS `category_id`,`category`.`name` AS `category_name` from (`task` join `category` on((`task`.`categoryId` = `category`.`categoryId`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-15 12:10:25
