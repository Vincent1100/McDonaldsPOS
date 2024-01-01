-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema POS
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `POS` ;

-- -----------------------------------------------------
-- Schema POS
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `POS` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `POS` ;

-- -----------------------------------------------------
-- Table `order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `order` (
  `order_id` INT NOT NULL,
  `complete` TINYINT(0) NOT NULL DEFAULT 0,
  `time_stamp` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `item` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `item` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(30) NOT NULL,
  `price` DECIMAL(9,2) NOT NULL,
  `img` VARCHAR(50) NOT NULL DEFAULT '\"noimage\"',
  PRIMARY KEY (`item_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `order_has_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order_has_item` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `order_has_item` (
  `order_order_id` INT NOT NULL,
  `item_item_id` INT NOT NULL,
  `count` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`order_order_id`, `item_item_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `password` INT NOT NULL,
  `time_stamp` DATETIME NOT NULL,
  `is_admin` TINYINT(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
