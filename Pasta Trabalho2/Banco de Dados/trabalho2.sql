CREATE DATABASE  IF NOT EXISTS `trabalho2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `trabalho2`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: trabalho2
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alimento_receita`
--

DROP TABLE IF EXISTS `alimento_receita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alimento_receita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pessoa_id` bigint NOT NULL,
  `nome` varchar(255) NOT NULL,
  `carboidratos` double DEFAULT NULL,
  `proteinas` double DEFAULT NULL,
  `gorduras` double DEFAULT NULL,
  `calorias` double DEFAULT NULL,
  `porcao` double DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pessoa_id` (`pessoa_id`),
  CONSTRAINT `alimento_receita_ibfk_1` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimento_receita`
--

LOCK TABLES `alimento_receita` WRITE;
/*!40000 ALTER TABLE `alimento_receita` DISABLE KEYS */;
INSERT INTO `alimento_receita` VALUES (1,4,'Patinho grelhado com arroz, feijão carioca e alface',41.7,43.2,8,411.6,300,'2023-12-08','2023-12-08'),(2,4,'Arroz cozido',28.1,2.5,0.2,124.2,100,'2023-12-08','2023-12-08'),(3,4,'Macarrão de trigo com ovos',76.6,10.3,2,365.59999999999997,100,'2023-12-08','2023-12-08'),(4,3,'Feijão Carioca',13.6,4.8,0.5,78.1,100,'2023-12-08','2023-12-08'),(5,1,'Pão de forma integral',49.9,9.4,3.7,270.5,100,'2023-12-08','2023-12-08'),(6,1,'Filé de peito de frango grelhado',0,32,2.5,150.5,100,'2023-12-08','2023-12-08'),(9,4,'Patê de Ovo com Requeijão',2.1,36.9,34.3,464.7,200,'2023-12-08','2023-12-08'),(10,3,'Quiabo',6.4,1.9,0.3,35.900000000000006,100,'2023-12-08','2023-12-08'),(12,3,'Molho de cenoura cozida',7.8,0.8,1,43.4,200,'2023-12-08','2023-12-08'),(13,3,'Strogonoffe de frango',30.2,5,8.6,218.2,200,'2023-12-08','2023-12-08'),(14,3,'Frango a passarinho',10,30,15,295,100,'2023-12-08','2023-12-08'),(15,3,'arroz',20,30,40,560,2,'2023-12-11','2023-12-11'),(16,3,'ovo',10,20,30,390,2,'2023-12-11','2023-12-11'),(17,3,'tutu',10,20,30,390,1,'2023-12-11','2023-12-11');
/*!40000 ALTER TABLE `alimento_receita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alimento_refeicoes`
--

DROP TABLE IF EXISTS `alimento_refeicoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alimento_refeicoes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_refeicao` bigint NOT NULL,
  `id_alimento_receita` bigint NOT NULL,
  `porcao` double NOT NULL,
  `proteina` double NOT NULL,
  `gordura` double NOT NULL,
  `carboidrato` double NOT NULL,
  `calorias` double NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_refeicao` (`id_refeicao`),
  KEY `id_alimento_receita` (`id_alimento_receita`),
  CONSTRAINT `alimento_refeicoes_ibfk_1` FOREIGN KEY (`id_refeicao`) REFERENCES `refeicoes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `alimento_refeicoes_ibfk_2` FOREIGN KEY (`id_alimento_receita`) REFERENCES `alimento_receita` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimento_refeicoes`
--

LOCK TABLES `alimento_refeicoes` WRITE;
/*!40000 ALTER TABLE `alimento_refeicoes` DISABLE KEYS */;
INSERT INTO `alimento_refeicoes` VALUES (5,6,16,200,100,140,70,3880,'2023-12-11','2023-12-11');
/*!40000 ALTER TABLE `alimento_refeicoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacao_fisica`
--

DROP TABLE IF EXISTS `avaliacao_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avaliacao_fisica` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_pessoa` bigint NOT NULL,
  `pescoco` double NOT NULL,
  `quadril` double NOT NULL,
  `cintura` double NOT NULL,
  `altura` double NOT NULL,
  `peso` double NOT NULL,
  `fator_atividade` double NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pessoa` (`id_pessoa`),
  CONSTRAINT `avaliacao_fisica_ibfk_1` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacao_fisica`
--

LOCK TABLES `avaliacao_fisica` WRITE;
/*!40000 ALTER TABLE `avaliacao_fisica` DISABLE KEYS */;
INSERT INTO `avaliacao_fisica` VALUES (1,3,27,98,70,155,53,1.5,'2023-12-09','2023-12-09');
/*!40000 ALTER TABLE `avaliacao_fisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensagem`
--

DROP TABLE IF EXISTS `mensagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensagem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `origem_id` bigint NOT NULL,
  `destino_id` bigint NOT NULL,
  `mensagem_texto` varchar(255) DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `origem_id` (`origem_id`),
  KEY `destino_id` (`destino_id`),
  CONSTRAINT `mensagem_ibfk_1` FOREIGN KEY (`origem_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mensagem_ibfk_2` FOREIGN KEY (`destino_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensagem`
--

LOCK TABLES `mensagem` WRITE;
/*!40000 ALTER TABLE `mensagem` DISABLE KEYS */;
INSERT INTO `mensagem` VALUES (2,1,3,'Olá, vai bem','2023-12-07','2023-12-07'),(3,5,3,'Emagreceu quantos kgs?','2023-12-07','2023-12-07'),(4,4,3,'Faz academia?','2023-12-07','2023-12-07'),(6,3,1,'ola maria','2023-12-07','2023-12-07'),(7,3,6,'me passe o contato de sua nutricionista','2023-12-07','2023-12-07'),(10,6,3,'ola, o contato e 666-666-666','2023-12-08','2023-12-08');
/*!40000 ALTER TABLE `mensagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `idade` int NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `dataNascimento` date NOT NULL,
  `login` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'Maria',23,'Feminino','2000-04-25','maria456','senha456','2023-12-05','2023-12-05'),(3,'Aliny Dutra',20,'Feminino','2003-05-15','aliny789','senha789','2023-12-05','2023-12-05'),(4,'Jose Marcolino',21,'Masculino','2002-08-25','jose10','senha10','2023-12-05','2023-12-05'),(5,'Ana',17,'Feminino','2006-06-23','ana6','senha6','2023-12-05','2023-12-05'),(6,'Lulli',22,'Feminino','2001-06-28','lulli','senhalulli','2023-12-05','2023-12-05'),(8,'Alice',23,'Feminino','2000-04-25','alice123','senha123','2023-12-05','2023-12-05');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `autor_id` bigint NOT NULL,
  `conteudo_da_mensagem` text,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `autor_id` (`autor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,2,'Dieta para gordura retida','2023-12-05','2023-12-05'),(3,4,'Cardápio para saúde mental','2023-12-05','2023-12-05'),(4,5,'Cardápio2','2023-12-05','2023-12-05'),(5,6,'Cardápio3','2023-12-05','2023-12-05'),(6,7,'Dieta do ovo','2023-12-05','2023-12-05'),(8,3,'Cardapio tay training','2023-12-05','2023-12-05'),(9,3,'Cardapio dezembro','2023-12-08','2023-12-08');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferencia_alimento`
--

DROP TABLE IF EXISTS `preferencia_alimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preferencia_alimento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pessoa_id` bigint NOT NULL,
  `alimento_id` bigint NOT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pessoa_id` (`pessoa_id`),
  KEY `alimento_id` (`alimento_id`),
  CONSTRAINT `preferencia_alimento_ibfk_1` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE,
  CONSTRAINT `preferencia_alimento_ibfk_2` FOREIGN KEY (`alimento_id`) REFERENCES `alimento_receita` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preferencia_alimento`
--

LOCK TABLES `preferencia_alimento` WRITE;
/*!40000 ALTER TABLE `preferencia_alimento` DISABLE KEYS */;
INSERT INTO `preferencia_alimento` VALUES (1,3,4,'2023-12-08','2023-12-08'),(2,3,10,'2023-12-08','2023-12-08'),(3,3,12,'2023-12-08','2023-12-08'),(4,3,13,'2023-12-08','2023-12-08');
/*!40000 ALTER TABLE `preferencia_alimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refeicoes`
--

DROP TABLE IF EXISTS `refeicoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refeicoes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `registro_dieta_id` bigint NOT NULL,
  `carboidrato` double DEFAULT NULL,
  `proteina` double DEFAULT NULL,
  `gordura` double DEFAULT NULL,
  `calorias` double DEFAULT NULL,
  `nome_refeicao` varchar(255) DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `registro_dieta_id` (`registro_dieta_id`),
  CONSTRAINT `refeicoes_ibfk_1` FOREIGN KEY (`registro_dieta_id`) REFERENCES `registro_dieta` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refeicoes`
--

LOCK TABLES `refeicoes` WRITE;
/*!40000 ALTER TABLE `refeicoes` DISABLE KEYS */;
INSERT INTO `refeicoes` VALUES (1,2,32.63,12.5,8,252.52,'Café da manhã','2023-12-10','2023-12-10'),(2,2,35,20.9,6.1,278.5,'Almoço','2023-12-10','2023-12-10'),(4,2,390,20,30,1910,'Cafe da manha','2023-12-11','2023-12-11'),(6,2,560,30,40,2720,'Ceia','2023-12-11','2023-12-11');
/*!40000 ALTER TABLE `refeicoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_dieta`
--

DROP TABLE IF EXISTS `registro_dieta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_dieta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pessoa_id` bigint NOT NULL,
  `avaliacao_fisica_id` bigint NOT NULL,
  `tipo_dieta_id` bigint NOT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `calorias` double DEFAULT NULL,
  `numero_refeicoes` int DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pessoa_id` (`pessoa_id`),
  KEY `tipo_dieta_id` (`tipo_dieta_id`),
  KEY `avaliacao_fisica_id` (`avaliacao_fisica_id`),
  CONSTRAINT `registro_dieta_ibfk_1` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE,
  CONSTRAINT `registro_dieta_ibfk_2` FOREIGN KEY (`tipo_dieta_id`) REFERENCES `tipo_dieta` (`id`),
  CONSTRAINT `registro_dieta_ibfk_3` FOREIGN KEY (`avaliacao_fisica_id`) REFERENCES `avaliacao_fisica` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_dieta`
--

LOCK TABLES `registro_dieta` WRITE;
/*!40000 ALTER TABLE `registro_dieta` DISABLE KEYS */;
INSERT INTO `registro_dieta` VALUES (2,3,1,2,'MANTER O PESO',4,4,'2023-12-10','2023-12-10');
/*!40000 ALTER TABLE `registro_dieta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seguir`
--

DROP TABLE IF EXISTS `seguir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seguir` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `origem_id` bigint NOT NULL,
  `seguindo_id` bigint NOT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `origem_id` (`origem_id`),
  KEY `seguindo_id` (`seguindo_id`),
  CONSTRAINT `seguir_ibfk_1` FOREIGN KEY (`origem_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE,
  CONSTRAINT `seguir_ibfk_2` FOREIGN KEY (`seguindo_id`) REFERENCES `pessoa` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguir`
--

LOCK TABLES `seguir` WRITE;
/*!40000 ALTER TABLE `seguir` DISABLE KEYS */;
INSERT INTO `seguir` VALUES (3,4,3,'2023-12-05','2023-12-05'),(4,5,3,'2023-12-05','2023-12-05'),(5,6,3,'2023-12-05','2023-12-05'),(6,3,1,'2023-12-05','2023-12-05'),(8,3,4,'2023-12-05','2023-12-05'),(9,3,5,'2023-12-05','2023-12-05'),(13,3,8,'2023-12-11','2023-12-11');
/*!40000 ALTER TABLE `seguir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_dieta`
--

DROP TABLE IF EXISTS `tipo_dieta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_dieta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `carboidrato` double DEFAULT NULL,
  `proteina` double DEFAULT NULL,
  `gordura` double DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_dieta`
--

LOCK TABLES `tipo_dieta` WRITE;
/*!40000 ALTER TABLE `tipo_dieta` DISABLE KEYS */;
INSERT INTO `tipo_dieta` VALUES (1,'EQUILIBRADA',40,30,30,'2023-12-09','2023-12-09'),(2,'LOW CARB',30,50,20,'2023-12-09','2023-12-09'),(3,'CETOGÊNICA',15,15,70,'2023-12-09','2023-12-09');
/*!40000 ALTER TABLE `tipo_dieta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-11 12:06:42
