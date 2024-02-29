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

-- 테이블 project.chat 구조 내보내기
CREATE TABLE IF NOT EXISTS `chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `toName` varchar(10) DEFAULT NULL,
  `fromName` varchar(10) DEFAULT NULL,
  `data` varchar(50) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_chat_user` (`toName`) USING BTREE,
  KEY `FK_chat_user_2` (`fromName`) USING BTREE,
  CONSTRAINT `FK_chat_user` FOREIGN KEY (`toName`) REFERENCES `user` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_chat_user_2` FOREIGN KEY (`fromName`) REFERENCES `user` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.chat:~2 rows (대략적) 내보내기
INSERT INTO `chat` (`id`, `toName`, `fromName`, `data`, `time`) VALUES
	(1, '이지은', '송강', '안녕', '2024-02-29 02:45:14'),
	(2, '송강', '이지은', '그래 안녕', '2024-02-29 03:41:19');

-- 테이블 project.employment 구조 내보내기
CREATE TABLE IF NOT EXISTS `employment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `storeid` bigint(20) DEFAULT NULL,
  `storename` varchar(50) DEFAULT NULL,
  `ownerid` varchar(50) DEFAULT NULL,
  `job` bigint(20) DEFAULT NULL,
  `ownername` varchar(10) DEFAULT NULL,
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
  `status` varchar(10) NOT NULL DEFAULT 'ing',
  PRIMARY KEY (`id`),
  KEY `FK_employment_job` (`job`),
  KEY `FK_employment_store` (`storeid`),
  KEY `FK_employment_store_2` (`ownerid`),
  KEY `FK_employment_store_3` (`storename`),
  KEY `FK_employment_store_4` (`storeimg`),
  KEY `FK_employment_user` (`ownername`),
  CONSTRAINT `FK_employment_job` FOREIGN KEY (`job`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_employment_store` FOREIGN KEY (`storeid`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_employment_store_2` FOREIGN KEY (`ownerid`) REFERENCES `store` (`userid`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_employment_store_3` FOREIGN KEY (`storename`) REFERENCES `store` (`storename`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_store_4` FOREIGN KEY (`storeimg`) REFERENCES `store` (`storeimg1`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_user` FOREIGN KEY (`ownername`) REFERENCES `user` (`uname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.employment:~18 rows (대략적) 내보내기
INSERT INTO `employment` (`id`, `storeid`, `storename`, `ownerid`, `job`, `ownername`, `phone`, `title`, `content`, `uploaddate`, `pay`, `pay_type`, `start_time`, `end_time`, `time`, `location`, `area1`, `area2`, `storeimg`, `deadline`, `status`) VALUES
	(7, 3, '두찜 연산점', 'sajang', 1, '송강', '051-533-1233', '성실하신 알바생 모집합니다!', '두찜 연산점에서 성실하신 알바생 모집합니다!', '2024-02-23', 11000, 'hour', '13:00', '18:30', 'weekday', '부산 연제구 거제천로152번길 34', '부산', '연제구', 'sajang1.jpg', '2024-03-15', 'ing'),
	(8, 7, 'BBQ 송정해변점', 'owner', 2, '김진영', '051-632-1192', '오래 일하실 아르바이트생 모집', 'BBQ 송정해변점에서 오래일하실 직원분 모집합니다.', '2024-02-23', 2500000, 'month', '13:00', '21:30', 'weekend', '부산 해운대구 송정중앙로 35', '부산', '해운대구', 'owner1.jpg', '2024-03-21', 'ing'),
	(9, 3, '두찜 연산점', 'sajang', 1, '송강', '051-533-1233', '주방 직원 모십니다.', '두찜 연산점 평일반 주방 직원 모십니다.', '2024-02-23', 3200000, 'month', '11:30', '22:00', 'weekday', '부산 연제구 거제천로152번길 34', '부산', '연제구', 'sajang1.jpg', '2024-03-18', 'ing'),
	(10, 3, '두찜 연산점', 'sajang', 1, '송강', '051-533-1233', '두찜 연산점 점장님 모집합니다.', '두찜 연산점 점장님 모집합니다.', '2024-02-23', 3500000, 'month', '10:30', '22:00', 'all', '부산 연제구 거제천로152번길 34', '부산', '연제구', 'sajang1.jpg', '2024-03-20', 'ing'),
	(11, 3, '두찜 연산점', 'sajang', 3, '송강', '051-632-1192', '두찜 연산점 카운터 알바생 모집합니다.', '두찜 연산점 카운터 알바생 모집합니다.', '2024-02-26', 12000, NULL, '11:00', '17:30', 'weekday', '부산 연제구 거제천로152번길 34', '부산', '연제구', 'sajang1.jpg', '2024-03-15', 'ing'),
	(12, 8, '동대문엽기떡볶이 기장점', 'owner4', 2, '황정민', '051-334-4312', '동대문엽기떡볶이 기장점 홀서빙 아르바이트 모집', '동대문엽기떡볶이 기장점 홀서빙 아르바이트 모집합니다.', '2024-02-26', 2300000, 'month', '15:30', '21:00', 'weekday', '부산 기장군 기장읍 동부산관광5로 14', '부산', '기장군', 'owner41.jpg', '2024-03-15', 'ing'),
	(13, 8, '동대문엽기떡볶이 기장점', 'owner4', 1, '황정민', '051-334-4312', '동대문엽기떡볶이 기장점 주방직원 모집합니다.', '동대문엽기떡볶이 기장점 주방직원 모집합니다.', '2024-02-26', 2800000, 'month', '16:00', '23:00', 'all', '부산 기장군 기장읍 동부산관광5로 14', '부산', '기장군', 'owner41.jpg', '2024-03-17', 'ing'),
	(14, 9, '(주)호동 공조', 'owner3', 6, '강호동', '051-632-1122', '덕트시공 단기 직원 구합니다.', '덕트시공 단기 직원 구합니다.', '2024-02-26', 1500000, 'day', '09:00', '18:00', 'weekday', '부산 해운대구 해운대로608번길 13', '부산', '해운대구', 'owner31.jpg', '2024-03-15', 'ing'),
	(15, 10, '(주)LH디자인', 'owner2', 13, '유재석', '051-332-1132', '웹 디자인 프로젝트 프리랜서 모십니다.', '회사에서 현재 진행중인 웹 구축 프로젝트 \r\n웹 디자인 (약 2개월정도 소요예정)프리랜서 모십니다.', '2024-02-26', 3500000, 'month', '09:00', '18:00', 'weekday', '부산 연제구 거제시장로 5', '부산', '연제구', 'owner21.jpg', '2024-03-15', 'ing'),
	(16, 13, '(주)두현 인터내셔널', 'owner5', 5, '장동건', '051-335-5561', '고객센터 상담사 직원 모집합니다.', '고객센터 상담사 직원 모집합니다.', '2024-02-26', 10500, 'hour', '09:30', '18:30', 'weekday', '부산 해운대구 해운대로570번길 17 한양수자인마린오피스텔 (우동) 한양수자인마린오피스텔', '부산', '해운대구', 'owner51.jpg', '2024-03-24', 'ing'),
	(20, 14, '한신포차 부산 본점', 'owner6', 3, '백종원', '051-685-1134', '성실하신 홀 알바생 모십니다.', '한신포차 부산 본점에서 성실하신 홀 알바생 모십니다.', '2024-02-28', 11000, 'hour', '17:00', '23:00', 'weekday', '부산 해운대구 수변로 3', '부산', '해운대구', 'owner61.jpg', '2024-03-22', 'ing'),
	(21, 15, '고반식당 연제점', 'owner7', 1, '하정우', '051-632-1131', '고반식당 연제점에서 직원 모집합니다.', '고반식당 연제점에서 직원 모집합니다.', '2024-02-28', 2600000, 'month', '14:30', '22:00', 'weekday', '부산 연제구 교대로 24', '부산', '연제구', 'owner71.jpg', '2024-04-15', 'ing'),
	(22, 17, '이디야커피 교대점', 'owner8', 3, '최무식', '051-632-1442', '이디야커피 교대점 아르바이트생 모집', '이디야커피 교대점 아르바이트생 모집', '2024-02-28', 10500, 'hour', '13:00', '18:00', 'weekday', '부산 연제구 교대로 3', '부산', '연제구', 'owner81.jpg', '2024-03-18', 'ing'),
	(23, 18, 'J헤어 부산점', 'owner9', 5, '신수지', '051-335-4413', 'J헤어에서 디자이너 모집합니다.', 'J헤어에서 디자이너 모집합니다.', '2024-02-28', 2700000, 'month', '12:00', '21:00', 'weekend', '부산 연제구 거제대로 123', '부산', '연제구', 'owner91.jpg', '2024-03-11', 'ing'),
	(24, 19, '(주)신영시스템', 'owner10', 9, '김윤석', '051-153-4412', '(주)신영시스템 사출 가공 직원 모집', '(주)신영시스템 사출 가공 직원 모집', '2024-02-28', 3200000, 'month', '09:00', '19:00', 'weekday', '부산 연제구 신촌로 27', '부산', '연제구', 'owner101.jpg', '2024-03-14', 'ing'),
	(25, 20, '다이소 부산점', 'owner11', 4, '장사장', '051-234-1123', '다이소에서 진열 직원 모집합니다.', '다이소에서 진열 직원 모집합니다.\r\n\r\n평일 아침 9시~6시이고,\r\n점심시간은 1시입니다.\r\n활기차고 밝은 분들 많은 지원 바랍니다', '2024-02-29', 10300, 'hour', '09:00', '18:00', 'weekday', '부산 연제구 신촌로17번길 8', '부산', '연제구', 'owner111.jpg', '2024-03-16', 'ing'),
	(26, 22, 'GS25 연산점', 'owner12', 3, '김숙희', '051-112-4461', 'GS25 편의점 평일 주간반 모집', 'GS25 편의점 평일 주간반 모집합니다~~', '2024-02-29', 9800, 'hour', '09:00', '18:00', 'weekday', '부산 연제구 거제대로 130', '부산', '연제구', 'owner121.jpg', '2024-03-30', 'ing'),
	(27, 24, 'GS편의점 부산역점', 'sajang2', 3, '김사장', '051-632-1112', 'GS편의점에서 직원을 모집합니다.', 'GS편의점에서 직원을 모집합니다.', '2024-02-29', 9800, 'hour', '09:00', '18:00', 'weekday', '부산 동래구 종합운동장로 29', '부산', '동래구', 'sajang21.jpg', '2024-03-23', 'ing');

-- 테이블 project.employment_personality 구조 내보내기
CREATE TABLE IF NOT EXISTS `employment_personality` (
  `employ_id` bigint(20) DEFAULT NULL,
  `personality_id` bigint(20) DEFAULT NULL,
  KEY `FK_employment_personality_personality` (`personality_id`),
  KEY `FK_employment_personality_employment` (`employ_id`),
  CONSTRAINT `FK_employment_personality_employment` FOREIGN KEY (`employ_id`) REFERENCES `employment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_personality_personality` FOREIGN KEY (`personality_id`) REFERENCES `personality` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.employment_personality:~72 rows (대략적) 내보내기
INSERT INTO `employment_personality` (`employ_id`, `personality_id`) VALUES
	(7, 1),
	(7, 2),
	(7, 3),
	(8, 1),
	(8, 3),
	(8, 11),
	(8, 13),
	(9, 10),
	(9, 11),
	(9, 15),
	(9, 18),
	(10, 11),
	(10, 14),
	(10, 16),
	(10, 18),
	(11, 4),
	(11, 9),
	(11, 13),
	(11, 15),
	(12, 1),
	(12, 2),
	(12, 3),
	(12, 9),
	(13, 1),
	(13, 3),
	(13, 10),
	(13, 13),
	(14, 1),
	(14, 4),
	(14, 10),
	(14, 18),
	(15, 1),
	(15, 2),
	(15, 3),
	(16, 6),
	(16, 7),
	(16, 9),
	(16, 12),
	(20, 6),
	(20, 7),
	(20, 9),
	(20, 12),
	(20, 18),
	(21, 3),
	(21, 12),
	(21, 15),
	(21, 18),
	(22, 3),
	(22, 4),
	(22, 7),
	(22, 8),
	(23, 2),
	(23, 6),
	(23, 9),
	(23, 12),
	(23, 16),
	(24, 1),
	(24, 4),
	(24, 17),
	(24, 18),
	(25, 2),
	(25, 3),
	(25, 12),
	(25, 13),
	(26, 1),
	(26, 2),
	(26, 3),
	(27, 1),
	(27, 2),
	(27, 4),
	(27, 13),
	(27, 16);

-- 테이블 project.employment_resume 구조 내보내기
CREATE TABLE IF NOT EXISTS `employment_resume` (
  `employ_id` bigint(20) DEFAULT NULL,
  `resume_id` bigint(20) DEFAULT NULL,
  KEY `FK_employment_resume_employment` (`employ_id`),
  KEY `FK_employment_resume_resume` (`resume_id`),
  CONSTRAINT `FK_employment_resume_employment` FOREIGN KEY (`employ_id`) REFERENCES `employment` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_employment_resume_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.employment_resume:~6 rows (대략적) 내보내기
INSERT INTO `employment_resume` (`employ_id`, `resume_id`) VALUES
	(23, 25),
	(7, 23),
	(7, 22),
	(7, 21),
	(11, 28),
	(12, 28);

-- 테이블 project.job 구조 내보내기
CREATE TABLE IF NOT EXISTS `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.job:~14 rows (대략적) 내보내기
INSERT INTO `job` (`id`, `name`) VALUES
	(1, '주방'),
	(2, '서빙'),
	(3, '카운터'),
	(4, '단순노무'),
	(5, '서비스업'),
	(6, '건설·공사'),
	(7, '물류'),
	(8, '행사·홍보'),
	(9, '조립·생산'),
	(10, '배송·운전'),
	(11, '사무·관리'),
	(12, '프로젝트'),
	(13, '프리랜서'),
	(14, '기타');

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

-- 테이블 데이터 project.oauth2_authorized_client:~1 rows (대략적) 내보내기
INSERT INTO `oauth2_authorized_client` (`client_registration_id`, `principal_name`, `access_token_type`, `access_token_value`, `access_token_issued_at`, `access_token_expires_at`, `access_token_scopes`, `refresh_token_value`, `refresh_token_issued_at`, `created_at`) VALUES
	('naver', '윤한민', 'Bearer', _binary 0x414141414f7371344c3761674a753261504d5a4a4b58453778633730753668594a327452616b30496159556863597a386935506747506c574b576f79726c4171512d7150705f4c7656794838335a757844724c7564766c55467034, '2024-02-29 08:48:05', '2024-02-29 09:48:05', NULL, _binary 0x4171316e32567a576b55725a5a623457493469733359437541486a6969646c434b6e386135486c6f3069704a566d3732706969347930696937334a304c385a35346f49664637356f4c376b423555324e3442747370735457384d796c50715a66436c6d4b74313130624e52516f6750793052436f39684b6d705753663169696f494b6d345731, '2024-02-29 08:48:05', '2024-02-29 08:48:05');

-- 테이블 project.personality 구조 내보내기
CREATE TABLE IF NOT EXISTS `personality` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.personality:~16 rows (대략적) 내보내기
INSERT INTO `personality` (`id`, `name`) VALUES
	(1, '성실해요'),
	(2, '꼼꼼해요'),
	(3, '부지런해요'),
	(4, '힘이좋아요'),
	(5, '친절해요'),
	(6, '센스쟁이'),
	(7, '밝아요'),
	(8, '철두철미해요'),
	(9, '긍정왕!'),
	(10, '체력좋아요!'),
	(11, '융통성있어요'),
	(12, '친화력짱!'),
	(13, '재빨라요.'),
	(14, '자신감넘쳐요.'),
	(15, '시간약속 잘지켜요.'),
	(16, '적극적이에요'),
	(17, '지각안해요!'),
	(18, '배려를잘해요.');

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.resume:~7 rows (대략적) 내보내기
INSERT INTO `resume` (`id`, `profile_img`, `userid`, `username`, `education`, `degree`, `score`, `liketime`, `description`, `loc`, `birthday`, `gender`, `phone`) VALUES
	(6, 'user.png', 'sajang', NULL, NULL, NULL, NULL, '1,1,3,1,3,1,1', NULL, NULL, NULL, NULL, NULL),
	(20, 'user7.jpg', 'user7', '조보아', '고등학교 졸업', '인문계', NULL, '1,1,1,1,1,1,1', '백종원 사장님 밑에서 일 배웠어요!', '연제구', '1993-09-23', 'F', '010-4425-8875'),
	(21, 'user6.jpg', 'user6', '차은우', '전문대(2·3년제) 졸업', '기계공학과', NULL, '1,1,1,1,1,1,1', '안녕하세요 차은우입니다.', '연제구', '1998-06-18', 'M', '010-4425-8875'),
	(22, 'user5.jpg', 'user5', '김유정', '대학교(4년제) 졸업', '어문계열', NULL, '1,1,1,1,1,1,1', '편의점 아르바이트 3년했어요!', '연제구', '2000-03-23', 'F', '010-4425-8875'),
	(23, 'user4.png', 'user4', '김우식', '고등학교 졸업', '실업계', NULL, '2,1,1,3,1,1,1', '체력이 좋아요!', '연제구', '1994-02-28', 'M', '010-4425-8875'),
	(25, 'user2.jpg', 'user2', '고윤정', '대학교(4년제) 졸업', '경영학과', NULL, '1,1,1,1,1,1,1', '성실하고 일 잘해요!', '연제구', '1999-05-12', 'F', '010-4425-8875'),
	(28, 'user.png', 'user', '이지은', '대학교(4년제) 졸업', '경영학과', NULL, '1,1,1,1,1,3,3', '안녕하세요.', '연제구', '1997-02-19', 'F', '010-1569-7505');

-- 테이블 project.resume_job 구조 내보내기
CREATE TABLE IF NOT EXISTS `resume_job` (
  `resume_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  KEY `FK_resume_job_resume` (`resume_id`),
  KEY `FK_resume_job_job` (`job_id`),
  CONSTRAINT `FK_resume_job_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_resume_job_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.resume_job:~30 rows (대략적) 내보내기
INSERT INTO `resume_job` (`resume_id`, `job_id`) VALUES
	(6, 1),
	(6, 2),
	(6, 3),
	(6, 8),
	(6, 9),
	(20, 1),
	(20, 2),
	(20, 3),
	(21, 1),
	(21, 2),
	(21, 3),
	(21, 8),
	(22, 1),
	(22, 2),
	(22, 3),
	(22, 8),
	(23, 1),
	(23, 4),
	(23, 5),
	(23, 8),
	(23, 9),
	(23, 10),
	(25, 1),
	(25, 2),
	(25, 3),
	(25, 8),
	(28, 1),
	(28, 2),
	(28, 3),
	(28, 4);

-- 테이블 project.resume_personality 구조 내보내기
CREATE TABLE IF NOT EXISTS `resume_personality` (
  `resume_id` bigint(20) DEFAULT NULL,
  `personality_id` bigint(20) DEFAULT NULL,
  KEY `FK_resume_personality_resume` (`resume_id`),
  KEY `FK_resume_personality_persionality` (`personality_id`),
  CONSTRAINT `FK_resume_personality_persionality` FOREIGN KEY (`personality_id`) REFERENCES `personality` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_resume_personality_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.resume_personality:~34 rows (대략적) 내보내기
INSERT INTO `resume_personality` (`resume_id`, `personality_id`) VALUES
	(20, 4),
	(20, 8),
	(20, 9),
	(20, 12),
	(20, 13),
	(20, 15),
	(21, 2),
	(21, 3),
	(21, 7),
	(21, 8),
	(21, 12),
	(22, 2),
	(22, 3),
	(22, 4),
	(22, 7),
	(22, 8),
	(22, 9),
	(22, 13),
	(23, 4),
	(23, 7),
	(23, 8),
	(23, 11),
	(23, 13),
	(23, 15),
	(25, 4),
	(25, 6),
	(25, 7),
	(25, 8),
	(25, 9),
	(25, 13),
	(28, 1),
	(28, 3),
	(28, 11),
	(28, 12);

-- 테이블 project.role 구조 내보내기
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.role:~3 rows (대략적) 내보내기
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_OWNER'),
	(3, 'ROLE_ADMIN');

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.store:~14 rows (대략적) 내보내기
INSERT INTO `store` (`id`, `userid`, `storename`, `job`, `address`, `storeimg1`, `storeimg2`, `storeimg3`, `phone`, `area1`, `area2`) VALUES
	(3, 'sajang', '두찜 연산점', 1, '부산 연제구 거제천로152번길 34', 'sajang1.jpg', 'sajang2.jpg', 'sajang3.jpg', '051-632-1192', '부산', '연제구'),
	(7, 'owner', 'BBQ 송정해변점', 1, '부산 해운대구 송정중앙로 35', 'owner1.jpg', 'owner2.jpg', 'owner3.jpg', '051-533-1233', '부산', '해운대구'),
	(8, 'owner4', '동대문엽기떡볶이 기장점', 1, '부산 기장군 기장읍 동부산관광5로 14', 'owner41.jpg', 'owner42.jpg', 'owner43.jpg', '051-334-4312', '부산', '기장군'),
	(9, 'owner3', '(주)호동 공조', 11, '부산 해운대구 해운대로608번길 13', 'owner31.jpg', 'owner32.jpg', 'owner33.jpg', '051-632-1122', '부산', '해운대구'),
	(10, 'owner2', '(주)LH디자인', 11, '부산 연제구 거제시장로 5', 'owner21.jpg', NULL, NULL, '051-332-1132', '부산', '연제구'),
	(13, 'owner5', '(주)두현 인터내셔널', 5, '부산 해운대구 해운대로570번길 17 한양수자인마린오피스텔 (우동) 한양수자인마린오피스텔', 'owner51.jpg', NULL, NULL, '051-335-5561', '부산', '해운대구'),
	(14, 'owner6', '한신포차 부산 본점', 1, '부산 연제구 연산3동 12', 'owner61.jpg', 'owner62.jpg', 'owner63.jpg', '051-685-1134', '부산', '연제구'),
	(15, 'owner7', '고반식당 연제점', 1, '부산 연제구 교대로 24', 'owner71.jpg', 'owner72.jpg', 'owner73.jpg', '051-632-1131', '부산', '연제구'),
	(17, 'owner8', '이디야커피 교대점', 1, '부산 연제구 교대로 3', 'owner81.jpg', 'owner82.jpg', 'owner83.jpg', '051-632-1447', '부산', '연제구'),
	(18, 'owner9', 'J헤어 부산점', 5, '부산 연제구 거제대로 123', 'owner91.jpg', 'owner92.jpg', 'owner93.jpg', '051-335-4413', '부산', '연제구'),
	(19, 'owner10', '(주)신영시스템', 9, '부산 연제구 신촌로 27', 'owner101.jpg', NULL, NULL, '051-153-4412', '부산', '연제구'),
	(20, 'owner11', '다이소 부산점', 5, '부산 연제구 신촌로17번길 8', 'owner111.jpg', 'owner112.jpg', NULL, '051-234-1123', '부산', '연제구'),
	(22, 'owner12', 'GS25 연산점', 1, '부산 연제구 거제대로 130', 'owner121.jpg', NULL, NULL, '051-112-4461', '부산', '연제구'),
	(23, 'owner13', 'CU 부산점', 1, '부산 연제구 거제대로 128-15', 'owner131.jpg', 'owner132.jpg', 'owner133.jpg', '051-112-3241', '부산', '연제구'),
	(24, 'sajang2', 'GS편의점 부산역점', 1, '부산 동래구 종합운동장로 29', 'sajang21.jpg', NULL, NULL, '051-632-1112', '부산', '동래구');

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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 project.user:~26 rows (대략적) 내보내기
INSERT INTO `user` (`id`, `userid`, `useremail`, `uname`, `password`, `phone`, `birthday`, `address`, `enabled`, `type`, `gender`, `profileurl`, `social`, `area1`, `area2`) VALUES
	(2, 'admin', 'admin@admin', '관리자', '$2a$10$1jXfsXd9y2gC/Vdjm5GCtO91r9uB9MEupb6GlVWQbAklzurnnk2M.', '010-1111-1111', '2013-02-19', '부산 연제구 연산5동 sk아파트', b'1', 'admin', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(3, 'user', 'jien@naver.com', '이지은', '$2a$10$/0NJnMJtqtFf5GqpFky5L.qGWvzSlgfkjYVIhFA9cyzK9oJ751LkW', '010-1569-7505', '1997-02-19', '부산 연제구 거제동 거제1길-3', b'1', 'user', 'F', 'user.png', b'0', '부산', '연제구'),
	(4, 'min', 'min@min.com', '민아', '$2a$10$7hoOgdMnvnHHqh/JPY6YwuEQyETpmV6fRpf3V9gQADqRmWu4cSQi6', '010-1234-1234', '2024-02-09', '부산 연제구 연산2동 102-3', b'0', 'user', 'F', 'default.jpg', b'0', '부산', '연제구'),
	(5, 'sajang', 'sajang@naver.com', '송강', '$2a$10$qxHFA2Q3ufHW3iU/lOEm8O/GZgi0VGJYd8//zdE..DJDRJUZVQn5G', '010-1234-1234', '1997-02-19', '부산 해운대구 명장동 2길-1', b'1', 'owner', 'M', 'sajang.jpg', b'0', '부산', '연제구'),
	(6, 'alba', 'alba', '사나', '$2a$10$sw17WTBQT.luuoBouC801OCdewrNXHAfir27fo5wRQ27HMGan1Mqm', '010-1234-1234', '1977-02-19', '부산 연제구 연산6동 101-7', b'0', 'user', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(7, 'owner', 'owner@owner.com', '김진영', '$2a$10$/bPHz9zpk.OlCBpo8xXvheNmF/tDJjF8DZl1hBRYT7Q/e6IE8ysGy', '010-1234-1234', '1997-02-19', '부산 연제구 연산5동 현대아파트 205호', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(8, 'owner2', 'owner2@owner.com', '유재석', '$2a$10$DUiwvR/tGx5nHAb8QvBK9.19Jgb2/uPbVu5uo2CSMUOGh5v0TEH1K', NULL, NULL, '부산 연제구 연산6동 101-7', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(9, 'owner3', 'owner3@owner.com', '강호동', '$2a$10$lojbgk6Mto84Ba2SPB5e2uUW72TxJakRjGhS3YBgL9EUfwdTTzavm', '051-632-1192', NULL, '부산 연제구 연산5동 현대아파트 205호', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(10, 'owner4', 'min@min.com', '황정민', '$2a$10$q9zYW78uoNExCHku/ztBJ.FG80hxBY0WPCBBchBYyhF3WXcT/gCee', '051-632-1192', NULL, '부산 연제구 연산5동 현대아파트 205호', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(11, 'owner5', 'owner5@owner.com', '장동건', '$2a$10$UdnlHzrll/mHTV0fUf.xhuobJqjBvZKnk1hCwUWQaOzZXtuKn59cK', '010-2253-2321', NULL, '부산 연제구 연산5동 현대아파트 205호', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(12, 'user2', 'dbswjd@naver.com', '고윤정', '$2a$10$0OR92hdxL61Tz7DmXuLbWerr8nIvaKV58AfkCeKFIfztnc4RFOco.', '010-4425-8875', '1999-05-12', '부산 연제구 연산5동 현대아파트 205호', b'1', 'user', 'F', 'user2.jpg', b'0', '부산', '연제구'),
	(16, 'user3', 'user3@naver.com', '김유진', '$2a$10$rsYCCwEOm0hnXoWSfQu.2.Au0Z/GqF13dfPQqAvReP41VmSE0YeOW', '010-1235-6664', '2002-06-11', '부산 연제구 거제천로 102-3', b'0', 'user', 'F', 'default.jpg', b'0', '부산', '연제구'),
	(17, 'user4', 'user4.naver.com', '김우식', '$2a$10$oVV5JgpStbVUeuAfJWVbEuQCO90CBAZC3p6T4ybh9lphPNhMuxZqq', '010-2344-4541', '1994-02-28', '부산 연제구 연산5동 현대아파트 205호', b'1', 'user', 'M', 'user4.png', b'0', '부산', '연제구'),
	(22, 'user5', 'user5', '김유정', '$2a$10$Skx/GMCUGCqsuXqrEOiS1O8DK4eqnrh2RVtgliKgjxViCCUonZpSO', '010-2253-1234', '2000-03-23', '부산 연제구 신촌로 11', b'1', 'user', 'F', 'user5.jpg', b'0', '부산', '연제구'),
	(23, 'user6', 'user6@naver.com', '차은우', '$2a$10$R3OcR3sC/A9zqbOM8bXNneS0ceKnpeA4wjFNu9x4GDNKbHy6/yhti', '010-2536-1234', '1998-06-18', '부산 해운대구 APEC로 17', b'1', 'user', 'M', 'user6.jpg', b'0', '부산', '연제구'),
	(24, 'user7', 'boa@gmail.com', '조보아', '$2a$10$xPPKTCqq.082P//MBsTMBuCgmjhrQNBnug4W8apXrjt5kf.L4/Qiu', '010-3234-4141', '1993-09-23', '부산 해운대구 대천로 35', b'1', 'user', 'F', 'user7.jpg', b'0', '부산', '연제구'),
	(25, 'owner6', 'owner6@owner.com', '백종원', '$2a$10$pXD6Bgvo/xvH1qehBH0uyeuXyZuKcSVM6sPYCFS2DhK0Nt0Tpua7y', '010-1235-6612', '1968-04-24', '부산 연제구 거제대로 124', b'1', 'owner', 'M', 'owner6.jpg', b'0', '부산', '연제구'),
	(26, 'owner7', 'owner7@gmail.com', '하정우', '$2a$10$z2M3v/ZcrxrTDXNekciiDu4MfLU8iEhdm2IdHUJj7U9fFQjGV6odu', '010-2253-1234', '1968-04-11', '부산 연제구 교대로 10-1', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(27, 'owner8', 'user3@naver.com', '최무식', '$2a$10$C783bC0U1DhdExxuK4d7S.AljggGUu2DpzXf8JJtY85egMFF7luES', '010-2253-1234', '1961-08-18', '부산 동래구 종합운동장로 29', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '동래구'),
	(28, 'owner9', 'owner9@gmail.com', '신수지', '$2a$10$AV3ICns55K/hGh4Hw7sM4.bIs8oFjYqN5XA5SYCzGYm.fpXmRZara', '010-5583-4412', '1975-01-03', '부산 연제구 거제대로 123', b'1', 'owner', 'F', 'default.jpg', b'0', '부산', '연제구'),
	(29, 'owner10', 'owner10@owner.com', '김윤석', '$2a$10$HPrFMLf9K23omQhOyFrVJeH.V2udkx6NX.hnaqf.HiQGTGpiKBlWO', '010-1234-1125', '1962-04-28', '부산 연제구 거제천로152번길 34', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(34, 'owner11', 'min@min.com', '장사장', '$2a$10$O9kW0IwE6IDwXb3jd//jo.qqTHYKlohD/EERgZs9pZz5HLHZ.V7di', '010-2253-1476', '1967-08-28', '부산 동래구 종합운동장로 29', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '동래구'),
	(35, 'owner12', 'owne12r@owner.com', '김숙희', '$2a$10$mgtbSZOeEWjwph.DJdfFb.bd5IDFyMBrAK8wfxKoIRRFnbOH9TEBS', '010-2153-4534', '1974-08-16', '부산 연제구 거제대로 170', b'1', 'owner', 'F', 'default.jpg', b'0', '부산', '연제구'),
	(36, 'owner13', 'owner12@owner.com', '장희수', '$2a$10$rB1nSX1mmHyq0QqiV5C9UO4if3c3JUD3teb83GNNuGFqbNBylyG.C', '010-4413-1574', '1968-02-19', '부산 연제구 교대로 20-2', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '연제구'),
	(37, 'sajang2', 'owner@owner.com', '김사장', '$2a$10$JlrD31imJmv0bk11EvpAq.nSoPdd8/Itg/vD4hJyPBvRpH5yGbB2m', '010-1234-4321', '1965-05-23', '부산 강서구 가락대로 312', b'1', 'owner', 'M', 'default.jpg', b'0', '부산', '강서구'),
	(38, 'gksals8473', 'gksals8473@naver.com', '윤한민', NULL, NULL, NULL, NULL, b'0', NULL, NULL, 'default.jpg', b'1', NULL, NULL);

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

-- 테이블 데이터 project.user_role:~26 rows (대략적) 내보내기
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(2, 3),
	(3, 1),
	(4, 1),
	(5, 2),
	(6, 1),
	(7, 2),
	(8, 2),
	(9, 2),
	(10, 2),
	(11, 2),
	(12, 1),
	(16, 1),
	(17, 1),
	(22, 1),
	(23, 1),
	(24, 1),
	(25, 2),
	(26, 2),
	(27, 2),
	(28, 2),
	(29, 2),
	(34, 2),
	(35, 2),
	(36, 2),
	(37, 2),
	(38, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
