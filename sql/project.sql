-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.10.6-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- project 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;
USE `project`;

-- 테이블 project.employment 구조 내보내기
CREATE TABLE IF NOT EXISTS `employment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `storeid` bigint(20) DEFAULT NULL,
  `storename` varchar(50) DEFAULT NULL,
  `ownerid` varchar(50) DEFAULT NULL,
  `job` bigint(20) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `uploaddate` date NOT NULL,
  `pay` int(30) DEFAULT NULL,
  `pay_type` varchar(20) DEFAULT NULL,
  `start_time` varchar(10) NOT NULL DEFAULT '',
  `end_time` varchar(10) NOT NULL DEFAULT '',
  `time` varchar(50) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `area1` varchar(20) DEFAULT NULL,
  `area2` varchar(20) DEFAULT NULL,
  `storeimg` varchar(50) DEFAULT 'storedefault.jpg',
  `deadline` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_employment_job` (`job`),
  KEY `FK_employment_store` (`storeid`),
  KEY `FK_employment_store_2` (`ownerid`),
  KEY `FK_employment_store_3` (`storename`),
  KEY `FK_employment_store_4` (`storeimg`),
  CONSTRAINT `FK_employment_job` FOREIGN KEY (`job`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_employment_store` FOREIGN KEY (`storeid`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_employment_store_2` FOREIGN KEY (`ownerid`) REFERENCES `store` (`userid`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_employment_store_3` FOREIGN KEY (`storename`) REFERENCES `store` (`storename`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_store_4` FOREIGN KEY (`storeimg`) REFERENCES `store` (`storeimg1`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.employment_personality 구조 내보내기
CREATE TABLE IF NOT EXISTS `employment_personality` (
  `employ_id` bigint(20) DEFAULT NULL,
  `personality_id` bigint(20) DEFAULT NULL,
  KEY `FK_employment_personality_personality` (`personality_id`),
  KEY `FK_employment_personality_employment` (`employ_id`),
  CONSTRAINT `FK_employment_personality_employment` FOREIGN KEY (`employ_id`) REFERENCES `employment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_personality_personality` FOREIGN KEY (`personality_id`) REFERENCES `personality` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.employment_resume 구조 내보내기
CREATE TABLE IF NOT EXISTS `employment_resume` (
  `employ_id` bigint(20) DEFAULT NULL,
  `resume_id` bigint(20) DEFAULT NULL,
  KEY `FK_employment_resume_employment` (`employ_id`),
  KEY `FK_employment_resume_resume` (`resume_id`),
  CONSTRAINT `FK_employment_resume_employment` FOREIGN KEY (`employ_id`) REFERENCES `employment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_resume_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.job 구조 내보내기
CREATE TABLE IF NOT EXISTS `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.oauth2_authorized_client 구조 내보내기
CREATE TABLE IF NOT EXISTS `oauth2_authorized_client` (
  `client_registration_id` varchar(100) NOT NULL,
  `principal_name` varchar(200) NOT NULL,
  `access_token_type` varchar(100) NOT NULL,
  `access_token_value` blob NOT NULL,
  `access_token_issued_at` timestamp NOT NULL,
  `access_token_expires_at` timestamp NOT NULL,
  `access_token_scopes` varchar(1000) DEFAULT NULL,
  `refresh_token_value` blob DEFAULT NULL,
  `refresh_token_issued_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`client_registration_id`,`principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.personality 구조 내보내기
CREATE TABLE IF NOT EXISTS `personality` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.resume 구조 내보내기
CREATE TABLE IF NOT EXISTS `resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `profile_img` varchar(50) DEFAULT 'default',
  `userid` varchar(20) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `education` varchar(50) DEFAULT NULL,
  `degree` varchar(50) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `liketime` varchar(50) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `loc` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_resume_user` (`userid`) USING BTREE,
  KEY `FK_resume_user1` (`username`),
  CONSTRAINT `FK_resume_user1` FOREIGN KEY (`username`) REFERENCES `user` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.resume_job 구조 내보내기
CREATE TABLE IF NOT EXISTS `resume_job` (
  `resume_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  KEY `FK_resume_job_resume` (`resume_id`),
  KEY `FK_resume_job_job` (`job_id`),
  CONSTRAINT `FK_resume_job_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_resume_job_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.resume_personality 구조 내보내기
CREATE TABLE IF NOT EXISTS `resume_personality` (
  `resume_id` bigint(20) DEFAULT NULL,
  `personality_id` bigint(20) DEFAULT NULL,
  KEY `FK_resume_personality_resume` (`resume_id`),
  KEY `FK_resume_personality_persionality` (`personality_id`),
  CONSTRAINT `FK_resume_personality_persionality` FOREIGN KEY (`personality_id`) REFERENCES `personality` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_resume_personality_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.role 구조 내보내기
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.store 구조 내보내기
CREATE TABLE IF NOT EXISTS `store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `storename` varchar(50) NOT NULL,
  `job` bigint(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `storeimg1` varchar(50) DEFAULT 'storedefault.jpg',
  `storeimg2` varchar(50) DEFAULT 'storedefault.jpg',
  `storeimg3` varchar(50) DEFAULT 'storedefault.jpg',
  `phone` varchar(15) DEFAULT NULL,
  `area1` varchar(20) DEFAULT NULL,
  `area2` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `storename` (`storename`),
  UNIQUE KEY `storeimg1` (`storeimg1`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `address` (`address`),
  KEY `FK_store_job` (`job`),
  KEY `FK_store_user` (`userid`),
  CONSTRAINT `FK_store_job` FOREIGN KEY (`job`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_store_user` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `useremail` varchar(30) DEFAULT NULL,
  `uname` varchar(10) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `type` varchar(50) DEFAULT 'user',
  `gender` varchar(1) DEFAULT NULL,
  `profileurl` varchar(50) NOT NULL DEFAULT 'default.jpg',
  `social` bit(1) NOT NULL DEFAULT b'0',
  `area1` varchar(50) DEFAULT NULL,
  `area2` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userid` (`userid`),
  UNIQUE KEY `uname` (`uname`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 project.user_role 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL DEFAULT 0,
  `role_id` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `FK1_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK2_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
