--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 7.1.13.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 10.06.2016 16:45:04
-- Версия сервера: 5.7.13-log
-- Версия клиента: 4.1
--


--
-- Описание для базы данных address_book
--
CREATE DATABASE IF NOT EXISTS address_book
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установить режим SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Установка кодировки, с использованием которой клиент будет посылать запросы на сервер
--
SET NAMES 'utf8';

-- 
-- Установка базы данных по умолчанию
--
USE address_book;

--
-- Описание для таблицы contact
--
CREATE TABLE IF NOT EXISTS contact (
  contact_id int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  name_full varchar(255) NOT NULL COMMENT 'ФИО',
  phone varchar(255) DEFAULT NULL COMMENT 'Телефон',
  skype varchar(255) DEFAULT NULL COMMENT 'Скайп',
  email varchar(255) DEFAULT NULL COMMENT 'Электронная почта',
  PRIMARY KEY (contact_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 805311523
AVG_ROW_LENGTH = 2730
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = 'Контакты'
ROW_FORMAT = DYNAMIC;

DELIMITER $$

--
-- Описание для процедуры sp_contact_del
--
CREATE DEFINER = 'root'@'localhost'
PROCEDURE sp_contact_del (IN pi_contact_id integer(11))
COMMENT 'Удаление контакта'
BEGIN
  DELETE
    FROM contact
  WHERE contact_id = pi_contact_id;
END
$$

--
-- Описание для процедуры sp_contact_ins
--
CREATE DEFINER = 'root'@'localhost'
PROCEDURE sp_contact_ins (IN pi_contact_id integer(11), IN pi_name_full varchar(255), IN pi_phone varchar(255), IN pi_skype varchar(255), IN pi_email varchar(255))
BEGIN
  INSERT INTO `contact` (`contact_id`,
  `name_full`,
  `phone`,
  `skype`,
  `email`)
    VALUES (pi_contact_id, pi_name_full, pi_phone, pi_skype, pi_email);
END
$$

--
-- Описание для процедуры sp_contact_sel
--
CREATE DEFINER = 'root'@'localhost'
PROCEDURE sp_contact_sel (IN pi_contact_id integer(11), IN pi_name_full varchar(255))
BEGIN
  SELECT
    c.contact_id,
    c.name_full,
    c.phone,
    c.skype,
    c.email
  FROM contact c
  WHERE (pi_contact_id IS NULL
  OR c.contact_id = pi_contact_id)
  AND (pi_name_full IS NULL
  OR c.name_full LIKE pi_name_full)
  ;
END
$$

--
-- Описание для процедуры sp_contact_upd
--
CREATE DEFINER = 'root'@'localhost'
PROCEDURE sp_contact_upd (IN pi_contact_id integer(11), IN pi_name_full varchar(255), IN pi_phone varchar(255), IN pi_skype varchar(255), IN pi_email varchar(255))
COMMENT 'Изменяем контакт'
BEGIN
  UPDATE contact
  SET name_full = pi_name_full,
      phone = pi_phone,
      skype = pi_skype,
      email = pi_email
  WHERE contact_id = pi_contact_id;
END
$$

DELIMITER ;

-- 
-- Вывод данных для таблицы contact
--
INSERT INTO contact VALUES
(1, 'аааааааа', '5555555555', 'ааааааааа', 'qwe@qwe.ua'),
(2, 'bbbbbbbb', '777777777', 'cccccccc', 'abc@abc.com'),
(804994523, 'oooooooo', '4444444', 'yyyyyyyyy', 'j'),
(805311519, 'oooooooo', '9999999999', 'eeeeeeeee', 'yyyyyyy'),
(805311521, 'aaaaaa', 'bbbbbbb', 'ddddddd', 'ccccccc'),
(805311522, 'eeeeeeee', 'eeeeeee', 'vvvvvv', 'vvvvvvvv');

-- 
-- Восстановить предыдущий режим SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;